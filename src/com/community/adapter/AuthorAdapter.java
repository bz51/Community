package com.community.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.community.activity.R;
import com.community.entity.ManagerEntity;
import com.community.thread.AuthorizeTask;

public class AuthorAdapter extends BaseAdapter {
	private List<ManagerEntity> list; 
	private Context context;
	private TextView usernameTextView;
	private TextView timeTextView;
	private List<TextView> successTextViewList = new ArrayList<TextView>();
	private List<Button> authorizeButtonList = new ArrayList<Button>();
	private List<Button> refuseButtonList = new ArrayList<Button>();
	private int position;
	
	public AuthorAdapter(List<ManagerEntity> list, Context context) {
		super();
		this.list = list;
		this.context = context;
	}

	/**
	 * 适配器根据getCount()函数来确定要加载多少项
	 */
	@Override
	public int getCount() {
		return list.size();
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

	/**
	 * 当列表里的每一项显示到界面时，都会调用这个方法一次，并返回一个view 所以方法里面尽量要简单，不要做没必要的动作
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		this.position = position;
		convertView = LayoutInflater.from(context).inflate(R.layout.listview_author, null);   
		usernameTextView = (TextView)convertView.findViewById(R.id.author_usernameTextView);   
		timeTextView = (TextView)convertView.findViewById(R.id.author_timeTextView);   
        TextView successTextView = (TextView)convertView.findViewById(R.id.author_successTextView);
        Button authorizeButton = (Button)convertView.findViewById(R.id.author_authorizeButton);
        Button refuseButton = (Button)convertView.findViewById(R.id.author_refuseButton);
        successTextViewList.add(successTextView);
        authorizeButtonList.add(authorizeButton);   
        refuseButtonList.add(refuseButton);
        //设置当前button的索引
        authorizeButton.setTag(position);
		
        addlistener(authorizeButton);
        
        showInViews();
        
	    return convertView;
	}

//	private void findviews(View convertView) {
//		convertView = LayoutInflater.from(context).inflate(R.layout.listview_author, null);   
//		usernameTextView = (TextView)convertView.findViewById(R.id.author_usernameTextView);   
//		timeTextView = (TextView)convertView.findViewById(R.id.author_timeTextView);   
//        successTextView = (TextView)convertView.findViewById(R.id.author_successTextView);   
//        authorizeButton = (Button)convertView.findViewById(R.id.author_authorizeButton);   
//        refuseButton = (Button)convertView.findViewById(R.id.author_refuseButton);
//		
//	}
	
	private void addlistener(Button authorizeButton) {
		authorizeButton.setOnClickListener(authorizeButtonClick);
	}
	
	private void showInViews() {
		usernameTextView.setText(list.get(position).getUsername());
		timeTextView.setText(list.get(position).getTime()+"");
	}

	private OnClickListener authorizeButtonClick = new OnClickListener() {
		@Override
		public void onClick(View v) {
			int position = (Integer) v.getTag();
			Log.i("my", position+"");
			new AuthorizeTask(context,authorizeButtonList.get(position),refuseButtonList.get(position),successTextViewList.get(position)).execute(list.get(position).getId()+"");
		}
	};

}
