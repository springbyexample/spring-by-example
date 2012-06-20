package com.spring.by.example.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;


@Component("simpleLoggerListener")
public class SimpleLoggerListener extends MessageListenerAdapter {
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleLoggerListener.class);

	public void handleMessage(String textMessage) {
		logger.debug(textMessage);
	}

}
