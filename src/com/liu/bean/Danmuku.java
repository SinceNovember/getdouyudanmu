
package com.liu.bean;

import java.util.Date;

 /** 
 * @ClassName: Danmuku 
 * @author: lyd
 * @date: 2017年9月1日 下午1:26:28  
 */
public class Danmuku {

	private int uid;//用户id
	private String snick;
	private String content;
	private Date date;
	private int rid;
	
	public Danmuku(int uid, String snick, String content,  int rid) {
		super();
		this.uid = uid;
		this.snick = snick;
		this.content = content;
		this.rid = rid;
	}
	@Override
	public String toString() {
		return "Danmuku [uid=" + uid + ", snick=" + snick + ", content=" + content + ", date=" + date + ", rid=" + rid
				+ "]";
	}
	public int getUid() {
		return uid;
	}
	public void setUid(int uid) {
		this.uid = uid;
	}
	public String getSnick() {
		return snick;
	}
	public void setSnick(String snick) {
		this.snick = snick;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}
	
	
}
