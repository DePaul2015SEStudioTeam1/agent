package edu.depaul.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import edu.depaul.maestroService.ContainerMan;

/**
 * 
 * @author Deonte D Johnson
 *
 */
final class IpData implements Data {

	@Override
	public void getData(ContainerMan _container) {
		
		try {
			
			File f = new File("ipFile.txt");
			
			BufferedReader readFrom = new BufferedReader(new FileReader(f));
			
			//find a position to start and find the ip data
			String line = null;
			
			while((line = readFrom.readLine()) != null) {
		
				if(line.indexOf("ip address") != -1) {
					_container.setPrimaryIpAddress(line);
				}
				
				if(line.indexOf("mac address") != -1) {
					java.util.StringTokenizer token = new java.util.StringTokenizer(line);
					
					//skip first token, mac address
					token.nextToken(".");
					_container.setPrimaryMacAddress(token.nextToken("."));
				}
				
				if(line.indexOf("host name") != -1) {
					java.util.StringTokenizer token = new java.util.StringTokenizer(line);
					
					//skip first token, host name
					token.nextToken(".");
					_container.setHostName(token.nextToken("."));
				}
			}
			
			readFrom.close();
			
		} catch(IOException e) { System.err.print("Issue opening file"); }
		
	}
	
	public DataName getDataName() {
		return DataName.IP;
	}
}
