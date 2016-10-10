package com.pop.imitator.core;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.commons.codec.Charsets;
import org.apache.commons.io.FileUtils;

import com.pop.imitator.hc.HcInstance;
import com.pop.imitator.util.InitTools;

import lombok.extern.slf4j.Slf4j;

/**
 * 秒 请求发生器
 * 
 * @author pop
 * 
 */
@Slf4j
public class SecondSeqCreator implements SeqCreator {

	private ExecutorService es;
	private String targetUrl = InitTools.getInstance().getProps().getProperty("target_url");
	@Override
	public void start() {
		List<String> seqs = readTimeList();
		if (seqs.size() == 0) {
			log.warn("未读取到历史模拟序列，退出。");
			return;
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd HH:mm:ss");

		// String line = seqs.get(i);
		// String[] ssStrings = line.split("\t");
		// String date = ssStrings[0];
		// int count = Integer.parseInt(ssStrings[1]);
		long tick = 0;

		String line;
		String[] ssStrings;
		String date;
		long timeStamp = 0;
		long lastTimeStamp = 0;
		int count;
		try {
			line = seqs.get(0);
			ssStrings = line.split("\t");
			date = ssStrings[0];
			timeStamp = sdf.parse(date).getTime();
			lastTimeStamp = timeStamp;
			count = Integer.parseInt(ssStrings[1]);
			
		} catch (ParseException e2) {
			log.error("解析首行数据出错，退出。", e2);
			return;
		}
		laiNfa(count);//发送首行数据
		for (int i = 1; i < seqs.size(); i++) {

			line = seqs.get(i);
			ssStrings = line.split("\t");
			date = ssStrings[0];
			timeStamp = 0;
			try {
				timeStamp = sdf.parse(date).getTime();
			} catch (ParseException e1) {
				log.error("解析时间出错，当前时间：{},行数：{}", date, (i + 1));
			}
			 count = Integer.parseInt(ssStrings[1]);

			tick = timeStamp - lastTimeStamp;
			lastTimeStamp = timeStamp;
			try {
				log.info("根据历史记录，需要休息[{}]秒再发送。", tick / 1000);
				Thread.sleep(tick);
				log.info("休息完毕。");
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			laiNfa(count);

		}

	}

	private List<String> readTimeList() {
		String timeSeqPath = InitTools.getInstance().getProps()
				.getProperty("time_seq_file", "timeseq.txt");
		File timeSeq = new File(timeSeqPath);
		List<String> list = new ArrayList<String>();
		if (timeSeq.exists()) {//先按系统文件路径去找
			try {
				list = FileUtils.readLines(timeSeq, Charsets.UTF_8);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			URL fileName = this.getClass().getClassLoader().getResource(timeSeqPath);
			if(fileName == null) {//再按类路径去找
				log.error("找不到时间序列文件[{}]，请检查配置项[time_seq_file]是否正确，退出。",
						timeSeqPath);
			} else {
				try {
					list = FileUtils.readLines(new File(fileName.getFile()), Charsets.UTF_8);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
		return list;
 
	}

	private void laiNfa(int n) {
		log.info("本秒开始发送[{}]条请求。", n);
		for (int i = 0; i < n; i++) {
			laiyifa();
		}
		log.info("发送请求提交到发送线程完毕。");
	}

	private void laiyifa() {
		
		es.submit(new ReqTask(targetUrl));
	}

	@Override
	public void init(ExecutorService es) {
		this.es = es;

	}

	public static void main(String[] args) {
		SecondSeqCreator s = new SecondSeqCreator();
		ExecutorService es = Executors.newFixedThreadPool(2000);
		s.init(es);
		HcInstance.dummy();
		s.start();
		es.shutdown();
	}

}
