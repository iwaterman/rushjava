package org.jagent.asm.method;

import java.io.InputStream;

import org.jagent.asm.Asm5Utils;

public class NopBeanPrint {

	public static void main(String[] args) {
		InputStream is = NopBeanPrint.class.getResourceAsStream("NopBean.class");

		Asm5Utils.traceClass(is);
	}

}
