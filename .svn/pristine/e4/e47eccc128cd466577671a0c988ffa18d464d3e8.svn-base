package com.community.activity;

import com.community.thread.GetInfoTask;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TabHost;

public class MyTopicActivity extends TabActivity   {
	private Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_my_topic);
		
		Resources res = getResources(); // Resource object to get Drawables  
	    TabHost tabHost = getTabHost();  // The activity TabHost  
	    TabHost.TabSpec spec;  // Resusable TabSpec for each tab  
	    Intent intent;  // Reusable Intent for each tab  
	  
	    // Create an Intent to launch an Activity for the tab (to be reused)  
	    intent = new Intent().setClass(this, MyTopicMyPostActivity.class);  
	  
	    // Initialize a TabSpec for each tab and add it to the TabHost  
	    spec = tabHost.newTabSpec("artists").setIndicator("我的发布").setContent(intent);  
	    tabHost.addTab(spec);  
	  
	    // Do the same for the other tabs  
	    intent = new Intent().setClass(this, MyTopicMyCommentActivity.class);  
	    spec = tabHost.newTabSpec("albums").setIndicator("我的评价")  
	                  .setContent(intent);  
	    //,  res.getDrawable(R.drawable.ic_tab_albums)
	    tabHost.addTab(spec);  
	  
	  
	    tabHost.setCurrentTab(0); 
	    
	    findView();
		setListener();
		
	}
	
	private void findView() {
		backButton = (Button) this.findViewById(R.id.myTopic_backButton);
	}
	
	private void setListener() {
		backButton.setOnClickListener(backButtonClick);
	}
	
	private OnClickListener backButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			MyTopicActivity.this.finish();
		}
	};
}
