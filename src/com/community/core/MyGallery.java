package com.community.core;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.widget.Gallery;

/**
 * �Զ��廭�ȣ�ʵ��һ��ֻ����һ��ͼƬ���޹���
 * 
 * @author Feng
 * 
 */
public class MyGallery extends Gallery {

    public MyGallery(Context context, AttributeSet attrs) {  

        super(context, attrs);  

        // TODO Auto-generated constructor stub  

    } 


	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,float velocityY) {

        return false; 

	}

}
