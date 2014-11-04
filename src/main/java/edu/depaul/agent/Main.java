package edu.depaul.agent;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ApplicationContext context = new ClassPathXmlApplicationContext("beans/agent-config.xml");
		Agent agent = (Agent) context.getBean("agent");
		agent.run();
	}

}
