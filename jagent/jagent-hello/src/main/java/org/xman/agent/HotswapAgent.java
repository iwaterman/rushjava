package org.xman.agent;

import java.lang.instrument.Instrumentation;

public class HotswapAgent {
	public static void premain(String args, Instrumentation instrumentation) {
		System.out.println("Hello, Agent! args=" + args);
	}
}
