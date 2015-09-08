package com.community.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.community.activity.MyListView;
import com.community.activity.R;
import com.community.activity.TopicDetailActivity;
import com.community.core.HttpUtils;
import com.community.core.Pagination;
import com.community.core.Parameter;
import com.community.core.Utils;
import com.community.entity.TopicEntity;

public class GetMyPostTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private MyListView listview;
	private Pagination pagination;
	private int lastIndex = 0;
	private TextView loadMoreTextView;
	private ProgressBar progressBar;
	
	public GetMyPostTask(Context context, MyListView listview,Pagination pagination,ProgressBar progressBar,TextView loadMoreTextView) {
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
		//获取user_id
		SharedPreferences sharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE); 
		String user_id =sharedPreferences.getString("user_id", "");
		//发送
		String parameter = "?topicEntity.post_id="+user_id+"&pagination.currPage="+pagination.getCurrPage();
		return HttpUtils.getJsonContent(Parameter.RE_GETMYTOPICS+parameter);	
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//获取此时listview位置
		lastIndex = listview.getFirstVisiblePosition();
		// 将json——>pagination_TopicEntity
		pagination = json2Pagination_TopicEntity(result);
		// 将结果显示到listview
		showInListview(pagination);
		//滚动条消失
		progressBar.setVisibility(View.GONE);
		listview.onRefreshComplete();
		loadMoreTextView.setVisibility(View.VISIBLE);
	}
	
	/**
	 * 将json——>pagination_TopicEntity
	 * @param result
	 * @return
	 */
	private Pagination json2Pagination_TopicEntity(String result) {
		try {
			JSONObject o = new JSONObject(result).getJSONObject("pagination");
			pagination.setCurrPage(o.getLong("currPage"));
			pagination.setMaxPage(o.getLong("maxPage"));
			pagination.setMaxRowCount(o.getLong("maxRowCount"));
			pagination.setRowsPerPage(o.getInt("rowsPerPage"));
			
//			List<TopicEntity> list = new ArrayList<TopicEntity>();
			List<TopicEntity> list = pagination.getData();
			if(list==null)
				list = new ArrayList<TopicEntity>();
			JSONArray array = new JSONArray(o.getString("data"));
			for(int i=0;i<array.length();i++){
				JSONObject object = array.getJSONObject(i);
				TopicEntity topicEntity = new TopicEntity();
				topicEntity.setId(object.getLong("id"));
				topicEntity.setPost_id(object.getLong("post_id"));
				topicEntity.setCommunity_id(object.getLong("community_id"));
				topicEntity.setContent(object.getString("content"));
				topicEntity.setPost_username(object.getString("post_username"));
				topicEntity.setTitle(object.getString("title"));
				topicEntity.setRole(object.getInt("role"));
				topicEntity.setState(object.getInt("state"));
				//设置时间
				topicEntity.setTime(Utils.string2Timestamp(object.getString("time")));
				list.add(topicEntity);
			}
			pagination.setData(list);
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
		}
		
		return pagination;
	}
	
	private void showInListview(Pagination pagination) {
		//创建数据源
		ArrayList<HashMap<String,String>> dataSource =  new ArrayList<HashMap<String,String>>();
		List<TopicEntity> origin_dataSource = pagination.getData();
		if (origin_dataSource != null) {
			for (TopicEntity entity : origin_dataSource) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("topicTitle", entity.getTitle());
				map.put("topicPostUsername", entity.getPost_username());
				map.put("topicTime", Utils.timestamp2String(entity.getTime()));
				dataSource.add(map);
			}
			// 创建适配器
			SimpleAdapter listItemAdapter = new SimpleAdapter(context,
					dataSource,// 数据源
					R.layout.listview_topic,// ListItem的XML实现
					// 动态数组与ImageItem对应的子项
					new String[] { "topicTitle", "topicPostUsername",
							"topicTime" },
					// ImageItem的XML文件里面的一个ImageView,两个TextView ID
					new int[] { R.id.topic_titleTextView,
							R.id.topic_postUsernameTextView,
							R.id.topic_timeTextView });
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
			//获取所选帖子
			TopicEntity topicEntity = (TopicEntity) pagination.getData().get(position);
			//跳转
			Intent intent = new Intent(context,TopicDetailActivity.class);
			intent.putExtra("id",topicEntity.getId());
			intent.putExtra("content",topicEntity.getContent());
			intent.putExtra("community_id",topicEntity.getCommunity_id());
			intent.putExtra("post_id",topicEntity.getPost_id());
			intent.putExtra("post_username",topicEntity.getPost_username());
			intent.putExtra("role",topicEntity.getRole());
			intent.putExtra("title",topicEntity.getTitle());
			intent.putExtra("time",topicEntity.getTime());
			context.startActivity(intent);
		}
		
	};
}
