package com.spring.by.example.jms.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.util.Log4jConfigurer;

public class LoggerDaemon {

	private static final String APPLICATION_CONTEXT_PATH = "classpath:application-context.xml";
	private static final String LOG4J_LOCATION = "classpath:log4j.xml";
	private static final Logger logger = LoggerFactory.getLogger(LoggerDaemon.class);

	public static void main(String[] args) {
		try {
			Log4jConfigurer.initLogging(LOG4J_LOCATION);

			logger.info("Start loading context");

			AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_PATH);
			applicationContext.registerShutdownHook();

			logger.info("Context was loaded sucessfully");
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}

}
