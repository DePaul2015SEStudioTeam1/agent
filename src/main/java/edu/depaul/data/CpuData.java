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
final class CpuData implements Data {

	@Override
	public void getData(ContainerMan _container) {
		try {
			
			File f = new File("cpuFile.txt");
			
			BufferedReader readFrom = new BufferedReader(new FileReader(f));
			
			String line = null;
			
			while((line = readFrom.readLine()) != null) {
				
				if(line.indexOf("Model") != -1) {
					java.util.StringTokenizer token = new java.util.StringTokenizer(line);
					
					//skip first token, model
					token.nextToken(".");
					_container.setCpuModel(token.nextToken("."));
				}
				
				if(line.indexOf("Vendor") != -1) {
					java.util.StringTokenizer token = new java.util.StringTokenizer(line);
					
					//skip first token, vendor
					token.nextToken(".");
					_container.setCpuVendor(token.nextToken("."));
				}
				
				if(line.indexOf("Total CPUs") != -1) {
					boolean flag = false;
					java.util.StringTokenizer token = new java.util.StringTokenizer(line);
				
					while(!flag) {
						try {
							_container.setCpuCount(Integer.parseInt(token.nextToken(".")));
							flag = true;
						}
						catch(NumberFormatException e) { 
							continue;
						}
					} 
				}
			}
			
			//close reader
			readFrom.close();
		
		} catch(IOException e) { e.printStackTrace(); }
	}
	
	@Override
	public DataName getDataName() {
		return DataName.SYSTEM;
	}
}
