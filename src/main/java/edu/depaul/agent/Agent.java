package edu.depaul.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.log4j.Logger;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInfo;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

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
					File file = new File("jsonDump.json");
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
		
		/*String JsonData;
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
			JsonObject jsonObject = Json.createReader(new java.io.StringReader(JsonData)).readObject();
			System.out.println("Some data: " + jsonObject.get("name"));
			System.out.println("Some data: " + jsonObject.get("namespace"));
			
			in.close();
		
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	}	
				
/*
	private static final Logger logger = Logger.getLogger(Agent.class);
	private Sigar sigar = new Sigar();
	private MaestroService<Container> maestroService;
	private Container container = new Container();
	private String agentId;
	private Mem mem;
	private CpuInfo[] cpus;
	private NetInfo netInfo;
	private NetInterfaceConfig config;
	
	public void setMaestroService(MaestroService<Container> maestroService) {
		this.maestroService = maestroService;
	}
	
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	public void run() {
		collectStatistics();
	}

	private void collectStatistics() {
		getSigarStatistics();
		setMem();
		setCPU();
		setDiskUsage();
		setOperatingSystem();
		setSystemInfo();
		maestroService.store(container);
	}

	private void getSigarStatistics() {
		try {
			mem = sigar.getMem();
			cpus = sigar.getCpuInfoList();
			netInfo = sigar.getNetInfo();
			config = sigar.getNetInterfaceConfig();
		}
		catch(SigarException se) {
			logger.error(se.getMessage(), se);
		}
	}

	private void setMem() {
		// mem
		container.setMemFree(mem.getFree());
		container.setMemTotal(mem.getTotal());
		container.setMemUsed(mem.getUsed());
	}

	private void setCPU() {
		// cpu
		container.setCpuCount(cpus.length);
		container.setCpuModel(cpus[0].getModel());
		container.setCpuVendor(cpus[0].getVendor());
	}

	private void setDiskUsage() {
		// disk
		File file = new File("/");
		container.setDiskSpaceFree(file.getFreeSpace());
		container.setDiskSpaceUsed(file.getTotalSpace() - file.getFreeSpace());
		container.setDiskSpaceTotal(file.getTotalSpace());

	}
	private void setOperatingSystem() {

		// os
		container.setOsDataModel(System.getProperty("sun.arch.data.model"));
		container.setOsDescription(System.getProperty("os.version"));
		container.setOsName(System.getProperty("os.name"));

	}
	private void setSystemInfo() {
		try {
			// system info
			container.setHostName(netInfo.getHostName());
			container.setPrimaryIpAddress(sigar.getFQDN());
			container.setPrimaryMacAddress(config.getHwaddr());
			container.setAgentId(agentId);
		}
		catch(SigarException se) {
			logger.error(se.getMessage(), se);
		}

	} */
}
