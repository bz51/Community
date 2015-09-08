package com.community.activity;

import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
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
import android.widget.Toast;

import com.community.adapter.ImageAdapter;
import com.community.core.HttpUtils;
import com.community.core.MyGallery;
import com.community.core.Parameter;


public class MainActivity extends Activity{
	/**
	 * 命名规则：
	 * 1.id:当前页名字_功能名+控件类型  @+id/login_signinButton
	 * 2.控件变量：功能名+控件类型  private Button loginButton;
	 * 3.监听器变量：功能名+控件类型+动作  loginButtonClick
	 */
	private TextView titleTextView;
	private Button getInfoButton;
	private Button topicButton;
	private Button managerButton;
	private Button myCommentButton;
	private Button myInfoButton;
	private Button phoneButton;
	private Button myPostButton;
	private Button postButton;
	private MyGallery gallery;
	private int index = 0;
	private String infoCount = "0";//当前公告的数量
	private NotificationManager mNotificationManager;
	private Notification mNotification;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		//初始化NotificationManager
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		verifyLogin();//验证是否登陆
		findView();
		setListener();
		
		//焦点图
		ImageAdapter adapter = new ImageAdapter(this);
		gallery.setAdapter(adapter);
        Timer timer = new Timer();  
        timer.schedule(task, 5000, 5000);  
        
        //启动定时器，每5秒查看是否有公告
        Timer timer_info = new Timer();  
        timer_info.schedule(task_info, 0, 5000);  
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
    
    
    //启动线程，每5秒发一次请求，查看是否有公告
	//定时器
    private TimerTask task_info = new TimerTask() {  
        @Override 
        public void run() {  
        	//获取当前用户id
    		SharedPreferences sharedPreferences= MainActivity.this.getSharedPreferences("community",MainActivity.MODE_PRIVATE); 
    		String user_id =sharedPreferences.getString("user_id", ""); 
    		String role =sharedPreferences.getString("role", ""); 
    		infoCount = sharedPreferences.getString("infoCount", "");
			if ("0".equals(role)) {
				// 发送
				String parameter = "?residentEntity.id=" + user_id;
				String json = HttpUtils
						.getJsonContent(Parameter.RE_GETMYINFOCOUNT + parameter);
				// 解析json
				String infoCount_new = json2infoCount(json);
				// 有新公告，则通知栏提示
				if (!infoCount.equals(infoCount_new) && !"error".equals(infoCount_new)) {
					infoCount = infoCount_new;
					SharedPreferences mySharedPreferences = MainActivity.this
							.getSharedPreferences("community",
									MainActivity.MODE_PRIVATE);
					SharedPreferences.Editor editor = mySharedPreferences
							.edit();
					editor.putString("infoCount", infoCount); // 用putString的方法保存数据
					editor.commit();// 提交当前数据
					Message message = new Message();
					message.what = 2;
					handler_info.sendMessage(message);
				}
			}
        }

		private String json2infoCount(String json) {
			String infoCount_new = "0";
			try {
				JSONObject jsonObject = new JSONObject(json);
				infoCount_new = jsonObject.getString("result");
			} catch (JSONException e) {
//				Toast.makeText(MainActivity.this, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
				return "error";
			}
			return infoCount_new;
		}  
    };  
    private Handler handler_info = new Handler() {  
        @Override 
        public void handleMessage(Message msg) {  
            super.handleMessage(msg);  
            switch (msg.what) {  
            case 2:  
                Toast.makeText(MainActivity.this, "有新消息！", Toast.LENGTH_LONG).show();
                showNotify();
                break;  
            default:  
                break;  
            }  
        }  
    }; 
    
