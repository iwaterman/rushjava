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
		System.out.println("Hello, Asm5!");
	}
}
