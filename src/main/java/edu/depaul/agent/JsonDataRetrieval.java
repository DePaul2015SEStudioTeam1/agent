package edu.depaul.agent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

/**
 * @author Jet2kus84
 *
 */
public class JsonDataRetrieval {

	private JsonParser parser;
	private String url;
	private JsonParser openConnection(String _url) {
		JsonParser p = null;
		try {
			//create a URL for the cAdvisor location
			URL url = new URL(_url);
			
			//open a connection to the cAdvisor location
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			
			//create a json parser object
			p = Json.createParser(connection.getInputStream());
			
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return p;
	}
	
	enum CadvisorData {
		CT, //cpu total
		CL, //cpu limit
		TIME, //timestamp
		MU, //memory usage
		ML, //memory limit
		FSC, //filesystem capacity
		FSU //filesystem usgae
	}
	
	public JsonDataRetrieval(String _url) {
		this.url = _url;
	}	
	
	ArrayList<String> getContainerName() {
		parser = this.openConnection(url);
		
		java.util.ArrayList<String> containerNames = new java.util.ArrayList<String>();
		
		while (parser.hasNext()) {
            Event event = parser.next();
           
            if(event == Event.KEY_NAME) {
            	if(parser.getString().equalsIgnoreCase("name")) {
            		parser.next(); //VALUE
            		containerNames.add(parser.getString());
            	}
            }
		}
		return containerNames;
	}
	
	ArrayList<String> getContainerAltName() {
		
		parser = this.openConnection(url);
		
		java.util.ArrayList<String> altNames = new java.util.ArrayList<String>();
		
		while (parser.hasNext()) {
            Event event = parser.next();
           
            if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(parser.getString().equalsIgnoreCase("aliases")) {
            		parser.next(); // START_ARRAY
            		parser.next(); //Alternative container name
            		altNames.add(parser.getString());
            	}
            }
		}
		return altNames;
	}
	
	HashMap<String, String> getContainerCpuTotal() {
		parser = this.openConnection(url);
		
		java.util.HashMap<String, String> cpu = new java.util.HashMap<String, String>();
		String k = null, v = null;
		
		while (parser.hasNext()) {
            Event event = parser.next();
           	
            if(event == Event.KEY_NAME) {
            	
            	 //Get aliases
            	if(parser.getString().equalsIgnoreCase("aliases")) {
            		parser.next(); // START_ARRAY
            		parser.next(); //Alternative container name
            		k = parser.getString() + CadvisorData.CT;
            	}
            	
            	//Get cpu total
            	if(parser.getString().equalsIgnoreCase("total")) {
            		parser.next(); //VALUE
            		v = parser.getString();
            	}
            	cpu.put(k, v);
            }
		}
		return cpu;
	}
	
	HashMap<String, String> getContainerCpuLimit() {	
		parser = this.openConnection(url);
		
		HashMap<String, String> cpu = new java.util.HashMap<String, String>();
		
		String k = null, v = null;
		while (parser.hasNext()) {
			Event event = parser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(parser.getString().equalsIgnoreCase("aliases")) {
            		parser.next(); // START_ARRAY
            		parser.next(); //Alternative container name
            		k = parser.getString() + CadvisorData.CL;
            	}
            	
            	//Get cpu limit
                if(parser.getString().equalsIgnoreCase("cpu")) {
                	if(parser.next() == Event.START_OBJECT) {
                		parser.next(); //KEY_NAME
                		if(parser.getString().equalsIgnoreCase("limit")) {
                			parser.next(); //VALUE
                			v = parser.getString();
                		}
                	}
                }
            }
		    	
            cpu.put(k, v);
		}
		
