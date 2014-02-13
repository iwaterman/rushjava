package org.apache.tomcat.util.threads;

public class LimitLatchSimpleTest {

	public static void main(String[] args) throws InterruptedException {
		LimitLatch latch = new LimitLatch(1);
		latch.countUpOrAwait();
		latch.countUpOrAwait();
	}
}
