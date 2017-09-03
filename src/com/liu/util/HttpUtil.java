package com.liu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtil {
	public static String get(String url){
		HttpURLConnection conn=null;
		try{
			conn=(HttpURLConnection) new URL(url).openConnection();
			conn.setConnectTimeout(10*1000);
			conn.setReadTimeout(20*1000);
			conn.setRequestMethod("GET");
			InputStream inputStream=conn.getInputStream();
//			LogUtil.d("HTTP GET",url);
			BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
			String line;
			StringBuilder stringBuilder=new StringBuilder();
			while((line=bufferedReader.readLine())!=null){
				stringBuilder.append(line);
			}
			bufferedReader.close();
			inputStream.close();
			return stringBuilder.toString();
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(conn!=null){
				conn.disconnect();
			}
		}
		return null;
	}
}
