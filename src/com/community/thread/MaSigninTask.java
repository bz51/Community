package com.community.thread;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.community.activity.MaSigninSuccessActivity;
import com.community.activity.MainManagerActivity;
import com.community.core.HttpUtils;
import com.community.core.Parameter;
import com.community.core.ProgressDialogTools;

public class MaSigninTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private ProgressDialog progressDialog;
	
	public MaSigninTask(Context context) {
		super();
		this.context = context;
		this.progressDialog = new ProgressDialog(context);
		ProgressDialogTools.getProgressDialog(progressDialog, "", "");
	}

	@Override
	protected String doInBackground(String... params) {
		//获取community_id
		SharedPreferences sharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE); 
		String community_id =sharedPreferences.getString("community_id", "0");
		//发送
		String parameter = "?managerEntity.username="+params[0]+"&managerEntity.password="+params[1]+"&managerEntity.community_id="+community_id;
		return HttpUtils.getJsonContent(Parameter.MA_SIGNIN+parameter);
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
			editor.putString("role", "1"); //用putString的方法保存数据 
			editor.commit();//提交当前数据 
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
		}
		
		//跳转
		Toast.makeText(context, "注册成功！", Toast.LENGTH_LONG).show();
		Intent intent = new Intent(this.context,MainManagerActivity.class);
		this.context.startActivity(intent);
	}

	
}
