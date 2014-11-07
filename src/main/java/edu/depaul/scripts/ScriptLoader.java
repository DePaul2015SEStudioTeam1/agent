package edu.depaul.scripts;

/**
 * 
 * @author Deonte D Johnson
 *
 */
public class ScriptLoader {
	public static void load() {
		//initial scripts
		ScriptManager.getInstance().addScript(new DosScript());
		ScriptManager.getInstance().addScript(new UnixScript());
	}
}
