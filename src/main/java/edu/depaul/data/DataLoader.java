package edu.depaul.data;

/**
 * 
 * @author Deonte D Johnson
 *
 */
public class DataLoader {
	public static void load() {
		DataManager.getInstance().addData(new FreeMemoryData());
		DataManager.getInstance().addData(new VersionData());
		DataManager.getInstance().addData(new IpData());
		DataManager.getInstance().addData(new CpuData());
		DataManager.getInstance().addData(new DiskData());
	}
}
