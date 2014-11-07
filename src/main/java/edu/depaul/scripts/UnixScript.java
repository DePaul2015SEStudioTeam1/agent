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
class UnixScript implements Scripts {
	
	public void createScript() {
		
		file = new java.io.File("systeminfo.command");
		
		//get the current location of the command file
		String path = file.getAbsolutePath();
		
		PrintWriter w;
		try {
			w = new PrintWriter(file);
			
			//create batch file and retrieve system metrics using sigar resources
			String b = "echo off\n" + 
					//point to the current directory
					"cd " + path.substring(0, path.indexOf("systeminfo.command")) 
					+ "\n" +
					
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
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	
	public void writeToLogFile(StringBuilder str) {
	
		logFile = new java.io.File(LOG_FILE);
		
		try {
			
			PrintWriter w = new PrintWriter(logFile);
			w.println(str);
			w.flush();
			w.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void runScript() {
		try {
			//run .command for Unix
			Desktop.getDesktop().open(file);
		} catch (IOException e) {
			e.printStackTrace();
		}	
	}
	
	public void openLog() {
		try {
			Desktop.getDesktop().open(logFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public boolean canExcute() {
		return file.canExecute();
	}
	
	public ScriptType getScriptType() {
		return ScriptType.UNIX;
	}

	private java.io.File file, logFile;
}
