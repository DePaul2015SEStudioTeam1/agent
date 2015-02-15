package edu.depaul.agent;

import static org.junit.Assert.*;

import java.net.MalformedURLException;

import junit.framework.Assert;

import org.junit.Test;

import edu.depaul.armada.model.AgentContainer;

public class LogCollectorTest {

	LogCollector log = new LogCollector("http://140.192.249.16:8890/api/v1.2/docker");
	
	@Test
	public void createContainerLogTest() {
		
		//write a test for null argument
		
		boolean instance = false;
		
		AgentContainer l = log.createContainerLog(0);
		
		if(l instanceof AgentContainer) instance = true;
		
		Assert.assertTrue(instance);
	}
	
	@Test
	public void exceedIndexTest() {
		
		boolean thrown = false;
			
		//check if exception is thrown if index isn't within range
		//exception should be thrown if index is greater than # of containers
		try {
			log.createContainerLog(6);
		} catch(IllegalArgumentException e) { thrown = true; }
		Assert.assertTrue(thrown);
		
		//exception should be thrown if index is less than # of containers
		thrown = false;
		try {
			log.createContainerLog(-1);
		} catch(IllegalArgumentException e) { thrown = true; }
		Assert.assertTrue(thrown);
		
		//check that exception isn't thrown on an index within range
		thrown = false;
		try {
			for(int i = 0; i < log.getNumberOfContainers(); i++)
				log.createContainerLog(i);
			
		} catch(IllegalArgumentException e) {
			thrown = true;
		}
		Assert.assertFalse(thrown);
	}
	@Test
	public void ArgumentNotNullTest() {
		LogCollector l = null;
		boolean thrown = false;
		
		try {
			 l = new LogCollector(null);
		} catch (IllegalArgumentException e) {
			thrown = true;
		}
		
		Assert.assertTrue(thrown);
	}
	
	@Test
	public void MalformedURLExceptionTest() { //remove test and give to dataRetrieval
		
		boolean thrown = false;
		
		try {
			
			//only here to remove unreachable error
			new java.net.URL("www.ccc.edu"); //unused
			
			new LogCollector("afsdfasd");
		} catch(MalformedURLException e) {
			thrown = true;
		}
		
		Assert.assertTrue(thrown);

	}
	
	@Test
	public void numberOfContainersTest() {
	
		AgentContainer[] logArray = new AgentContainer[log.getNumberOfContainers()];
		
		for(int i = 0; i < log.getNumberOfContainers(); i++)
			logArray[i] = log.createContainerLog(i);
		
		Assert.assertEquals(logArray.length, log.getNumberOfContainers());
	}
	
	@Test
	public void dataFieldsInitializationTest() {
	
		for(int i = 0; i < log.getNumberOfContainers(); i++) {
			
			AgentContainer dataLog = log.createContainerLog(i);
		
			//check if any of the data is initialized to null
			Assert.assertNotNull(dataLog.cAdvisorURL);
			Assert.assertNotNull(dataLog.name);
			Assert.assertNotNull(dataLog.containerUniqueId);
			Assert.assertNotNull(dataLog.timestamp);
		
			//check that values are greater than 0
			Assert.assertTrue((dataLog.memTotal >= 0) ? true : false);
			Assert.assertTrue((dataLog.memUsed >= 0) ? true : false);
			Assert.assertTrue((dataLog.cpuTotal >= 0) ? true : false);
			Assert.assertTrue((dataLog.cpuUsed >= 0) ? true : false);
			Assert.assertTrue((dataLog.filesystemTotal >= 0) ? true : false);
			Assert.assertTrue((dataLog.filesystemUsed >= 0) ? true : false);
		
		}
	}

}
