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
final class VersionData implements Data {

	@Override
	public void getData(ContainerMan _container) {
		try {
			
			//open file
			File f = new File("versionFile.txt");
		
			//create a file reader
			BufferedReader readFrom = new BufferedReader(new FileReader(f));
		
			//read the first line from the file
			String line = readFrom.readLine();
			
			while((line = readFrom.readLine()) != null) {
				
				if(line.indexOf("OS description") != -1) {
					java.util.StringTokenizer token = new java.util.StringTokenizer(line);
					
					//skip first token, OS description
					token.nextToken(".");
					_container.setOsDescription(token.nextToken("."));
				}
				
				if(line.indexOf("OS name") != -1) {
					java.util.StringTokenizer token = new java.util.StringTokenizer(line);
					
					//skip first token, OS name
					token.nextToken(".");
					_container.setOsName(token.nextToken("."));
				}
				
				if(line.indexOf("data model") != -1) {
					java.util.StringTokenizer token = new java.util.StringTokenizer(line);
					
					//skip first token, data model
					token.nextToken(".");
					_container.setOsDataModel(token.nextToken("."));
				}
			}
			
			//close reader
			readFrom.close();
		
			} catch (IOException e) { System.err.print("Issue opening file"); }
	}
	
	@Override
	public DataName getDataName() {
		return DataName.VERSION;
	}
}
