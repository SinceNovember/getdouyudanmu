package com.liu.bean;

 /** 
 * @ClassName: Request 
 * @author: lyd
 * @date: 2017��9��1�� ����1:51:40 
 * @describe:��������������������
 */
public class Request {
	/**  
	* @Title: gid  
	* @Description: ��һ������������͵����ݰ�
	* @param @param roomId
	* @param @param devid
	* @param @return    �趨�ļ�  
	* @return String    ��������  
	* @throws  
	*/  
	public static String gid(int roomId,String devid,String rt,String vk){
		return String.format("type@=loginreq/username@=/ct@=0/password@=/roomid@=%d/devid@=%s/rt@=%s/vk@=%s/ver@=20150929/", roomId,devid,rt,vk);
	}

	/**  
	* @Title: danmukuLogin  
	* @Description:����������͵�½��Ļ����������
	
	*/  
	public static String danmukuLogin(int roomId){
		 return String.format("type@=loginreq/username@=visitor34807350/password@=1234567890123456/roomid@=%d/", roomId);
	}
	/**  
	* @Title: joinGroup  
	* @Description:���ͼ��뵯Ļ������������
	
	*/  
	public static String joinGroup(int rid,int gid){
		return String.format("type@=joingroup/rid@=%d/gid@=%d/", rid,gid);
	}
	/**  
	* @Title: keepLive  
	* @Description:�����������ϵ���������������������Ա�������
	*/  
	public static String keepLive(int tick)
	{
		return String.format("type@=keeplive/tick@=%d/", tick);
	}
}
