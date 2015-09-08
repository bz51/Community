package com.community.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;

import com.community.thread.GetAuthorTask;

public class AuthorActivity extends Activity {
	private Button preButton;
	private Button nextButton;
	private ListView listview;
	private int currPage = 1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_author);

		findView();
		setListener();
		
		new GetAuthorTask(AuthorActivity.this,listview,currPage,preButton,nextButton).execute();
	}

	private void findView() {
		preButton = (Button) this.findViewById(R.id.author_preButton);
		nextButton = (Button) this.findViewById(R.id.author_nextButton);
		listview = (ListView) this.findViewById(R.id.author_listview);
	}
	
	private void setListener() {
		preButton.setOnClickListener(preButtonClick);
		nextButton.setOnClickListener(nextButtonClick);
	}
	
	//点击“上页”按钮
	private OnClickListener preButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			currPage--;
			new GetAuthorTask(AuthorActivity.this,listview,currPage,preButton,nextButton).execute();
		}
	};
	
	//点击“下页”按钮
	private OnClickListener nextButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			currPage++;
			new GetAuthorTask(AuthorActivity.this,listview,currPage,preButton,nextButton).execute();
		}
	};
}
