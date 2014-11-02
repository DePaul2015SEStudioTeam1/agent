package edu.depaul.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

final class VersionData implements Data {

	public void getData(StringBuilder b) {
		try {
			
			//open file
			File f = new File("versionFile.txt");
		
			//create a file reader
			BufferedReader readFrom = new BufferedReader(new FileReader(f));
		
			//read the first line from the file
			String line = readFrom.readLine();
			
			while((line = readFrom.readLine()) != null) {
				
				if((line.indexOf("OS description") != -1) 
						|| (line.indexOf("OS name") != -1) 
						|| (line.indexOf("Current user") != -1)
						|| (line.indexOf("data model") != -1)
						|| (line.indexOf("Java home") != -1)) {
					b.append(line.trim() + "<br>");	
				}
			}
					
			//close reader
			readFrom.close();
		
			} catch (IOException e) { System.err.print("Issue opening file"); }
	}
	
	public DataName getDataName() {
		return DataName.VERSION;
	}
}
