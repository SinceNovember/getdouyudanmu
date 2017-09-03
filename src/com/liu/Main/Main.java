package com.liu.Main;

import java.util.Set;
import com.liu.Config.Config;
import com.liu.Thread.CrawlerThread;

public class Main {

	public static void main(String[] args) {
		if(!Config.loadSuccess)
			return ;
		Set<String> nameSet=Config.room_map.keySet();
		for(String name:nameSet){
			new Thread(new CrawlerThread(name,Config.room_map.get(name)),"主播:"+name).start(); 
		}
	}
}
