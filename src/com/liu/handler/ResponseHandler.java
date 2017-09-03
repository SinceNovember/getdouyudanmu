package com.liu.handler;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.liu.bean.Danmuku;
import com.liu.bean.ServerInfo;
import com.liu.util.LogUtil;


/** 
 * @ClassName: ResponseHandler 
 * @author: lyd
 * @date: 2017��9��2�� ����10:16:47 
 * @describe:�����ͻ�������Ϣ
 */
public class ResponseHandler {
	 private static final String regex_room_id = "\"room_id\":(\\d*),";
	    private static final String regex_room_status = "\"show_status\":(\\d*),";
	    private static final String regex_server = "%7B%22ip%22%3A%22(.*?)%22%2C%22port%22%3A%22(.*?)%22%7D%2C";
	    private static final String regex_group_id = "type@=setmsggroup.*/rid@=(\\d*?)/gid@=(\\d*?)/";
	    private static final String regex_danmaku_server = "/ip@=(.*?)/port@=(\\d*?)/";
	    private static final String regex_chat_danmaku = "type@=chatmsg/.*rid@=(\\d*?)/.*uid@=(\\d*).*nn@=(.*?)/txt@=(.*?)/(.*)/";
	    private static Matcher getMatcher(String content,String regx){
	    	Pattern pattern=Pattern.compile(regx,Pattern.DOTALL);
	    	return pattern.matcher(content);
	    }
	    /**  
	    * @Title: parseRoomId  
	    * @Description:��������id
	    */  
	    public static int parseRoomId(String content){
	    	int rid=-1;
	    	if(content==null) return rid;
	    	Matcher matcher=getMatcher(content, regex_room_id);
	    	if(matcher.find()){
	    		rid=Integer.parseInt(matcher.group(1));
	    	}
//	    	LogUtil.d("Parse RoomId",rid+"");
	    	return rid;
	    }
	    /**  
	    * @Title: parseOnline  
	    * @Description: �����Ƿ�����
	    */  
	    public static boolean parseOnline(String content){
	    	if(content==null) return false;
	    	Matcher matcher=getMatcher(content, regex_room_status);
	    	return matcher.find()&&"1".equals(matcher.group(1));
	    }
	    /**  
	    * @Title: parseServerInfo  
	    * @Description: �����������ĵ�ַ��˿�
	    */  
	    public static List<ServerInfo> parseServerInfo(String content){
	    	if(content==null) return null;
	    	Matcher matcher=getMatcher(content, regex_server);
	    	List<ServerInfo> serverList=new ArrayList<ServerInfo>();
	    	while(matcher.find()){
	    		ServerInfo serverInfo=new ServerInfo(matcher.group(1), Integer.parseInt(matcher.group(2)));
	    		serverList.add(serverInfo);
//	    		LogUtil.d("Parse ServerInfo",serverInfo.toString());
	    	}
	    	return serverList;
	    }
	    public static List<ServerInfo> parseDanmukuServer(String content){
	    	if(content==null) return null;
	    	Matcher matcher=getMatcher(content,regex_danmaku_server);
	    	List<ServerInfo> serverList=new ArrayList<ServerInfo>();
	    	while(matcher.find())
	    	{
	    		ServerInfo serverInfo=new ServerInfo(matcher.group(1), Integer.parseInt(matcher.group(2)));
	    		serverList.add(serverInfo);
//	    		 LogUtil.d("Parse DanmakuServer", serverInfo.toString());
	    	}
	    	return serverList;
	    }
	    /**  
	    * @Title: ParseID  
	    * @Description:����id
	    */  
	    public static int ParseID(String response){
	    	if(response==null)
	    		return -1;
	    	Matcher matcher=getMatcher(response, regex_group_id);
	    	int gid=-1;
	    	if(matcher.find()){
	    		gid=Integer.parseInt(matcher.group(2));
	    	}
//	    	 LogUtil.d("Parse ID", "GId -> " + gid);

	         return gid;
	    }
	    public static Danmuku parseDanmaku(String response) {
	        if (response == null) return null;

	        Matcher matcher = getMatcher(response, regex_chat_danmaku);
	        Danmuku danmuku = null;
	        if(matcher.find()){
	        	danmuku=new Danmuku(Integer.parseInt(matcher.group(2)), matcher.group(3), matcher.group(4), Integer.parseInt(matcher.group(1)));
	        }
//	        LogUtil.d("Parse Danmaku", danmuku + "");

	        return danmuku;
	    }
}
