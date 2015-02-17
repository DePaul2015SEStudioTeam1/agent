package edu.depaul.agent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Agent {
	
	public static void main(String[] args) {
		if(args == null || args.length == 0) {
			System.err.println("Please supply 'armada.service.url'. Example: http://localhost:8083/");
			System.exit(1);
		}
		
		System.getProperties().setProperty("armada.service.url", args[0]);
		ApplicationContext context = new ClassPathXmlApplicationContext("beans/agent-config.xml");
		LogCollectionTask logCollectionTask = (LogCollectionTask) context.getBean("logCollectionTask");

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(logCollectionTask, 5, 5, TimeUnit.SECONDS);
	}
}
