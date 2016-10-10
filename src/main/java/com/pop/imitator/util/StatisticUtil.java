package com.pop.imitator.util;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class StatisticUtil {
	
	private static ConcurrentHashMap<String, AtomicInteger> map = new ConcurrentHashMap<>(); 
	
	public static int add(String counterName) {
		AtomicInteger counter = map.get(counterName);
		if(counter == null) {
			while(counter == null) {
				counter = map.putIfAbsent(counterName, new AtomicInteger());
			}
		}
		return counter.incrementAndGet();
	}
	
	public static String dump() {
		return map.toString();
	}

}
