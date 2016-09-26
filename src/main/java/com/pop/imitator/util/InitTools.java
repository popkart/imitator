package com.pop.imitator.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import lombok.Getter;
import org.apache.commons.io.IOUtils;


/**
 * Title: {@link InitTools}.<br>
 * Description: 初始化工具类，他能初始化：
 * <ul>
 * <li>日志系统（log4j）；
 * <li>属性管理系统（Properties、PropertySet）；
 * <li>JNDI（Catalina的实现）；
 * </ul>
 */
public class InitTools {

	/** 单例模式，唯一实例 */
	private static InitTools instance = null;

	public synchronized static InitTools getInstance() {
		if (instance == null) {
			instance = new InitTools();
		}
		return instance;
	}
	@Getter
	private Properties props;

		private InitTools() {
		 String configPath = "config/config.properties";
		
			Properties props = new Properties();
			InputStream is = null;
			try {
				is =this.getClass().getClassLoader().getResource(configPath).openStream();
				if (is != null) {
					props.load(is);
					this.props = props;
				}
				System.out.println(props);
			} catch (IOException ex) {
				throw new IllegalArgumentException("读取Properties配置文件时出错。");
			} finally {
				IOUtils.closeQuietly(is);
			}
	}
 
public static void main(String[] args) {
	InitTools.getInstance();
}
}
