package edu.depaul.agent;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import edu.depaul.armada.model.ContainerLog;

public class LogCollector {

	private int numberOfContainers;
	private JsonDataRetrieval data;
	
	public LogCollector(String _url) {
		data = new JsonDataRetrieval(_url);
		numberOfContainers = data.getContainerName().size();
	}
	
	/**
	 * 
	 * @param i index of container in list of containers running
	 * @return container at index
	 */
	public ContainerLog createContainerLog(int i) {
		
		//container unique id
		ArrayList<String> containerIds = data.getContainerName();
		
		//retrieve container by alternative aliases 
		ArrayList<String> containerNames = data.getContainerAltName();
		ArrayList<HashMap<String, String>> statisticMapList = new ArrayList<HashMap<String,String>>();
		ArrayList<ContainerLog> logList = new ArrayList<ContainerLog>();
		
		//list to hold hash map data
		statisticMapList.add(data.getContainerCpuLimit());
		statisticMapList.add(data.getContainerCpuTotal());
		statisticMapList.add(data.getContianerTimestamp());
		statisticMapList.add(data.getContainerMemoryUsage());
		statisticMapList.add(data.getContinerMemoryLimit());
		statisticMapList.add(data.getContainerFileSystemCapacity());
		statisticMapList.add(data.getContainerFileSystemUsage());
		
		//create a new log for each container
		ContainerLog containerLog = new ContainerLog();
		
		//run through each hash map in the array list
		for(HashMap<String,String> logStatisticMap : statisticMapList) {
			logStatisticMap.remove(null);
			
			Set<Entry<String, String>> set = logStatisticMap.entrySet();
			Iterator<Entry<String, String>> iterator = set.iterator();
			
			while(iterator.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>)iterator.next();
				
				//get the container(s) unique id
				containerLog.setContainerId(containerIds.get(i));
				
				if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.CPU_TOTAL)) {
					containerLog.setTotalCpuUsage(new BigInteger(entry.getValue()).longValue());
				} else if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.TIMESTAMP)) {
					containerLog.setTimestamp(entry.getValue());					
				} else if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.MEMORY_USAGE)) {
					containerLog.setMemUsage(new BigInteger(entry.getValue()).longValue());
				} else if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.FILESYSTEM_USAGE)) {
					containerLog.setFilesystemUsage(new BigInteger(entry.getValue()).longValue());
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
