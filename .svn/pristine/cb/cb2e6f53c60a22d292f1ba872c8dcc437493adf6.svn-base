package com.community.thread;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.community.activity.R;
import com.community.core.HttpUtils;
import com.community.core.Pagination;
import com.community.core.Parameter;
import com.community.core.Utils;
import com.community.entity.CommentEntity;
import com.community.entity.InfoEntity;
import com.community.entity.TopicEntity;

public class GetMaInfoTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private ListView listview;
	private Pagination pagination;
	private int lastIndex = 0;
	private TextView loadMoreTextView;
	private ProgressBar progressBar;
	
	public GetMaInfoTask(Context context, ListView listview,Pagination pagination,ProgressBar progressBar,TextView loadMoreTextView) {
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
		//获取user_id、community_id
		SharedPreferences sharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE); 
		String user_id =sharedPreferences.getString("user_id", ""); 
		String community_id =sharedPreferences.getString("community_id", ""); 
		//发送
		String parameter = "?infoEntity.community_id="+community_id+"&infoEntity.post_id="+user_id+"&pagination.currPage="+pagination.getCurrPage();
		return HttpUtils.getJsonContent(Parameter.MA_GETINFOS+parameter);		
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//获取此时listview位置
		lastIndex = listview.getFirstVisiblePosition();
		//将json——>pagination
		pagination = json2Pagination_InfoEntity(result);
		//将结果显示到listview
		showInListview(pagination);
		//滚动条消失
		progressBar.setVisibility(View.GONE);
		loadMoreTextView.setVisibility(View.VISIBLE);
	}

	/**
	 * json——>List<InfoEntity>
	 * @param result
	 * @return
	 */
	private Pagination json2Pagination_InfoEntity(String result) {
		try {
			JSONObject object = new JSONObject(result).getJSONObject("pagination");
			pagination.setCurrPage(object.getLong("currPage"));
			pagination.setMaxPage(object.getLong("maxPage"));
			pagination.setMaxRowCount(object.getLong("maxRowCount"));
			pagination.setRowsPerPage(object.getInt("rowsPerPage"));

			List<InfoEntity> list = pagination.getData();
			if(list==null)
				list = new ArrayList<InfoEntity>();
			JSONArray array = new JSONArray(object.getString("data"));
			for(int i = 0 ; i < array.length() ; i++){
				InfoEntity entity = new InfoEntity();
				JSONObject o = array.getJSONObject(i);
				entity.setArea_id(o.getInt("area_id"));
				entity.setBuilding_id(o.getInt("building_id"));
				entity.setCommunity_id(o.getInt("community_id"));
				entity.setContent(o.getString("content"));
				entity.setFloor_id(o.getInt("floor_id"));
				entity.setHouse_id(o.getInt("house_id"));
				entity.setId(o.getLong("id"));
				entity.setPost_id(o.getLong("post_id"));
				//获取时间
				String time = o.getString("time");
				//将json——>Timestamp
				entity.setTime(Utils.string2Timestamp(o.getString("time")));
				//当前InfoEntity放入list
				list.add(entity);
			}
			pagination.setData(list);
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
		}
		return pagination;
	}
	
	

	/**
	 * 
	 * @param pagination
	 */
	private void showInListview(Pagination pagination) {
		//创建数据源
		ArrayList<HashMap<String,String>> dataSource =  new ArrayList<HashMap<String,String>>();
		List<InfoEntity> origin_dataSource = pagination.getData();
		if (origin_dataSource != null) {
			for (InfoEntity entity : origin_dataSource) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("infoContent", entity.getContent());
				map.put("infoTime", Utils.timestamp2String(entity.getTime()));
				dataSource.add(map);
			}
			// 创建适配器
			SimpleAdapter listItemAdapter = new SimpleAdapter(context,
					dataSource,// 数据源
					R.layout.listview_info,// ListItem的XML实现
					// 动态数组与ImageItem对应的子项
					new String[] { "infoContent", "infoTime" },
					// ImageItem的XML文件里面的一个ImageView,两个TextView ID
					new int[] { R.id.info_contentTextView,
							R.id.info_timeTextView });
			// 适配器与listview绑定
			listview.setAdapter(listItemAdapter);
			listview.setSelection(lastIndex);
		}
	}
	

}
