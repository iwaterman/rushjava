package org.xman.http;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.http.HeaderElement;
import org.apache.http.HeaderElementIterator;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeaderElementIterator;
import org.apache.http.protocol.ExecutionContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class LongConnTest {
	// private static final String DATE_URL = "http://localhost:8080/demo/now";
//	private static final String DATE_URL = "http://10.129.148.74:8080/simple/now";
	private static final String DATE_URL = "http://10.17.40.17:8080/demo/now";

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

		HttpGet httpGet2 = new HttpGet(DATE_URL);
		httpGet2.setHeader("time", "1000");
		for (int i = 0; i < 1; i++) {
			HttpResponse response2 = httpclient.execute(httpGet2);

			InputStream in2 = response2.getEntity().getContent();
			System.out.println("r2: " + IOUtils.toString(in2));
		}

		System.out.println("out cycle... ");
		Thread.sleep(1900000);

		httpclient.getConnectionManager().shutdown();
	}

	public static CloseableHttpClient getLongConnect() {
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

		return httpclient;
	}

}