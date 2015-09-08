package com.community.thread;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.community.activity.CommentActivity;
import com.community.core.HttpUtils;
import com.community.core.Parameter;
import com.community.core.ProgressDialogTools;
import com.community.core.URLEcoder;

public class PostTopicCommentTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private long topic_id;
	private String topic_title;
	private ProgressDialog progressDialog;
	
	public PostTopicCommentTask(Context context) {
		super();
		this.context = context;
		this.progressDialog = new ProgressDialog(context);
		ProgressDialogTools.getProgressDialog(progressDialog, "", "");
	}

	@Override
	protected String doInBackground(String... params) {
		//获取topic_title、topic_id
		topic_id = Long.parseLong(params[2]);
		topic_title = params[1];
		//获取user_id、username
		SharedPreferences sharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE); 
		String user_id =sharedPreferences.getString("user_id", "");
		String username =sharedPreferences.getString("username", "");
		//发送
		String parameter = "?commentEntity.content="+URLEcoder.ecode(params[0])+"&commentEntity.user_id="+user_id+"&commentEntity.post_username="+username+"&commentEntity.role=0&commentEntity.topic_title="+URLEcoder.ecode(topic_title)+"&commentEntity.topic_id="+topic_id;
		return HttpUtils.getJsonContent(Parameter.RE_POSTTOPICCOMMENT+parameter);	
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//取消进度条
		progressDialog.dismiss();
		String result2 = "";
		try {
			JSONObject object = new JSONObject(result);
			result2 = object.getString("result");
		} catch (JSONException e) {
			Toast.makeText(context, "评论失败！", Toast.LENGTH_LONG).show();
			System.exit(0);
		}
		if(result2.equals("yes")){
			Toast.makeText(context, "评论成功！", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(context,CommentActivity.class);
			intent.putExtra("topic_id", topic_id);
			intent.putExtra("topic_title", topic_title);
			((Activity) context).finish();
			context.startActivity(intent);
		}
		else
			Toast.makeText(context, "评论失败！", Toast.LENGTH_LONG).show();
	}
	
	

}
