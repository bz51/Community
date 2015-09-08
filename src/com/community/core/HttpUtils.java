package com.community.core;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

import android.util.Log;

/**
 * 发送请求、提交表单
 * @author Chai
 *
 */
public class HttpUtils {
	//URL的header
//	public static final String URLHEADER = "http://192.168.1.100:8080/CommunityServer/";
	public static final String URLHEADER = "http://115.28.217.42/CommunityServer/";
//	public static final String URLHEADER = "http://10.0.2.2:8080/CommunityServer/";
	
	/**
	 * 将url提交给服务器，返回一个jsonString
	 * @param url_path url
	 * @return jsonString
	 */
	public static String getJsonContent(String url_path) {
		//为url自动加上域名
		url_path = URLHEADER+url_path;
		Log.i("my", url_path);
		
		try {
			URL url = new URL(url_path);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(30000);
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
//			connection.connect();//自己加的
			int code = connection.getResponseCode();
			if (code == 200) {
				return changeInputStream(connection.getInputStream());
			}
		} catch (Exception e) {
			Log.e("error:", e.getMessage());
		}
		return "error";
	}

	private static String changeInputStream(InputStream inputStream) {
		String jsonString = "";
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		int len = 0;
		byte[] data = new byte[1024];
		try {
			while ((len = inputStream.read(data)) != -1) {
				outputStream.write(data, 0, len);
			}
			jsonString = new String(outputStream.toByteArray());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Log.i("my", "json:"+jsonString);
		return jsonString;
	}
	


	/**
	 * 发送POST请求
	 * @param path 请求路径
	 * @param params 请求参数
	 * @return
	 */
	public static boolean sendPOSTRequest(String path, Map<String, String> params, String encoding) throws Exception{
		//  title=liming&length=30
		StringBuilder sb = new StringBuilder();
		if(params!=null && !params.isEmpty()){
			for(Map.Entry<String, String> entry : params.entrySet()){
				sb.append(entry.getKey()).append("=");
				sb.append(URLEncoder.encode(entry.getValue(), encoding));
				sb.append("&");
			}
			sb.deleteCharAt(sb.length() - 1);
		}
		byte[] data = sb.toString().getBytes();
		
		HttpURLConnection conn = (HttpURLConnection) new URL(path).openConnection();
//		conn.setConnectTimeout(5000);
		conn.setRequestMethod("POST");
		conn.setDoOutput(true);//允许对外传输数据
		conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
		conn.setRequestProperty("Content-Length", data.length+"");
		OutputStream outStream = conn.getOutputStream();
		outStream.write(data);
		outStream.flush();
		if(conn.getResponseCode() == 200){
			return true;
		}
		return false;
	}

	
}
