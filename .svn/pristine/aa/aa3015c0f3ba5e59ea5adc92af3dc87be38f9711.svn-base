package com.community.core;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Utils {
	
	/**
	 * 将String——>Timestamp
	 * @param time
	 * @return
	 */
	public static Timestamp string2Timestamp(String time) {
		String time_old = new String(time);
		String time_new = time_old.replace("T", "#");
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd#HH:mm:ss");
		Timestamp timeStamp = null;
		try {
			Date date = sdf.parse(time_new);
			timeStamp = new Timestamp(date.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeStamp;
	}
	
	/**
	 * 将Timestamp——>用户容易看到的String类型
	 */
	public static String timestamp2String(Timestamp time_old){
		long time = time_old.getTime();
		long now = new Date().getTime();
		//time<1h，显示xx分钟
		if((now-time)/1000/3600<=1){
			return (now-time)/1000/60+"分钟前";
		}
		//1h<time<24h，显示xx小时
		else if((now-time)/1000/3600>1 && (now-time)/1000/3600<=24){
			return (now-time)/1000/60/60+"小时前";
		}
		//24h<time<24hX7，显示xx天前
		else if((now-time)/1000/3600>24 && (now-time)/1000/3600<=24*7){
			return (now-time)/1000/60/60/24+"天前";
		}
		//time>7天，显示3月23日
		else{
			SimpleDateFormat sdf=new SimpleDateFormat("MM-dd");
			return sdf.format(time);
		}
	}
}
