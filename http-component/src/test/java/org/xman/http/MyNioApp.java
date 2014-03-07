package org.xman.http;


import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.nio.IOControl;
import org.apache.http.nio.client.methods.AsyncCharConsumer;
import org.apache.http.nio.client.methods.HttpAsyncMethods;
import org.apache.http.nio.protocol.HttpAsyncRequestProducer;
import org.apache.http.protocol.HttpContext;

import java.io.IOException;
import java.nio.CharBuffer;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

public class MyNioApp {
	private static final String ACCESS_URL = "http://localhost:8080/demo/now";

	public static void main(String[] args) throws Exception {
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		try {
			// Start the client
			httpclient.start();

			final CountDownLatch latch2 = new CountDownLatch(1);
			final HttpGet request3 = new HttpGet(ACCESS_URL);
			request3.setHeader("time", "15000");
			HttpAsyncRequestProducer producer3 = HttpAsyncMethods.create(request3);
			AsyncCharConsumer<HttpResponse> consumer3 = new AsyncCharConsumer<HttpResponse>() {

				HttpResponse response;

				@Override
				protected void onResponseReceived(final HttpResponse response) {
					this.response = response;
				}

				@Override
				protected void onCharReceived(final CharBuffer buf, final IOControl ioctrl) throws IOException {
					// Do something useful
				}

				@Override
				protected void releaseResources() {
				}

				@Override
				protected HttpResponse buildResult(final HttpContext context) {
					return this.response;
				}

			};
			httpclient.execute(producer3, consumer3, new FutureCallback<HttpResponse>() {

				public void completed(final HttpResponse response3) {
					latch2.countDown();
					System.out.println(request3.getRequestLine() + "->" + response3.getStatusLine());
				}

				public void failed(final Exception ex) {
					latch2.countDown();
					System.out.println(request3.getRequestLine() + "->" + ex);
				}

				public void cancelled() {
					latch2.countDown();
					System.out.println(request3.getRequestLine() + " cancelled");
				}

			});
			latch2.await();

		} finally {
			httpclient.close();
		}
	}

}
