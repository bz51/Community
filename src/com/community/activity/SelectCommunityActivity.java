package com.community.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.community.core.Pagination;
import com.community.thread.SearchCommunityTask;

public class SelectCommunityActivity extends Activity implements OnScrollListener,OnEditorActionListener  {
	private ListView listView;	 
	private EditText searchEditText;
	private Button searchButton;
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
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_select_community);

		findView();
		setListener();
		
		pagination.queryKeyWord = "";
		new SearchCommunityTask(SelectCommunityActivity.this,listView,pagination,progressBar,loadMoreTextView).execute();
	}
	
	private void findView() {
		listView = (ListView) this.findViewById(R.id.selectCommnity_listView);
		searchEditText = (EditText) this.findViewById(R.id.selectCommnity_searchEditText);
		searchButton = (Button) this.findViewById(R.id.selectCommnity_searchButton);
		//��ȡfooter
		footerView = getLayoutInflater().inflate(R.layout.footer_listview, null);
		listView.addFooterView(footerView);
		loadMoreTextView = (TextView) footerView.findViewById(R.id.listfooter_loadMoreTextView);
		progressBar = (ProgressBar) footerView.findViewById(R.id.listfooter_loadProgressBar);
	}
	
	private void setListener() {
		searchButton.setOnClickListener(searchButtonClick);
		listView.setOnScrollListener(this);
		searchEditText.setOnEditorActionListener(this);
	}
	
	//�������������ť
	private OnClickListener searchButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String keyword = searchEditText.getText().toString();
			pagination.queryKeyWord = keyword;
			//�ؼ��ֲ���ʱ������
			if(keyword!=null && !keyword.equals("")){
				new SearchCommunityTask(SelectCommunityActivity.this,listView,pagination,progressBar,loadMoreTextView).execute();
				searchEditText.setText(pagination.queryKeyWord);
			}
			else{
				Toast.makeText(SelectCommunityActivity.this, "�ؼ��ֲ���Ϊ�գ�", Toast.LENGTH_LONG).show();
			}
		}
	};
	
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
        		new SearchCommunityTask(SelectCommunityActivity.this,listView,pagination,progressBar,loadMoreTextView).execute();
        	else{
        		Toast.makeText(SelectCommunityActivity.this, "������~", Toast.LENGTH_LONG).show();
        		loadMoreTextView.setText("�Ѽ���ȫ��");
        	}
        } 
    }
    
    
    /**
     * �������̡�������ʱ������
     */
    @Override  
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {  
        if(actionId==EditorInfo.IME_ACTION_SEARCH){
        	String keyword = searchEditText.getText().toString();
			pagination.queryKeyWord = keyword;
			//�ؼ��ֲ���ʱ������
			if(keyword!=null && !keyword.equals("")){
				new SearchCommunityTask(SelectCommunityActivity.this,listView,pagination,progressBar,loadMoreTextView).execute();
				searchEditText.setText(pagination.queryKeyWord);
			}
			else{
				Toast.makeText(SelectCommunityActivity.this, "�ؼ��ֲ���Ϊ�գ�", Toast.LENGTH_LONG).show();
			}
        }  
        return true;  
    } 
}
