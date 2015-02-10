package edu.depaul.agent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

/**
 * @author Deonte Johnson
 *
 */
public class JsonDataRetrieval {

	private JsonParser jsonParser;
	private String url;

	public enum CAdvisorData {
		CPU_TOTAL,
		CPU_LIMIT,
		TIMESTAMP,
		MEMORY_USAGE,
		MEMORY_LIMIT,
		FILESYSTEM_CAPACITY,
		FILESYSTEM_USAGE
	}

	public JsonDataRetrieval(String _url) {
		this.url = _url;
	}

	private JsonParser openConnection(String _url) {
		JsonParser jsonParser = null;
		try {
			URL cAdvisorUrl = new URL(_url);
			HttpURLConnection cAdvisorConnection = (HttpURLConnection) cAdvisorUrl.openConnection();
			jsonParser = Json.createParser(cAdvisorConnection.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return jsonParser;
	}
	
	public ArrayList<String> getContainerName() {
		jsonParser = this.openConnection(url);
		
		java.util.ArrayList<String> containerNames = new java.util.ArrayList<String>();
		
		while (jsonParser.hasNext()) {
            Event event = jsonParser.next();
           
            if(event == Event.KEY_NAME) {
            	if(jsonParser.getString().equalsIgnoreCase("name")) {
            		jsonParser.next();
            		containerNames.add(jsonParser.getString());
            	}
            }
		}
		return containerNames;
	}
	
	ArrayList<String> getContainerAltName() {
		
		jsonParser = this.openConnection(url);
		
		java.util.ArrayList<String> altNames = new java.util.ArrayList<String>();
		
		while (jsonParser.hasNext()) {
            Event event = jsonParser.next();
           
            if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(jsonParser.getString().equalsIgnoreCase("aliases")) {
            		jsonParser.next(); // START_ARRAY
            		jsonParser.next(); //Alternative container name
            		altNames.add(jsonParser.getString());
            	}
            }
		}
		return altNames;
	}
	
	HashMap<String, String> getContainerCpuTotal() {
		jsonParser = this.openConnection(url);
		
		java.util.HashMap<String, String> cpu = new java.util.HashMap<String, String>();
		String k = null, v = null;
		
		while (jsonParser.hasNext()) {
            Event event = jsonParser.next();
           	
            if(event == Event.KEY_NAME) {
            	
            	 //Get aliases
            	if(jsonParser.getString().equalsIgnoreCase("aliases")) {
            		jsonParser.next(); // START_ARRAY
            		jsonParser.next(); //Alternative container name
            		k = jsonParser.getString() + CAdvisorData.CPU_TOTAL;
            	}
            	
            	//Get cpu total
            	if(jsonParser.getString().equalsIgnoreCase("total")) {
            		jsonParser.next(); //VALUE
            		v = jsonParser.getString();
            	}
            	cpu.put(k, v);
            }
		}
		return cpu;
	}
	
	HashMap<String, String> getContainerCpuLimit() {	
		jsonParser = this.openConnection(url);
		
		HashMap<String, String> cpu = new java.util.HashMap<String, String>();
		
		String k = null, v = null;
		while (jsonParser.hasNext()) {
			Event event = jsonParser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(jsonParser.getString().equalsIgnoreCase("aliases")) {
            		jsonParser.next(); // START_ARRAY
            		jsonParser.next(); //Alternative container name
            		k = jsonParser.getString() + CAdvisorData.CPU_LIMIT;
            	}
            	
            	//Get cpu limit
                if(jsonParser.getString().equalsIgnoreCase("cpu")) {
                	if(jsonParser.next() == Event.START_OBJECT) {
                		jsonParser.next(); //KEY_NAME
                		if(jsonParser.getString().equalsIgnoreCase("limit")) {
                			jsonParser.next(); //VALUE
                			v = jsonParser.getString();
                		}
                	}
                }
            }
		    	
            cpu.put(k, v);
		}
		
		return cpu;
	}
	
