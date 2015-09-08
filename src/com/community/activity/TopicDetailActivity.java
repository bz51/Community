package com.community.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.community.entity.TopicEntity;

public class TopicDetailActivity extends Activity {
	private TopicEntity topicEntity = new TopicEntity();
	private Button commentButton;
	private TextView titleTextView;
	private TextView contentTextView;
	private TextView timeTextView;
	private String time;
	private Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_topic_detail);
		
		findView();
		setListener();
		
		//获取TopicEntity
		getTopicEntity();
		
		//将数据显示到各个组件上
		showInViews();
	}
	
	private void findView() {
		titleTextView = (TextView) this.findViewById(R.id.topicDetail_titleTextView);
		contentTextView = (TextView) this.findViewById(R.id.topicDetail_contentTextView);
		timeTextView = (TextView) this.findViewById(R.id.topicDetail_timeTextView);
		commentButton = (Button) this.findViewById(R.id.topicDetail_commentButton);
		backButton = (Button) this.findViewById(R.id.topicDetail_backButton);
	}
	
	private void setListener() {
		commentButton.setOnClickListener(commentButtonClick);
		backButton.setOnClickListener(backButtonClick);
	}
	
	private OnClickListener backButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			TopicDetailActivity.this.finish();
		}
	};
	
	//点击“评论”按钮
	private OnClickListener commentButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(TopicDetailActivity.this,CommentActivity.class);
			intent.putExtra("topic_id", topicEntity.getId());
			intent.putExtra("topic_title", topicEntity.getTitle());
			TopicDetailActivity.this.startActivity(intent);
		}
	};

	/**
	 * 获取TopicEntity
	 */
	private void getTopicEntity() {
		Intent intent = this.getIntent();
		topicEntity.setCommunity_id(intent.getLongExtra("community_id", 0));
		topicEntity.setContent(intent.getStringExtra("content"));
		topicEntity.setId(intent.getLongExtra("id", 0));
		topicEntity.setPost_id(intent.getLongExtra("post_id", 0));
		topicEntity.setPost_username(intent.getStringExtra("post_username"));
		topicEntity.setRole(intent.getIntExtra("role", 0));
		topicEntity.setTitle(intent.getStringExtra("title"));
		time = intent.getStringExtra("time");
	}
	
	/**
	 * 将数据显示到各个组件上
	 */
	private void showInViews(){
		titleTextView.setText(topicEntity.getTitle());
		contentTextView.setText(topicEntity.getContent());
		timeTextView.setText(time);
	}
}
