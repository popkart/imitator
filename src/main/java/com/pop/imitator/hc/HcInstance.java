package com.pop.imitator.hc;

import java.io.IOException;
import java.io.InputStream;

import lombok.extern.slf4j.Slf4j;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.config.SocketConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

@Slf4j
public class HcInstance {
	private static CloseableHttpClient  hc;
	static {
		init();
	}
	/**
	 * 调用我来立即初始化httpclient
	 */
	public static void dummy() {}
	public static void fetch(String url) throws UnsupportedOperationException, IOException {
        try {
            HttpGet httpget = new HttpGet(url);

            System.out.println("Executing request " + httpget.getRequestLine());
            CloseableHttpResponse response = hc.execute(httpget);
            try {
                System.out.println("----------------------------------------");
                System.out.println(response.getStatusLine());

                // Get hold of the response entity
                HttpEntity entity = response.getEntity();

                // If the response does not enclose an entity, there is no need
                // to bother about connection release
                if (entity != null) {
                    InputStream instream = entity.getContent();
                    try {
                        instream.read();
                        // do something useful with the response
                    } catch (IOException ex) {
                        // In case of an IOException the connection will be released
                        // back to the connection manager automatically
                        throw ex;
                    } finally {
                        // Closing the input stream will trigger connection release
                        try {
							instream.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                    }
                }
            } finally {
                response.close();
            }
        } finally{}
	}
	
	/**
	 * 初始化httpclient
	 */
	private static void init() {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setDefaultMaxPerRoute(5000);//如果只请求一个网站，则路由数是1
        cm.setMaxTotal(5000);
        SocketConfig socketConfig = SocketConfig.custom()
                //.setSoKeepAlive(false)
                .setSoTimeout(3000)
                .build();
        cm.setDefaultSocketConfig(socketConfig);
       hc = HttpClients.custom()
		.disableCookieManagement().disableAutomaticRetries()
		.disableContentCompression()
		//.setConnectionTimeToLive(0, TimeUnit.SECONDS)
		.setConnectionManager(cm)
		.build();
       log.info("HttpClient init.");
	}

}
