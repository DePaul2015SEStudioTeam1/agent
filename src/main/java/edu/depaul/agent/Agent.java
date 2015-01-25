package edu.depaul.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

//import javax.json.Json;
//import javax.json.JsonObject;

import org.apache.log4j.Logger;
import edu.depaul.maestro.service.MaestroService;

/**
 * 
 * @author Deonte D Johnson
 *
 * edit - john davidson, 1/10/2015
 */
public class Agent implements Runnable {
	private static final int HTTP_OK = 200;
	private static final int HTTP_UNATHORIZED = 401;
	private static final int HTTP_INVALID = -1;
	
	@Override
	public void run() {
		//attempt to connect and read json data
		HttpURLConnection connection = null;
		
		try {
			
			//input the url we want access to 
			URL url = new URL("http://140.192.249.16:8890/api/v1.2/docker");
			connection = (HttpURLConnection) url.openConnection();
			
			//retrieve status code
			int rCode = connection.getResponseCode();
			
			switch(rCode) {
				case HTTP_OK:
					System.out.println("HTTP/1.0 200 OK");
					BufferedReader reader = new BufferedReader(
							new java.io.InputStreamReader(connection.getInputStream()));
					
					String data = null;
					File file = new File("jsonDump.txt");
					PrintWriter w = new PrintWriter(file);
					
					while((data = reader.readLine()) != null) {
						System.out.println(data);
						w.println(data + "\n");
					}
					reader.close();
					w.close();
					break;
					
				case HTTP_UNATHORIZED:
					System.out.println("HTTP/1.0 401 unauthorized");
					break;
					
				case HTTP_INVALID:
					System.out.println("Not valid HTTP");
					break;

				default:
					break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
		//attempt to read and parse json data from file first
		String JsonData;
		String data = null;
	
		try {
			BufferedReader in = new BufferedReader(new java.io.FileReader("jsonDump.txt"));
			
			//initialize string with first line
			JsonData = in.readLine();
			
			while((data = in.readLine()) != null) {
				//get every line after the first line
				JsonData += data;
			}
			
			System.out.println(JsonData);
//			JsonObject jsonObject = Json.createReader(new java.io.StringReader(JsonData)).readObject();
//			System.out.println("Some data: " + jsonObject.get("name"));
//			System.out.println("Some data: " + jsonObject.get("namespace"));
			
			in.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
