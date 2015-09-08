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
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.community.activity.MyListView;
import com.community.activity.R;
import com.community.core.HttpUtils;
import com.community.core.Parameter;
import com.community.core.ProgressDialogTools;
import com.community.core.Utils;
import com.community.entity.InfoEntity;

public class GetInfoTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private MyListView listview;
	private ProgressDialog progressDialog;
	
	public GetInfoTask(Context context, MyListView listview) {
		super();
		this.context = context;
		this.listview = listview;
		this.progressDialog = new ProgressDialog(context);
		ProgressDialogTools.getProgressDialog(progressDialog, "", "");
	}

	@Override
	protected String doInBackground(String... params) {
		//获取当前用户id
		SharedPreferences sharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE); 
		String user_id =sharedPreferences.getString("user_id", ""); 
		//发送
		String parameter = "?residentEntity.id="+user_id;
		return HttpUtils.getJsonContent(Parameter.RE_GETMYINFO+parameter);		
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//取消进度条
		progressDialog.dismiss();
		listview.onRefreshComplete();
		//将json——>List<InfoEntity>
		List<InfoEntity> list = json2List_InfoEntity(result);
		//将结果显示到listview
		showInListview(list);
	}

	/**
	 * json——>List<InfoEntity>
	 * @param result
	 * @return
	 */
	private List<InfoEntity> json2List_InfoEntity(String result) {
		List<InfoEntity> list = new ArrayList<InfoEntity>();
		JSONObject object;
		try {
			object = new JSONObject(result);
			JSONArray array = object.getJSONArray("infoList");
			for(int i = 0 ; i < array.length() ; i++){
				InfoEntity entity = new InfoEntity();
				JSONObject o = array.getJSONObject(i);
				entity.setArea_id(o.getInt("area_id"));
				entity.setBuilding_id(o.getInt("building_id"));
				entity.setCommunity_id(o.getInt("community_id"));
				entity.setContent(o.getString("content"));
				entity.setFloor_id(o.getInt("floor_id"));
				entity.setHouse_id(o.getInt("house_id"));
				entity.setId(o.getLong("id"));
				entity.setPost_id(o.getLong("post_id"));
				//获取时间
				entity.setTime(Utils.string2Timestamp(o.getString("time")));
				//当前InfoEntity放入list
				list.add(entity);
			}
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.NULL, Toast.LENGTH_LONG).show();
		}
		return list;
	}
	

	/**
	 * 
	 * @param list
	 */
	private void showInListview(List<InfoEntity> list) {
		//创建数据源
		ArrayList<HashMap<String,String>> dataSource = new ArrayList<HashMap<String,String>>();
		List<InfoEntity> origin_dataSource = list;
		if (origin_dataSource != null) {
			for (InfoEntity entity : origin_dataSource) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("infoContent", entity.getContent());
				map.put("infoTime", Utils.timestamp2String(entity.getTime()));
				dataSource.add(map);
			}
			// 创建适配器
			SimpleAdapter listItemAdapter = new SimpleAdapter(context,
					dataSource,// 数据源
					R.layout.listview_info,// ListItem的XML实现
					// 动态数组与ImageItem对应的子项
					new String[] { "infoContent", "infoTime" },
					// ImageItem的XML文件里面的一个ImageView,两个TextView ID
					new int[] { R.id.info_contentTextView,
							R.id.info_timeTextView });
			// 适配器与listview绑定
			listview.setAdapter(listItemAdapter);
		}
	}
	

}
