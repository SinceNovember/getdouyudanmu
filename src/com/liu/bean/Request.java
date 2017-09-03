package com.liu.bean;

 /** 
 * @ClassName: Request 
 * @author: lyd
 * @date: 2017年9月1日 下午1:51:40 
 * @describe:想服务器发送请求的内容
 */
public class Request {
	/**  
	* @Title: gid  
	* @Description: 第一次向服务器发送的内容包
	* @param @param roomId
	* @param @param devid
	* @param @return    设定文件  
	* @return String    返回类型  
	* @throws  
	*/  
	public static String gid(int roomId,String devid,String rt,String vk){
		return String.format("type@=loginreq/username@=/ct@=0/password@=/roomid@=%d/devid@=%s/rt@=%s/vk@=%s/ver@=20150929/", roomId,devid,rt,vk);
	}

	/**  
	* @Title: danmukuLogin  
	* @Description:像服务器发送登陆弹幕服务器请求
	
	*/  
	public static String danmukuLogin(int roomId){
		 return String.format("type@=loginreq/username@=visitor34807350/password@=1234567890123456/roomid@=%d/", roomId);
	}
	/**  
	* @Title: joinGroup  
	* @Description:发送加入弹幕服务器的请求
	
	*/  
	public static String joinGroup(int rid,int gid){
		return String.format("type@=joingroup/rid@=%d/gid@=%d/", rid,gid);
	}
	/**  
	* @Title: keepLive  
	* @Description:心跳包，不断的像服务器发送心跳包，以保持连接
	*/  
	public static String keepLive(int tick)
	{
		return String.format("type@=keeplive/tick@=%d/", tick);
	}
}
