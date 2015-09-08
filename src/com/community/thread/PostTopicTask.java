package com.community.thread;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.community.activity.TopicActivity;
import com.community.core.HttpUtils;
import com.community.core.Parameter;
import com.community.core.ProgressDialogTools;
import com.community.core.URLEcoder;

public class PostTopicTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private ProgressDialog progressDialog;
	
	public PostTopicTask(Context context) {
		super();
		this.context = context;
		this.progressDialog = new ProgressDialog(context);
		ProgressDialogTools.getProgressDialog(progressDialog, "", "");
	}

	@Override
	protected String doInBackground(String... params) {
		//获取user_id、username
		SharedPreferences sharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE); 
		String user_id =sharedPreferences.getString("user_id", "0");
		String username =sharedPreferences.getString("username", "");
		String community_id =sharedPreferences.getString("community_id", "0");
		//获取title、content
		String title = params[0];
		String content = params[1];
		//发送
		String parameter = "?topicEntity.content="+URLEcoder.ecode(content)+"&topicEntity.title="+URLEcoder.ecode(title)+"&topicEntity.post_id="+user_id+"&topicEntity.post_username="+username+"&topicEntity.role=0"+"&topicEntity.community_id="+community_id;
		return HttpUtils.getJsonContent(Parameter.RE_POSTTOPICS+parameter);		
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
			Toast.makeText(context, "发布失败！", Toast.LENGTH_LONG).show();
			System.exit(0);
		}
		if(result2.equals("yes")){
			Toast.makeText(context, "发布成功！", Toast.LENGTH_LONG).show();
			Intent intent = new Intent(context,TopicActivity.class);
			context.startActivity(intent);
		}
		else
			Toast.makeText(context, "发布失败！", Toast.LENGTH_LONG).show();
	}
	
}
