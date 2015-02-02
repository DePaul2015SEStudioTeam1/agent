package edu.depaul.agent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import edu.depaul.agent.JsonDataRetrieval.CadvisorData;


public class Cadvisor {

	private int numberOfContainers;
	private JsonDataRetrieval data;
	
	public Cadvisor(String _url) {
		data = new JsonDataRetrieval(_url);
		numberOfContainers = data.getContainerName().size();
	}
	
	/**
	 * 
	 * @param i index of container in list of containers running
	 * @return container at index
	 */
	public Container_log CreateContainerLog(int i) {
		ArrayList<String> id = data.getContainerName();
		ArrayList<String> names = data.getContainerAltName();
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		ArrayList<Container_log> log = new ArrayList<Container_log>();
		
		//list to hold hash map data
		list.add(data.getContainerCpuLimit());
		list.add(data.getContainerCpuTotal());
		list.add(data.getContianerTimestamp());
		list.add(data.getContainerMemoryUsage());
		list.add(data.getContinerMemoryLimit());
		list.add(data.getContainerFileSystemCapacity());
		list.add(data.getContainerFileSystemUsage());
		
		//create a new log for each container
		Container_log _container_log = new Container_log();
		
		//run through each hash map in the array list
		for(HashMap<String,String> hm : list) {
			hm.remove(null, null);
			
			Set<Entry<String, String>> set = hm.entrySet();
			Iterator<Entry<String, String>> iterator = set.iterator();
			
			while(iterator.hasNext()) {
				Map.Entry<String, String> _entry = (Map.Entry<String, String>)iterator.next();
				
				//get the containers alt id
				_container_log.setID(names.get(i));
				
				//get container id
				_container_log.setContainerID(id.get(i));
				
				if(_entry.getKey().equalsIgnoreCase(names.get(i) + CadvisorData.CL)) {
					_container_log.setCpuLimit(_entry.getValue());
					
				} else if(_entry.getKey().equalsIgnoreCase(names.get(i) + CadvisorData.CT)) {
					_container_log.setCpuTotal(_entry.getValue());
					
				} else if(_entry.getKey().equalsIgnoreCase(names.get(i) + CadvisorData.TIME)) {
					_container_log.setTimeStamp(_entry.getValue());
					
				} else if(_entry.getKey().equalsIgnoreCase(names.get(i) + CadvisorData.MU)) {
					_container_log.setMemUsage(_entry.getValue());
					
				} else if(_entry.getKey().equalsIgnoreCase(names.get(i) + CadvisorData.ML)) {
					_container_log.setMemLimit(_entry.getValue());
					
				} else if(_entry.getKey().equalsIgnoreCase(names.get(i) + CadvisorData.FSC)) {
					_container_log.setFileSystemCapacity(_entry.getValue());
					
				} else if(_entry.getKey().equalsIgnoreCase(names.get(i) + CadvisorData.FSU)) {
					_container_log.setFileSystem_Usage(_entry.getValue());
				}
			}
			log.add(_container_log);
		}
		return log.get(i);
	}
	
	/**
	 * 
	 * @return number of containers running
	 */
	public int getNumberOfContainers() {
		return numberOfContainers;
	}
}
