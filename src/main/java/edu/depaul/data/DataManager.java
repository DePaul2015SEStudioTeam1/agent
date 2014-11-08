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
	 * @param b StringBuilder object to create and write log
	 */
	public void getAllData(StringBuilder b) { //Remove
		for(Data i : list) {
			if(i instanceof CpuData) {
				CpuData c = (CpuData)i;
				c.getData(b);
			}
			else if(i instanceof IpData) {
				IpData _i = (IpData)i;
				_i.getData(b);
			}
			else if(i instanceof VersionData) {
				VersionData v = (VersionData)i;
				v.getData(b);
			}
			
		}
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
