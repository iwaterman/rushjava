package org.apache.tomcat.util.threads;

import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolSimulator {
	private LimitLatch connectionLimitLatch;
	private Executor executor = null;

	public Executor getExecutor() {
		return executor;
	}

	public ThreadPoolSimulator() {
		connectionLimitLatch = new LimitLatch(200);

		TaskQueue taskqueue = new TaskQueue(1);
		boolean daemon = true;
		TaskThreadFactory tf = new TaskThreadFactory("bio-exec-", daemon, 5);
		executor = new ThreadPoolExecutor(1, 200, 60, TimeUnit.SECONDS, taskqueue, tf);

		taskqueue.setParent((ThreadPoolExecutor) executor);
	}

	protected void releaseConnectionLatch() {
		LimitLatch latch = connectionLimitLatch;
		if (latch != null)
			latch.releaseAll();
		connectionLimitLatch = null;
	}

	protected void countUpOrAwaitConnection() throws InterruptedException {
		LimitLatch latch = connectionLimitLatch;
		if (latch != null)
			latch.countUpOrAwait();
	}

	protected long countDownConnection() {
		LimitLatch latch = connectionLimitLatch;
		if (latch != null) {
			long result = latch.countDown();
			if (result < 0) {
				System.out.println("Incorrect connection count, multiple socket.close called on the same socket.");
			}
			return result;
		} else {
			return -1;
		}
	}

	protected boolean processSocket() {
		boolean result = false;

		getExecutor().execute(new SocketProcessor());

		result = true;

		return result;
	}

	class SocketProcessor implements Runnable {

		@Override
		public void run() {
			try {
				int off = new Random().nextInt(3000);
				System.out.println(Thread.currentThread().getName() + "====running! off=" + off);
				Thread.sleep(off);
				System.out.println(Thread.currentThread().getName() + "====done! off=" + off);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void start() {
		Thread thread = new Thread(new Acceptor());
		thread.setName("Acceptor--1");
		thread.start();
	}

	class Acceptor implements Runnable {

		@Override
		public void run() {
			for (int i = 0; i < 3; i++) {
				try {
					countUpOrAwaitConnection();
					if (!processSocket()) {
						countDownConnection();
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			try {
				Thread.sleep(10000000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	public static void main(String[] args) {
		ThreadPoolSimulator test = new ThreadPoolSimulator();
		test.start();
	}
}
