package com.community.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.community.thread.GetUserInfoTask;

public class UserInfoActivity extends Activity {
	private TextView communityTextView;
	private TextView locTextView;
	private TextView nameTextView;
	private TextView phoneTextView;
	private Button modifyButton;
	private Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_info);
		
		findView();
		setListener();
		
		//获取当前用户信息
		new GetUserInfoTask(this,communityTextView,locTextView,nameTextView,phoneTextView,modifyButton).execute();
	}
	
	private void findView() {
		modifyButton = (Button) this.findViewById(R.id.userInfo_modifyButton);
		backButton = (Button) this.findViewById(R.id.userInfo_backButton);
		communityTextView = (TextView) this.findViewById(R.id.userInfo_communityTextView);
		locTextView = (TextView) this.findViewById(R.id.userInfo_locTextView);
		nameTextView = (TextView) this.findViewById(R.id.userInfo_nameTextView);
		phoneTextView = (TextView) this.findViewById(R.id.userInfo_phoneTextView);
	}
	
	private void setListener() {
		backButton.setOnClickListener(backButtonClick);
	}
	
	private OnClickListener backButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			UserInfoActivity.this.finish();
		}
	};
	

}
