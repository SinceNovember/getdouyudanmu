package com.liu.util;

import java.util.HashMap;
import java.util.Map;

/** 
 * @ClassName: SttCode 
 * @author: lyd
 * @date: 2017年9月1日 下午7:52:04 
 * @describe:对@=进行编码解码
 */
public class SttCode {
	public static String encode(String content){
		if(content==null||content.length()<=0) 
			return null;
		return null;
	}
	public static Map<String, String> decode(String content){
		if(content==null||content.length()<=0) return null;
		String[] strs=content.split("/");
		Map<String,String>map =new HashMap<String,String>();
		for(String str:strs){
			if(str.contains("@=")){
				String[] kv=str.split("@=");
				if(kv.length<2) kv=new String[]{kv[0],""};
				map.put(deFilterStr(kv[0]),deFilterStr(kv[1]));
			}
			else
			{
				map.put("",deFilterStr(str));
			}
		}
			return map;
	}
	public static String deFilterStr(String str){
		if(str==null) return null;
		return str.trim().replace("@A", "@").replace("@S", "/");
	}
	public static String filterStr(String str){
		if(str==null) return null;
		return str.trim().replace("@", "@A").replace("/", "@S");
	}
	public static String displayMap(Map<String, String>map)
	{
		StringBuilder sb=new StringBuilder();
		for(String s:map.keySet())
		{
			sb.append(s).append(":").append(map.get(s)).append("\n");
		}
		return sb.toString();
	}
}
