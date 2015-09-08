package com.community.activity;

import com.community.thread.PostTopicCommentTask;
import com.community.thread.PostTopicTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class PostTopicActivity extends Activity {
	private EditText titleEditText;
	private EditText contentEditText;
	private Button sendButton;
	private Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_post_topic);
		
		findView();
		setListener();
	}
	
	private void findView() {
		titleEditText = (EditText) this.findViewById(R.id.postTopic_titleEditText);
		contentEditText = (EditText) this.findViewById(R.id.postTopic_contentEditText);
		sendButton = (Button) this.findViewById(R.id.postTopic_sendButton);
		backButton = (Button) this.findViewById(R.id.postTopic_backButton);
	}
	
	private void setListener() {
		sendButton.setOnClickListener(sendButtonClick);
		backButton.setOnClickListener(backButtonClick);
	}
	
	//点击“发送”按钮
	private OnClickListener sendButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//获取title和content
			String title = titleEditText.getText().toString();
			String content = contentEditText.getText().toString();
			//发送评论
			if(!title.equals("") && !content.equals(""))
				new PostTopicTask(PostTopicActivity.this).execute(title,content);
			else
				Toast.makeText(PostTopicActivity.this, "内容和标题都不能为空!", Toast.LENGTH_LONG).show();
		}
	};
	
	private OnClickListener backButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			PostTopicActivity.this.finish();
		}
	};
}
