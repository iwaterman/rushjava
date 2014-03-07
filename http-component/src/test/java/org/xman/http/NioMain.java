package org.xman.http;


import java.util.concurrent.CountDownLatch;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.nio.client.AbstractHttpAsyncClient;
import org.apache.http.impl.nio.client.DefaultHttpAsyncClient;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.reactor.IOReactorException;

public class NioMain {
	/**
	 * @param args
	 * @throws IOReactorException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOReactorException, InterruptedException {
		final HttpAsyncClient httpclient = new DefaultHttpAsyncClient();
        ((AbstractHttpAsyncClient) httpclient).start();
        HttpGet[] requests = new HttpGet[] {
                new HttpGet("http://localhost:8080/demo/now"),
                new HttpGet("http://localhost:8080/demo/now"),
                new HttpGet("http://localhost:8080/demo/now")
        };
        
        final CountDownLatch latch = new CountDownLatch(requests.length);
        try {
            for (final HttpGet request: requests) {
                httpclient.execute(request, new FutureCallback<HttpResponse>() {

                    public void completed(final HttpResponse response) {
                        latch.countDown();
                        System.out.println(request.getRequestLine() + "->" + response.getStatusLine());
                    }

                    public void failed(final Exception ex) {
                        latch.countDown();
                        ex.printStackTrace();
                    }

                    public void cancelled() {
                        latch.countDown();
                    }

                });
            }
            System.out.println("Doing...");
        }finally {
            latch.await();
            ((AbstractHttpAsyncClient) httpclient).shutdown();
        }
        System.out.println("Done");
	}

}
