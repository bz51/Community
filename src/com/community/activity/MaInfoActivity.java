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

import com.community.core.Pagination;
import com.community.thread.GetMaInfoTask;

public class MaInfoActivity extends Activity implements OnScrollListener {
	private ListView listview;
	private TextView loadMoreTextView;
	private ProgressBar progressBar;
	private View footerView;
	private int visibleLastIndex = 0;   //最后的可视项索引 
    private int visibleItemCount;       // 当前窗口可见项总数 
    private int totalItemCount;       // listview中item总个数
    private Pagination pagination = new Pagination();
    private ArrayList<HashMap<String,String>> dataSource =  new ArrayList<HashMap<String,String>>();//创建数据源
    private Button postButton;
    private Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ma_info);

		findView();
		setListener();
		
		//获取公告
		new GetMaInfoTask(this,listview,pagination,progressBar,loadMoreTextView).execute();
	}
	
	private void findView() {
		listview = (ListView) this.findViewById(R.id.maInfo_listView);
		postButton = (Button) this.findViewById(R.id.maInfo_postButton);
		backButton = (Button) this.findViewById(R.id.backButton);
		//获取footer
		footerView = getLayoutInflater().inflate(R.layout.footer_listview, null);
		listview.addFooterView(footerView);
		loadMoreTextView = (TextView) footerView.findViewById(R.id.listfooter_loadMoreTextView);
		progressBar = (ProgressBar) footerView.findViewById(R.id.listfooter_loadProgressBar);
	}
	
	private void setListener() {
		listview.setOnScrollListener(this);
		postButton.setOnClickListener(postButtonClick);
		backButton.setOnClickListener(backButtonClick);
	}
	
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
        		new GetMaInfoTask(this,listview,pagination,progressBar,loadMoreTextView).execute();
        	else{
        		Toast.makeText(this, "到底了~", Toast.LENGTH_LONG).show();
        		loadMoreTextView.setText("已加载全部");
        	}
        } 
    }
    
	//点击“发布”按钮
	private OnClickListener postButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MaInfoActivity.this,PostInfoActivity.class);
			MaInfoActivity.this.finish();
			MaInfoActivity.this.startActivity(intent);
		}
	};
	
	//点击“后退”按钮
	private OnClickListener backButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			MaInfoActivity.this.finish();
		}
	};
}
