package edu.depaul.agent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonParser;

/**
 * 
 * @author Deonte Johnson
 * 
 * modified 2/15/15 - Deonte
 *
 */
public class LogCollector {

	//TODO: make this value configurable, able to pass from configuration
	private static final String CADVISOR_URL = "http://140.192.249.16:8890/api/v1.2/docker";
//	private static final String CADVISOR_URL = "http://127.0.0.1:8890/api/v1.2/docker";
	private static JsonParser jsonParser = null;

	public static void connect() {
		try {
			URL url = new URL(CADVISOR_URL);
			HttpURLConnection cAdvisorConnection = (HttpURLConnection) url.openConnection();
			jsonParser = Json.createParser(cAdvisorConnection.getInputStream());
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}
	}
	/**
	 *      This will get a List of ContainerLogs, one for each container.
	 *      The agent will use this returned map to log to maestro.
	 *
	 *      @return  List of ContainerLog
	 */
	public static List<ContainerLog> getCurrentLogs(){
		final List<ContainerLog> logList = new ArrayList<>();
		parseJson(logList);
		return logList;
	}

	private static void parseJson(final List<ContainerLog> logList) {
		/**
		 * Logic:
		 *      1. while jsonParser has next
		 *          2. create a ContainerLog
		 *          3. call populateLog(ContainerLog)
		 *          4. Put containerLog into logList
		 *      2. exit when all containers have been stepped into
		 */
		while (jsonParser.hasNext()) {
			ContainerLog containerLog = new ContainerLog();
			JsonParser.Event nextContainerId = jsonParser.next();
			nextContainerId = jsonParser.next();
			populateLog(containerLog);
			logList.add(containerLog);
		}
	}

	private static void populateLog(final ContainerLog containerLog) {
		/**
		 * JSON is structured like this...
		 *
		 *      { "containerId" :
		 *          {   name : "name",
		 *              aliases : [ "alias", "alias" ],
		 *              namespace : "namespace",
		 *              spec :
		 *                  {
		 *                  }
		 *              stats : []
		 *          }
		 *        "containerId2" :
		 *          {
		 *              ...
		 *          }
		 *      }
		 */
		try {




			containerLog.setContainerID(jsonParser.getString());

			iterateParserUntilKeyMatch("timestamp");
			nextJson(); //select timestamp value
			containerLog.setTimeStamp(jsonParser.getString());

			iterateParserUntilKeyMatch("cpu");
			iterateParserUntilKeyMatch("total");
			nextJson(); //select total cpu usage
			containerLog.setCpuTotal(jsonParser.getString());

			iterateParserUntilKeyMatch("memory");
			iterateParserUntilKeyMatch("usage");
			nextJson();
			containerLog.setMemUsage(jsonParser.getString());

			iterateParserUntilKeyMatch("filesystem");
			iterateParserUntilKeyMatch("usage");
			nextJson();
			containerLog.setFileSystemUsage(jsonParser.getString());


			iterateParserUntilKeyMatch("//.*//.*");
		} catch (NullPointerException npe) {
			npe.printStackTrace();
		}

	}

	private static void iterateParserUntilKeyMatch(String regexKey) {
		JsonParser.Event nextJsonEvent = nextJson();

		while (nextJsonEvent != null) {
			if (nextJsonEvent == JsonParser.Event.KEY_NAME) {
				if (jsonParser.getString().matches(regexKey)) return;
			}
			nextJsonEvent = nextJson();
		}
	}

	//iterates to the next json event and returns it
	private static JsonParser.Event nextJson() {
		return (jsonParser.hasNext()) ? jsonParser.next() : null;
	}

//	private int numberOfContainers;
//	private JsonDataRetrieval data;
//	private String url;
//
//	public LogCollector(String _url) {
//		if(_url == null) {
//			throw new IllegalArgumentException();
//		}
//		this.url = _url;
//		data = new JsonDataRetrieval(_url);
//		numberOfContainers = data.getContainerName().size();
//	}
//
//	/**
//	 *
//	 * @param i index of container in list of containers running
//	 * @return container at index
//	 */
//	public AgentContainer createContainerLog(int i) {
//
//		if((i >= numberOfContainers) || (i < 0))
//			throw new IllegalArgumentException
//			("index must be between 0 - " + (numberOfContainers-1));
//
//		//container unique id
//		ArrayList<String> containerUniqueIds = data.getContainerName();
//
//		//retrieve container by alternative aliases
//		ArrayList<String> containerNames = data.getContainerAltName();
//		ArrayList<HashMap<String, String>> statisticMapList = new ArrayList<HashMap<String,String>>();
//		ArrayList<AgentContainer> logList = new ArrayList<AgentContainer>();
//
//		statisticMapList.add(data.getContainerCpuLimit());
//		statisticMapList.add(data.getContainerCpuTotal());
//		statisticMapList.add(data.getContianerTimestamp());
//		statisticMapList.add(data.getContainerMemoryUsage());
//		statisticMapList.add(data.getContinerMemoryLimit());
//		statisticMapList.add(data.getContainerFileSystemCapacity());
//		statisticMapList.add(data.getContainerFileSystemUsage());
//
//		//create a new log for each container
//		AgentContainer containerLog = new AgentContainer();
//
//		//run through each hash map in the array list
//		for(HashMap<String,String> logStatisticMap : statisticMapList) {
//			logStatisticMap.remove(null);
//
//			Set<Entry<String, String>> set = logStatisticMap.entrySet();
//			Iterator<Entry<String, String>> iterator = set.iterator();
//
//			while(iterator.hasNext()) {
//				Map.Entry<String, String> entry = (Map.Entry<String, String>)iterator.next();
//
//				//get the container(s) unique id
//				containerLog.containerUniqueId = containerUniqueIds.get(i);
//
//				//get container short id
//				containerLog.name = containerNames.get(i);
//
//				//send cAdvisor url
//				containerLog.cAdvisorURL = url;
//
//				if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.CPU_TOTAL)) {
//					containerLog.cpuTotal = new BigInteger(entry.getValue()).longValue();
//				} else if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.TIMESTAMP)) {
//					containerLog.timestamp = java.sql.Timestamp.valueOf(entry.getValue());
//				} else if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.MEMORY_USAGE)) {
//					containerLog.memUsed = new BigInteger(entry.getValue()).longValue();
//				} else if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.FILESYSTEM_USAGE)) {
//					containerLog.filesystemUsed = new BigInteger(entry.getValue()).longValue();
//				} else if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.FILESYSTEM_CAPACITY)) {
//					containerLog.filesystemTotal = new BigInteger(entry.getValue()).longValue();
//				} else if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.MEMORY_LIMIT)) {
//					//memTotal kept coming out to -1, this fixed the issue - DJ
//					containerLog.memTotal = (long) Double.parseDouble(entry.getValue());//new BigInteger(entry.getValue()).longValue();
//				} else if(entry.getKey().equalsIgnoreCase(containerNames.get(i) + JsonDataRetrieval.CAdvisorData.CPU_LIMIT)) {
//					containerLog.cpuUsed = new BigInteger(entry.getValue()).longValue();
//				}
//			}
//			logList.add(containerLog);
//		}
//		return logList.get(i);
//	}
//
//
//	public int getNumberOfContainers() {
//		return numberOfContainers;
//	}
}
