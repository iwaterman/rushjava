package com.apress.springrecipes.sequence;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("sequences.xml");

		SequenceGenerator seq = (SequenceGenerator) context.getBean("sequenceGenerator");

		System.out.println(seq.getSequence());
	}

}
