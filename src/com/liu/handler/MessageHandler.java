package com.liu.handler;


import java.awt.image.BufferedImageFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.liu.bean.Message;
import com.liu.util.HexUtil;
import com.liu.util.LogUtil;
/** 
 * @ClassName: MessageHandler 
 * @author: lyd
 * @date: 2017��9��1�� ����10:32:00 
 * @describe:
 */
public class MessageHandler {
	/**  
	* @Title: send  
	* @Description:������Ϣ
	*/  
	public static void send(Socket socket,String content)throws IOException{//向服务器发送数据包
		if(socket==null||!socket.isConnected())
			return ;
		Message  message=new Message(content);
//		System.out.println("message:"+message);
		OutputStream outputStream=socket.getOutputStream();
		outputStream.write(message.getBytes());
//		LogUtil.d("Send Message",message.toString());
	}
	public static void receive(Socket socket,MessageHandler.OnReceiveListener listener)throws IOException{
		if(socket==null||!socket.isConnected()) return ;
		int len;
		byte[] buffer=new byte[8*1024];
		InputStream inputStream=socket.getInputStream();
		while(socket.isConnected()&&(len=inputStream.read(buffer))!=-1){
			if(listener!=null){
				listener.onReceive(splitResponse(Arrays.copyOf(buffer, len)));//将接收来的数据数组考到Len并进行分割解析操作并接受
				if(listener.isFinished()) return ;
			}
		}
	}
	public static List<String> splitResponse(byte[]buffer){//分割接受
		if(buffer==null||buffer.length<=0) return null;
		List<String> resList=new ArrayList<>();
		String byteArray=HexUtil.bytes2HexString(buffer).toLowerCase();//将字节转换为十六进制小写
		String[] responseStrings=byteArray.split("b2020000");//以b2020000进行分割
		int end;
		for(int i=1;i<responseStrings.length;i++){
			if(!responseStrings[i].contains("00")) continue;
			end=responseStrings[i].indexOf("00");
			byte[] bytes=HexUtil.hexString2Bytes(responseStrings[i].substring(0,end));//将分割完的数组分离结尾为00的并转换为字节
			if(bytes!=null)
			{
				resList.add(new String(bytes));//字节变为字符
			}
				
		}
		return resList;
	}

	public interface OnReceiveListener{
		
		void onReceive(List<String> responses);
		boolean isFinished();
	}
}
