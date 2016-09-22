package com.pop.imitator.core;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 秒 请求发生器
 * 
 * @author pop
 * 
 */
public class SecondSeqCreator implements SeqCreator {

	private ExecutorService es;

	@Override
	public void start() {
	

	}
	
	private void laiyifa() {
		String url = "http://localhost:8080/index.html";
		es.submit(new ReqTask(url));
	}

	@Override
	public void init(ExecutorService es) {
		this.es = es;

	}

}
