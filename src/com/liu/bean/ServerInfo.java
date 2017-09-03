package com.liu.bean;

 /** 
 * @ClassName: ServerInfo 
 * @author: lyd
 * @date: 2017年9月1日 下午2:17:33 
 * @describe:端口和地址信息
 */
public class ServerInfo {
	private String host;
	private int port;
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public int getPort() {
		return port;
	}
	public void setPort(int port) {
		this.port = port;
	}
	@Override
	public String toString() {
		return "ServerInfo [host=" + host + ", port=" + port + "]";
	}
	public ServerInfo(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}
	
}
