package com.community.activity;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.community.core.Pagination;
import com.community.thread.GetTopicCommentsTask;
import com.community.thread.GetTopicsTask;
import com.community.thread.PostTopicCommentTask;

public class CommentActivity extends Activity implements OnScrollListener {
	private ListView listview;
	private EditText commentEditText;
	private Button sendButton;
	private long topic_id;
	private String topic_title;
	private TextView loadMoreTextView;
	private ProgressBar progressBar;
	private View footerView;
	private int visibleLastIndex = 0;   //最后的可视项索引 
    private int visibleItemCount;       // 当前窗口可见项总数 
    private int totalItemCount;       // listview中item总个数
    private Pagination pagination = new Pagination();
    private ArrayList<HashMap<String,String>> dataSource =  new ArrayList<HashMap<String,String>>();//创建数据源
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_comment);

		findView();
		setListener();
		
		//获取本条帖子id、title
		topic_id = getIntent().getLongExtra("topic_id", 0);
		topic_title = getIntent().getStringExtra("topic_title");
		//获取本条帖子的评论
		new GetTopicCommentsTask(CommentActivity.this,listview,pagination,progressBar,loadMoreTextView,topic_id).execute();
	}
	
	private void findView() {
		sendButton = (Button) this.findViewById(R.id.comment_sendButton1);
		listview = (ListView) this.findViewById(R.id.comment_listview);
		commentEditText = (EditText) this.findViewById(R.id.comment_commentEditText);
		//获取footer
		footerView = getLayoutInflater().inflate(R.layout.footer_listview, null);
		listview.addFooterView(footerView);
		loadMoreTextView = (TextView) footerView.findViewById(R.id.listfooter_loadMoreTextView);
		progressBar = (ProgressBar) footerView.findViewById(R.id.listfooter_loadProgressBar);
	}
	
	private void setListener() {
		sendButton.setOnClickListener(sendButtonClick);
		listview.setOnScrollListener(this);
	}
	
	//点击“发送”按钮
	private OnClickListener sendButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//获取评论内容
			String comment = commentEditText.getText().toString();
			//发送评论
			new PostTopicCommentTask(CommentActivity.this).execute(comment,topic_title,topic_id+"");
		}
	};
	
//	//点击“上页”按钮
//	private OnClickListener preButtonClick = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			currPage--;
//			new GetTopicCommentsTask(CommentActivity.this,listview,preButton,nextButton,topic_id,currPage).execute();
//		}
//	};
//	
//	//点击“下页”按钮
//	private OnClickListener nextButtonClick = new OnClickListener() {
//		@Override
//		public void onClick(View v) {
//			currPage++;
//			new GetTopicCommentsTask(CommentActivity.this,listview,preButton,nextButton,topic_id,currPage).execute();
//		}
//	};
	
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
        		new GetTopicCommentsTask(CommentActivity.this,listview,pagination,progressBar,loadMoreTextView,topic_id).execute();
        	else{
        		Toast.makeText(this, "到底了~", Toast.LENGTH_LONG).show();
        		loadMoreTextView.setText("已加载全部");
        	}
        } 
    }
}
