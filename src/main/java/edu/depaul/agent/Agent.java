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
		
		//create file object for scripts
		ScriptManager.getInstance().createScripts();
		
		//run appropriate script
		if(ScriptManager.getInstance().canExecuteScript(ScriptType.DOS)) {
			ScriptManager.getInstance().runScript(ScriptType.DOS);
		} 
		else if(ScriptManager.getInstance().canExecuteScript(ScriptType.UNIX)) {
			ScriptManager.getInstance().runScript(ScriptType.UNIX);
		}
		else {
			//if agent can't run on OS
			System.out.println("Can not run on this OS");
		
			//exit program if script doesn't work for OS
			System.exit(0);
		}
								
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
	}

	private ContainerMan container;
	private MaestroService<Container> maestroService;
}
