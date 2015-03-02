package edu.depaul.agent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.json.stream.JsonParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import edu.depaul.armada.model.AgentContainerLog;

public class LogCollector {

	private static final Logger logger = LoggerFactory.getLogger(LogCollector.class);
	
	private ThreadLocal<JsonParser> jsonParser = new ThreadLocal<JsonParser>();
	private Map<String, CpuTime> previousCpuTotals = new HashMap<>();
	private String cAdvisorURL;

	/**
	 * 
	 * @param cAdvisorURL url giving the base location of the 
	 * json data for active containers
	 */
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
			logger.error(e.getMessage(), e);
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
		String memoryLimit = jsonParser.get().getString();

		//if the number is big enough to be the max value,
		// then cAdvisor has defaulted it to  have an "unlimited" limit of memory.
		// Set it as -1 to more clearly indicate that there is no hard limit.
		if (memoryLimit.length() >= 19) {
			memoryLimit = "-1";
		}
		containerLog.memTotal = Long.valueOf(memoryLimit);

	}

	private synchronized void setContainerTimestamp(AgentContainerLog containerLog) throws Exception {
		containerLog.timestamp = new Timestamp(System.currentTimeMillis());
	}

	private void setContainerCpuTotalUsage(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("cpu");
		iterateParserUntilKeyMatch("total");
		nextJson(); //select total cpu usage

		boolean previousCpuTotalExists = previousCpuTotals.containsKey(containerLog.name);
		Long currentCpuNanosTotal = jsonParser.get().getLong();
		Long lastCalculatedCpuNanosTotal;
		Timestamp lastCpuTimestamp;
		if (previousCpuTotalExists) {
			CpuTime previousTime = previousCpuTotals.get(containerLog.name);
			lastCalculatedCpuNanosTotal = previousTime.nanoseconds;
			lastCpuTimestamp = previousTime.timestamp;

			long usedNanosInInterval = currentCpuNanosTotal - lastCalculatedCpuNanosTotal;
			long thisTimestamp = containerLog.timestamp.getTime();
			long lastTimestamp = lastCpuTimestamp.getTime();
			long totalMillisInInterval = thisTimestamp - lastTimestamp;

			//multiply by 1000000 to go from millis to nanos
			Double cpuPercentUsage = (double) usedNanosInInterval / ((double)totalMillisInInterval * 1000000.0);

			//percentage of CPU used, times 100 to represent 2 decimal places.
			// Example: if 13.25% of cpu is used, then:
			//      cpuPercentUsage = 13.25
			//      containerLog.cpuUsed = 1325
			containerLog.cpuUsed = (long)(cpuPercentUsage * 100);


		} else {
			//Because this means it's the first log collection of this container,
			// there is no interval to measure CPU used over.
			containerLog.cpuUsed = 0;
			logger.warn("TIMESTAMP: " + containerLog.timestamp);
			previousCpuTotals.put(containerLog.name, new CpuTime(containerLog.timestamp, currentCpuNanosTotal));
		}
	}

	private void setContainerMemoryUsage(AgentContainerLog containerLog) throws Exception {
		iterateParserUntilKeyMatch("memory");
		iterateParserUntilKeyMatch("usage");
		nextJson();

		containerLog.memUsed = jsonParser.get().getLong() / 1024; //KB of memory used
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

	public class CpuTime {
		public final Timestamp timestamp;
		public final Long nanoseconds;

		public CpuTime(Timestamp timestamp, Long nanoseconds) {
			this.timestamp = timestamp;
			this.nanoseconds = nanoseconds;
		}
	}
}
