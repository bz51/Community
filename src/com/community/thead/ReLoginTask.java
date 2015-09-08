package com.community.thead;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.community.activity.MainActivity;
import com.community.core.HttpUtils;
import com.community.core.Parameter;
import com.community.core.ProgressDialogTools;

/**
 * 用户登陆
 * @author Administrator
 *
 */
public class ReLoginTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private ProgressDialog progressDialog;
	
	public ReLoginTask(Context context){
		this.context = context;
		this.progressDialog = new ProgressDialog(context);
		ProgressDialogTools.getProgressDialog(progressDialog, "", "");
	}
	
	@Override
	protected String doInBackground(String... params) {
		String parameter = "?residentEntity.username="+params[0]+"&residentEntity.password="+params[1];
		return HttpUtils.getJsonContent(Parameter.RE_LOGIN+parameter);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//取消进度条
		progressDialog.dismiss();
		//解析json
		try {
			JSONObject json = new JSONObject(result);
			String user_id = json.getString("user_id");
			String community_id = json.getString("community_id");
			String username = json.getString("username");
			String community_name = json.getString("community_name");
			//将user_id、community_id存入share
			SharedPreferences mySharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE);//实例化SharedPreferences对象（第一步）  
			SharedPreferences.Editor editor = mySharedPreferences.edit();//实例化SharedPreferences.Editor对象（第二步）  
			editor.putString("user_id", user_id); //用putString的方法保存数据 
			editor.putString("community_id", community_id); 
			editor.putString("community_name", community_name); 
			editor.putString("username", username);
			editor.putString("role", "0"); //用putString的方法保存数据 
			editor.commit();//提交当前数据 
		} catch (JSONException e) {
			//登陆失败
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
			return;
		}
		
		//跳转
		Intent intent = new Intent(this.context,MainActivity.class);
		this.context.startActivity(intent);
		
	}


}
