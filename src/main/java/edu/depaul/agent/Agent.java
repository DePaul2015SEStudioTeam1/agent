package edu.depaul.agent;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * An agent object.
 *
 */
public class Agent {
	
	/**
	 * The main method for the Agent class. It outputs error messages if Armada itself is not running.
	 * @param args
	 */
	public static void main(String[] args) {
		if(args == null || args.length < 1) {
			System.err.println("Usage: java -jar agent.jar <armada.service.url> <c.advisor.url>");
			System.err.println("Example: java -jar agent.jar http://armadahost:8083/ http://localhost:8890/api/v1.2/docker");
			System.exit(1);
		}
		
		System.getProperties().setProperty("armada.service.url", args[0]);
		System.getProperties().setProperty("cadvisor.url", args[1]);
		new ClassPathXmlApplicationContext("beans/agent-config.xml");
	}
}
