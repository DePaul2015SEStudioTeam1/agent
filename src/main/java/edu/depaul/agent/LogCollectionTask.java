package edu.depaul.agent;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.remoting.RemoteConnectFailureException;

import edu.depaul.armada.model.AgentContainerLog;
import edu.depaul.armada.service.ArmadaService;

/**
 * This class obtains container logs to be parsed by the agent.
 *
 */
public class LogCollectionTask implements Runnable {
	
	private static final Logger logger = LoggerFactory.getLogger(LogCollectionTask.class);

	private LogCollector logCollector;
	private ArmadaService armadaService; 
	
	/**
	 * Creates a reference to the LogCollector object that it is passed.
	 * @param logCollector object for log data collections
	 */
	public void setLogCollector(LogCollector logCollector){
		this.logCollector = logCollector;
	}
	
	/**
	 * Creates a reference to the ArmadaService object that it is passed.
	 * @param armadaService
	 */
	public void setArmadaService(ArmadaService armadaService) {
		this.armadaService = armadaService;
	}

	/**
	 * Runnable method that parses a List<AgentContainerLog> and sends it to Armad.
	 */
	@Override
	public void run() {
		try {
			// AgentContainerLog is a DTO used by the agent to send data over to 
			// the Armada. It's is used to send in all the info that is 
			// needed to save logs
			List<AgentContainerLog> containerLogList = logCollector.getCurrentLogs();
			for(AgentContainerLog log : containerLogList) {
				try {
					armadaService.send(log);
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
