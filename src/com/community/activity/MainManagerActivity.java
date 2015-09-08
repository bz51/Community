package com.community.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.community.adapter.ImageAdapter;
import com.community.core.MyGallery;

public class MainManagerActivity extends Activity {
	private TextView titleTextView;
	private Button postButton;
	private Button getInfoButton;
	private Button getTopicButton;
	private Button searchUserButton;
	private Button authorButton;
	private Button residentButton;
	private Button phoneButton;
	private MyGallery gallery;
	private int index = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main_manager);

		findView();
		setListener();
		
		//若当前用户是普通管理员，将“授权”按钮隐藏
//		hideAuthorButton();
		
		//焦点图
		ImageAdapter adapter = new ImageAdapter(this);
		gallery.setAdapter(adapter);
        Timer timer = new Timer();  
        timer.schedule(task, 5000, 5000);  
    }  

	//定时器
    private TimerTask task = new TimerTask() {  
        @Override 
        public void run() {  
        	//获取图片总数
        	int maxPics = ImageAdapter.imageIds.length;
        	if(index>=maxPics)
        		index = 0;
            Message message = new Message();  
            message.what = 2;  
//            index = gallery.getSelectedItemPosition();  
            handler.sendMessage(message);  
        }  
    };  

    private Handler handler = new Handler() {  
        @Override 
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            switch (msg.what) {  
            case 2:  
                gallery.setSelection(index);  
                index++;
                break;  
            default:  
                break;  
            }  
        }  
    }; 


	/**
	 * 若当前用户是普通管理员，将“授权”按钮隐藏
	 */
/*	private void hideAuthorButton() {
		//获取community_id
		SharedPreferences sharedPreferences= this.getSharedPreferences("community",this.MODE_PRIVATE); 
		String is_super =sharedPreferences.getString("is_super", "");
		if(is_super.equals("1"))
			authorButton.setVisibility(View.GONE);
	}
*/
	private void findView() {
		titleTextView = (TextView) this.findViewById(R.id.mainManager_titleTextView);
		SharedPreferences sharedPreferences= this.getSharedPreferences("community",this.MODE_PRIVATE); 
		titleTextView.setText(sharedPreferences.getString("community_name", "")+"首页");
		postButton = (Button) this.findViewById(R.id.mainManager_postButton);
		residentButton = (Button) this.findViewById(R.id.mainManager_residentButton);
		getInfoButton = (Button) this.findViewById(R.id.mainManager_getInfoButton);
		phoneButton = (Button) this.findViewById(R.id.mainManager_phoneButton);
//		getTopicButton = (Button) this.findViewById(R.id.mainManager_getTopicButton);
//		searchUserButton = (Button) this.findViewById(R.id.mainManager_searchUserButton);
//		authorButton = (Button) this.findViewById(R.id.mainManager_authorButton);
		gallery = (MyGallery) this.findViewById(R.id.main_gallery);
	}
	
	private void setListener() {
		postButton.setOnClickListener(postButtonClick);
		getInfoButton.setOnClickListener(getInfoButtonClick);
//		getTopicButton.setOnClickListener(getTopicButtonClick);
//		searchUserButton.setOnClickListener(searchUserButtonClick);
//		authorButton.setOnClickListener(authorButtonClick);
		residentButton.setOnClickListener(residentButtonClick);
		phoneButton.setOnClickListener(phoneButtonClick);
	}
	
	//点击“发布公告”按钮
	private OnClickListener postButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainManagerActivity.this,PostInfoActivity.class);
			MainManagerActivity.this.startActivity(intent);
		}
	};
	
	//点击“本小区公告”按钮
	private OnClickListener getInfoButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainManagerActivity.this,MaInfoActivity.class);
			MainManagerActivity.this.startActivity(intent);
		}
	};
	
	//点击“便民电话”按钮
	private OnClickListener phoneButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainManagerActivity.this,PhoneActivity.class);
			MainManagerActivity.this.startActivity(intent);
		}
	};
	
	//点击“本小区论坛”按钮
/*	private OnClickListener getTopicButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainManagerActivity.this,TopicActivity.class);
			MainManagerActivity.this.startActivity(intent);
		}
	};
	*/
	//点击“查询住户”按钮
/* 	private OnClickListener searchUserButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			
		}
	};*/
	
	//点击“授权管理”按钮
	/*private OnClickListener authorButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainManagerActivity.this,AuthorActivity.class);
			MainManagerActivity.this.startActivity(intent);
		}
	};*/
	
	//点击“住户入口”按钮
	private OnClickListener residentButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(MainManagerActivity.this,LoginActivity.class);
			MainManagerActivity.this.finish();
			MainManagerActivity.this.startActivity(intent);
		}
	};

}
