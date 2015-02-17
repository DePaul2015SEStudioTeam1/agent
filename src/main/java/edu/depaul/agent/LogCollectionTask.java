package edu.depaul.agent;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteConnectFailureException;

import edu.depaul.armada.model.AgentContainerLog;
import edu.depaul.armada.service.ArmadaService;

public class LogCollectionTask implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(LogCollectionTask.class);

	private LogCollector logCollector;
	private ArmadaService armadaService; 
	
	public void setLogCollector(LogCollector logCollector){
		this.logCollector = logCollector;
	}
	
	public void setArmadaService(ArmadaService armadaService) {
		this.armadaService = armadaService;
	}

	@Override
	public void run() {
		try {
			// AgentContainerLog is a DTO used by the agent to send data over to 
			// the Armada. It's is used to send in all the info that is 
			// needed to save logs
			List<AgentContainerLog> data = logCollector.getCurrentLogs();
			for(int i = 0; i < data.size(); i++) {
				AgentContainerLog log = data.get(i);
				try {
					armadaService.send(log);
					logger.info("Log sent!");
				}
				catch(RemoteConnectFailureException e) {
					logger.error("Armada could not be reached!");
				}
			}

		}
		catch(Exception e) {
			logger.error(e.getMessage(), e);
		}
	}
}
