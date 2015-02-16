package edu.depaul.agent;

import edu.depaul.armada.model.AgentContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.depaul.armada.service.ArmadaService;

import java.util.List;

public class LogCollectionTask implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(LogCollectionTask.class);

	private ArmadaService armadaService; 
	private String id = "";
	
	public void setArmadaService(ArmadaService armadaService) {
		this.armadaService = armadaService;
	}

	@Override
	public void run() {
		try {

			LogCollector.connect();
			List<ContainerLog> logList = LogCollector.getCurrentLogs();

			/**
			 * TODO: clean this up in class
			 * This is the old code, which creates an "AgentContainer" called log.
			 *
			 * Let's refactor this; the name "AgentContainer" doesn't make sense,
			 *  and sounds like an object that singularly describes the static aspects
			 *  of each container.
			 * It should be something along the lines of "AgentContainerLog" or something.
			 * we can then call armadaService.send(log).
			 *
			 * For now, I'm test printing like we were before.
			 */
//			for(int i = 0; i < data.getNumberOfContainers(); i++) {
//				AgentContainer log = data.createContainerLog(i);
//			armadaService.send(log);
//				logger.info("Saved log!");
//			}

			for (ContainerLog log : logList) {
				System.out.println(log);
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setAgentId(String id) {
		this.id = id;
	}
}
