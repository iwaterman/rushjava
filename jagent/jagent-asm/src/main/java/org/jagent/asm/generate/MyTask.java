package org.jagent.asm.generate;

public class MyTask implements Runnable{
	private int sid;
	
	public int getSid() {
		return sid;
	}

	public void setSid(int sid) {
		this.sid = sid;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(1000L);
			
			System.out.println("Hello, Asm5!");
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
