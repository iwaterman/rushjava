package org.xman.http;

import java.io.IOException;

import org.apache.http.HttpException;
import org.apache.http.HttpRequest;
import org.apache.http.message.BasicHttpRequest;
import org.apache.http.nio.NHttpClientConnection;
import org.apache.http.protocol.HttpContext;

public class NioClientTest {

	/**
	 * @param args
	 * @throws HttpException
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException, HttpException {
		NHttpClientConnection conn = null;
		// Obtain execution context
		HttpContext context = conn.getContext();
		// Obtain processing state
		Object state = context.getAttribute("state");
		// Generate a request based on the state information
		HttpRequest request = new BasicHttpRequest("GET", "/");
		conn.submitRequest(request);
		System.out.println(conn.isRequestSubmitted());
	}

}
