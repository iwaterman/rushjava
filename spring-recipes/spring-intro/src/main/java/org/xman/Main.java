package org.xman;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
	private static final Logger log = LoggerFactory.getLogger(Main.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		

	}
	
	public static void autoRegister(){
		ApplicationContext context = new ClassPathXmlApplicationContext("mybeans.xml");
		
		Product p = (Product) context.getBean("aaa");
		
		//p.getName()
		log.info(p.getName());
	}
	
	public static void manulRegister(){
		DefaultListableBeanFactory dlBeanFactory = new DefaultListableBeanFactory();
		
		AbstractBeanDefinition beanDefinition  = new RootBeanDefinition();
	}

}
