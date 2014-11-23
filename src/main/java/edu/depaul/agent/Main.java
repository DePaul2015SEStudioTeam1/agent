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
public class Main {
	
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans/agent-config.xml");
		Agent agent = (Agent) context.getBean("agent");
		agent.setAgentId((args == null || args.length < 1)? UUID.randomUUID().toString() : args[0]);
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(agent, 3, 3, TimeUnit.SECONDS);
	}
}
