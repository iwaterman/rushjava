package org.jagent.asm.bean;

import java.io.InputStream;

import org.jagent.asm.Asm5Utils;

public class PrintPerson {

	public static void main(String[] args) {
		InputStream is = PrintPerson.class.getResourceAsStream("Person.class");
		
		Asm5Utils.traceClass(is);
	}

}
