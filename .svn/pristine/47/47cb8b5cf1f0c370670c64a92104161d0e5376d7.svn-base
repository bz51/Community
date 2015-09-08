package com.community.adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Gallery;
import android.widget.ImageView;

import com.community.activity.R;

public class ImageAdapter extends BaseAdapter {
	public static int[] imageIds = {R.drawable.focus1,R.drawable.focus2,R.drawable.focus3,R.drawable.focus4};
	private Context context;
	
	public ImageAdapter(Context context) {
		super();
		this.context = context;
	}

	@Override
	public int getCount() {
		return imageIds.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ImageView imageView = new ImageView(context);
		imageView.setImageResource(imageIds[position]);
		imageView.setScaleType(ImageView.ScaleType.CENTER);
//		imageView.setLayoutParams(new Gallery.LayoutParams(163,106));
//		imageView.setBackgroundResource(mGalleryItemBackground);
		return imageView;
	}

}
