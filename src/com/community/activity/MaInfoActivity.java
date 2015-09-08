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
	private int visibleLastIndex = 0;   //���Ŀ��������� 
    private int visibleItemCount;       // ��ǰ���ڿɼ������� 
    private int totalItemCount;       // listview��item�ܸ���
    private Pagination pagination = new Pagination();
    private ArrayList<HashMap<String,String>> dataSource =  new ArrayList<HashMap<String,String>>();//��������Դ
    private Button postButton;
    private Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_ma_info);

		findView();
		setListener();
		
		//��ȡ����
		new GetMaInfoTask(this,listview,pagination,progressBar,loadMoreTextView).execute();
	}
	
	private void findView() {
		listview = (ListView) this.findViewById(R.id.maInfo_listView);
		postButton = (Button) this.findViewById(R.id.maInfo_postButton);
		backButton = (Button) this.findViewById(R.id.backButton);
		//��ȡfooter
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
        		new GetMaInfoTask(this,listview,pagination,progressBar,loadMoreTextView).execute();
        	else{
        		Toast.makeText(this, "������~", Toast.LENGTH_LONG).show();
        		loadMoreTextView.setText("�Ѽ���ȫ��");
        	}
        } 
    }
    
	//�������������ť
	private OnClickListener postButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MaInfoActivity.this,PostInfoActivity.class);
			MaInfoActivity.this.finish();
			MaInfoActivity.this.startActivity(intent);
		}
	};
	
	//��������ˡ���ť
	private OnClickListener backButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			MaInfoActivity.this.finish();
		}
	};
}
