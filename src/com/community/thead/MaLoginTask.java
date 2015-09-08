package com.community.thead;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.widget.Toast;

import com.community.activity.MainManagerActivity;
import com.community.core.HttpUtils;
import com.community.core.Parameter;
import com.community.core.ProgressDialogTools;

/**
 * ����Ա��½
 * @author Administrator
 *
 */
public class MaLoginTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private ProgressDialog progressDialog;
	
	public MaLoginTask(Context context){
		this.context = context;
		this.progressDialog = new ProgressDialog(context);
		ProgressDialogTools.getProgressDialog(progressDialog, "", "");
	}
	
	@Override
	protected String doInBackground(String... params) {
		String parameter = "?managerEntity.username="+params[0]+"&managerEntity.password="+params[1];
		return HttpUtils.getJsonContent(Parameter.MA_LOGIN+parameter);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//ȡ��������
		progressDialog.dismiss();
		//����json
		try {
			JSONObject json = new JSONObject(result);
			String user_id = json.getString("user_id");
			String community_id = json.getString("community_id");
			String community_name = json.getString("community_name");
			String username = json.getString("username");
			String is_super = json.getString("is_super");
			//��user_id��community_id����share
			SharedPreferences mySharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE);//ʵ����SharedPreferences���󣨵�һ����  
			SharedPreferences.Editor editor = mySharedPreferences.edit();//ʵ����SharedPreferences.Editor���󣨵ڶ�����  
			editor.putString("user_id", user_id); //��putString�ķ����������� 
			editor.putString("community_id", community_id); 
			editor.putString("community_name", community_name); 
			editor.putString("username", username); 
			editor.putString("is_super", is_super); 
			editor.putString("role", "1"); //��putString�ķ����������� 
			editor.commit();//�ύ��ǰ���� 
		} catch (JSONException e) {
			//��½ʧ��
			Toast.makeText(context, result, Toast.LENGTH_LONG).show();
			return;
		}
		
		//��ת
		Intent intent = new Intent(this.context,MainManagerActivity.class);
		this.context.startActivity(intent);
		
	}


}
