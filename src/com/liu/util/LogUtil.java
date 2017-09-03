package com.liu.util;


/** 
 * @ClassName: LogUtil 
 * @author: lyd
 * @date: 2017年9月1日 下午3:48:38 
 * @describe:log持久化
 */
public class LogUtil {
	private static final boolean debug_mode=true;
	private static final String  default_tag="TAG";
	private static final String level_info="INFO";
	private static final String level_warning="WARNING";
	private static final String level_debug="DEBUG";
	private static final String level_error="ERROR";
	private static String timestamp(){
		return DateUtil.now();
	}
	private static String logMessage(String level,String tag,String message){
		return String.format("[%s][%s][%s][%s] %s",
                timestamp(), level,
                Thread.currentThread().getName(),//当期线程名
                tag, message);
	}
	private static String logMessage(String level, String tag, String invokeInfo, String message) {
        return String.format("[%s][%s][%s][%S][%s] %s",
                timestamp(), level,
                Thread.currentThread().getName(),//当期线程名
                tag, invokeInfo, message);
    }
	 private static void printOut(String log) {
	        System.out.println(log);
	    }

	    private static void printErr(String log) {
	        System.err.println(log);
	    }
	    public static void d(String message){
	    	if(debug_mode){
	    		printOut(logMessage(level_debug, default_tag, getInvokeInfo(),message));
	    	}
	    }
	    public static void d(String tag,String message){
	    	if(debug_mode){
	    		printOut(logMessage(level_debug, tag, getInvokeInfo(),message));
	    	}
	    }
	    public static void e(String message){
	    	if(debug_mode){
	    		printErr(logMessage(level_debug, default_tag, getInvokeInfo(),message));
	    	}
	    }
	    public static void e(String tag,String message){
	    	if(debug_mode){
	    		printErr(logMessage(level_debug, tag, getInvokeInfo(),message));
	    	}
	    }
	    public static void i(String message) {
	        i(default_tag, message);
	    }

	    public static void i(String tag, String message) {
	        printOut(logMessage(level_info, tag, message));
	    }
	    public static void w(String message) {
	        w(default_tag, message);
	    }

	    public static void w(String tag, String message) {
	        printErr(logMessage(level_warning, tag, message));
	    }
	    private static String getInvokeInfo(){
	    	String result;
	    	StackTraceElement thisMethodStack=(new Exception()).getStackTrace()[2];
	    	result=thisMethodStack.getClassName();
	    	int lastIndex=result.lastIndexOf(".");
	    	result=result.substring(lastIndex+1,result.length());
	    	int line=thisMethodStack.getLineNumber();
	    	if(result.contains("$"))
	    		result=result.substring(0,result.indexOf("$"));
	    	return result+":"+line;
	    }
	    public static void main(String[] args) {
	    	  i("123");
	          i("iiiiiiii", "123");

	          d("123");
	          d("ddddd", "123");

	          w("123");
	          w("twwwwwag", "123");

	          e("123");
	          e("eeeeeee", "123");
		}
}
