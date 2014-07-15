package org.jagent.asm.method;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.util.TraceClassVisitor;

public class AddTimerAdapterTest {

	public static void main(String[] args) {
		ClassWriter cw = new ClassWriter(0);
		AddTimerAdapter cv = new AddTimerAdapter(cw);
		InputStream is = AddTimerAdapterTest.class.getResourceAsStream("AddTimerBean.class");
		try {
			ClassReader cr = new ClassReader(is);
			cr.accept(cv, 0);
			
			byte[] bytes = cw.toByteArray();
			
			PrintWriter pw = new PrintWriter(System.out);
			cw = new ClassWriter(0);
			TraceClassVisitor tcv = new TraceClassVisitor(cw, pw);
			
			cr = new ClassReader(bytes);
			cr.accept(tcv, 0);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
