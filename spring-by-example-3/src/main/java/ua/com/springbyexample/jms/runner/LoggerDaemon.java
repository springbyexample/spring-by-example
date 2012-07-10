package ua.com.springbyexample.jms.runner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class LoggerDaemon {

	private static final String APPLICATION_CONTEXT_PATH = "classpath:application-context.xml";
	private static final Logger logger = LoggerFactory.getLogger(LoggerDaemon.class);

	public static void main(String[] args) {
		try {

			logger.info("Start loading context");

			AbstractApplicationContext applicationContext = new ClassPathXmlApplicationContext(APPLICATION_CONTEXT_PATH);
			applicationContext.registerShutdownHook();

			logger.info("Context was loaded sucessfully");
		} catch (Exception e) {
			logger.error("Exception", e);
		}
	}

}
