package com.community.thread;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.community.activity.R;
import com.community.activity.SetUserActivity;
import com.community.activity.SigninManagerActivity;
import com.community.core.HttpUtils;
import com.community.core.Pagination;
import com.community.core.Parameter;
import com.community.core.URLEcoder;
import com.community.entity.CommunityEntity;
import com.community.entity.TopicEntity;

public class SearchCommunityTask extends AsyncTask<String, Integer, String> {
	private Context context;
	private ListView listview;
	private Pagination pagination;
	private int lastIndex = 0;
	private TextView loadMoreTextView;
	private ProgressBar progressBar;
	
	public SearchCommunityTask(Context context, ListView listview,Pagination pagination,ProgressBar progressBar,TextView loadMoreTextView) {
		super();
		this.context = context;
		this.listview = listview;
		this.progressBar = progressBar;
		this.loadMoreTextView = loadMoreTextView;
		//��ʼ��pagination
		this.pagination = pagination;
		//��������ʼת
		progressBar.setVisibility(View.VISIBLE);
		loadMoreTextView.setVisibility(View.GONE);
	}

	@Override
	protected String doInBackground(String... params) {
		//���ؼ��ֱ���
		String keyword = URLEcoder.ecode(pagination.queryKeyWord);
		String parameter = "?communityEntity.name="+keyword+"&pagination.currPage="+pagination.getCurrPage();
		return HttpUtils.getJsonContent(Parameter.RE_GETCOMMUNITY+parameter);		
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
		//��ȡ��ʱlistviewλ��
		lastIndex = listview.getFirstVisiblePosition();
		//��jsonString����>pagination
		pagination = json2Pagination_Community(result);
		//�������ʾ��listview
		showInListview(pagination);
		//��������ʧ
		progressBar.setVisibility(View.GONE);
		loadMoreTextView.setVisibility(View.VISIBLE);
	}


	/**
	 * ��jsonString����>pagination
	 * @param result
	 * @return
	 */
	private Pagination json2Pagination_Community(String result) {
		try {
			JSONObject o = new JSONObject(result);
			pagination.setCurrPage(o.getLong("currPage"));
			pagination.setMaxPage(o.getLong("maxPage"));
			pagination.setMaxRowCount(o.getLong("maxRowCount"));
			pagination.setRowsPerPage(o.getInt("rowsPerPage"));
			
			List<CommunityEntity> list = null;
//			if(!pagination.queryKeyWord.equals(""))
//				list = pagination.getData();
			if(list==null)
				list = new ArrayList<CommunityEntity>();
			JSONArray array = new JSONArray(o.getString("data"));
			for(int i=0;i<array.length();i++){
				JSONObject object = array.getJSONObject(i);
				CommunityEntity communityEntity = new CommunityEntity();
				communityEntity.setId(object.getLong("id"));
				communityEntity.setBuildings(object.getInt("buildings"));
				communityEntity.setCity_id(object.getLong("city_id"));
				communityEntity.setName(object.getString("name"));
				communityEntity.setCity(object.getString("city"));
				communityEntity.setResidents(object.getInt("residents"));
				communityEntity.setState(object.getInt("state"));
				list.add(communityEntity);
			}
			pagination.setData(list);
		} catch (JSONException e) {
			Toast.makeText(context, Parameter.SEVER_EXCEPTION, Toast.LENGTH_LONG).show();
		}

		return pagination;
	}
	
	/**
	 * �������ʾ��listview
	 * @param pagination2
	 */
	private void showInListview(Pagination pagination) {
		//��������Դ
		ArrayList<HashMap<String,String>> dataSource =  new ArrayList<HashMap<String,String>>();
		List<CommunityEntity> origin_dataSource = pagination.getData();
		if (origin_dataSource != null) {
			for (CommunityEntity entity : origin_dataSource) {
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("communityName", entity.getName());
				map.put("communityCity", entity.getCity());
				dataSource.add(map);
			}
			// ����������
			SimpleAdapter listItemAdapter = new SimpleAdapter(context,
					dataSource,// ����Դ
					R.layout.listview_select_community,// ListItem��XMLʵ��
					// ��̬������ImageItem��Ӧ������
					new String[] { "communityName", "communityCity" },
					// ImageItem��XML�ļ������һ��ImageView,����TextView ID
					new int[] { R.id.selectCommunity_communityNameTextView,
							R.id.selectCommunity_communityLocTextView });
			// ��������listview��
			listview.setAdapter(listItemAdapter);
			// ��ӵ���¼�
			listview.setOnItemClickListener(listItemClick);
			listview.setSelection(lastIndex);
		}
	}
	
	
	//���ListItem
	private OnItemClickListener listItemClick = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,long id) {
			//��ȡcommunity_id
			long community_id = ((CommunityEntity)pagination.getData().get(position)).getId();
			//��community_id����share
			SharedPreferences mySharedPreferences= context.getSharedPreferences("community",context.MODE_PRIVATE);//ʵ����SharedPreferences���󣨵�һ����  
			SharedPreferences.Editor editor = mySharedPreferences.edit();//ʵ����SharedPreferences.Editor���󣨵ڶ�����  
			editor.putString("community_id", community_id+""); //��putString�ķ����������� 
			editor.putString("community_name", ((CommunityEntity)pagination.getData().get(position)).getName()); //��putString�ķ����������� 
			editor.commit();//�ύ��ǰ���� 
			//��ȡfromWhere
			String fromWhere = ((Activity) context).getIntent().getStringExtra("fromWhere");
			Toast.makeText(context, "fromWhere:"+fromWhere, Toast.LENGTH_LONG).show();
			//�ӡ�����Աע��ҳ�桱����
			if(Parameter.FROM_MA_SIGNIN_ACTIVITY.equals(fromWhere)){
				Intent intent = new Intent(context,SigninManagerActivity.class);
				Intent intent_old = ((Activity) context).getIntent();
				intent.putExtra("hasData", true);
				intent.putExtra("username",intent_old.getStringExtra("username"));
				intent.putExtra("password",intent_old.getStringExtra("password"));
				intent.putExtra("community_name",((CommunityEntity)pagination.getData().get(position)).getName());
				((Activity) context).finish();
				context.startActivity(intent);
				return;
			}
			//�ӡ�ס��ע��ҳ�������
			Intent intent = new Intent(context,SetUserActivity.class);
			context.startActivity(intent);
		}
		
	};
	
	

}
