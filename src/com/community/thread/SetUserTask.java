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

import com.community.activity.UserInfoActivity;
import com.community.core.HttpUtils;
import com.community.core.Parameter;
import com.community.core.ProgressDialogTools;
import com.community.core.URLEcoder;

public class SetUserTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private ProgressDialog progressDialog;
	
	public SetUserTask(Context context) {
		super();
		this.context = context;
		this.progressDialog = new ProgressDialog(context);
		ProgressDialogTools.getProgressDialog(progressDialog, "", "");
	}

	@Override
	protected String doInBackground(String... params) {
		//获取各个参数
		String build = params[0];
		String area = params[1];
		String floor = params[2];
		String house = params[3];
		String name = URLEcoder.ecode(params[4]);
		String phone = params[5];
		String is_owner = params[6];
		//获取当前用户id
		SharedPreferences sharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE); 
		String user_id =sharedPreferences.getString("user_id", "");
		String community_id =sharedPreferences.getString("community_id", "");
		//发送
		String parameter = "?residentEntity.id="+user_id+"&residentEntity.community_id="+community_id+"&residentEntity.build_id="+build
				+"&residentEntity.area_id="+area+"&residentEntity.floor_id="+floor+"&residentEntity.house_id="+house
				+"&residentEntity.name="+name+"&residentEntity.phone="+phone+"&residentEntity.is_owner="+is_owner;
		return HttpUtils.getJsonContent(Parameter.RE_SETUSER+parameter);	
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//取消进度条
		progressDialog.dismiss();
		JSONObject object;
		String community = "default";
		try {
			object = new JSONObject(result);
			community = object.getString("community_id");
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
		}
		//获取本地community_id
		SharedPreferences sharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE); 
		String community_id =sharedPreferences.getString("community_id", "");
		//本地community_id==服务器community_id，则成功！
		if(community_id.equals(community)){
			Intent intent = new Intent(context,UserInfoActivity.class);
			((Activity) context).finish();
			context.startActivity(intent);
		}
	}

	
}
