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

import com.community.activity.MaInfoActivity;
import com.community.core.HttpUtils;
import com.community.core.Parameter;
import com.community.core.ProgressDialogTools;
import com.community.core.URLEcoder;

public class PostInfoTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private ProgressDialog progressDialog;
	
	public PostInfoTask(Context context) {
		super();
		this.context = context;
		this.progressDialog = new ProgressDialog(context);
		ProgressDialogTools.getProgressDialog(progressDialog, "", "");
	}

	@Override
	protected String doInBackground(String... params) {
		//获取门牌号，并将门牌号中为空的都设为默认值0！
		String build = params[0].equals("")?"0":params[0];
		String area = params[1].equals("")?"0":params[1];
		String floor = params[2].equals("")?"0":params[2];
		String house = params[3].equals("")?"0":params[3];
		String content = params[4];
		
		//获取community_id
		SharedPreferences sharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE); 
		String community_id =sharedPreferences.getString("community_id", "0");
		String user_id =sharedPreferences.getString("user_id", "0");
		//发送
		String parameter = "?infoEntity.content="+URLEcoder.ecode(content)+"&infoEntity.post_id="+user_id+"&infoEntity.building_id="+build+"&infoEntity.area_id="+area+"&infoEntity.floor_id="+floor+"&infoEntity.house_id="+house+"&infoEntity.community_id="+community_id;
		return HttpUtils.getJsonContent(Parameter.MA_POSTINFO+parameter);	
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
				Intent intent = new Intent(context,MaInfoActivity.class);
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
