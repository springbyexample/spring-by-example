package ua.com.springbyexample.jmx;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

@ManagedResource(objectName = "spring.by.example:name=StateBean")
@Component
public class StateBean implements ApplicationContextAware {

	@Resource
	private LoggerState loggerState;

	private ApplicationContext applicationContext;

	@ManagedAttribute(description = "Retreive last logger message")
	public String getLastLoggerMessage() {
		return loggerState.lastMessage();
	}

	@ManagedOperation(description = "Shutdown deamon gracefully")
	public void shutdown() {
		final ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(new Runnable() {
			@Override
			public void run() {
				((ConfigurableApplicationContext) applicationContext).close();
				executor.shutdown();
			}
		});
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}

}
