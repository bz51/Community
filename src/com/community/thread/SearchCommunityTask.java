package com.community.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.community.activity.R;
import com.community.activity.SetUserActivity;
import com.community.activity.SigninManagerActivity;
import com.community.core.HttpUtils;
import com.community.core.Pagination;
import com.community.core.Parameter;
import com.community.core.URLEcoder;
import com.community.entity.CommunityEntity;
import com.community.entity.TopicEntity;

public class SearchCommunityTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private ListView listview;
	private Pagination pagination;
	private int lastIndex = 0;
	private TextView loadMoreTextView;
	private ProgressBar progressBar;
	
	public SearchCommunityTask(Context context, ListView listview,Pagination pagination,ProgressBar progressBar,TextView loadMoreTextView) {
		super();
		this.context = context;
		this.listview = listview;
		this.progressBar = progressBar;
		this.loadMoreTextView = loadMoreTextView;
		//初始化pagination
		this.pagination = pagination;
		//滚动条开始转
		progressBar.setVisibility(View.VISIBLE);
		loadMoreTextView.setVisibility(View.GONE);
	}

	@Override
	protected String doInBackground(String... params) {
		//将关键字编码
		String keyword = URLEcoder.ecode(pagination.queryKeyWord);
		String parameter = "?communityEntity.name="+keyword+"&pagination.currPage="+pagination.getCurrPage();
		return HttpUtils.getJsonContent(Parameter.RE_GETCOMMUNITY+parameter);		
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//获取此时listview位置
		lastIndex = listview.getFirstVisiblePosition();
		//将jsonString——>pagination
		pagination = json2Pagination_Community(result);
		//将结果显示到listview
		showInListview(pagination);
		//滚动条消失
		progressBar.setVisibility(View.GONE);
		loadMoreTextView.setVisibility(View.VISIBLE);
	}


	/**
	 * 将jsonString——>pagination
	 * @param result
	 * @return
	 */
	private Pagination json2Pagination_Community(String result) {
		try {
			JSONObject o = new JSONObject(result);
			pagination.setCurrPage(o.getLong("currPage"));
			pagination.setMaxPage(o.getLong("maxPage"));
			pagination.setMaxRowCount(o.getLong("maxRowCount"));
			pagination.setRowsPerPage(o.getInt("rowsPerPage"));
			
			List<CommunityEntity> list = null;
//			if(!pagination.queryKeyWord.equals(""))
//				list = pagination.getData();
			if(list==null)
				list = new ArrayList<CommunityEntity>();
			JSONArray array = new JSONArray(o.getString("data"));
			for(int i=0;i<array.length();i++){
				JSONObject object = array.getJSONObject(i);
				CommunityEntity communityEntity = new CommunityEntity();
				communityEntity.setId(object.getLong("id"));
				communityEntity.setBuildings(object.getInt("buildings"));
				communityEntity.setCity_id(object.getLong("city_id"));
				communityEntity.setName(object.getString("name"));
				communityEntity.setCity(object.getString("city"));
				communityEntity.setResidents(object.getInt("residents"));
				communityEntity.setState(object.getInt("state"));
				list.add(communityEntity);
			}
			pagination.setData(list);
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
		}

		return pagination;
	}
	
	/**
	 * 将结果显示到listview
	 * @param pagination2
	 */
	private void showInListview(Pagination pagination) {
		//创建数据源
		ArrayList<HashMap<String,String>> dataSource =  new ArrayList<HashMap<String,String>>();
		List<CommunityEntity> origin_dataSource = pagination.getData();
		if (origin_dataSource != null) {
			for (CommunityEntity entity : origin_dataSource) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("communityName", entity.getName());
				map.put("communityCity", entity.getCity());
				dataSource.add(map);
			}
			// 创建适配器
			SimpleAdapter listItemAdapter = new SimpleAdapter(context,
					dataSource,// 数据源
					R.layout.listview_select_community,// ListItem的XML实现
					// 动态数组与ImageItem对应的子项
					new String[] { "communityName", "communityCity" },
					// ImageItem的XML文件里面的一个ImageView,两个TextView ID
					new int[] { R.id.selectCommunity_communityNameTextView,
							R.id.selectCommunity_communityLocTextView });
			// 适配器与listview绑定
			listview.setAdapter(listItemAdapter);
			// 添加点击事件
			listview.setOnItemClickListener(listItemClick);
			listview.setSelection(lastIndex);
		}
	}
	
	
	//点击ListItem
	private OnItemClickListener listItemClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			//获取community_id
			long community_id = ((CommunityEntity)pagination.getData().get(position)).getId();
			//将community_id存入share
			SharedPreferences mySharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE);//实例化SharedPreferences对象（第一步）  
			SharedPreferences.Editor editor = mySharedPreferences.edit();//实例化SharedPreferences.Editor对象（第二步）  
			editor.putString("community_id", community_id+""); //用putString的方法保存数据 
			editor.putString("community_name", ((CommunityEntity)pagination.getData().get(position)).getName()); //用putString的方法保存数据 
			editor.commit();//提交当前数据 
			//获取fromWhere
			String fromWhere = ((Activity) context).getIntent().getStringExtra("fromWhere");
			Toast.makeText(context, "fromWhere:"+fromWhere, Toast.LENGTH_LONG).show();
			//从“管理员注册页面”而来
			if(Parameter.FROM_MA_SIGNIN_ACTIVITY.equals(fromWhere)){
				Intent intent = new Intent(context,SigninManagerActivity.class);
				Intent intent_old = ((Activity) context).getIntent();
				intent.putExtra("hasData", true);
				intent.putExtra("username",intent_old.getStringExtra("username"));
				intent.putExtra("password",intent_old.getStringExtra("password"));
				intent.putExtra("community_name",((CommunityEntity)pagination.getData().get(position)).getName());
				((Activity) context).finish();
				context.startActivity(intent);
				return;
			}
			//从“住户注册页面而来”
			Intent intent = new Intent(context,SetUserActivity.class);
			context.startActivity(intent);
		}
		
	};
	
	

}
