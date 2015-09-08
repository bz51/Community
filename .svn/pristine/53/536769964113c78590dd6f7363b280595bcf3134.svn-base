package com.community.thread;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.community.activity.SetUserActivity;
import com.community.activity.UserInfoActivity;
import com.community.core.HttpUtils;
import com.community.core.Parameter;
import com.community.core.ProgressDialogTools;
import com.community.entity.ResidentEntity;

public class GetUserInfoTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private TextView communityTextView;
	private TextView locTextView;
	private TextView nameTextView;
	private TextView phoneTextView;
	private Button modifyButton;
	private ResidentEntity residentEntity;
	private ProgressDialog progressDialog;
	private String community_name;

	public GetUserInfoTask(Context context, TextView communityTextView,
			TextView locTextView, TextView nameTextView, TextView phoneTextView,Button modifyButton) {
		super();
		this.context = context;
		this.communityTextView = communityTextView;
		this.locTextView = locTextView;
		this.nameTextView = nameTextView;
		this.phoneTextView = phoneTextView;
		this.modifyButton = modifyButton;
		//
		modifyButton.setOnClickListener(modifyButtonClick);
		this.progressDialog = new ProgressDialog(context);
		ProgressDialogTools.getProgressDialog(progressDialog, "", "");
	}

	@Override
	protected String doInBackground(String... params) {
		//获取user_id
		SharedPreferences sharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE); 
		String user_id =sharedPreferences.getString("user_id", "");
		community_name =sharedPreferences.getString("community_name", "");
		//发送
		String parameter = "?residentEntity.id="+user_id;
		return HttpUtils.getJsonContent(Parameter.RE_GETUSER+parameter);	
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//取消进度条
		progressDialog.dismiss();
		//将json——>ResidentEntity
		residentEntity = json2ResidentEntity(result);
		//将数据显示到控件上
		showInViews();
	}


	/**
	 * 将json——>ResidentEntity
	 * @param result
	 * @return
	 */
	private ResidentEntity json2ResidentEntity(String result) {
		JSONObject object;
		try {
			object = new JSONObject(result).getJSONObject("residentEntity");
			residentEntity = new ResidentEntity();
			residentEntity.setArea_id(object.getInt("area_id"));
			residentEntity.setBuild_id(object.getInt("build_id"));
			residentEntity.setCommunity_id(object.getLong("community_id"));
			residentEntity.setFloor_id(object.getInt("floor_id"));
			residentEntity.setHouse_id(object.getInt("house_id"));
			residentEntity.setId(object.getLong("id"));
			residentEntity.setIs_owner(object.getInt("is_owner"));
			residentEntity.setName(object.getString("name"));
			residentEntity.setPassword(object.getString("password"));
			residentEntity.setPhone(object.getString("phone"));
			residentEntity.setState(object.getInt("state"));
			residentEntity.setUsername(object.getString("username"));
//			residentEntity.setTime();
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
		}
		return residentEntity;
	}
	
	
	
	/**
	 * 将数据显示到控件上
	 */
	private void showInViews() {
		if (residentEntity != null) {
			communityTextView.setText(community_name);
			locTextView.setText(residentEntity.getBuild_id() + "栋"
					+ residentEntity.getArea_id() + "单元"
					+ residentEntity.getFloor_id() + "层"
					+ residentEntity.getHouse_id() + "号");
			nameTextView.setText(residentEntity.getName());
			phoneTextView.setText(residentEntity.getPhone());
		}
	}
	
	//点击“修改”按钮
	private OnClickListener modifyButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if (residentEntity != null) {
				Intent intent = new Intent(context, SetUserActivity.class);
				intent.putExtra("hasDate", true);
				intent.putExtra("area_id", residentEntity.getArea_id());
				intent.putExtra("build_id", residentEntity.getBuild_id());
				intent.putExtra("floor_id", residentEntity.getFloor_id());
				intent.putExtra("house_id", residentEntity.getHouse_id());
				intent.putExtra("is_owner", residentEntity.getIs_owner());
				intent.putExtra("name", residentEntity.getName());
				intent.putExtra("phone", residentEntity.getPhone());
				((Activity) context).finish();
				context.startActivity(intent);
			}
		}
	};
}
