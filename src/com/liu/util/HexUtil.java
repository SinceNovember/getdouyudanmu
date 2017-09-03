package com.liu.util;

 /** 
 * @ClassName: HexUtil 
 * @author: lyd
 * @date: 2017年9月1日 下午2:23:18 
 * @describe:字节与十六进制转换
 */
public class HexUtil {
	private final static String hex_String="0123456789ABCDEF";
	private final static byte[] hex_byte=hex_String.getBytes();
	/**  
	* @Title: byte2Hex  
	* @Description:字节数组转为十六进制
	*/  
	public static String byte2Hex(byte[]b){
		byte[]buff=new byte[3*b.length];
		for(int i=0;i<b.length;i++)
		{
			buff[3*i]=hex_byte[(b[i]>>4)&0x0f];
			buff[3*i+1]=hex_byte[(b[i])&0x0f];
			buff[3*i+2]=45;
		}
		return new String(buff);
	}
	
	/**  
	* @Title: bytes2HexString  
	* @Description:去除16禁止中的"-"
	*/  
	public static String bytes2HexString(byte[]b){
		return byte2Hex(b).replace("-", "");
	}
	/**  
	* @Title: bytesHexStringWithSpace  
	* @Description:将"-"替换为空格
	*/  
	public static String bytesHexStringWithSpace(byte[]b)
	{
		return byte2Hex(b).replace("-", " ");
		
	}
	public static byte CharToByte(char c){
		return (byte) hex_String.indexOf(c);
	}
	/**  
	* @Title: hexString2Bytes  
	* @Description:十六进制转字节
	*/  
	public static byte[] hexString2Bytes(String hexString)
	{
		if(hexString==null||hexString.equals(""))
		{
			return null;
		}
		hexString=hexString.replace(" ", "");
		hexString=hexString.toUpperCase();
		int length=hexString.length()/2;
		char[] hexChars=hexString.toCharArray();
		byte[] d=new byte[length];
		for(int i=0;i<length;i++)
		{
			int pos=i*2;
			d[i]=(byte)(CharToByte(hexChars[pos])<<4|CharToByte(hexChars[pos+1]));
		}
		return d;
	}
}