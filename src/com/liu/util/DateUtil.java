package com.liu.util;


import java.text.SimpleDateFormat;
import java.util.Date;

/** 
 * @ClassName: DateUtil 
 * @author: lyd
 * @date: 2017��9��1�� ����2:19:14 
 * @describe:ʱ�乤��
 */
public class DateUtil {
	private static SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	/**  
	* @Title: datetime  
	* @Description:�������ڼ�ʱ���ַ���
	*/  
	public static String datetime(Date date){
		return formatter.format(date);
	}
	/**  
	* @Title: now  
	* @Description:���ص�ǰ��ʱ��
	*/  
	public static String now(){
		return formatter.format(new Date());
	}
	public static void main(String[] args) {
		System.out.println(now());
	}
}
