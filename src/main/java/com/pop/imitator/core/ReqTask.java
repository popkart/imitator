package com.pop.imitator.core;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.pop.imitator.hc.HcInstance;
import com.pop.imitator.util.StatisticUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 单个请求任务
 * @author pop
 *
 */
@Slf4j
public class ReqTask implements Runnable {
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	/** 请求的URL*/
	String reqUrl;
	/** 请求发送时间点*/
	Date startDate;
	/** 该请求完成耗费时间,ms*/
	long completeTime;
	
	public ReqTask(String url) {
		this.reqUrl = url;
	}
	@Override
	public void run() {
		startDate = new Date();
//		try {
//			Thread.sleep(1000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		try {
			HcInstance.fetch(reqUrl);
		} catch (Exception e) {
			StatisticUtil.add("fetch-err");
			log.error("eeeeeeeeeeee",e);
		} 
		
		
		
		Date stopDate = new Date();
		log.debug("start Time[{}], get url:{} spend[{}]ms", sdf.format(startDate), reqUrl, (stopDate.getTime() - startDate.getTime()));
		
	}
}
