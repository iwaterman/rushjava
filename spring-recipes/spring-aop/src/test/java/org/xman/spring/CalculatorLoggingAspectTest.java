package org.xman.spring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class CalculatorLoggingAspectTest {

	@Test
	public void test() {
		ApplicationContext context = new ClassPathXmlApplicationContext("xman-beans.xml");
		ArithmeticCalculator arithmeticCalculator = (ArithmeticCalculator) context.getBean("arithmeticCalculator");
		arithmeticCalculator.add(1, 0);
//		arithmeticCalculator.sub(4, 3);
//		arithmeticCalculator.mul(2, 3);
//		arithmeticCalculator.div(4, 2);
//		
//		UnitCalculator unitCalculator = (UnitCalculator) context.getBean("unitCalculator");
//		unitCalculator.kilogramToPound(10);
//		unitCalculator.kilometerToMile(5);
	}

}
