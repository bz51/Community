package com.community.activity;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.community.thead.ReIsLoginTask;
import com.community.thread.ReSigninTask;


public class SigninActivity extends Activity {
	private EditText usernameEditText;
	private EditText passwordEditText;
	private EditText repasswordEditText;
	private TextView resultUserTextView;
	private TextView resultPassTextView;
	private TextView resultRePassTextView;
	private Button signinButton;
	private Button loginButton;
	private Button managerButton;
	private Button backButton;
	private static final String USERNAME_RULE = "�û���������3-16λӢ�ġ�����";
	private static final String PASSWORD_RULE = "�û���������3-16λӢ�ġ�����";
	private static final String REPASSWORD_RULE = "�������벻һ��";
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_signin);
		
		findView();
		setListener();
	}

	
	private void findView() {
		loginButton = (Button) this.findViewById(R.id.signin_loginButton);
		signinButton = (Button) this.findViewById(R.id.signin_signinButton);
		managerButton = (Button) this.findViewById(R.id.signin_managerButton);
		backButton = (Button) this.findViewById(R.id.signin_backButton);
		usernameEditText = (EditText) this.findViewById(R.id.signin_usernameEditText);
		passwordEditText = (EditText) this.findViewById(R.id.signin_passwordEditText);
		repasswordEditText = (EditText) this.findViewById(R.id.signin_repasswordEditText);
		resultUserTextView = (TextView) this.findViewById(R.id.signin_resultUserTextView);
		resultPassTextView = (TextView) this.findViewById(R.id.signin_resultPassTextView);
		resultRePassTextView = (TextView) this.findViewById(R.id.signin_resultRePassTextView);
	}
	

	private void setListener() {
		loginButton.setOnClickListener(loginButtonClick);
		signinButton.setOnClickListener(signinButtonClick);
		managerButton.setOnClickListener(managerButtonClick);
		backButton.setOnClickListener(backButtonClick);
		usernameEditText.setOnFocusChangeListener(usernameEditTextFocus);
		passwordEditText.setOnFocusChangeListener(passwordEditTextFocus);
		repasswordEditText.setOnFocusChangeListener(repasswordEditTextFocus);
	}
	
	//�����ע�ᡱ��ť
	private OnClickListener signinButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String username = usernameEditText.getText().toString();
			String password = passwordEditText.getText().toString();
			String repassword = repasswordEditText.getText().toString();
			//��username��password��repassword�����ˣ���ע��
			if(!username.equals("") && !password.equals("") && !repassword.equals("")){
				new ReSigninTask(SigninActivity.this).execute(username,password);
			}
		}
	};
	
	//���������Ա��½����ť
	private OnClickListener managerButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(SigninActivity.this,SigninManagerActivity.class);
			SigninActivity.this.finish();
			SigninActivity.this.startActivity(intent);
		}
	};
	
	//�������½����ť
	private OnClickListener loginButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(SigninActivity.this,LoginActivity.class);
			SigninActivity.this.finish();
			SigninActivity.this.startActivity(intent);
		}
	};
	
	//��������ˡ���ť
	private OnClickListener backButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			SigninActivity.this.finish();
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
				new ReIsLoginTask(SigninActivity.this,resultUserTextView,signinButton).execute(username);
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
			resultUserTextView.setText(SigninActivity.USERNAME_RULE);
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
			resultPassTextView.setText(SigninActivity.PASSWORD_RULE);
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
			resultRePassTextView.setText(SigninActivity.REPASSWORD_RULE);
			return false;
		}
		signinButton.setClickable(true);
		resultRePassTextView.setText("");
		return true;
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
