package com.community.thread;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.community.core.HttpUtils;
import com.community.core.Parameter;
import com.community.core.ProgressDialogTools;

public class AuthorizeTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private Button authorizeButton;
	private Button refuseButton;
	private TextView successTextView;
	private ProgressDialog progressDialog;
	
	public AuthorizeTask(Context context, Button authorizeButton,
			Button refuseButton, TextView successTextView) {
		super();
		this.context = context;
		this.authorizeButton = authorizeButton;
		this.refuseButton = refuseButton;
		this.successTextView = successTextView;
		this.progressDialog = new ProgressDialog(context);
		ProgressDialogTools.getProgressDialog(progressDialog, "", "");
	}

	@Override
	protected String doInBackground(String... params) {
		//发送
		String parameter = "?managerEntity.id="+params[0];
		return HttpUtils.getJsonContent(Parameter.MA_AUTHORIZE+parameter);	
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
				authorizeButton.setVisibility(View.GONE);
				refuseButton.setVisibility(View.GONE);
				successTextView.setVisibility(View.VISIBLE);
			}else{
					Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
			}
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
		}
	}

}
