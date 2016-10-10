package com.pop.imitator.core;

import java.util.concurrent.ExecutorService;

/**
 * 请求发生器。启动后产生请求到任务队列
 * @author pop
 *
 */
public interface SeqCreator {
	/**
	 * 启动发生器开始工作
	 */
	void start();
	/**
	 * 初始化，传入线程池执行器
	 * @param es 执行模拟请求的线程池
	 */
	void init(ExecutorService es);

}
