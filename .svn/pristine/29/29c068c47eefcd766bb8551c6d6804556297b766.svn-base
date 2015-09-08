package com.community.thread;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.Toast;

import com.community.activity.TopicDetailActivity;
import com.community.core.HttpUtils;
import com.community.core.Parameter;
import com.community.core.ProgressDialogTools;
import com.community.entity.TopicEntity;

public class GetTopicByIdTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private TopicEntity topicEntity;
	private ProgressDialog progressDialog;
	
	public GetTopicByIdTask(Context context) {
		super();
		this.context = context;
		this.progressDialog = new ProgressDialog(context);
		ProgressDialogTools.getProgressDialog(progressDialog, "", "");
	}

	@Override
	protected String doInBackground(String... params) {
		//获取topic_id
		String topic_id = params[0];
		//发送
		String parameter = "?topicEntity.id="+topic_id;
		return HttpUtils.getJsonContent(Parameter.RE_GETTOPICBYID+parameter);	
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//取消进度条
		progressDialog.dismiss();
		//将json——>TopicEntity
		topicEntity = json2TopicEntity(result);
		//跳转
		Intent intent = new Intent(context,TopicDetailActivity.class);
		intent.putExtra("community_id", topicEntity.getCommunity_id());
		intent.putExtra("content", topicEntity.getContent());
		intent.putExtra("id", topicEntity.getId());
		intent.putExtra("post_id", topicEntity.getPost_id());
		intent.putExtra("post_username", topicEntity.getPost_username());
		intent.putExtra("role", topicEntity.getRole());
		intent.putExtra("title", topicEntity.getTitle());
		context.startActivity(intent);
	}

	/**
	 * 将json——>TopicEntity
	 * @param result
	 * @return
	 */
	private TopicEntity json2TopicEntity(String result) {
		JSONObject object;
		try {
			object = new JSONObject(result).getJSONObject("topicEntity");
			topicEntity = new TopicEntity();
			topicEntity.setCommunity_id(object.getLong("community_id"));
			topicEntity.setContent(object.getString("content"));
			topicEntity.setId(object.getLong("id"));
			topicEntity.setPost_id(object.getLong("post_id"));
			topicEntity.setPost_username(object.getString("post_username"));
			topicEntity.setRole(object.getInt("role"));
			topicEntity.setTitle(object.getString("title"));
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
		}
		return topicEntity;
	}

}
