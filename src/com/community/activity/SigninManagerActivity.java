package com.community.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.community.core.Parameter;
import com.community.thead.MaIsLoginTask;
import com.community.thead.ReIsLoginTask;
import com.community.thread.MaSigninTask;
import com.community.thread.ReSigninTask;


public class SigninManagerActivity extends Activity {
	private EditText usernameEditText;
	private EditText passwordEditText;
	private EditText repasswordEditText;
	private TextView resultUserTextView;
	private TextView resultPassTextView;
	private TextView resultRePassTextView;
	private Button signinButton;
	private Button loginButton;
	private Button residentButton;
	private Button selectCommunityButton;
	private Button backButton;
	private static final String USERNAME_RULE = "�û���������3-16λӢ�ġ�����";
	private static final String PASSWORD_RULE = "�û���������3-16λӢ�ġ�����";
	private static final String REPASSWORD_RULE = "�������벻һ��";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_signin_manager);
		
		findView();
		setListener();
		
		//�����ݼ����������ؼ���
		showInViews();
	}


	private void findView() {
		loginButton = (Button) this.findViewById(R.id.signinManager_loginButton);
		signinButton = (Button) this.findViewById(R.id.signinManager_signinButton);
		residentButton = (Button) this.findViewById(R.id.signinManager_residentButton);
		selectCommunityButton = (Button) this.findViewById(R.id.signinManager_selectCommunityButton);
		backButton = (Button) this.findViewById(R.id.signinManager_backButton);
		usernameEditText = (EditText) this.findViewById(R.id.signinManager_usernameEditText);
		passwordEditText = (EditText) this.findViewById(R.id.signinManager_passwordEditText);
		repasswordEditText = (EditText) this.findViewById(R.id.signinManager_repasswordEditText);
		resultUserTextView = (TextView) this.findViewById(R.id.signinManager_resultUserTextView);
		resultPassTextView = (TextView) this.findViewById(R.id.signinManager_resultPassTextView);
		resultRePassTextView = (TextView) this.findViewById(R.id.signinManager_resultRePassTextView);
	}
	

	private void setListener() {
		loginButton.setOnClickListener(loginButtonClick);
		signinButton.setOnClickListener(signinButtonClick);
		residentButton.setOnClickListener(residentButtonClick);
		selectCommunityButton.setOnClickListener(selectCommunityButtonClick);
		backButton.setOnClickListener(backButtonClick);
		usernameEditText.setOnFocusChangeListener(usernameEditTextFocus);
		passwordEditText.setOnFocusChangeListener(passwordEditTextFocus);
		repasswordEditText.setOnFocusChangeListener(repasswordEditTextFocus);
	}

	//�����ѡ��С������ť
	private OnClickListener selectCommunityButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(SigninManagerActivity.this,SelectCommunityActivity.class);
			intent.putExtra("fromWhere", Parameter.FROM_MA_SIGNIN_ACTIVITY);
			intent.putExtra("username", usernameEditText.getText().toString());
			intent.putExtra("password", passwordEditText.getText().toString());
			SigninManagerActivity.this.finish();
			SigninManagerActivity.this.startActivity(intent);
		}
	};
	
	//�����ע�ᡱ��ť
	private OnClickListener signinButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String username = usernameEditText.getText().toString();
			String password = passwordEditText.getText().toString();
			String repassword = repasswordEditText.getText().toString();
			String community = selectCommunityButton.getText().toString();
			Log.i("my", community);
			//��username��password��repassword�����ˣ���ע��
			if(!username.equals("") && !password.equals("") && !repassword.equals("") && !community.equals("ѡ������С��")){
				new MaSigninTask(SigninManagerActivity.this).execute(username,password);
			}
		}
	};
	
	//�������½����ť
	private OnClickListener residentButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(SigninManagerActivity.this,LoginManagerActivity.class);
			SigninManagerActivity.this.finish();
			SigninManagerActivity.this.startActivity(intent);
		}
	};
	
	//�����ס����½����ť
	private OnClickListener loginButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(SigninManagerActivity.this,LoginActivity.class);
			SigninManagerActivity.this.finish();
			SigninManagerActivity.this.startActivity(intent);
		}
	};
	
	//��������ˡ���ť
	private OnClickListener backButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			SigninManagerActivity.this.finish();
		}
	};
	
	//usernameEditTextʧȥ����
	private OnFocusChangeListener usernameEditTextFocus = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus){
				String username = usernameEditText.getText().toString();
				//��֤username�Ƿ����Ҫ��
				if(!verifyUsername(username))
					return ;
				
				//�ж��û����Ƿ�ע���
				new MaIsLoginTask(SigninManagerActivity.this,resultUserTextView,signinButton).execute(username);
			}
		}
	};
	
	//passwordEditTextʧȥ����
	private OnFocusChangeListener passwordEditTextFocus = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus){
				String password = passwordEditText.getText().toString();
				//��֤password�Ƿ����Ҫ��
				if(!verifyPassword(password))
					return ;
			}
		}
	};
	
	//repasswordEditTextʧȥ����
	private OnFocusChangeListener repasswordEditTextFocus = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus){
				String repassword = repasswordEditText.getText().toString();
				String password = passwordEditText.getText().toString();
				//��֤repassword�Ƿ����Ҫ��
				if(!verifyRePassword(repassword,password))
					return ;
			}
		}
	};
	
	

	/**
	 * ��֤�û����Ƿ��������
	 * @param username
	 */
	private boolean verifyUsername(String username){
		Pattern pattern = Pattern.compile("([a-z]|[A-Z]|[0-9]|[\\u4e00-\\u9fa5])+");
		Matcher matcher = pattern.matcher(username);
		boolean result= matcher.matches();
		if(!result){
			signinButton.setClickable(false);
			resultUserTextView.setText(SigninManagerActivity.USERNAME_RULE);
			return false;
		}
		signinButton.setClickable(true);
		resultUserTextView.setText("");
		return true;
	}
	
	/**
	 * ��֤�����Ƿ��������
	 * @param password
	 */
	private boolean verifyPassword(String password){
		Pattern pattern = Pattern.compile("([a-z]|[A-Z]|[0-9]|[\\u4e00-\\u9fa5])+");
		Matcher matcher = pattern.matcher(password);
		boolean result= matcher.matches();
		if(!result){
			signinButton.setClickable(false);
			resultPassTextView.setText(SigninManagerActivity.PASSWORD_RULE);
			return false;
		}
		signinButton.setClickable(true);
		resultPassTextView.setText("");
		return true;
	}
	
	/**
	 * ��֤���������Ƿ�һ��
	 * @param password 
	 * @param repassword
	 */
	private boolean verifyRePassword(String repassword, String password){
		if(!repassword.equals(password)){
			signinButton.setClickable(false);
			resultRePassTextView.setText(SigninManagerActivity.REPASSWORD_RULE);
			return false;
		}
		signinButton.setClickable(true);
		resultRePassTextView.setText("");
		return true;
	}
	
	/**
	 * �����ݼ����������ؼ���
	 */
	private void showInViews() {
		Intent intent = this.getIntent();
		boolean hasData = intent.getBooleanExtra("hasData",false);
		if(hasData){
			usernameEditText.setText(intent.getStringExtra("username"));
			passwordEditText.setText(intent.getStringExtra("password"));
			repasswordEditText.setText(intent.getStringExtra("password"));
			selectCommunityButton.setText(intent.getStringExtra("community_name"));
		}
	}
	
//	@Override
//	public boolean onCreateOptionsMenu(Menu menu) {
//		// Inflate the menu; this adds items to the action bar if it is present.
//		getMenuInflater().inflate(R.menu.signin, menu);
//		return true;
//	}
//
//	@Override
//	public boolean onOptionsItemSelected(MenuItem item) {
//		// Handle action bar item clicks here. The action bar will
//		// automatically handle clicks on the Home/Up button, so long
//		// as you specify a parent activity in AndroidManifest.xml.
//		int id = item.getItemId();
//		if (id == R.id.action_settings) {
//			return true;
//		}
//		return super.onOptionsItemSelected(item);
//	}
}
