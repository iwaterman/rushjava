package org.xman.spring;

import java.util.Arrays;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.core.annotation.Order;

@Aspect
@Order(1)
public class CalculatorLoggingAspect {
	private Logger log = Logger.getLogger(CalculatorLoggingAspect.class);

	@Before("CalculatorPointcuts.loggingOperation()")
	public void logBefore(JoinPoint joinPoint) {
		log.info("The method " + joinPoint.getSignature().getName() + "() begins with "
				+ Arrays.toString(joinPoint.getArgs()));
	}

	@Before("CalculatorPointcuts.loggingOperation()")
	public void logJoinPoint(JoinPoint joinPoint) {
		log.info("Join point kind : " + joinPoint.getKind());
		log.info("Signature declaring type : " + joinPoint.getSignature().getDeclaringTypeName());
		log.info("Signature name : " + joinPoint.getSignature().getName());
		log.info("Arguments : " + Arrays.toString(joinPoint.getArgs()));
		log.info("Target class : " + joinPoint.getTarget().getClass().getName());
		log.info("This class : " + joinPoint.getThis().getClass().getName());
	}

	@After("CalculatorPointcuts.loggingOperation()")
	public void logAfter(JoinPoint joinPoint) {
		log.info("The method " + joinPoint.getSignature().getName() + "() ends");
	}

	@AfterReturning("CalculatorPointcuts.loggingOperation()")
	public void logAfterReturning(JoinPoint joinPoint) {
		log.info("The method " + joinPoint.getSignature().getName() + "() ends");
	}

	@AfterReturning(pointcut = "CalculatorPointcuts.loggingOperation()", returning = "result")
	public void logAfterReturning(JoinPoint joinPoint, Object result) {
		log.info("The method " + joinPoint.getSignature().getName() + "() ends with " + result);
	}

	@AfterThrowing(pointcut = "CalculatorPointcuts.loggingOperation()", throwing = "e")
	public void logAfterThrowing(JoinPoint joinPoint, Throwable e) {
		log.error("An exception " + e + " has been thrown in " + joinPoint.getSignature().getName() + "()");
	}

	@Around("CalculatorPointcuts.loggingOperation()")
	public Object logAround(ProceedingJoinPoint joinPoint) throws Throwable {
		log.info("The method " + joinPoint.getSignature().getName() + "() begins with "
				+ Arrays.toString(joinPoint.getArgs()));
		try {
			Object result = joinPoint.proceed();
			log.info("The method " + joinPoint.getSignature().getName() + "() ends with " + result);
			return result;
		} catch (IllegalArgumentException e) {
			log.error("Illegal argument " + Arrays.toString(joinPoint.getArgs()) + " in "
					+ joinPoint.getSignature().getName() + "()");
			throw e;
		}
	}
	
}