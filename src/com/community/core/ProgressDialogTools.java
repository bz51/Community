package com.community.core;

import android.app.Activity;
import android.app.ProgressDialog;


/**
 * ��ȡһ��ProgressBar
 * @author Chai
 *
 */
public class ProgressDialogTools {
	
	/**
	 * ��ȡProgressBar
	 * @param progressDialog
	 * @param title
	 * @param content
	 * @return
	 */
	public static ProgressDialog getProgressDialog(ProgressDialog progressDialog,String title,String content){
		progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//���÷��ΪԲ�ν�����
		progressDialog.setTitle(title);//���ñ���
//		progressDialog.setIcon(R.drawable.icon);//����ͼ��
		progressDialog.setMessage(content);
		progressDialog.setIndeterminate(false);//���ý������Ƿ�Ϊ����ȷ
		progressDialog.setCancelable(false);//���ò��ܱ��ر�
		progressDialog.show();
		return progressDialog;
	}
}
