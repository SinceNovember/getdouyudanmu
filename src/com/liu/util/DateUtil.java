package com.liu.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * @ClassName: DateUtil 
 * @author: lyd
 * @date: 2017年9月1日 下午2:19:14 
 * @describe:时间工具
 */
public class DateUtil {
	private static SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**  
	* @Title: datetime  
	* @Description:返回日期家时间字符串
	*/  
	public static String datetime(Date date){
		return formatter.format(date);
	}
	/**  
	* @Title: now  
	* @Description:返回当前的时间
	*/  
	public static String now(){
		return formatter.format(new Date());
	}
	public static void main(String[] args) {
		System.out.println(now());
	}
}
