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
	private static final String USERNAME_RULE = "用户名必须是3-16位英文、数字";
	private static final String PASSWORD_RULE = "用户名必须是3-16位英文、数字";
	private static final String REPASSWORD_RULE = "两次密码不一致";
	

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
	
	//点击“注册”按钮
	private OnClickListener signinButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			String username = usernameEditText.getText().toString();
			String password = passwordEditText.getText().toString();
			String repassword = repasswordEditText.getText().toString();
			//若username、password、repassword都填了，则注册
			if(!username.equals("") && !password.equals("") && !repassword.equals("")){
				new ReSigninTask(SigninActivity.this).execute(username,password);
			}
		}
	};
	
	//点击“管理员登陆”按钮
	private OnClickListener managerButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(SigninActivity.this,SigninManagerActivity.class);
			SigninActivity.this.finish();
			SigninActivity.this.startActivity(intent);
		}
	};
	
	//点击“登陆”按钮
	private OnClickListener loginButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(SigninActivity.this,LoginActivity.class);
			SigninActivity.this.finish();
			SigninActivity.this.startActivity(intent);
		}
	};
	
	//点击“后退”按钮
	private OnClickListener backButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			SigninActivity.this.finish();
		}
	};
	
	//usernameEditText失去焦点
	private OnFocusChangeListener usernameEditTextFocus = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus){
				String username = usernameEditText.getText().toString();
				//验证username是否符合要求
				if(!verifyUsername(username))
					return ;
				
				//判断用户名是否被注册掉
				new ReIsLoginTask(SigninActivity.this,resultUserTextView,signinButton).execute(username);
			}
		}
	};
	
	//passwordEditText失去焦点
	private OnFocusChangeListener passwordEditTextFocus = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus){
				String password = passwordEditText.getText().toString();
				//验证password是否符合要求
				if(!verifyPassword(password))
					return ;
			}
		}
	};
	
	//repasswordEditText失去焦点
	private OnFocusChangeListener repasswordEditTextFocus = new OnFocusChangeListener() {
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus){
				String repassword = repasswordEditText.getText().toString();
				String password = passwordEditText.getText().toString();
				//验证repassword是否符合要求
				if(!verifyRePassword(repassword,password))
					return ;
			}
		}
	};
	
	

	/**
	 * 验证用户名是否符合条件
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
	 * 验证密码是否符合条件
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
	 * 验证两次密码是否一致
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
