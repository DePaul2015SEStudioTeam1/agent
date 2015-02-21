package edu.depaul.agent;

import junit.framework.Assert;

import org.junit.Test;

import edu.depaul.armada.model.AgentContainerLog;

public class LogCollectorTest {

	@Test
	public void ListNotEmptyTest() {
		//"http://140.192.249.16:8890/api/v1.2/docker"
		LogCollector log = new LogCollector();
		log.setcAdvisorURL("http://140.192.249.16:8890/api/v1.2/docker");
		java.util.List<AgentContainerLog> list = log.getCurrentLogs();

		Assert.assertFalse(list.isEmpty());
	}
	
	@Test
	public void emptyCollectionTest() {
		//"http://140.192.249.16:8890/api/v1.2/docker"
		LogCollector log = new LogCollector();
		log.setcAdvisorURL("List should be empty");
		java.util.List<AgentContainerLog> list = log.getCurrentLogs();
	
		Assert.assertTrue(list.isEmpty());
	}
	
	@Test
	public void checkFieldInitializationTest() {
		LogCollector log = new LogCollector();
		log.setcAdvisorURL("http://140.192.249.16:8890/api/v1.2/docker");
		java.util.List<AgentContainerLog> list = log.getCurrentLogs();
		
		for (AgentContainerLog l : list) {
			Assert.assertNotNull(l.containerUniqueId);
			Assert.assertNotNull(l.name);
			Assert.assertTrue(l.cpuTotal > 0);
			//Assert.assertTrue(l.memTotal > 0);
			Assert.assertNotNull(l.timestamp);
			Assert.assertTrue(l.cpuUsed > 0);
			Assert.assertTrue(l.memUsed > 0);
			Assert.assertTrue(l.diskTotal > 0);
			Assert.assertTrue(l.diskUsed > 0);
			
			
		}
	}

}
