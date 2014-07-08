package org.jagent.asm;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.TraceClassVisitor;

public class Asm5Utils {
	
	/**
	 *  在控制台输出类的解析。
	 *  
	 * @param is 输入流。
	 */
	public static void traceClass(InputStream is) {
		PrintWriter pw = new PrintWriter(System.out);
		ClassWriter cw = new ClassWriter(0);
		TraceClassVisitor cv = new TraceClassVisitor(cw, pw);
		
		try {
			ClassReader cr = new ClassReader(is);
			cr.accept(cv, 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void byte2File(Byte[] bytes) {
		
	}
}
