package com.community.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.community.activity.MyListView;
import com.community.activity.R;
import com.community.core.HttpUtils;
import com.community.core.Parameter;
import com.community.core.ProgressDialogTools;
import com.community.entity.PhoneEntity;

public class GetPhoneTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private MyListView listView;
	private List<PhoneEntity> list;
	private ProgressDialog progressDialog;
	
	public GetPhoneTask(Context context,MyListView listView) {
		super();
		this.context = context;
		this.listView = listView;
		this.progressDialog = new ProgressDialog(context);
		ProgressDialogTools.getProgressDialog(progressDialog, "", "");
	}

	@Override
	protected String doInBackground(String... params) {
		SharedPreferences sharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE); 
		String community_id =sharedPreferences.getString("community_id", ""); 
		//发送
		String parameter = "?phoneEntity.community_id="+community_id;
		return HttpUtils.getJsonContent(Parameter.RE_GETPHONE+parameter);	
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//取消进度条
		progressDialog.dismiss();
		listView.onRefreshComplete();
		// 将json——>List<PhoneEntity>
		list = json2List_PhoneEntity(result);
		// 将结果显示到listview
		showInListview(list);
	}

	private void showInListview(List<PhoneEntity> list) {
		//创建数据源
				ArrayList<HashMap<String,String>> dataSource = new ArrayList<HashMap<String,String>>();
				List<PhoneEntity> origin_dataSource = list;
				if (origin_dataSource != null) {
					for (PhoneEntity entity : origin_dataSource) {
						HashMap<String, String> map = new HashMap<String, String>();
						map.put("text", entity.getText());
						map.put("phone", entity.getPhone());
						dataSource.add(map);
					}
					// 创建适配器
					SimpleAdapter listItemAdapter = new SimpleAdapter(context,
							dataSource,// 数据源
							R.layout.listview_phone,// ListItem的XML实现
							// 动态数组与ImageItem对应的子项
							new String[] { "text", "phone" },
							// ImageItem的XML文件里面的一个ImageView,两个TextView ID
							new int[] { R.id.phone_textTextView,
									R.id.phone_phoneTextView });
					// 适配器与listview绑定
					listView.setAdapter(listItemAdapter);
					listView.setOnItemClickListener(listItemClick);
				}		
	}

	private List<PhoneEntity> json2List_PhoneEntity(String result) {
		List<PhoneEntity> list = new ArrayList<PhoneEntity>();
		JSONObject object;
		try {
			object = new JSONObject(result);
			JSONArray array = object.getJSONArray("phoneList");
			for(int i = 0 ; i < array.length() ; i++){
				PhoneEntity entity = new PhoneEntity();
				JSONObject o = array.getJSONObject(i);
				entity.setCommunity_id(o.getInt("community_id"));
				entity.setPhone(o.getString("phone"));
				entity.setText(o.getString("text"));
				entity.setId(o.getLong("id"));
				//当前PhoneEntity放入list
				list.add(entity);
			}
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.NULL, Toast.LENGTH_LONG).show();
		}
		return list;
	}
	
	//点击ListItem
		private OnItemClickListener listItemClick = new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
				String phone = list.get(position).getPhone();
				Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:"+phone));  
                context.startActivity(intent);  
			}
			
		};
}
