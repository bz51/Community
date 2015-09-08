package com.community.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.community.activity.MyListView.OnRefreshListener;
import com.community.core.Pagination;
import com.community.thread.GetMyPostTask;
import com.community.thread.GetTopicsTask;

public class TopicActivity extends Activity implements OnScrollListener{
	private MyListView listview;
	private Button postTopicButton;
	private Button myTopicButton;
	private Button backButton;
	//------------------------------------------------------------------------------------------------------
	private TextView loadMoreTextView;
	private ProgressBar progressBar;
	private View footerView;
	private int visibleLastIndex = 0;   //最后的可视项索引 
    private int visibleItemCount;       // 当前窗口可见项总数 
    private int totalItemCount;       // listview中item总个数
    private Pagination pagination = new Pagination();
    private ArrayList<HashMap<String,String>> dataSource =  new ArrayList<HashMap<String,String>>();//创建数据源
  //------------------------------------------------------------------------------------------------------
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_topic);
		
		findView();
		setListener();
		
		//获取本小区论坛
		//------------------------------------------------------------------------------------------------------
		new GetTopicsTask(this,listview,pagination,progressBar,loadMoreTextView).execute();
		//------------------------------------------------------------------------------------------------------
	}
	
	private void findView() {
		listview = (MyListView) this.findViewById(R.id.topic_listView);
		postTopicButton = (Button) this.findViewById(R.id.topic_postTopicButton);
		myTopicButton = (Button) this.findViewById(R.id.topic_myTopicButton);
		backButton = (Button) this.findViewById(R.id.topic_backButton);
		//------------------------------------------------------------------------------------------------------
		//获取footer
		footerView = getLayoutInflater().inflate(R.layout.footer_listview, null);
		listview.addFooterView(footerView);
		loadMoreTextView = (TextView) footerView.findViewById(R.id.listfooter_loadMoreTextView);
		progressBar = (ProgressBar) footerView.findViewById(R.id.listfooter_loadProgressBar);
		//------------------------------------------------------------------------------------------------------
	}
	
	private void setListener() {
		postTopicButton.setOnClickListener(postTopicButtonClick);
		myTopicButton.setOnClickListener(myTopicButtonClick);
		//------------------------------------------------------------------------------------------------------
		listview.setOnScrollListener(this);
		listview.setonRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new GetTopicsTask(TopicActivity.this,listview,pagination,progressBar,loadMoreTextView).execute();
			}
		});
		//------------------------------------------------------------------------------------------------------
		backButton.setOnClickListener(backButtonClick);
	}
	
	private OnClickListener backButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			TopicActivity.this.finish();
		}
	};
	
//	//点击“上页”按钮
//	private OnClickListener preButtonClick = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			//获取本小区论坛
//			currPage--;
//			new GetTopicsTask(TopicActivity.this,listview,currPage,paginationTextView,preButton,nextButton,pagination).execute();
//		}
//	};
//	
//	//点击“下页”按钮
//	private OnClickListener nextButtonClick = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			//获取本小区论坛
//			currPage++;
//			new GetTopicsTask(TopicActivity.this,listview,currPage,paginationTextView,preButton,nextButton,pagination).execute();
//		}
//	};
	
	//点击“发帖”按钮
	private OnClickListener postTopicButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(TopicActivity.this,PostTopicActivity.class);
			TopicActivity.this.startActivity(intent);
		}
	};
	
	//点击“我的帖子”按钮
	private OnClickListener myTopicButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(TopicActivity.this,MyTopicActivity.class);
			TopicActivity.this.startActivity(intent);
		}
	};
	
	//------------------------------------------------------------------------------------------------------
	/**
     * 滑动时被调用
     */ 
    @Override 
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) { 
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;
        this.visibleLastIndex = firstVisibleItem + visibleItemCount - 1; 
    } 
   
    /**
     * 滑动状态改变时被调用
     */ 
    @Override 
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex+1 == totalItemCount) { 
        	pagination.setCurrPage((pagination.getCurrPage()+1));
        	if(pagination.getCurrPage()<=pagination.getMaxPage())
        		new GetTopicsTask(this,listview,pagination,progressBar,loadMoreTextView).execute();
        	else{
        		Toast.makeText(TopicActivity.this, "到底了~", Toast.LENGTH_LONG).show();
        		loadMoreTextView.setText("已加载全部");
        	}
        } 
    }
  //------------------------------------------------------------------------------------------------------
}
