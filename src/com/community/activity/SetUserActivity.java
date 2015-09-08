package com.community.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.community.thread.SetUserTask;

public class SetUserActivity extends Activity {
	private EditText buildEditText;
	private EditText areaEditText;
	private EditText floorEditText;
	private EditText houseEditText;
	private EditText nameEditText;
	private EditText phoneEditText;
	private CheckBox isOwnerCheckBox;
	private Button submitButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_set_user);

		findView();
		setListener();
		
		//控件上显示默认数据
		showInViews();
	}
	
	/**
	 * 控件上显示默认数据
	 */
	private void showInViews() {
		Intent intent = this.getIntent();
		boolean hasDate = intent.getBooleanExtra("hasDate", false);
		if(hasDate){
			buildEditText.setText(intent.getIntExtra("build_id", 0)+"");
			areaEditText.setText(intent.getIntExtra("area_id", 0)+"");
			floorEditText.setText(intent.getIntExtra("floor_id", 0)+"");
			houseEditText.setText(intent.getIntExtra("house_id", 0)+"");
			nameEditText.setText(intent.getStringExtra("name"));
			phoneEditText.setText(intent.getStringExtra("phone"));
			int is_owner = intent.getIntExtra("house_id", 0);
			if(is_owner==1)
				isOwnerCheckBox.setChecked(true);
			else
				isOwnerCheckBox.setChecked(false);
		}
	}

	private void findView() {
		buildEditText = (EditText) this.findViewById(R.id.setUser_buildingsEditText);
		areaEditText = (EditText) this.findViewById(R.id.setUser_areaIdEditText);
		floorEditText = (EditText) this.findViewById(R.id.setUser_floorIdEditText);
		houseEditText = (EditText) this.findViewById(R.id.setUser_houseIdEditText);
		nameEditText = (EditText) this.findViewById(R.id.setUser_nameEditText);
		phoneEditText = (EditText) this.findViewById(R.id.setUser_phoneEditText);
		isOwnerCheckBox = (CheckBox) this.findViewById(R.id.setUser_isOwnerCheckBox);
		submitButton = (Button) this.findViewById(R.id.setUser_submitButton);
	}
	
	private void setListener() {
		submitButton.setOnClickListener(submitButtonClick);
	}
	
	//点击“确认”按钮
	private OnClickListener submitButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			//获取所有输入
			String build = buildEditText.getText().toString();
			String area = areaEditText.getText().toString();
			String floor = floorEditText.getText().toString();
			String house = houseEditText.getText().toString();
			String name = nameEditText.getText().toString();
			String phone = phoneEditText.getText().toString();
			String is_owner = isOwnerCheckBox.isChecked()?"0":"1";
			//若都不为空，则提交
			if(!build.equals("") && !area.equals("") && !floor.equals("") && !house.equals("")
					&& !name.equals("") && !phone.equals("")){
				new SetUserTask(SetUserActivity.this).execute(build,area,floor,house,name,phone,is_owner);
			}else{
				Toast.makeText(SetUserActivity.this, "请完善信息！", Toast.LENGTH_LONG).show();
			}
		}
	};
}
