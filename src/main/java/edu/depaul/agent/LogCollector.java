package edu.depaul.agent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.json.Json;
import javax.json.stream.JsonParser;

import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.depaul.armada.model.AgentContainerLog;

/**
 * 
 * @author Deonte Johnson
 * 
 * modified 2/15/15 - Deonte
 *
 */
public class LogCollector {

	private static final Logger logger = LoggerFactory.getLogger(LogCollector.class);
	
	private ThreadLocal<JsonParser> jsonParser = new ThreadLocal<JsonParser>();
	private String cAdvisorURL;

	public void setcAdvisorURL(String cAdvisorURL) {
		this.cAdvisorURL = cAdvisorURL;
	}

	/**
	 * This will get a List of ContainerLogs, one for each container.
	 * The agent will use this returned map to log to armada.
	 *
	 * @return  List of ContainerLog
	 */
	public List<AgentContainerLog> getCurrentLogs(){
		try {
			URL url = new URL(cAdvisorURL);
			HttpURLConnection cAdvisorConnection = (HttpURLConnection) url.openConnection();
			jsonParser.set(Json.createParser(cAdvisorConnection.getInputStream()));
			List<AgentContainerLog> logList = new ArrayList<>();
			parseJson(logList);
			return logList;
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
		return Collections.emptyList();
	}

	private void parseJson(final List<AgentContainerLog> logList) {
		/**
		 * Logic:
		 *      1. while jsonParser has next
		 *          2. create a ContainerLog
		 *          3. call populateLog(ContainerLog)
		 *          4. Put containerLog into logList
		 *      2. exit when all containers have been stepped into
		 */
		while (jsonParser.get().hasNext()) {
			AgentContainerLog containerLog = new AgentContainerLog();
			populateLog(containerLog);
			logList.add(containerLog);
		}
	}

	private void populateLog(final AgentContainerLog containerLog) {

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

	private void setContainerUniqueId(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("name"); //"name" is the JSON field that actually holds the ID
		nextJson();
		containerLog.containerUniqueId = jsonParser.get().getString();
	}

	private void setContainerName(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("aliases"); //"aliases" holds the human readable name
		nextJson();
		nextJson(); //iterate twice, since it is the beginning of an array
		containerLog.name = jsonParser.get().getString();
	}

	private void setContainerCpuLimit(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("cpu"); //"name" is the JSON field that actually holds the ID
		iterateParserUntilKeyMatch("limit"); //"name" is the JSON field that actually holds the ID
		nextJson();
		containerLog.cpuTotal = jsonParser.get().getLong();
	}

	private void setContainerMemoryLimit(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("memory"); //"name" is the JSON field that actually holds the ID
		iterateParserUntilKeyMatch("limit"); //"name" is the JSON field that actually holds the ID
		nextJson();
		containerLog.memTotal = jsonParser.get().getLong();
	}

	private void setContainerTimestamp(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("timestamp");
		nextJson(); //select timestamp value
		String timestamp = jsonParser.get().getString();
		Date date = DateUtils.parseDate(timestamp, new String[]{"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"});
		containerLog.timestamp = new Timestamp(date.getTime() - 10 * 60 * 60 * 1000);
	}

	private void setContainerCpuTotalUsage(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("cpu");
		iterateParserUntilKeyMatch("total");
		nextJson(); //select total cpu usage
		containerLog.cpuUsed = jsonParser.get().getLong();
	}

	private void setContainerMemoryUsage(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("memory");
		iterateParserUntilKeyMatch("usage");
		nextJson();
		containerLog.memUsed = jsonParser.get().getLong();
	}

	private void setContainerFilesystemCapacity(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("filesystem");
		iterateParserUntilKeyMatch("capacity");
		nextJson();
		containerLog.diskTotal = jsonParser.get().getLong();
	}

	private void setContainerFilesystemUsage(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("usage");
		nextJson();
		containerLog.diskUsed = jsonParser.get().getLong();
	}

	private void iterateParserUntilKeyMatch(String regexKey) {
		JsonParser.Event nextJsonEvent = nextJson();

		while (nextJsonEvent != null) {
			if (nextJsonEvent == JsonParser.Event.KEY_NAME) {
				if (jsonParser.get().getString().matches(regexKey)) return;
			}
			nextJsonEvent = nextJson();
		}
	}

	//iterates to the next json event and returns it
	private JsonParser.Event nextJson() {
		return (jsonParser.get().hasNext()) ? jsonParser.get().next() : null;
	}

}
