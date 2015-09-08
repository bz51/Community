package com.community.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
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
import com.community.entity.CommentEntity;
import com.community.entity.TopicEntity;

public class GetTopicCommentsTask extends AsyncTask<String, Integer, String> {
	private long topic_id;
	private Context context;
	private ListView listview;
	private Pagination pagination;
	private int lastIndex = 0;
	private TextView loadMoreTextView;
	private ProgressBar progressBar;
	
	public GetTopicCommentsTask(Context context, ListView listview,Pagination pagination,ProgressBar progressBar,TextView loadMoreTextView,
			long topic_id) {
		super();
		this.context = context;
		this.listview = listview;
		this.progressBar = progressBar;
		this.loadMoreTextView = loadMoreTextView;
		this.topic_id = topic_id;
		//初始化pagination
		this.pagination = pagination;
		//滚动条开始转
		progressBar.setVisibility(View.VISIBLE);
		loadMoreTextView.setVisibility(View.GONE);
	}

	@Override
	protected String doInBackground(String... params) {
		//发送
		String parameter = "?topicEntity.id="+topic_id+"&pagination.currPage="+pagination.getCurrPage();
		return HttpUtils.getJsonContent(Parameter.RE_GETTOPICCOMMENTS+parameter);		
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//获取此时listview位置
		lastIndex = listview.getFirstVisiblePosition();
		//将json——>Pagination_CommentEntity
		pagination = json2Pagination_CommentEntity(result);
		//将结果显示到listview
		showInListview(pagination);
		//滚动条消失
		progressBar.setVisibility(View.GONE);
		loadMoreTextView.setVisibility(View.VISIBLE);
	}

	/**
	 * 将json——>Pagination_CommentEntity
	 * @param result
	 * @return
	 */
	private Pagination json2Pagination_CommentEntity(String result) {
		try {
			JSONObject o = new JSONObject(result).getJSONObject("pagination");
			pagination.setCurrPage(o.getLong("currPage"));
			pagination.setMaxPage(o.getLong("maxPage"));
			pagination.setMaxRowCount(o.getLong("maxRowCount"));
			pagination.setRowsPerPage(o.getInt("rowsPerPage"));
			
			List<CommentEntity> list = pagination.getData();
			if(list==null)
				list = new ArrayList<CommentEntity>();
			JSONArray array = new JSONArray(o.getString("data"));
			for(int i=0;i<array.length();i++){
				JSONObject object = array.getJSONObject(i);
				CommentEntity commentEntity = new CommentEntity();
				commentEntity.setId(object.getLong("id"));
				commentEntity.setUser_id(object.getLong("user_id"));
				commentEntity.setTopic_id(object.getLong("topic_id"));
				commentEntity.setContent(object.getString("content"));
				commentEntity.setPost_username(object.getString("post_username"));
				commentEntity.setTopic_title(object.getString("topic_title"));
				commentEntity.setRole(object.getInt("role"));
				commentEntity.setState(object.getInt("state"));
				//设置时间
//				commentEntity.setTime();
				list.add(commentEntity);
			}
			pagination.setData(list);
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
		}
		
		return pagination;
	}
	
	
	/**
	 * 将结果显示到listview
	 * @param pagination
	 */
	private void showInListview(Pagination pagination) {
		//创建数据源
		ArrayList<HashMap<String,String>> dataSource =  new ArrayList<HashMap<String,String>>();
		List<CommentEntity> origin_dataSource = pagination.getData();
		if (origin_dataSource != null) {
			for (CommentEntity entity : origin_dataSource) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("commentContent", entity.getContent());
				map.put("commentTime", entity.getTime() + "");
				map.put("commentPostUser", entity.getPost_username());
				dataSource.add(map);
			}
			// 创建适配器
			SimpleAdapter listItemAdapter = new SimpleAdapter(context,
					dataSource,// 数据源
					R.layout.listview_comment,// ListItem的XML实现
					// 动态数组与ImageItem对应的子项
					new String[] { "commentContent", "commentTime",
							"commentPostUser" },
					// ImageItem的XML文件里面的一个ImageView,两个TextView ID
					new int[] { R.id.comment_commentTextView,
							R.id.comment_timeTextView,
							R.id.comment_usernameTextView });
			// 适配器与listview绑定
			listview.setAdapter(listItemAdapter);
			listview.setSelection(lastIndex);
		}
	}

}
