package edu.depaul.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.depaul.armada.model.AgentContainer;
import edu.depaul.armada.service.ArmadaService;

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
		
			//TODO: are we instantiating a new LogCollector every time run() happens?
			//TODO: maybe do this once on startup
			//send cAdvisor's url location to start retrieving data
			LogCollector data = new LogCollector("http://140.192.249.16:8890/api/v1.2/docker");
	
			//TODO: can remove print statements
			//print a log for each container
			for(int i = 0; i < data.getNumberOfContainers(); i++) {
				AgentContainer log = data.createContainerLog(i);
				//armadaService.store(log);
				logger.info("Saved log!");
			}
	
			//Container container = new Container();
			AgentContainer log = data.createContainerLog(0);
			//container.setContainerUniqueId(log.getContainerUniqueId());
			//container.setCpuLimit(10L);
			//container.setFilesystemCapacity(50L);
			//container.setMemLimit(25L);
			//armadaService.store(container);
			
			logger.info("Saved container!");
		
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	public void setAgentId(String id) {
		this.id = id;
	}
}
