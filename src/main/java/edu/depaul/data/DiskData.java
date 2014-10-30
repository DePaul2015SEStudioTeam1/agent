package djohn.microservices.data;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * 
 * @author Jet2kus84
 *
 */
final class DiskData implements Data {

	@Override
	public void getData(StringBuilder b) {
		try {
			
			File f = new File("diskFile.txt");
			
			BufferedReader readFrom = new BufferedReader(new FileReader(f));
			
			String line = null;
			java.util.StringTokenizer token;
			
			while((line = readFrom.readLine()) != null) {
		
				//Windows OS
				if(line.indexOf("C:\\") != -1) {
					token = new java.util.StringTokenizer(line.trim().substring(WINDOWS_OS_START));
					buildString(token, b);
				}
				
				//OS X
				if(line.indexOf("disk") != -1) {
					token = new java.util.StringTokenizer(line.trim().substring(OS_X_START));
					buildString(token, b);
				}
			}
			
			//close reader
			readFrom.close();
			
			//delete file from directory
			f.deleteOnExit();
		} catch(IOException e) { e.printStackTrace(); }
	}

	@Override
	public DataName getDataName() {
		return DataName.DISK;
	}

	private void buildString(java.util.StringTokenizer token, StringBuilder b) {
		b.append("Total disk space: " + token.nextToken() + "<br>");
		b.append("Used disk space: " + token.nextToken() + "<br>");
		b.append("Available disk space: " + token.nextToken() + "<br>");
	}
	
	private final int WINDOWS_OS_START = 3;
	private final int OS_X_START = 13;
}
