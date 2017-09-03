package com.liu.Config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.liu.util.LogUtil;

public class Config {
	 private static final String properties_name="conf.properties";
	 public static boolean loadSuccess=false;
	 public static boolean debug_mode=false;
	 public static boolean db_enable=false;
	 public static String db_name=null;
	 public static String db_username=null;
	 public static String db_password=null;
	 public static Map<String, String> room_map=new HashMap<String,String>();
	 static{
		 InputStream inputStream=null;
		 try{
			 Properties properties=new Properties();
			 inputStream=new FileInputStream(new File(properties_name));
			 properties.load(inputStream);
			 debug_mode=Boolean.parseBoolean(properties.getProperty("debug"));
			 db_enable=Boolean.parseBoolean(properties.getProperty("db.enable"));
			 db_username=properties.getProperty("db.username");
			 db_password=properties.getProperty("db.password");
			 Set<Object> objects=properties.keySet();
			 for(Object object:objects){
				 String key=((String) object).trim();
				 if(!key.startsWith("room.url.")||key.length()<=9) continue;
				 room_map.put(key.substring(9), properties.getProperty(key));
			 }
			 loadSuccess=true;
			 LogUtil.i("读取配置信息成功！");
//			 shoswConfig();
		 }catch (Exception e) {
			LogUtil.e(e.toString());
			LogUtil.w("读取配置信息失败！");
		}finally {
			if(inputStream!=null)
			{
				try{
					inputStream.close();
				}catch (IOException e) {
					LogUtil.e(e.toString());
				}
				
			}
		}
	 }
//	 private static void showConfig(){
//		 LogUtil.d("Config","--------------------------------");
//		 LogUtil.d("Config","debug_mode:"+debug_mode);
//		 LogUtil.d("Config","db_enable:"+db_enable);
//		 LogUtil.d("Config","debug_mode:"+db_username);
//		 LogUtil.d("Config","db_enable:"+db_password);
//		 Set<String> nameset=room_map.keySet();
//		 for(String name:nameset){
//			 LogUtil.d("Config","Room_URL:"+name+">>"+room_map.get(name));
//		 }
//		 LogUtil.d("Config","-------------------------------");
//	 }
	 public static void main(String[] args) {
		
	}
}
