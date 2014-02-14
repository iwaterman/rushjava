package org.xman;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.apress.springrecipes.sequence.SequenceGenerator;

public class UtilSetTest {

	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("util_sequences.xml");

		SequenceGenerator generator = (SequenceGenerator) context.getBean("sequenceGenerator");
		
		System.out.println(generator);
	}

}
