package org.xman.http;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HttpContext;

public class LongConnMultiThreadTest {
	// private static final String DATE_URL =
	// "http://10.129.148.74:8080/simple/now";
	private static final String DATE_URL = "http://localhost:8080/demo/now";

	public static void main(String args[]) throws Exception {
		getData();
	}

	public static void getData() throws Exception {
		ConnectionKeepAliveStrategy keepAliveStrat = new DefaultConnectionKeepAliveStrategy() {
			@Override
			public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
				long keepAlive = super.getKeepAliveDuration(response, context);
				if (keepAlive == -1) {
					// Keep connections alive 5 seconds if a keep-alive value
					// has not be explicitly set by the server
					keepAlive = 5000;
				}
				return keepAlive;
			}
		};

		CloseableHttpClient httpclient = HttpClients.custom().setKeepAliveStrategy(keepAliveStrat).build();

		// multiGetRequest(httpclient);
		doPostRequest(httpclient);

		Thread.sleep(1900000);
		httpclient.getConnectionManager().shutdown();
	}

	private static void doPostRequest(CloseableHttpClient httpclient) {
		HttpPost post = new HttpPost(DATE_URL);
		String time;
		for (int i = 1; i <= 5; i++) {
			time = "" + i * 3000;
			System.out.println(time);
			post.setHeader("time", time);
			new Thread(new AccessTask(httpclient, post)).start();
		}
	}

	private static void multiGetRequest(CloseableHttpClient httpclient) {
		HttpGet httpGet;
		for (int i = 1; i <= 15; i++) {
			httpGet = new HttpGet(DATE_URL + "?time=" + i * 3000);

			new Thread(new AccessTask(httpclient, httpGet)).start();
		}
	}

}

class AccessTask implements Runnable {
	private CloseableHttpClient client;
	private HttpRequestBase httpRequest;

	public AccessTask(CloseableHttpClient client, HttpRequestBase httpRequest) {
		this.client = client;
		this.httpRequest = httpRequest;
	}

	public void run() {
		try {
			String name = Thread.currentThread().getName();
			System.out.println(name + "  Start...");
			HttpResponse httpResponse = client.execute(httpRequest);

			InputStream stream = httpResponse.getEntity().getContent();
			System.out.println(name + " End..." + IOUtils.toString(stream));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}