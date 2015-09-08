package com.community.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.community.activity.R;
import com.community.adapter.AuthorAdapter;
import com.community.core.HttpUtils;
import com.community.core.Pagination;
import com.community.core.Parameter;
import com.community.core.ProgressDialogTools;
import com.community.entity.ManagerEntity;

public class GetAuthorTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private ListView listview;
	private int currPage = 1;
	private Button preButton;
	private Button nextButton;
	private Pagination pagination;
	private ProgressDialog progressDialog;
	
	public GetAuthorTask(Context context, ListView listview,int currPage,Button preButton,Button nextButton) {
		super();
		this.context = context;
		this.listview = listview;
		this.currPage = currPage;
		this.preButton = preButton;
		this.nextButton = nextButton;
		if(pagination==null)
			pagination = new Pagination();
		this.progressDialog = new ProgressDialog(context);
		ProgressDialogTools.getProgressDialog(progressDialog, "", "");
	}

	@Override
	protected String doInBackground(String... params) {
		//获取community_id
		SharedPreferences sharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE); 
		String community_id =sharedPreferences.getString("community_id", ""); 
		//发送
		String parameter = "?managerEntity.community_id="+community_id+"&pagination.currPage="+currPage;
		return HttpUtils.getJsonContent(Parameter.MA_GETAUTHORS+parameter);		
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//取消进度条
		progressDialog.dismiss();
		//将json——>pagination
		pagination = json2Pagination_ManagerEntity(result);
		//将结果显示到listview
		showInListview2(pagination);
		//隐藏“上页”、“下页”按钮
		preButton.setVisibility(View.VISIBLE);
		nextButton.setVisibility(View.VISIBLE);
		if(pagination.getCurrPage()>=pagination.getMaxPage()){
			nextButton.setVisibility(View.INVISIBLE);
		}
		if(pagination.getCurrPage()<=1){
			preButton.setVisibility(View.INVISIBLE);
		}
	}

	/**
	 * @param result
	 * @return
	 */
	private Pagination json2Pagination_ManagerEntity(String result) {
		try {
			JSONObject object = new JSONObject(result).getJSONObject("pagination");
			pagination.setCurrPage(object.getLong("currPage"));
			pagination.setMaxPage(object.getLong("maxPage"));
			pagination.setMaxRowCount(object.getLong("maxRowCount"));
			pagination.setRowsPerPage(object.getInt("rowsPerPage"));

			List<ManagerEntity> list = new ArrayList<ManagerEntity>();
			JSONArray array = new JSONArray(object.getString("data"));
			for(int i = 0 ; i < array.length() ; i++){
				ManagerEntity entity = new ManagerEntity();
				JSONObject o = array.getJSONObject(i);
				entity.setCommunity_id(o.getLong("community_id"));
				entity.setId(o.getLong("id"));
				entity.setIs_super(o.getInt("is_super"));
				entity.setPassword(o.getString("password"));
				entity.setState(o.getInt("state"));
				entity.setUsername(o.getString("username"));
				//获取时间
				String time = o.getString("time");
				//将json——>Timestamp
//				entity.setTime(json2Timestamp(time));
				//当前ManagerEntity放入list
				list.add(entity);
			}
			pagination.setData(list);
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
		}
		return pagination;
	}
	
	

	/**
	 * 【弃用，现用showInListview2】
	 * @param pagination
	 */
	private void showInListview(Pagination pagination) {
		//创建数据源
		ArrayList<HashMap<String,String>> dataSource =  new ArrayList<HashMap<String,String>>();
		List<ManagerEntity> origin_dataSource = pagination.getData();
		if (origin_dataSource != null) {
			for (ManagerEntity entity : origin_dataSource) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("username", entity.getUsername());
				map.put("time", entity.getTime() + "");
				dataSource.add(map);
			}
			// 创建适配器
			SimpleAdapter listItemAdapter = new SimpleAdapter(context,
					dataSource,// 数据源
					R.layout.listview_author,// ListItem的XML实现
					// 动态数组与ImageItem对应的子项
					new String[] { "username", "time" },
					// ImageItem的XML文件里面的一个ImageView,两个TextView ID
					new int[] { R.id.author_usernameTextView,
							R.id.author_timeTextView });
			// 适配器与listview绑定
			listview.setAdapter(listItemAdapter);
			// 添加点击事件
			listview.setOnItemClickListener(listItemClick);
		}
	}
	
	
	private void showInListview2(Pagination pagination) {
		AuthorAdapter adapter = new AuthorAdapter(pagination.getData(),context);
		//适配器与listview绑定
		listview.setAdapter(adapter);
	}
	
	// 点击ListItem【弃用】
	private OnItemClickListener listItemClick = new OnItemClickListener() {
		private Button authorizeButton;
		private Button refuseButton;
		private TextView successTextView;
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			//获取“授权”“拒绝”按钮
			authorizeButton = (Button) view.findViewById(R.id.author_authorizeButton);
			refuseButton = (Button) view.findViewById(R.id.author_refuseButton);
			successTextView = (TextView) view.findViewById(R.id.author_successTextView);
			//添加按钮事件
			authorizeButton.setOnClickListener(authorizeButtonClick);
			refuseButton.setOnClickListener(refuseButtonClick);
		}
		
		//点击“授权”按钮
		private OnClickListener authorizeButtonClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				currPage++;
				new AuthorizeTask(context,authorizeButton,refuseButton,successTextView).execute();
			}
		};
		
		//点击“拒绝”按钮
		private OnClickListener refuseButtonClick = new OnClickListener() {
			@Override
			public void onClick(View v) {
				
			}
		};

	};
}


