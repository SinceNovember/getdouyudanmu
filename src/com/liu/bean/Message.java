package com.liu.bean;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;

/** 
 * @ClassName: Message 
 * @author: lyd
 * @date: 2017��9��1�� ����1:29:50 
 * @describe:
 */
public class Message {
	
	// @Fields length : ���ĵĳ���
	private int[] length;
	// @Fields code : �볤�����
	private int[] code;
	// @Fields magic : �����������
	private int[] magic;
	// @Fields content : ���������Ǹ�
	private String content;
	// @Fields end : ��β
	private int[] end;
	/** 
	 * @Title:Message
	 * @Description:Ҫ�ύ������
	 * @param content 
	 * 斗鱼默认的数据包格式
	 * 1.所有长度，0x00,0x00,0x00
	 * 2.跟1一样
	 * 3.默认包 0xb1,0x02,0x00,0x00
	 * 4.正文
	 * 5.0x00
	 */  
	public Message(String content){
		length=new int[]{calcMessageLength(content),0x00,0x00,0x00};
		code=new int[]{calcMessageLength(content),0x00,0x00,0x00};
		magic=new int[]{0xb1,0x02,0x00,0x00};
		this.content=content;
		end=new int[]{0x00};
	}
	private int calcMessageLength(String content){
		return 4+4+(content==null?0:content.length())+1;
	}
	public int[] getLength() {
		return length;
	}
	public void setLength(int[] length) {
		this.length = length;
	}
	public int[] getCode() {
		return code;
	}
	public void setCode(int[] code) {
		this.code = code;
	}
	public int[] getMagic() {
		return magic;
	}
	public void setMagic(int[] magic) {
		this.magic = magic;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int[] getEnd() {
		return end;
	}
	public void setEnd(int[] end) {
		this.end = end;
	}
	@Override
	public String toString() {
		return "Message [length=" + Arrays.toString(length) + ", code=" + Arrays.toString(code) + ", magic="
				+ Arrays.toString(magic) + ", content=" + content + ", end=" + Arrays.toString(end) + "]";
	}
	private ByteArrayOutputStream baos;
	public byte[] getBytes()throws IOException{
		if(baos==null)
			baos=new ByteArrayOutputStream();//字节数组输出流
		for(int b:length) baos.write(b);
		for(int b:code) baos.write(b);
		for(int b: magic) baos.write(b);
		if(content!=null) baos.write(content.getBytes());
		for(int b:end) baos.write(b);//将数据包写入输出流
		return baos.toByteArray();
	}
}
