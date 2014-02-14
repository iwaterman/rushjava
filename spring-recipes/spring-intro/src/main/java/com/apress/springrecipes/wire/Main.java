package com.apress.springrecipes.wire;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.apress.springrecipes.sequence.SequenceGenerator;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("wires.xml");

		Roommate roommate = (Roommate) context.getBean("roommate");

		System.out.println(roommate);
	}

}
