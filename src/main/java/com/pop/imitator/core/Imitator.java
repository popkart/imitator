package com.pop.imitator.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.pop.imitator.hc.HcInstance;
import com.pop.imitator.util.StatisticUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Imitator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		log.info("程序启动.");
		ExecutorService eService = Executors.newFixedThreadPool(4000);
		SeqCreator sCreator = new SecondSeqCreator();
		sCreator.init(eService);
		HcInstance.dummy();
		sCreator.start();
		eService.shutdown();
		try {
			eService.awaitTermination(1000, TimeUnit.MINUTES);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.info(StatisticUtil.dump());

	}

}
