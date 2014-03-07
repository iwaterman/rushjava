package org.xman.http;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public class MyNioClient {
	private static final String ACCESS_URL = "http://localhost:8080/demo/now";

	public static void main(String[] args) throws Exception {
		
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
				
		try {
			// Start the client
			httpclient.start();

			HttpGet[] requests = new HttpGet[] { 
					new HttpGet(ACCESS_URL), 
					new HttpGet(ACCESS_URL), 
					new HttpGet(ACCESS_URL), 
					new HttpGet(ACCESS_URL), 
					new HttpGet(ACCESS_URL) };

			System.out.println(new Date() + "...Start!");
			final CountDownLatch latch1 = new CountDownLatch(requests.length);
			for (int i = 0; i < requests.length; i++) {
				final HttpGet request = requests[i];

				request.setHeader("time", "" + (i + 1) * 3000);
				httpclient.execute(request, new FutureCallback<HttpResponse>() {

					public void completed(final HttpResponse response2) {
						latch1.countDown();
						System.out.println(request.getRequestLine() + "->" + response2.getStatusLine() + " "
								+ new Date());
					}

					public void failed(final Exception ex) {
						latch1.countDown();
						System.out.println(request.getRequestLine() + "->" + ex);
					}

					public void cancelled() {
						latch1.countDown();
						System.out.println(request.getRequestLine() + " cancelled");
					}
				});
			}
			latch1.await();

			System.out.println(new Date() + "...Done!");
		} finally {
			httpclient.close();
		}
	}

}
