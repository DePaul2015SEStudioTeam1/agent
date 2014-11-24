package edu.depaul.data;

import java.util.LinkedList;
import java.util.List;

import edu.depaul.maestroService.ContainerMan;

/**
 * 
 * @author Deonte D Johnson
 *
 */
public class DataManager {

	public static DataManager getInstance() {
		if(instance == null)
			instance = new DataManager();
		return instance;
	}
	
	/**
	 * 
	 * @param _Data info type to add to system log
	 */
	void addData(Data _Data) {
		list.add(_Data);
	}
	
	/**
	 * remove all data objects
	 */
	public void dispose() {
		list.removeAll(this.list);
	}
	
	/**
	 * 
	 * @param container container from maestro to fill  with data
	 */
	public void getAllData(ContainerMan container) {
		for(Data i : list)
			i.getData(container);
	}
	
	private DataManager() {
		list = new LinkedList<Data>();
		instance = null;
	}
	
	private List<Data> list;
	private static DataManager instance;
}
