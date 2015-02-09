package edu.depaul.agent;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Starts the agent.
 * 
 * @author Deonte D Johnson
 */
public class Agent {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans/agent-config.xml");
		LogCollectionTask logCollectionTask = (LogCollectionTask) context.getBean("logCollectionTask");
		logCollectionTask.setAgentId((args == null || args.length < 1)? UUID.randomUUID().toString() : args[0]);

		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(logCollectionTask, 1, 1, TimeUnit.SECONDS);
	}
}
