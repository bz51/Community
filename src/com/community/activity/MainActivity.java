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
	 * ��������
	 * 1.id:��ǰҳ����_������+�ؼ�����  @+id/login_signinButton
	 * 2.�ؼ�������������+�ؼ�����  private Button loginButton;
	 * 3.������������������+�ؼ�����+����  loginButtonClick
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
	private String infoCount = "0";//��ǰ���������
	private NotificationManager mNotificationManager;
	private Notification mNotification;  
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		//��ʼ��NotificationManager
		mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		
		verifyLogin();//��֤�Ƿ��½
		findView();
		setListener();
		
		//����ͼ
		ImageAdapter adapter = new ImageAdapter(this);
		gallery.setAdapter(adapter);
        Timer timer = new Timer();  
        timer.schedule(task, 5000, 5000);  
        
        //������ʱ����ÿ5��鿴�Ƿ��й���
        Timer timer_info = new Timer();  
        timer_info.schedule(task_info, 0, 5000);  
    }  

	//��ʱ��
    private TimerTask task = new TimerTask() {  
        @Override 
        public void run() {  
        	//��ȡͼƬ����
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
    
    
    //�����̣߳�ÿ5�뷢һ�����󣬲鿴�Ƿ��й���
	//��ʱ��
    private TimerTask task_info = new TimerTask() {  
        @Override 
        public void run() {  
        	//��ȡ��ǰ�û�id
    		SharedPreferences sharedPreferences= MainActivity.this.getSharedPreferences("community",MainActivity.MODE_PRIVATE); 
    		String user_id =sharedPreferences.getString("user_id", ""); 
    		String role =sharedPreferences.getString("role", ""); 
    		infoCount = sharedPreferences.getString("infoCount", "");
			if ("0".equals(role)) {
				// ����
				String parameter = "?residentEntity.id=" + user_id;
				String json = HttpUtils
						.getJsonContent(Parameter.RE_GETMYINFOCOUNT + parameter);
				// ����json
				String infoCount_new = json2infoCount(json);
				// ���¹��棬��֪ͨ����ʾ
				if (!infoCount.equals(infoCount_new) && !"error".equals(infoCount_new)) {
					infoCount = infoCount_new;
					SharedPreferences mySharedPreferences = MainActivity.this
							.getSharedPreferences("community",
									MainActivity.MODE_PRIVATE);
					SharedPreferences.Editor editor = mySharedPreferences
							.edit();
					editor.putString("infoCount", infoCount); // ��putString�ķ�����������
					editor.commit();// �ύ��ǰ����
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
                Toast.makeText(MainActivity.this, "������Ϣ��", Toast.LENGTH_LONG).show();
                showNotify();
                break;  
            default:  
                break;  
            }  
        }  
    }; 
    
    private void showNotify() {
        int icon = R.drawable.ic_launcher;  
        CharSequence tickerText = "�����µ���Ϣ";  
        long when = System.currentTimeMillis();  
        mNotification = new Notification(icon,tickerText,when);  
        mNotification.defaults = Notification.DEFAULT_ALL;  
//        mNotification.flags |= Notification.F;  
        mNotification.flags |= Notification.FLAG_SHOW_LIGHTS;  
        // �� ����notification����Ϣ �� PendingIntent   
        Context context = this;  
        CharSequence contentTitle ="����С��";  
        CharSequence contentText = "�����µĹ���";  
        Intent notificationIntent = new Intent(this,InfoActivity.class);  
        PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,0 );  
        mNotification.setLatestEventInfo(context, contentTitle, contentText, contentIntent); 
        mNotification.defaults=Notification.DEFAULT_SOUND;
        mNotificationManager.notify(0,mNotification);  
    }

	
	/**
	 * ��֤�Ƿ��Ѿ���½
	 */
	private void verifyLogin() {
		SharedPreferences sharedPreferences= getSharedPreferences("community",MODE_PRIVATE); 
		String user_id =sharedPreferences.getString("user_id", "0"); 
		String community_id =sharedPreferences.getString("community_id", "0");
		String role =sharedPreferences.getString("role", "0");
		//��û��½������ת����½ҳ��
		if(user_id.equals("0") || community_id.equals("0")){
			Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
			this.finish();
			startActivity(intent);
		}
		//����½�����ж���ס��or��ҵ
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
		titleTextView.setText(sharedPreferences.getString("community_name", "")+"��ҳ");
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
	
	//���������Ա��ڡ���ť
	private OnClickListener managerButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), LoginManagerActivity.class);
			startActivity(intent);
		}
	};
	
	//���������绰����ť
	private OnClickListener phoneButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), PhoneActivity.class);
			startActivity(intent);
		}
	};
	
	//������ҵ����ۡ���ť
	private OnClickListener myCommentButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MyTopicActivity.class);
			startActivity(intent);
		}
	};
	
	//������ҵķ�������ť
	private OnClickListener myPostButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), MyTopicActivity.class);
			startActivity(intent);
		}
	};
	
	//�����С����̳����ť
	private OnClickListener topicButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), TopicActivity.class);
			startActivity(intent);
		}
	};
	
	//������ҵ���Ϣ����ť
	private OnClickListener myInfoButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), UserInfoActivity.class);
			startActivity(intent);
		}
	};
	
	//�������������ť
	private OnClickListener postButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), PostTopicActivity.class);
			startActivity(intent);
		}
	};
	
	//�����С�����桱��ť
	private OnClickListener getInfoButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent();
			intent.setClass(getApplicationContext(), InfoActivity.class);
			startActivity(intent);
		}
	};
	
}
