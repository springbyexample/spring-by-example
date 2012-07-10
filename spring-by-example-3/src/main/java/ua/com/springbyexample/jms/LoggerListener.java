package ua.com.springbyexample.jms;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.listener.SessionAwareMessageListener;
import org.springframework.stereotype.Component;


@Component("loggerListener")
public class LoggerListener implements SessionAwareMessageListener<TextMessage> {
	
	private static final Logger logger = LoggerFactory.getLogger(LoggerListener.class);

	@Override
	public void onMessage(TextMessage textMessage, Session session) throws JMSException {
		logger.debug(textMessage.getText());
	}

}
