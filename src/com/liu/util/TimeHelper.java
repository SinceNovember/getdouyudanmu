package com.liu.util;

public class TimeHelper {
	private int timeout=0;
	private long last=0;
	public TimeHelper(int timeout){
		this.timeout=timeout;
		last=System.currentTimeMillis();
	}
	public boolean checkTimeout(){
		long now=System.currentTimeMillis();
		boolean isTimeout=(now-last)>timeout;
		if(isTimeout)
			last=now;
		return isTimeout;
	}
}
