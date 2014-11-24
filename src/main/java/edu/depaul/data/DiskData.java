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
final class DiskData implements Data {

	@Override
	public void getData(ContainerMan _container) {
		
		try {
			
			File f = new File("diskFile.txt");
			
			BufferedReader readFrom = new BufferedReader(new FileReader(f));
			
			String line = null;
			
			java.util.StringTokenizer token;
			
			while((line = readFrom.readLine()) != null) {
		
				//Windows OS
				if(line.indexOf("C:\\") != -1) {
					
					//ensure to accommodate all data sizes (mb, gb,tb)
					
					token = new java.util.StringTokenizer(line.trim().substring(WINDOWS_OS_START));
					sendToContainer(token, _container);
				}
				
				//OS X
				if(line.indexOf("disk") != -1) {
					
					token = new java.util.StringTokenizer(line.trim().substring(OS_X_START));
					sendToContainer(token, _container);
				}
			}
			
			//close reader
			readFrom.close();
			
		} catch(IOException e) { e.printStackTrace(); }
	}
	
	public DataName getDataName() {
		return DataName.DISK;
	}

	private void sendToContainer(java.util.StringTokenizer token ,ContainerMan _container) {
		
		//hold the current token, total space  
		String holder = token.nextToken();
	
		//place TOTAL disk space into container
		_container.setDiskSpaceTotal(Long.parseLong(
				holder.substring(0, holder.indexOf(holder.substring(holder.length()-1)))));
		
		//next token, used space
		holder = token.nextToken();
		
		//place USED disk space into container
		_container.setDiskSpaceUsed(Long.parseLong(
				holder.substring(0, holder.indexOf(holder.substring(holder.length()-1)))));
	
		//next token, free space
		holder = token.nextToken();
		
		//place FREE disk space into container
		_container.setDiskSpaceFree(Long.parseLong(
				holder.substring(0, holder.indexOf(holder.substring(holder.length()-1)))));
	}

	private final int WINDOWS_OS_START = 3;
	private final int OS_X_START = 13;
	
}
