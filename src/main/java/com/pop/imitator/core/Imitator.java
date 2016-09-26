package com.pop.imitator.core;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Imitator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		log.debug("Hello,let's go!{}{}{}","pop"," is ",1);
		ExecutorService eService = Executors.newFixedThreadPool(2000);
		SeqCreator sCreator = new SecondSeqCreator();
		sCreator.init(eService);
		sCreator.start();
		eService.shutdown();

	}

}
