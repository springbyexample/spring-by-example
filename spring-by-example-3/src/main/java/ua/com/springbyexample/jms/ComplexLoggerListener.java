package ua.com.springbyexample.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


@Component("complexLoggerListener")
public class ComplexLoggerListener implements MessageListener {
	
	private static final Logger logger = LoggerFactory.getLogger(ComplexLoggerListener.class);

	@Override
	public void onMessage(Message message) {
		Assert.isTrue(message instanceof TextMessage, "listener supports only messages of type TextMessage");
		try {
			logger.debug(((TextMessage) message).getText());
		} catch (JMSException ex) {
			throw new RuntimeException("unable to retrieve message text", ex);
		}
	}

}
