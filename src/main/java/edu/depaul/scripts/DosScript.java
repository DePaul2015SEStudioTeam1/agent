package edu.depaul.scripts;

import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 
 * @author Deonte D Johnson
 *
 */
class DosScript implements Scripts {
	
	public void createScript() {
	
		try {
			file = new java.io.File("systeminfo.bat");
		
			PrintWriter w = new PrintWriter(file);
			
			//create batch file and retrieve system metrics using sigar.jar
			String b = "echo off\n" + 
					"java -jar sigar-bin/sigar.jar free > freeMemoryFile.txt\n" +
					"java -jar sigar-bin/sigar.jar version > versionFile.txt\n" +
					"java -jar sigar-bin/sigar.jar netinfo > ipFile.txt\n" + 
					"java -jar sigar-bin/sigar.jar df > diskFile.txt\n" + 
					"java -jar sigar-bin/sigar.jar cpuinfo > cpuFile.txt";
			
			//write commands to batch file
			w.write(b);
			
			//ensure everything is written
			w.flush();
			
			//close file
			w.close();
			
			//delete batch file
			file.deleteOnExit();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void runScript() {
		try {
			//run .bat for DOS
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public boolean canExcute() {
		return file.canExecute();
	}
	
	public ScriptType getScriptType() {
		return ScriptType.DOS;
	}
	
	private java.io.File file;
}
