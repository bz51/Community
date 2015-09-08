package com.community.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.community.activity.MyListView.OnRefreshListener;
import com.community.thread.GetInfoTask;
import com.community.thread.GetPhoneTask;

public class PhoneActivity extends Activity {
	private Button backButton;
	private Button postPhoneButton;
	private TextView textTextView;
	private TextView phoneTextView;
	private MyListView listView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_phone);
		
		findView();
		setListener();
		
		new GetPhoneTask(this,listView).execute();
//		new PostInfoTask(PostInfoActivity.this).execute(buildEditText.getText()+"",areaEditText.getText()+"",floorEditText.getText()+"",houseEditText.getText()+"",contentEditText.getText()+"");
	}
	
	private void findView() {
		backButton = (Button) this.findViewById(R.id.phone_backButton);
		postPhoneButton = (Button) this.findViewById(R.id.phone_postPhoneButton);
		SharedPreferences sharedPreferences= this.getSharedPreferences("community",this.MODE_PRIVATE); 
		String role = sharedPreferences.getString("role", "");
		if(role.equals("0"))
			postPhoneButton.setVisibility(View.GONE);
		else
			postPhoneButton.setVisibility(View.VISIBLE);
		textTextView = (TextView) this.findViewById(R.id.phone_textTextView);
		phoneTextView = (TextView) this.findViewById(R.id.phone_phoneTextView);
		listView = (MyListView) this.findViewById(R.id.phone_listView);
		listView.setonRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new GetPhoneTask(PhoneActivity.this,listView).execute();
			}
		});
	}
	
	private void setListener() {
		backButton.setOnClickListener(backButtonClick);
		postPhoneButton.setOnClickListener(postPhoneButtonClick);
	}
	
	//点击“后退”按钮
	private OnClickListener backButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			PhoneActivity.this.finish();
		}
	};
	
	//点击“添加”按钮
	private OnClickListener postPhoneButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), PostPhoneActivity.class);
			PhoneActivity.this.finish();
			startActivity(intent);
		}
	};
}
