package com.liu.Thread;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.jws.Oneway;
import javax.net.ssl.SSLContext;
import javax.print.DocFlavor.STRING;

import org.omg.CORBA.PRIVATE_MEMBER;

import com.liu.bean.Danmuku;
import com.liu.bean.Message;
import com.liu.bean.Request;
import com.liu.bean.ServerInfo;
import com.liu.handler.MessageHandler;
import com.liu.handler.ResponseHandler;
import com.liu.handler.MessageHandler.OnReceiveListener;
import com.liu.util.TimeHelper;
import com.liu.util.HttpUtil;
import com.liu.util.LogUtil;
import com.liu.util.MD5Util;
import com.liu.util.SttCode;

public class CrawlerThread implements Runnable{
		List<ServerInfo>danmukuServers=new ArrayList<ServerInfo>();
		private String roomName;
		private String roomUrl;
		private int rid=-1;
		private int gid=-1;
		private MessageHandler.OnReceiveListener loginListener=new OnReceiveListener() {
			private boolean finish=false;
			private TimeHelper timeHelper=new TimeHelper(10*1000);
			@Override
			public void onReceive(List<String> responses) {
				boolean f1=false,f2=false;
				for(String response:responses)
				{
//				    LogUtil.d("Receive Response", response);
				    if(response.contains("msgrepeaterlist")){
				    	LogUtil.i("获取弹幕服务器地址 ...");
//				    	System.out.println("response:"+response);
				    	String danmukuServerStr=SttCode.deFilterStr(SttCode.deFilterStr(response));
//				    	System.out.println("弹幕库:"+danmukuServerStr);
				    	danmukuServers=ResponseHandler.parseDanmukuServer(danmukuServerStr);
				    	LogUtil.i("获取到 " + danmukuServers.size() + " 个服务器地址 ...");
				    	f1=true;
				    } 
				    if(response.contains("setmsggroup")){
				    	LogUtil.i("获取弹幕群组ID ...");
				    	gid=ResponseHandler.ParseID(response);
				    	LogUtil.i("弹幕群组ID：:"+gid);
				    	f2=true;
				    }
				}
				finish=f1&&f2||timeHelper.checkTimeout();
				
			}
			
			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return finish;
			}
		};
		private MessageHandler.OnReceiveListener danmukuListener=new OnReceiveListener() {
			
			private boolean finished=false;
			private List<Danmuku> danmukus=new ArrayList<>();
			private TimeHelper timeHelper=new TimeHelper(20*60*1000);
			@Override
			public void onReceive(List<String> responses) {
				for(String response:responses){
//					   LogUtil.d("Receive Response", response);
					   if(!response.contains("chatmsg"))
						   continue;
					   Danmuku danmuku=ResponseHandler.parseDanmaku(response);
					   if(danmuku==null)continue;
					   danmukus.add(danmuku);
					   LogUtil.i("Danmuku",danmuku.getSnick()+":"+danmuku.getContent());
				}
				if(timeHelper.checkTimeout()&&!isOnline()) finished=true;
				
			}
			
			@Override
			public boolean isFinished() {
				// TODO Auto-generated method stub
				return finished;
			}
		};
		public CrawlerThread(String roomName,String roomUrl){
			this.roomName=roomName;
			this.roomUrl=roomUrl;
			LogUtil.i("房间名:"+roomName+"连接>>"+roomUrl+"");
		}
		public void run(){
			LogUtil.i("获取房间页面 ...");
			String pageHtml=HttpUtil.get(roomUrl);
			LogUtil.i("获取直播房间ID ...");
			rid=ResponseHandler.parseRoomId(pageHtml);
			boolean online=ResponseHandler.parseOnline(pageHtml);
			if(!online)
			{
				LogUtil.w("该房间还没有直播！>>"+roomUrl);
				onExit();
				return ;
			}
			LogUtil.i("获取服务器列表 ...");
			List<ServerInfo> serverList=ResponseHandler.parseServerInfo(pageHtml);
			if(serverList==null||serverList.size()<=0){
				  LogUtil.w("获取服务器列表失败！");
				  onExit();
				  return ;
			}
			loginRequest(serverList);
			if(rid==-1||gid==-1){
				onExit();
				return ;
			}
			startCrawler();
			onExit();
		}
		private void startCrawler(){
			try{
				if(danmukuServers==null||danmukuServers.size()<=0){
					LogUtil.w("没有可用的弹幕服务器 ...");
							return ;
				}
				ServerInfo danmukuServer=danmukuServers.get((int)(Math.random()*danmukuServers.size()));
				Socket socket=new Socket(danmukuServer.getHost(), danmukuServer.getPort());
				LogUtil.i("登陆到弹幕服务器 :"+danmukuServer.getHost()+":"+danmukuServer.getPort());
				MessageHandler.send(socket, Request.danmukuLogin(rid));
				LogUtil.i("进入 "+rid+"号房间,"+gid+"号弹幕群组 ...");
				new Thread(new KeepLiveThread(socket), "KeepLive-"+roomName).start();
				MessageHandler.send(socket, Request.joinGroup(rid, gid));
				 LogUtil.i("开始接收弹幕 ...");
		            LogUtil.i("----------------------------------------------------------------");
		            MessageHandler.receive(socket, danmukuListener);
			 } catch (IOException e) {
		            e.printStackTrace();
		            LogUtil.d("Error", e.toString());
		            LogUtil.w("与服务器连接失败!");
		        }
			
		}
		public void loginRequest(List<ServerInfo> ipList)
		{
			Socket socket=null;
			ServerInfo serverInfo=ipList.get((int)(Math.random()*ipList.size()));
//			System.out.println("总供服务器的个数:"+ipList.size());
//			System.out.println("ipList:"+(int)(Math.random()*ipList.size()));
			try{
				 LogUtil.i("登录到服务器" + serverInfo.getHost() + ":" + serverInfo.getPort());
				 socket=new Socket(serverInfo.getHost(), serverInfo.getPort());
				 String timestamp=String.valueOf(System.currentTimeMillis()/1000);
//				 System.out.println("timestamp:"+timestamp);
				 String uuid=UUID.randomUUID().toString().replace("-", "").toUpperCase();
//				 System.out.println("uuid:"+uuid);
				 String vk=MD5Util.MD5(timestamp+"7oE9nPEG9xXV69phU31FYCLUagKeYtsF"+uuid);
//				 System.out.println("vk:"+vk);
//				 System.out.println("发生请求");
//				 System.out.println("gid:"+Request.gid(rid, uuid, timestamp, vk));
				 MessageHandler.send(socket, Request.gid(rid, uuid, timestamp, vk));
//				 System.out.println("接受请求");
				 MessageHandler.receive(socket, loginListener);
			} catch (IOException e) {
	            LogUtil.d("Error", e.toString());
	            LogUtil.w("登陆到服务器失败！");
	        } finally {
	            if (socket != null)
	                try {
	                    socket.close();
	                } catch (IOException e) {
	                    LogUtil.d("Error", e.toString());
	                    LogUtil.w("连接关闭异常！");
	                }
	        }
		}
		public boolean isOnline(){
			String pageHtml=HttpUtil.get(roomUrl);
			return pageHtml!=null&&ResponseHandler.parseOnline(pageHtml);
			
		}
		private void onExit(){
			LogUtil.i("房间名:"+roomName+"结束 ...");
		}

	
}
