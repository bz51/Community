package com.community.activity;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.community.thread.PostInfoTask;

public class PostInfoActivity extends Activity {
	private Button sendButton;
	private EditText buildEditText;
	private EditText areaEditText;
	private EditText floorEditText;
	private EditText houseEditText;
	private EditText contentEditText;
	private TextView communityNameTextView;
	private Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_post_info);
		
		findView();
		setListener();
	}

	private void findView() {
		sendButton = (Button) this.findViewById(R.id.postInfo_sendButton);
		buildEditText = (EditText) this.findViewById(R.id.postInfo_buildEditText);
		areaEditText = (EditText) this.findViewById(R.id.postInfo_areaEditText);
		floorEditText = (EditText) this.findViewById(R.id.postInfo_floorEditText);
		houseEditText = (EditText) this.findViewById(R.id.postInfo_houseEditText);
		contentEditText = (EditText) this.findViewById(R.id.postInfo_contentEditText);
		backButton = (Button) this.findViewById(R.id.backButton);
		communityNameTextView = (TextView) this.findViewById(R.id.postInfo_communityNameTextView);
		SharedPreferences sharedPreferences= this.getSharedPreferences("community",this.MODE_PRIVATE); 
		communityNameTextView.setText("当前小区："+sharedPreferences.getString("community_name", ""));
	}
	
	private void setListener() {
		sendButton.setOnClickListener(sendButtonClick);
		backButton.setOnClickListener(backButtonClick);
	}
	
	//点击“发布公告”按钮
	private OnClickListener sendButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(!contentEditText.getText().toString().equals(""))
				new PostInfoTask(PostInfoActivity.this).execute(buildEditText.getText()+"",areaEditText.getText()+"",floorEditText.getText()+"",houseEditText.getText()+"",contentEditText.getText()+"");
			else
				Toast.makeText(PostInfoActivity.this, "内容不能为空！", Toast.LENGTH_LONG).show();
		}
	};
	
	
	//点击“后退”按钮
	private OnClickListener backButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			PostInfoActivity.this.finish();
		}
	};
}
