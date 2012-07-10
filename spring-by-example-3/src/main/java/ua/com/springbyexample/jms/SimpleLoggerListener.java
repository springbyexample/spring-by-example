package ua.com.springbyexample.jms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.adapter.MessageListenerAdapter;
import org.springframework.stereotype.Component;

import ua.com.springbyexample.jmx.LoggerState;



@Component("simpleLoggerListener")
public class SimpleLoggerListener extends MessageListenerAdapter implements LoggerState {
	
	private static final Logger logger = LoggerFactory.getLogger(SimpleLoggerListener.class);
	
	private String lastMessage;

	public void handleMessage(String textMessage) {
		lastMessage = textMessage;
		logger.debug(textMessage);
	}

	@Override
	public String lastMessage() {
		return lastMessage;
	}

}
