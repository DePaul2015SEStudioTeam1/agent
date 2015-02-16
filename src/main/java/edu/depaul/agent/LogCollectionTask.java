package edu.depaul.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.depaul.armada.model.AgentContainer;
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

			/**
			 * old code
			 */
//			//TODO: are we instantiating a new LogCollector every time run() happens?
//			//TODO: maybe do this once on startup
//			//send cAdvisor's url location to start retrieving data
//			LogCollector data = new LogCollector("http://140.192.249.16:8890/api/v1.2/docker");
//
//			for(int i = 0; i < data.getNumberOfContainers(); i++) {
//				AgentContainer log = data.createContainerLog(i);
////				armadaService.send(log);
//				logger.info("Saved log!");
//			}
//

			/**
			 * new code
			 */
			LogCollector.connect();
			List<ContainerLog> logList = LogCollector.getCurrentLogs();

			//test print
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
