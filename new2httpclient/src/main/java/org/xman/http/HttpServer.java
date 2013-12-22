package org.xman.http;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {

	public static void main(String[] args) {
		HttpServer server = new HttpServer();
		
		server.startup();
	}

	private void startup() {
		try {
			ServerSocket serverSocket = new ServerSocket(8080);
			while (true) {
				Socket socket = serverSocket.accept();
				
				Worker worker = new Worker(socket);
				new Thread(worker).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	class Worker implements Runnable {
		private Socket socket;
		
		public Worker(Socket socket) {
			this.socket = socket;
		}
		
		public void run() {
			OutputStream os = null;
			InputStream is = null;
			
			try {
				os = socket.getOutputStream();
				is = socket.getInputStream();
				
				BufferedReader reader = new BufferedReader(new InputStreamReader(is));
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			finally {
				closeCloseable(is);
				closeCloseable(os);
			}
		}
		
	}
	
	private static void closeCloseable(Closeable closable) {
			if (closable != null) {
				try {
					closable.close();
				} catch (IOException e) {
				}
			}
	}
}
