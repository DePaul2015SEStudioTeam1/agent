package edu.depaul.agent;

import edu.depaul.data.DataLoader;
import edu.depaul.data.DataManager;
import edu.depaul.maestro.service.MaestroService;
import edu.depaul.maestroService.ContainerMan;
import edu.depaul.operations.model.Container;
import edu.depaul.scripts.ScriptLoader;
import edu.depaul.scripts.ScriptManager;
import edu.depaul.scripts.ScriptType;

/**
 * 
 * @author Deonte D Johnson
 *
 */
public class Agent {
	
	public void run() {
		
		//retrieve current OS type
		ScriptType currentOS = null;
		
		//create file object for scripts
		ScriptManager.getInstance().createScripts();
		
		//run appropriate script
		if(ScriptManager.getInstance().canExecuteScript(ScriptType.DOS)) {
			ScriptManager.getInstance().runScript(ScriptType.DOS);
			currentOS = ScriptType.DOS;
		} 
		else if(ScriptManager.getInstance().canExecuteScript(ScriptType.UNIX)) {
			ScriptManager.getInstance().runScript(ScriptType.UNIX);
			currentOS = ScriptType.UNIX;
		}
		else {
			//if agent can't run on OS
			System.out.println("Can not run on this OS");
		
			//exit program if script doesn't work for OS
			System.exit(0);
		}
	
		DataManager.getInstance().getAllData(b);
				
		//write end of html log file, just for show until maestro is up and running
		b.append("</p></fieldset>Log details " +
				"provided by Deonte Johnson</body></html>"); //remove
				
		//write data to log
		ScriptManager.getInstance().writeToLogFile(b, currentOS); //remove
		
		//open log
		ScriptManager.getInstance().openLog(currentOS); // remove
		
		//get data loaded into program and place in containerMan
		DataManager.getInstance().getAllData(container);
		
		//send data obtained by the ContainerMan to the maestro
		maestroService.store(container.getContainer());
	}
	
	/**
	 * 
	 * @param maestroService
	 */
	public void setMaestroService(MaestroService<Container> maestroService) {
		this.maestroService = maestroService;
	}
	
	public Agent() {
					
		//load in data objects
		DataLoader.load();
		
		//load in script objects
		ScriptLoader.load();
		
		//create a new container to input data
		container = new ContainerMan();
		
		//gather data, will be replaced by container
		b = new StringBuilder();
		
		//write initial html script, just for show until maestro is up and running
		b.append("<html><title>Log</title><body bgcolor=#F8F8F8>" +
				"<h1 align=center>System Log</h1><hr><p align=center>" +
				new java.util.Date() +
				"</p><fieldset><p>");			
	}

	private ContainerMan container;
	private MaestroService<Container> maestroService;
	private StringBuilder b;
	
}
