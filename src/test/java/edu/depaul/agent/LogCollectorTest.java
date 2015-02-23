package edu.depaul.agent;

import java.lang.reflect.Field;

import junit.framework.Assert;

import org.junit.Test;

import edu.depaul.armada.model.AgentContainerLog;

public class LogCollectorTest {

	@Test
	public void setcAdvisorURLTest() throws Exception {
		
		LogCollector log = new LogCollector();
		log.setcAdvisorURL("http://140.192.249.16:8890/api/v1.2/docker");
		
		//access private field
		Field field = log.getClass().getDeclaredField("cAdvisorURL");
		field.setAccessible(true);
		
		Assert.assertSame(field.get(log), "http://140.192.249.16:8890/api/v1.2/docker");
	}
	
	@Test
	public void ListNotEmptyTest() {
		LogCollector log = new LogCollector();
		log.setcAdvisorURL("http://140.192.249.16:8890/api/v1.2/docker");
		java.util.List<AgentContainerLog> list = log.getCurrentLogs();

		Assert.assertFalse(list.isEmpty());
	}
	
	@Test
	public void emptyCollectionTest() {
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
