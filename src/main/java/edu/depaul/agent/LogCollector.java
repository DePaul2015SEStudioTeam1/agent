package edu.depaul.agent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonParser;

import org.apache.commons.lang.time.DateUtils;

import edu.depaul.armada.model.AgentContainerLog;

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
	public static List<AgentContainerLog> getCurrentLogs(){
		final List<AgentContainerLog> logList = new ArrayList<>();
		parseJson(logList);
		return logList;
	}

	private static void parseJson(final List<AgentContainerLog> logList) {
		/**
		 * Logic:
		 *      1. while jsonParser has next
		 *          2. create a ContainerLog
		 *          3. call populateLog(ContainerLog)
		 *          4. Put containerLog into logList
		 *      2. exit when all containers have been stepped into
		 */
		while (jsonParser.hasNext()) {
			AgentContainerLog containerLog = new AgentContainerLog();
			populateLog(containerLog);
			logList.add(containerLog);
		}
	}

	private static void populateLog(final AgentContainerLog containerLog) {

		try {
			//ordering of setting values is important
			setContainerUniqueId(containerLog);
			setContainerName(containerLog);
			setContainerCpuLimit(containerLog);
			setContainerMemoryLimit(containerLog);
			setContainerTimestamp(containerLog);
			setContainerCpuTotalUsage(containerLog);
			setContainerMemoryUsage(containerLog);
			setContainerFilesystemCapacity(containerLog);
			setContainerFilesystemUsage(containerLog);

			iterateParserUntilKeyMatch("/.*/.*");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void setContainerUniqueId(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("name"); //"name" is the JSON field that actually holds the ID
		nextJson();
		containerLog.containerUniqueId = jsonParser.getString();
	}

	private static void setContainerName(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("aliases"); //"aliases" holds the human readable name
		nextJson();
		nextJson(); //iterate twice, since it is the beginning of an array
		containerLog.name = jsonParser.getString();
	}

	private static void setContainerCpuLimit(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("cpu"); //"name" is the JSON field that actually holds the ID
		iterateParserUntilKeyMatch("limit"); //"name" is the JSON field that actually holds the ID
		nextJson();
		containerLog.cpuTotal = jsonParser.getLong();
	}

	private static void setContainerMemoryLimit(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("memory"); //"name" is the JSON field that actually holds the ID
		iterateParserUntilKeyMatch("limit"); //"name" is the JSON field that actually holds the ID
		nextJson();
		containerLog.memTotal = jsonParser.getLong();
	}

	private static void setContainerTimestamp(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("timestamp");
		nextJson(); //select timestamp value
		String timestamp = jsonParser.getString();
		Date date = DateUtils.parseDate(timestamp, new String[]{"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"});
		containerLog.timestamp = new Timestamp(date.getTime() - 10 * 60 * 60 * 1000);
	}

	private static void setContainerCpuTotalUsage(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("cpu");
		iterateParserUntilKeyMatch("total");
		nextJson(); //select total cpu usage
		containerLog.cpuUsed = jsonParser.getLong();
	}

	private static void setContainerMemoryUsage(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("memory");
		iterateParserUntilKeyMatch("usage");
		nextJson();
		containerLog.memUsed = jsonParser.getLong();
	}

	private static void setContainerFilesystemCapacity(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("filesystem");
		iterateParserUntilKeyMatch("capacity");
		nextJson();
		containerLog.diskTotal = jsonParser.getLong();
	}

	private static void setContainerFilesystemUsage(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("usage");
		nextJson();
		containerLog.diskUsed = jsonParser.getLong();
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

}
