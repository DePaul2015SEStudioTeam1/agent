package edu.depaul.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author Jet2kus84
 *
 */
final class CpuData implements Data {

	public void getData(StringBuilder b) {
		
		try {
			
			File f = new File("cpuFile.txt");
			
			BufferedReader readFrom = new BufferedReader(new FileReader(f));
			
			String line = null;
			
			while((line = readFrom.readLine()) != null) {
				
				//get host name
				if((line.indexOf("CPUs") != -1) || 
					(line.indexOf("Model") != -1) || 
					(line.indexOf("Cores") != -1) ||
					(line.indexOf("Vendor") != -1) ||
					(line.indexOf("Physical Memory") != -1))
				
				{
					b.append(line.trim() + "<br>");
				}
				
			}
			
			//close reader
			readFrom.close();
		
		} catch(IOException e) { e.printStackTrace(); }
		
	}
	
	public DataName getDataName() {
		return DataName.SYSTEM;
	}
}
