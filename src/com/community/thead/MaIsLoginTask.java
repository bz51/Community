package com.community.thead;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.community.core.HttpUtils;
import com.community.core.Parameter;

public class MaIsLoginTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private TextView resultUserTextView;
	private Button signinButton;
	
	public MaIsLoginTask(Context context,TextView resultTextView, Button signinButton){
		this.context = context;
		this.resultUserTextView = resultTextView;
		this.signinButton = signinButton;
	}
	
	
	@Override
	protected String doInBackground(String... params) {
		String username = params[0];
		//�ж�username�Ƿ��ѱ�ע��
		String parameter = "?managerEntity.username="+username;
		return HttpUtils.getJsonContent(Parameter.MA_ISLOGIN+parameter);
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		try {
			JSONObject json = new JSONObject(result);
			String jsonResult = json.getString("result");
			//��username�ѱ�ע��
			if(jsonResult.equals("no")){
				resultUserTextView.setText("�ѱ�ע�ᣡ");
				signinButton.setClickable(false);
			}
			else{
				resultUserTextView.setText("����ʹ�ã�");
				signinButton.setClickable(true);
			}
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
		}	
	}

	
}
