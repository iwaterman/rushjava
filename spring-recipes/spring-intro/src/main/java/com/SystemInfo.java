package com;

public class SystemInfo {
	public static void main(String[] args) {
		String osName = System.getProperty("os.name");
		
		System.out.println(osName);
	}
}
