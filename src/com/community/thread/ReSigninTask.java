package com.community.thread;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.community.activity.SelectCommunityActivity;
import com.community.core.HttpUtils;
import com.community.core.Parameter;
import com.community.core.ProgressDialogTools;

public class ReSigninTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private ProgressDialog progressDialog;
	
	public ReSigninTask(Context context) {
		super();
		this.context = context;
		this.progressDialog = new ProgressDialog(context);
		ProgressDialogTools.getProgressDialog(progressDialog, "", "");
	}

	@Override
	protected String doInBackground(String... params) {
		String parameter = "?residentEntity.username="+params[0]+"&residentEntity.password="+params[1];
		return HttpUtils.getJsonContent(Parameter.RE_SIGNIN+parameter);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//取消进度条
		progressDialog.dismiss();
		try {
			JSONObject json = new JSONObject(result);
			String user_id = json.getString("user_id");
			String username = json.getString("username");
			//将user_id存入share
			SharedPreferences mySharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE);//实例化SharedPreferences对象（第一步）  
			SharedPreferences.Editor editor = mySharedPreferences.edit();//实例化SharedPreferences.Editor对象（第二步）  
			editor.putString("user_id", user_id); //用putString的方法保存数据 
			editor.putString("username", username); //用putString的方法保存数据 
			editor.putString("role", "0"); //用putString的方法保存数据 
			editor.commit();//提交当前数据 
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
		}
		
		//跳转
		Intent intent = new Intent(this.context,SelectCommunityActivity.class);
		this.context.startActivity(intent);
	}

	
}
