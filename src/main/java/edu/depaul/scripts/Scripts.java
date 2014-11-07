package edu.depaul.scripts;

/**
 * 
 * @author Deonte D Johnson
 *
 */
interface Scripts {

	public void createScript();
	public void runScript();
	public void writeToLogFile(StringBuilder str);
	public void openLog();
	public boolean canExcute();
	public ScriptType getScriptType();
	
	final String LOG_FILE = "log.html";
	
}
