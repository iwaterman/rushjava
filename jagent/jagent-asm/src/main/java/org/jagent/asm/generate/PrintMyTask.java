package org.jagent.asm.generate;

import java.io.InputStream;

import org.jagent.asm.Asm5Utils;

public class PrintMyTask {

	public static void main(String[] args) {
		InputStream is = PrintMyTask.class.getResourceAsStream("MyTask.class");
		
		Asm5Utils.traceClass(is);
	}

}
