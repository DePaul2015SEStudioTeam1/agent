package edu.depaul.agent;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import javax.json.Json;
import javax.json.stream.JsonParser;
import javax.json.stream.JsonParser.Event;

public class JsonDataRetrieval {

	private JsonParser parser;
	
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
		
	public ArrayList<String> getContainerName(String _url) {
		parser = this.openConnection(_url);
		
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
	
	public ArrayList<String> getContainerAltName(String _url) {
		
		parser = this.openConnection(_url);
		
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
	
	public HashMap<String, String> getContainerCpuTotal(String _url) {
		parser = this.openConnection(_url);
		
		java.util.HashMap<String, String> cpu = new java.util.HashMap<String, String>();
		String k = null, v = null;
		
		while (parser.hasNext()) {
            Event event = parser.next();
           	
            if(event == Event.KEY_NAME) {
            	
            	 //Get aliases
            	if(parser.getString().equalsIgnoreCase("aliases")) {
            		parser.next(); // START_ARRAY
            		parser.next(); //Alternative container name
            		k = parser.getString();
            	}
            	
            	//Get cpu total
            	if(parser.getString().equalsIgnoreCase("total")) {
            		parser.next(); //VALUE
            		v = parser.getString();//cpuTotals.add(parser.getString());
            	}
            	cpu.put(k, v);
            }
		}
		return cpu;
	}
	
	public HashMap<String, String> getContainerCpuLimit(String _url) {	
		parser = this.openConnection(_url);
		
		HashMap<String, String> cpu = new java.util.HashMap<String, String>();
		
		String k = null, v = null;
		while (parser.hasNext()) {
			Event event = parser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(parser.getString().equalsIgnoreCase("aliases")) {
            		parser.next(); // START_ARRAY
            		parser.next(); //Alternative container name
            		k = parser.getString();
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
	
	public HashMap<String, String> getContainerMemoryUsage(String _url) {
		parser = this.openConnection(_url);
		
		HashMap<String, String> data = new java.util.HashMap<String, String>();
		String k = null, v = null;
		
		while (parser.hasNext()) {
			Event event = parser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(parser.getString().equalsIgnoreCase("aliases")) {
            		parser.next(); // START_ARRAY
            		parser.next(); //Alternative container name
            		k = parser.getString();
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
	
	public HashMap<String, String> getContinerMemoryLimit(String _url) {
		parser = this.openConnection(_url);
		
		HashMap<String, String> data = new java.util.HashMap<String, String>();
		String k = null, v = null;
		
		while (parser.hasNext()) {
			Event event = parser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(parser.getString().equalsIgnoreCase("aliases")) {
            		parser.next(); // START_ARRAY
            		parser.next(); //Alternative container name
            		k = parser.getString();
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
	
	public HashMap<String, String> getContainerFileSystemCapacity(String _url) {
		parser = this.openConnection(_url);
		
		HashMap<String, String> time = new java.util.HashMap<String, String>();
		
		String k = null, v = null;
		while (parser.hasNext()) {
			Event event = parser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(parser.getString().equalsIgnoreCase("aliases")) {
            		parser.next(); // START_ARRAY
            		parser.next(); //Alternative container name
            		k = parser.getString();
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
	
	public HashMap<String, String> getContainerFileSystemUsage(String _url) {
		parser = this.openConnection(_url);
		
		HashMap<String, String> time = new java.util.HashMap<String, String>();
		
		String k = null, v = null;
		while (parser.hasNext()) {
			Event event = parser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(parser.getString().equalsIgnoreCase("aliases")) {
            		parser.next(); // START_ARRAY
            		parser.next(); //Alternative container name
            		k = parser.getString();
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
	
	public HashMap<String, String> getContianerTimestamp(String _url) {
		parser = this.openConnection(_url);
		
		HashMap<String, String> time = new java.util.HashMap<String, String>();
		
		String k = null, v = null;
		while (parser.hasNext()) {
			Event event = parser.next();
			
			if(event == Event.KEY_NAME) {
            	//Get aliases
            	if(parser.getString().equalsIgnoreCase("aliases")) {
            		parser.next(); // START_ARRAY
            		parser.next(); //Alternative container name
            		k = parser.getString();
            	}
            	
            	//Get cpu limit
            	if(parser.getString().equalsIgnoreCase("timestamp")) {
            		parser.next();
            		v = parser.getString();
                }
            }
		    	
            time.put(k, v);
		}
		
		return time;
	}
		
	public static void main(String[] args) {
		for(int i = 0; i < 6; i++)
			print(i);
	}
	
	//used for main only
	private static void print(int i) {
		JsonDataRetrieval data = new JsonDataRetrieval();
		ArrayList<String> names = data.getContainerAltName("http://140.192.249.16:8890/api/v1.2/docker");
		ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String,String>>();
		
		list.add(data.getContainerCpuLimit("http://140.192.249.16:8890/api/v1.2/docker"));
		list.add(data.getContainerCpuTotal("http://140.192.249.16:8890/api/v1.2/docker"));
		list.add(data.getContianerTimestamp("http://140.192.249.16:8890/api/v1.2/docker"));
		list.add(data.getContainerMemoryUsage("http://140.192.249.16:8890/api/v1.2/docker"));
		list.add(data.getContinerMemoryLimit("http://140.192.249.16:8890/api/v1.2/docker"));
		list.add(data.getContainerFileSystemCapacity("http://140.192.249.16:8890/api/v1.2/docker"));
		list.add(data.getContainerFileSystemUsage("http://140.192.249.16:8890/api/v1.2/docker"));
		
		for(HashMap<String,String> hm : list) {
			hm.remove(null, null);
			
			Set<Entry<String, String>> set = hm.entrySet();
			Iterator<Entry<String, String>> iterator = set.iterator();
			
			while(iterator.hasNext()) {
				Map.Entry<String, String> _entry = (Map.Entry<String, String>)iterator.next();
				if(_entry.getKey().equalsIgnoreCase(names.get(i))) {
					System.out.println("Json Output: " + _entry.getKey() + " : " + _entry.getValue());
				}
			}
		}
		System.out.println();
	}
}
