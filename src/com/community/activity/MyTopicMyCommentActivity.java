package com.community.activity;

import java.util.ArrayList;
import java.util.HashMap;

import com.community.activity.MyListView.OnRefreshListener;
import com.community.core.Pagination;
import com.community.thread.GetMyCommentTask;
import com.community.thread.GetMyPostTask;
import com.community.thread.GetPhoneTask;
import com.community.thread.GetTopicsTask;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MyTopicMyCommentActivity extends Activity implements OnScrollListener {
	private MyListView listview;
	private TextView loadMoreTextView;
	private ProgressBar progressBar;
	private View footerView;
	private int visibleLastIndex = 0;   //���Ŀ��������� 
    private int visibleItemCount;       // ��ǰ���ڿɼ������� 
    private int totalItemCount;       // listview��item�ܸ���
    private Pagination pagination = new Pagination();
    private ArrayList<HashMap<String,String>> dataSource =  new ArrayList<HashMap<String,String>>();//��������Դ
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_topic_my_comment);
		

		findView();
		setListener();
		
		//��ȡ�ҵķ���
		new GetMyCommentTask(this,listview,pagination,progressBar,loadMoreTextView).execute();
	}
	
	private void findView() {
		listview = (MyListView) this.findViewById(R.id.myTopicMyComment_listView);
		//��ȡfooter
		footerView = getLayoutInflater().inflate(R.layout.footer_listview, null);
		listview.addFooterView(footerView);
		loadMoreTextView = (TextView) footerView.findViewById(R.id.listfooter_loadMoreTextView);
		progressBar = (ProgressBar) footerView.findViewById(R.id.listfooter_loadProgressBar);
	}
	
	private void setListener() {
		listview.setOnScrollListener(this);
		listview.setonRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				new GetMyCommentTask(MyTopicMyCommentActivity.this,listview,pagination,progressBar,loadMoreTextView).execute();
			}
		});
	}
	
	/**
     * ����ʱ������
     */ 
    @Override 
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) { 
        this.visibleItemCount = visibleItemCount;
        this.totalItemCount = totalItemCount;
        this.visibleLastIndex = firstVisibleItem + visibleItemCount - 1; 
    } 
   
    /**
     * ����״̬�ı�ʱ������
     */ 
    @Override 
    public void onScrollStateChanged(AbsListView view, int scrollState) {
        if (scrollState == OnScrollListener.SCROLL_STATE_IDLE && visibleLastIndex+1 == totalItemCount) { 
        	pagination.setCurrPage((pagination.getCurrPage()+1));
        	if(pagination.getCurrPage()<=pagination.getMaxPage())
        		new GetMyCommentTask(this,listview,pagination,progressBar,loadMoreTextView).execute();
        	else{
        		Toast.makeText(MyTopicMyCommentActivity.this, "������~", Toast.LENGTH_LONG).show();
        		loadMoreTextView.setText("�Ѽ���ȫ��");
        	}
        } 
    }
}