		return cpu;
	}
	
	HashMap<String, String> getContainerMemoryUsage() {
		parser = this.openConnection(url);
		
		HashMap<String, String> data = new java.util.HashMap<String, String>();
		String k = null, v = null;
		
		while (parser.hasNext()) {
			Event event = parser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(parser.getString().equalsIgnoreCase("aliases")) {
            		parser.next(); // START_ARRAY
            		parser.next(); //Alternative container name
            		k = parser.getString() + CadvisorData.MU;
            	}
            	
            	//Get memory usage
            	if(parser.getString().equalsIgnoreCase("memory")) {
            		if(parser.next() == Event.START_OBJECT) {
            			parser.next(); //KEY_NAME
            			if(parser.getString().equalsIgnoreCase("usage")) {
            				parser.next(); //VALUE
            				v = parser.getString();
            			}
            		}
            	}
            }
		    	
            data.put(k, v);
		}
		return data;
	}
	
	HashMap<String, String> getContinerMemoryLimit() {
		parser = this.openConnection(url);
		
		HashMap<String, String> data = new java.util.HashMap<String, String>();
		String k = null, v = null;
		
		while (parser.hasNext()) {
			Event event = parser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(parser.getString().equalsIgnoreCase("aliases")) {
            		parser.next(); // START_ARRAY
            		parser.next(); //Alternative container name
            		k = parser.getString() + CadvisorData.ML;
            	}
            	
            	//Get memory usage
            	if(parser.getString().equalsIgnoreCase("memory")) {
            		if(parser.next() == Event.START_OBJECT) {
            			parser.next(); //KEY_NAME
            			if(parser.getString().equalsIgnoreCase("limit")) {
            				parser.next(); //VALUE
            				v = parser.getString();
            			}
            		}
            	}
            }
		    	
            data.put(k, v);
		}
		return data;
	}
	
	HashMap<String, String> getContainerFileSystemCapacity() {
		parser = this.openConnection(url);
		
		HashMap<String, String> time = new java.util.HashMap<String, String>();
		
		String k = null, v = null;
		while (parser.hasNext()) {
			Event event = parser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(parser.getString().equalsIgnoreCase("aliases")) {
            		parser.next(); // START_ARRAY
            		parser.next(); //Alternative container name
            		k = parser.getString() + CadvisorData.FSC;
            	}
            	
            	//Get filesystem capacity
            	if(parser.getString().equalsIgnoreCase("capacity")) {
            		parser.next(); //Capacity value
            		v = parser.getString();
            	}
            }
		    	
            time.put(k, v);
		}
		
		return time;
	}
	
	HashMap<String, String> getContainerFileSystemUsage() {
		parser = this.openConnection(url);
		
		HashMap<String, String> time = new java.util.HashMap<String, String>();
		
		String k = null, v = null;
		while (parser.hasNext()) {
			Event event = parser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(parser.getString().equalsIgnoreCase("aliases")) {
            		parser.next(); // START_ARRAY
            		parser.next(); //Alternative container name
            		k = parser.getString() + CadvisorData.FSU;
            	}
            	
            	//Get filesystem and usage
            	if(parser.getString().equalsIgnoreCase("capacity")) {
            		parser.next(); //Capacity value
            		parser.next(); //JSON key name
            		parser.next(); //JSON value
            		v = parser.getString();
            	}
            }
		    	
            time.put(k, v);
		}
		
		return time;
	}
	
	HashMap<String, String> getContianerTimestamp() {
		parser = this.openConnection(url);
		
		HashMap<String, String> time = new java.util.HashMap<String, String>();
		
		String k = null, v = null;
		while (parser.hasNext()) {
			Event event = parser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(parser.getString().equalsIgnoreCase("aliases")) {
            		parser.next(); // START_ARRAY
            		parser.next(); //Alternative container name
            		k = parser.getString() + CadvisorData.TIME;
            	}
            	
            	//Get cpu limit
            	if(parser.getString().equalsIgnoreCase("timestamp")) {
            		parser.next();
            		v = javax.xml.bind.DatatypeConverter.parseDate(parser.getString()).getTime().toString();
                }
            }
		    	
            time.put(k, v);
		}
		
		return time;
	}
}