	HashMap<String, String> getContainerMemoryUsage() {
		jsonParser = this.openConnection(url);
		
		HashMap<String, String> data = new java.util.HashMap<String, String>();
		String k = null, v = null;
		
		while (jsonParser.hasNext()) {
			Event event = jsonParser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(jsonParser.getString().equalsIgnoreCase("aliases")) {
            		jsonParser.next(); // START_ARRAY
            		jsonParser.next(); //Alternative container name
            		k = jsonParser.getString() + CAdvisorData.MEMORY_USAGE;
            	}
            	
            	//Get memory usage
            	if(jsonParser.getString().equalsIgnoreCase("memory")) {
            		if(jsonParser.next() == Event.START_OBJECT) {
            			jsonParser.next(); //KEY_NAME
            			if(jsonParser.getString().equalsIgnoreCase("usage")) {
            				jsonParser.next(); //VALUE
            				v = jsonParser.getString();
            			}
            		}
            	}
            }
		    	
            data.put(k, v);
		}
		return data;
	}
	
	HashMap<String, String> getContinerMemoryLimit() {
		jsonParser = this.openConnection(url);
		
		HashMap<String, String> data = new java.util.HashMap<String, String>();
		String k = null, v = null;
		
		while (jsonParser.hasNext()) {
			Event event = jsonParser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(jsonParser.getString().equalsIgnoreCase("aliases")) {
            		jsonParser.next(); // START_ARRAY
            		jsonParser.next(); //Alternative container name
            		k = jsonParser.getString() + CAdvisorData.MEMORY_LIMIT;
            	}
            	
            	//Get memory usage
            	if(jsonParser.getString().equalsIgnoreCase("memory")) {
            		if(jsonParser.next() == Event.START_OBJECT) {
            			jsonParser.next(); //KEY_NAME
            			if(jsonParser.getString().equalsIgnoreCase("limit")) {
            				jsonParser.next(); //VALUE
            				v = jsonParser.getString();
            			}
            		}
            	}
            }
		    	
            data.put(k, v);
		}
		return data;
	}
	
	HashMap<String, String> getContainerFileSystemCapacity() {
		jsonParser = this.openConnection(url);
		
		HashMap<String, String> time = new java.util.HashMap<String, String>();
		
		String k = null, v = null;
		while (jsonParser.hasNext()) {
			Event event = jsonParser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(jsonParser.getString().equalsIgnoreCase("aliases")) {
            		jsonParser.next(); // START_ARRAY
            		jsonParser.next(); //Alternative container name
            		k = jsonParser.getString() + CAdvisorData.FILESYSTEM_CAPACITY;
            	}
            	
            	//Get filesystem capacity
            	if(jsonParser.getString().equalsIgnoreCase("capacity")) {
            		jsonParser.next(); //Capacity value
            		v = jsonParser.getString();
            	}
            }
		    	
            time.put(k, v);
		}
		
		return time;
	}
	
	HashMap<String, String> getContainerFileSystemUsage() {
		jsonParser = this.openConnection(url);
		
		HashMap<String, String> time = new java.util.HashMap<String, String>();
		
		String k = null, v = null;
		while (jsonParser.hasNext()) {
			Event event = jsonParser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(jsonParser.getString().equalsIgnoreCase("aliases")) {
            		jsonParser.next(); // START_ARRAY
            		jsonParser.next(); //Alternative container name
            		k = jsonParser.getString() + CAdvisorData.FILESYSTEM_USAGE;
            	}
            	
            	//Get filesystem and usage
            	if(jsonParser.getString().equalsIgnoreCase("capacity")) {
            		jsonParser.next(); //Capacity value
            		jsonParser.next(); //JSON key name
            		jsonParser.next(); //JSON value
            		v = jsonParser.getString();
            	}
            }
		    	
            time.put(k, v);
		}
		
		return time;
	}
	
	HashMap<String, String> getContianerTimestamp() {
		jsonParser = this.openConnection(url);
		
		HashMap<String, String> time = new java.util.HashMap<String, String>();
		
		String k = null, v = null;
		while (jsonParser.hasNext()) {
			Event event = jsonParser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(jsonParser.getString().equalsIgnoreCase("aliases")) {
            		jsonParser.next(); // START_ARRAY
            		jsonParser.next(); //Alternative container name
            		k = jsonParser.getString() + CAdvisorData.TIMESTAMP;
            	}
            	
            	//Get cpu limit
            	if(jsonParser.getString().equalsIgnoreCase("timestamp")) {
            		jsonParser.next();
            		v = javax.xml.bind.DatatypeConverter.parseDate(jsonParser.getString()).getTime().toString();
                }
            }
		    	
            time.put(k, v);
		}
		
		return time;
	}
}
