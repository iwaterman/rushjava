package org.xman.spring;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class CalculatorPointcuts {
	@Pointcut("execution(* *.*(..))")
	public void loggingOperation() {
	}
}