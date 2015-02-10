package edu.depaul.agent;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.depaul.armada.model.*;

public class LogCollector {

	private int numberOfContainers;
	private JsonDataRetrieval data;
	private String url;
	
	public LogCollector(String _url) {
		this.url = _url;
		data = new JsonDataRetrieval(_url);
		numberOfContainers = data.getContainerName().size();
	}
	
	/**
	 * 
	 * @param i index of container in list of containers running
	 * @return container at index
	 */
	public AgentContainer createContainerLog(int i) {
		
		//container unique id
		ArrayList<String> containerUniqueIds = data.getContainerName();
		
		//retrieve container by alternative aliases 
		ArrayList<String> containerNames = data.getContainerAltName();
		ArrayList<HashMap<String, String>> statisticMapList = new ArrayList<HashMap<String,String>>();
		ArrayList<AgentContainer> logList = new ArrayList<AgentContainer>();
		
		//list to hold hash map data DATA MUST BE IN THIS ORDER WHEN PLACING IN AgentContainer OBJECT
		statisticMapList.add(data.getContainerCpuLimit());
		statisticMapList.add(data.getContainerCpuTotal());
		statisticMapList.add(data.getContianerTimestamp());
		statisticMapList.add(data.getContainerMemoryUsage());
		statisticMapList.add(data.getContinerMemoryLimit());
		statisticMapList.add(data.getContainerFileSystemCapacity());
		statisticMapList.add(data.getContainerFileSystemUsage());
		
		//create a new log for each container
		AgentContainer containerLog = new AgentContainer();
		
		//run through each hash map in the array list
		for(HashMap<String,String> logStatisticMap : statisticMapList) {
			logStatisticMap.remove(null);
			
			Set<Entry<String, String>> set = logStatisticMap.entrySet();
			Iterator<Entry<String, String>> iterator = set.iterator();
			
			while(iterator.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>)iterator.next();
				
				//get the container(s) unique id
				containerLog.containerUniqueId = containerUniqueIds.get(i);
				
				//get container short id
				containerLog.name = containerNames.get(i);
				
				//send cAdvisor url
				containerLog.cAdvisorURL = url;
							
				if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.CPU_TOTAL)) {
					containerLog.cpu = new BigInteger(entry.getValue()).longValue();
				} else if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.TIMESTAMP)) {
					//containerLog.timestamp = new java.sql.TimeStamp(entry.getValue()); //convert to time stamp			
				} else if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.MEMORY_USAGE)) {
					containerLog.memUsed = new BigInteger(entry.getValue()).longValue();
				} else if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.FILESYSTEM_USAGE)) {
					containerLog.filesystemUsed = new BigInteger(entry.getValue()).longValue();
				} else if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.FILESYSTEM_CAPACITY)) {
					containerLog.filesystemTotal = new BigInteger(entry.getValue()).longValue();
				} else if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.MEMORY_LIMIT)) {
					containerLog.memTotal = new BigInteger(entry.getValue()).longValue();
				} else if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.CPU_LIMIT)) {
					containerLog.cpuUsed = new BigInteger(entry.getValue()).longValue(); //CHECK THIS
				}
			}
			logList.add(containerLog);
		}
		return logList.get(i);
	}
	

	public int getNumberOfContainers() {
		return numberOfContainers;
	}
}
