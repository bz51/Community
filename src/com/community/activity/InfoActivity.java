package com.community.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.community.activity.MyListView.OnRefreshListener;
import com.community.thread.GetInfoTask;

public class InfoActivity extends Activity {
	private MyListView listview;
	private Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_info);
		
		findView();
		setListener();
		
		//获取公告
		new GetInfoTask(this,listview).execute();
	}
	
	private void findView() {
		listview = (MyListView) this.findViewById(R.id.info_listView);
		backButton = (Button) this.findViewById(R.id.info_backButton);
	}
	
	private void setListener() {
		backButton.setOnClickListener(backButtonClick);
		listview.setonRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new GetInfoTask(InfoActivity.this,listview).execute();
			}
		});
	}
	
	private OnClickListener backButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			InfoActivity.this.finish();
		}
	};
	
	//点击“上页”按钮
	private OnClickListener preButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
		}
	};
	
	//点击“下页”按钮
	private OnClickListener nextButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
		}
	};
}
