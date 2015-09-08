package com.community.activity;

import com.community.thead.MaLoginTask;
import com.community.thead.ReLoginTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginManagerActivity extends Activity {
	private Button loginButton;
	private Button signinButton;
	private Button managerButton;
	private Button backButton;
	private EditText usernameEditText;
	private EditText passwordEditText;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_login_manager);
		
		findView();
		setListener();
	}

	private void findView() {
		loginButton = (Button) this.findViewById(R.id.loginManager_loginButton);
		signinButton = (Button) this.findViewById(R.id.loginManager_signinButton);
		managerButton = (Button) this.findViewById(R.id.loginManager_residentButton);
		backButton = (Button) this.findViewById(R.id.loginManager_backButton);
		usernameEditText = (EditText) this.findViewById(R.id.loginManager_username);
		passwordEditText = (EditText) this.findViewById(R.id.loginManager_password);
	}
	

	private void setListener() {
		loginButton.setOnClickListener(loginButtonClick);
		signinButton.setOnClickListener(signinButtonClick);
		managerButton.setOnClickListener(managerButtonClick);
		backButton.setOnClickListener(backButtonClick);
	}
	
	//点击“登陆”按钮
	private OnClickListener loginButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//获取username、password
			String username = usernameEditText.getText().toString();
			String password = passwordEditText.getText().toString();
			//验证username、password是否为空
			if(username.equals("") || password.equals("")){
				Toast.makeText(getApplicationContext(), "用户名密码不能为空！", Toast.LENGTH_LONG).show();
				return;
			}
			//登陆
			new MaLoginTask(LoginManagerActivity.this).execute(username,password);
		}
	};
	
	//点击“注册”按钮
	private OnClickListener signinButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(LoginManagerActivity.this,SigninManagerActivity.class);
			LoginManagerActivity.this.finish();
			startActivity(intent);
		}
	};
	
	//点击“住户登陆”按钮
	private OnClickListener managerButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			Intent intent = new Intent(LoginManagerActivity.this,LoginActivity.class);
			LoginManagerActivity.this.finish();
			startActivity(intent);
		}
	};
	
	//点击“后退”按钮
	private OnClickListener backButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			LoginManagerActivity.this.finish();
		}
	};
}
