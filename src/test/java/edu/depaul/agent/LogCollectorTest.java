package edu.depaul.agent;

import java.lang.reflect.Field;

import junit.framework.Assert;

import org.junit.Test;

import edu.depaul.armada.model.AgentContainerLog;

public class LogCollectorTest {

	@Test
	public void setcAdvisorURLTest() throws Exception {

		LogCollector logCollector = new LogCollector();
		logCollector.setcAdvisorURL("http://140.192.249.16:8890/api/v1.2/docker");

		//access private field
		Field field = logCollector.getClass().getDeclaredField("cAdvisorURL");
		field.setAccessible(true);

		Assert.assertSame(field.get(logCollector), "http://140.192.249.16:8890/api/v1.2/docker");
	}

	@Test
	public void ListNotEmptyTest() {
		LogCollector logCollector = new LogCollector();
		logCollector.setcAdvisorURL("http://140.192.249.16:8890/api/v1.2/docker");
		java.util.List<AgentContainerLog> list = logCollector.getCurrentLogs();

		Assert.assertFalse(list.isEmpty());
	}

	@Test
	public void emptyCollectionTest() {
		LogCollector logCollector = new LogCollector();
		logCollector.setcAdvisorURL("List should be empty");
		java.util.List<AgentContainerLog> list = logCollector.getCurrentLogs();

		Assert.assertTrue(list.isEmpty());
	}

	@Test
	public void checkFieldInitializationTest() {
		LogCollector logCollector = new LogCollector();
		logCollector.setcAdvisorURL("http://140.192.249.16:8890/api/v1.2/docker");
		java.util.List<AgentContainerLog> list = logCollector.getCurrentLogs();

		for (AgentContainerLog containerLog : list) {
			Assert.assertNotNull(containerLog.containerUniqueId);
			Assert.assertNotNull(containerLog.name);
			Assert.assertTrue(containerLog.cpuTotal > 0);
			//Assert.assertTrue(l.memTotal > 0);
			Assert.assertNotNull(containerLog.timestamp);
			Assert.assertTrue(containerLog.cpuUsed > 0);
			Assert.assertTrue(containerLog.memUsed > 0);
			Assert.assertTrue(containerLog.diskTotal > 0);
			Assert.assertTrue(containerLog.diskUsed > 0);

		}
	}

	@Test
	public void checkContainerUniqueId() {
		LogCollector logCollector = new LogCollector();

		//create a string with the JSON that we're test parsing
		String testJson = "{ /docker/17e93e2b85aff1233a841746a6b28f73e0ef7413ebf36f0f658837f0d13db51e: {\n" +
				"name: \"/docker/17e93e2b85aff1233a841746a6b28f73e0ef7413ebf36f0f658837f0d13db51e\",\n" +
				"aliases: [\n" +
				"\"deployer-classtest-1\",\n" +
				"\"17e93e2b85aff1233a841746a6b28f73e0ef7413ebf36f0f658837f0d13db51e\"\n" +
				"],\n" +
				"namespace: \"docker\",\n" +
				"spec: {\n" +
				"has_cpu: true,\n" +
				"cpu: {\n" +
				"limit: 1024,\n" +
				"max_limit: 0,\n" +
				"mask: \"0-7\"\n" +
				"},\n" +
				"has_memory: true,\n" +
				"memory: {\n" +
				"limit: 18446744073709552000,\n" +
				"swap_limit: 18446744073709552000\n" +
				"},\n" +
				"has_network: true,\n" +
				"has_filesystem: true\n" +
				"},\n" +
				"stats: [\n" +
				"{\n" +
				"timestamp: \"2015-02-23T22:51:25.014432807Z\",\n" +
				"cpu: {\n" +
				"usage: {\n" +
				"total: 6545364430269,\n" +
				"per_cpu_usage: [\n" +
				"2768354142138,\n" +
				"972900962624,\n" +
				"915988367371,\n" +
				"1575576553625,\n" +
				"56474072829,\n" +
				"96769364658,\n" +
				"73995651958,\n" +
				"85305315066\n" +
				"],\n" +
				"user: 2215200000000,\n" +
				"system: 3351510000000\n" +
				"},\n" +
				"load: 0\n" +
				"},\n" +
				"diskio: { },\n" +
				"memory: {\n" +
				"usage: 162570240,\n" +
				"working_set: 58372096,\n" +
				"container_data: {\n" +
				"pgfault: 21876626,\n" +
				"pgmajfault: 131\n" +
				"},\n" +
				"hierarchical_data: {\n" +
				"pgfault: 21876626,\n" +
				"pgmajfault: 131\n" +
				"}\n" +
				"},\n" +
				"network: {\n" +
				"rx_bytes: 0,\n" +
				"rx_packets: 0,\n" +
				"rx_errors: 0,\n" +
				"rx_dropped: 0,\n" +
				"tx_bytes: 0,\n" +
				"tx_packets: 0,\n" +
				"tx_errors: 0,\n" +
				"tx_dropped: 0\n" +
				"},\n" +
				"filesystem: [\n" +
				"{\n" +
				"device: \"/dev/disk/by-uuid/1a3998f2-2fd1-4af8-8556-de86d8d361b2\",\n" +
				"capacity: 978034905088,\n" +
				"usage: 69632,\n" +
				"reads_completed: 0,\n" +
				"reads_merged: 0,\n" +
				"sectors_read: 0,\n" +
				"read_time: 0,\n" +
				"writes_completed: 0,\n" +
				"writes_merged: 0,\n" +
				"sectors_written: 0,\n" +
				"write_time: 0,\n" +
				"io_in_progress: 0,\n" +
				"io_time: 0,\n" +
				"weighted_io_time: 0\n" +
				"}\n" +
				"]\n" +
				"} }";

		//create an input stream from that string

		//set logCollector's JsonParser to parse that stream

		//Create a new empty loglist

		//call logCollector.parseJson(logList)

		//assert that the log list's log fits criteria for this test
	}

}
