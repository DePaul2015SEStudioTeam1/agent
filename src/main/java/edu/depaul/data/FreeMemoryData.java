package edu.depaul.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import edu.depaul.maestroService.ContainerMan;

/**
 * 
 * 
 * @author Deonte D Johnson
 *
 */
final class FreeMemoryData implements Data {
	
	@Override
	public void getData(ContainerMan _container) {
		try {
		File f = new File("FreeMemoryFile.txt");
		
		//create a file reader
		BufferedReader readFrom = new BufferedReader(new FileReader(f));
		
		String line = null;
		java.util.StringTokenizer token;
		
		while((line = readFrom.readLine()) != null) {
	
			//Windows OS
			if(line.indexOf("Mem:") != -1) {
				token = new java.util.StringTokenizer(line.trim().substring(WINDOWS_OS_START));
				sendToContainer(token, _container);
			}
			
			//OS X
			if(line.indexOf("disk") != -1) {
				token = new java.util.StringTokenizer(line.trim().substring(OS_X_START));
				sendToContainer(token, _container);
			}
		}
				
		readFrom.close();
	
		} catch (IOException e) { System.err.print("Issue opening file"); }
		
	}
	
	private void sendToContainer(java.util.StringTokenizer token ,ContainerMan _container) {
		
		//hold the current token, total space  
		String holder = token.nextToken();
	
		//place TOTAL disk space into container
		_container.setMemTotal(Long.parseLong(holder));
		
		//next token, used space
		holder = token.nextToken();
		
		//place USED disk space into container
		_container.setMemUsed(Long.parseLong(holder));
	
		//next token, free space
		holder = token.nextToken();
		
		//place FREE disk space into container
		_container.setMemFree(Long.parseLong(holder));
	}
	
	public DataName getDataName() {
		return DataName.VOLUME;
	}
	
	private final int WINDOWS_OS_START = 8;
	private final int OS_X_START = 13;
	
}
