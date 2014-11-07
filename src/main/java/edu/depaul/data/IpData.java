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

	public void getData(StringBuilder b) {
		
		try {
			
			File f = new File("ipFile.txt");
			
			BufferedReader readFrom = new BufferedReader(new FileReader(f));
			
			//find a position to start and find the ip data
			String line = null;
			
			while((line = readFrom.readLine()) != null) {
		
				//get address info
				if((line.indexOf("ip address") != -1) || 
						(line.indexOf("mac address") != -1) ||
						(line.indexOf("host name") != -1)) {
					b.append(line.trim() + "<br>");
				}
			}
			
			readFrom.close();
			
		} catch(IOException e) { System.err.print("Issue opening file"); }
		
	}
	
	public DataName getDataName() {
		return DataName.IP;
	}

	@Override
	public void getData(ContainerMan _container) {
		// TODO Auto-generated method stub
		
	}
}
