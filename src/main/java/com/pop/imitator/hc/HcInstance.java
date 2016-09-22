package com.pop.imitator.hc;

import org.apache.http.client.HttpClient;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HcInstance {
	private static HttpClient hc;

	public static void fetch(String url) {

	}
	
	private static void init() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setDefaultMaxPerRoute(5000);//如果只请求一个网站，则路由数是1
        cm.setMaxTotal(5000);
        SocketConfig socketConfig = SocketConfig.custom()
                .setSoKeepAlive(false)
                .build();
        cm.setDefaultSocketConfig(socketConfig);
       hc = HttpClients.custom()
		.disableCookieManagement().disableAutomaticRetries()
		.disableContentCompression()
		//.setConnectionTimeToLive(0, TimeUnit.SECONDS)
		.setConnectionManager(cm)
		.build();
	}

}
