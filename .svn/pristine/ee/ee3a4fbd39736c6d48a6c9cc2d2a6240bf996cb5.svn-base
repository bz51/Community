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

import com.community.activity.PhoneActivity;
import com.community.core.HttpUtils;
import com.community.core.Parameter;
import com.community.core.ProgressDialogTools;
import com.community.core.URLEcoder;

public class PostPhoneTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private ProgressDialog progressDialog;
	
	public PostPhoneTask(Context context) {
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
		String parameter = "?phoneEntity.community_id="+community_id+"&phoneEntity.text="+URLEcoder.ecode(params[1])+"&phoneEntity.phone="+params[0];
		return HttpUtils.getJsonContent(Parameter.MA_POSTPHONE+parameter);	
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//取消进度条
		progressDialog.dismiss();
		JSONObject object;
		try {
			object = new JSONObject(result);
			String result2 = object.getString("result");
			if(result2.equals("yes")){
				Toast.makeText(context,"发布成功！", Toast.LENGTH_LONG).show();
				Intent intent = new Intent(context,PhoneActivity.class);
				Activity activity = (Activity) context;
				activity.finish();
				context.startActivity(intent);
			}else{
				Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
		}
	}

}
