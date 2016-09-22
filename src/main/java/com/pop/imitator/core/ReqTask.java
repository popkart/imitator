package com.pop.imitator.core;

/**
 * 单个请求任务
 * @author pop
 *
 */
public class ReqTask implements Runnable {
	/** 请求的URL*/
	String reqUrl;
	/** 请求发送时间点*/
	long startTimestamp;
	/** 该请求完成耗费时间,ms*/
	long completeTime;
	
	public ReqTask(String url) {
		this.reqUrl = url;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
}
