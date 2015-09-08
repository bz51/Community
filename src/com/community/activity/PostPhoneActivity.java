package com.community.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.community.thread.PostPhoneTask;

public class PostPhoneActivity extends Activity {
	private Button backButton;
	private Button postButton;
	private EditText phoneEditText;
	private EditText textEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_post_phone);

		findView();
		setListener();
	}
	
	private void findView() {
		backButton = (Button) this.findViewById(R.id.postPhone_backButton);
		postButton = (Button) this.findViewById(R.id.postPhone_submitButton);
		phoneEditText = (EditText) this.findViewById(R.id.postPhone_phoneEditText);
		textEditText = (EditText) this.findViewById(R.id.postPhone_textEditText);
	}
	
	private void setListener() {
		backButton.setOnClickListener(backButtonClick);
		postButton.setOnClickListener(postButtonClick);
	}
	
	//��������ˡ���ť
	private OnClickListener backButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			PostPhoneActivity.this.finish();
		}
	};
	
	//�������������ť
	private OnClickListener postButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String phone = phoneEditText.getText().toString();
			String text = textEditText.getText().toString();
			if(phone.equals("") || text.equals(""))
				Toast.makeText(PostPhoneActivity.this, "���ݲ���Ϊ�գ�", Toast.LENGTH_LONG).show();
			else
				new PostPhoneTask(PostPhoneActivity.this).execute(phone,text);
		}
	};
}
