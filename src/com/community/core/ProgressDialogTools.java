package com.community.core;

import android.app.Activity;
import android.app.ProgressDialog;


/**
 * 获取一个ProgressBar
 * @author Chai
 *
 */
public class ProgressDialogTools {
	
	/**
	 * 获取ProgressBar
	 * @param progressDialog
	 * @param title
	 * @param content
	 * @return
	 */
	public static ProgressDialog getProgressDialog(ProgressDialog progressDialog,String title,String content){
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置风格为圆形进度条
		progressDialog.setTitle(title);//设置标题
//		progressDialog.setIcon(R.drawable.icon);//设置图标
		progressDialog.setMessage(content);
		progressDialog.setIndeterminate(false);//设置进度条是否为不明确
		progressDialog.setCancelable(false);//设置不能被关闭
		progressDialog.show();
		return progressDialog;
	}
}
