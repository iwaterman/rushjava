package org.jagent.asm.hello;

import java.io.IOException;
import java.io.InputStream;

import org.jagent.asm.Asm5Utils;

public class TraceClassVisitorTest {

	public void printClass() throws IOException {
		InputStream is = this.getClass().getResourceAsStream("HelloAsm.class");
		
		Asm5Utils.traceClass(is);
	}
	
	public static void main(String[] args) throws IOException {
		TraceClassVisitorTest test = new TraceClassVisitorTest();
		test.printClass();
	}

}
