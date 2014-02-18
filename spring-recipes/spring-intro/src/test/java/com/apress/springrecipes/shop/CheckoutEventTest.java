package com.apress.springrecipes.shop;

import static org.junit.Assert.*;

import java.io.IOException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = { "classpath:beans-annotation.xml" })
public class CheckoutEventTest {
//	@Autowired
//	private ApplicationContext applicationContext;
	
	@Test
	public void test() {
//		Cashier cashier = applicationContext.getBean("cashier1", Cashier.class);
//		ShoppingCart cart = applicationContext.getBean("shoppingCart", ShoppingCart.class);
//		try {
//			cashier.checkout(cart);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
	
	@Test
	public void aatest() {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans-annotation.xml");
		
		Cashier cashier = context.getBean("cashier1", Cashier.class);
		ShoppingCart cart = context.getBean("shoppingCart", ShoppingCart.class);
		try {
			cashier.checkout(cart);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