    private void showNotify() {
        int icon = R.drawable.ic_launcher;  
        CharSequence tickerText = "您有新的消息";  
        long when = System.currentTimeMillis();  
        mNotification = new Notification(icon,tickerText,when);  
        mNotification.defaults = Notification.DEFAULT_ALL;  
//        mNotification.flags |= Notification.F;  
        mNotification.flags |= Notification.FLAG_SHOW_LIGHTS;  
        // ③ 定义notification的消息 和 PendingIntent   
        Context context = this;  
        CharSequence contentTitle ="叮咚小区";  
        CharSequence contentText = "您有新的公告";  
        Intent notificationIntent = new Intent(this,InfoActivity.class);  
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,0 );  
        mNotification.setLatestEventInfo(context, contentTitle, contentText, contentIntent); 
        mNotification.defaults=Notification.DEFAULT_SOUND;
        mNotificationManager.notify(0,mNotification);  
    }

	
	/**
	 * 验证是否已经登陆
	 */
	private void verifyLogin() {
		SharedPreferences sharedPreferences= getSharedPreferences("community",MODE_PRIVATE); 
		String user_id =sharedPreferences.getString("user_id", "0"); 
		String community_id =sharedPreferences.getString("community_id", "0");
		String role =sharedPreferences.getString("role", "0");
		//若没登陆，则跳转至登陆页面
		if(user_id.equals("0") || community_id.equals("0")){
			Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			this.finish();
			startActivity(intent);
		}
		//若登陆，则判断是住户or物业
		else{
			if(role.equals("1")){
				Intent intent = new Intent(getApplicationContext(), MainManagerActivity.class);
				this.finish();
				startActivity(intent);
			}
		}
	}

	private void findView() {
		titleTextView = (TextView) this.findViewById(R.id.main_titleTextView);
		SharedPreferences sharedPreferences= this.getSharedPreferences("community",this.MODE_PRIVATE); 
		titleTextView.setText(sharedPreferences.getString("community_name", "")+"首页");
		managerButton = (Button) this.findViewById(R.id.main_managerButton);
		myCommentButton = (Button) this.findViewById(R.id.main_myCommentButton);
		myPostButton = (Button) this.findViewById(R.id.main_myPostButton);
		phoneButton = (Button) this.findViewById(R.id.main_phoneButton);
		postButton = (Button) this.findViewById(R.id.main_postButton);
		getInfoButton = (Button) this.findViewById(R.id.main_getInfoButton);
		topicButton = (Button) this.findViewById(R.id.main_topicButton);
		myInfoButton = (Button) this.findViewById(R.id.main_myInfoButton);
		gallery = (MyGallery) this.findViewById(R.id.main_gallery);
	}
	

	private void setListener() {
		managerButton.setOnClickListener(managerButtonClick);
		phoneButton.setOnClickListener(phoneButtonClick);
		myCommentButton.setOnClickListener(myCommentButtonClick);
		myPostButton.setOnClickListener(myPostButtonClick);
		postButton.setOnClickListener(postButtonClick);
		getInfoButton.setOnClickListener(getInfoButtonClick);
		topicButton.setOnClickListener(topicButtonClick);
		myInfoButton.setOnClickListener(myInfoButtonClick);
	}
	
	//点击“管理员入口”按钮
	private OnClickListener managerButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), LoginManagerActivity.class);
			startActivity(intent);
		}
	};
	
	//点击“便民电话”按钮
	private OnClickListener phoneButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), PhoneActivity.class);
			startActivity(intent);
		}
	};
	
	//点击“我的评论”按钮
	private OnClickListener myCommentButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MyTopicActivity.class);
			startActivity(intent);
		}
	};
	
	//点击“我的发布”按钮
	private OnClickListener myPostButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MyTopicActivity.class);
			startActivity(intent);
		}
	};
	
	//点击“小区论坛”按钮
	private OnClickListener topicButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), TopicActivity.class);
			startActivity(intent);
		}
	};
	
	//点击“我的信息”按钮
	private OnClickListener myInfoButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), UserInfoActivity.class);
			startActivity(intent);
		}
	};
	
	//点击“发帖”按钮
	private OnClickListener postButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), PostTopicActivity.class);
			startActivity(intent);
		}
	};
	
	//点击“小区公告”按钮
	private OnClickListener getInfoButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), InfoActivity.class);
			startActivity(intent);
		}
	};
	
}
