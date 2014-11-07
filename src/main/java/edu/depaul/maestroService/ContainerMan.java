package edu.depaul.maestroService;

import edu.depaul.operations.model.Container;

/**
 * 
 * @author Deonte D Johnson
 *
 *
 *
 */
public class ContainerMan {
	
	/**
	 * 
	 * @param memTotal total RAM in CPU
	 */
	public void setMemTotal(long memTotal) {
		container.setMemTotal(memTotal);
	}
	
	/**
	 * 
	 * @param memUsed used RAM in CPU
	 */
	public void setMemUsed(long memUsed) {
		container.setMemUsed(memUsed);
	}
	
	/**
	 * 
	 * @param memFree free RAM in CPU
	 */
	public void setMemFree(long memFree) {
		container.setMemFree(memFree);
	}
	
	/**
	 * 
	 * @param osDescription type of operating system
	 */
	public void setOsDescription(String osDescription) {
		container.setOsDescription(osDescription);
	}
	
	/**
	 * 
	 * @param osName name of operating system
	 */
	public void setOsName(String osName) {
		container.setOsName(osName);
	}
	
	/**
	 * 
	 * @param osDataModel data model of operating system
	 */
	public void setOsDataModel(String osDataModel) {
		container.setOsDataModel(osDataModel);
	}
	
	/**
	 * 
	 * @param primaryIpAddress IP address being used by CPU
	 */
	public void setPrimaryIpAddress(String primaryIpAddress) {
		container.setPrimaryIpAddress(primaryIpAddress);
	}
	
	/**
	 * 
	 * @param primaryMacAddress mac address being used by CPU
	 */
	public void setPrimaryMacAddress(String primaryMacAddress) {
		container.setPrimaryMacAddress(primaryMacAddress);
	}
	
	/**
	 * 
	 * @param hostName 
	 */
	public void setHostName(String hostName) {
		container.setHostName(hostName);
	}
	
	/**
	 * 
	 * @param cpuVendor processor vendor name
	 */
	public void setCpuVendor(String cpuVendor) {
		container.setCpuVendor(cpuVendor);
	}

	/**
	 * 
	 * @param cpuModel computer/laptop model
	 */
	public void setCpuModel(String cpuModel) {
		container.setCpuModel(cpuModel);
	}
	
	/**
	 * 
	 * @param cpuCount 
	 */
	public void setCpuCount(int cpuCount) {
		container.setCpuCount(cpuCount);
	}
	
	/**
	 * 
	 * @param diskSpaceTotal
	 */
	public void setDiskSpaceTotal(long diskSpaceTotal) {
		container.setDiskSpaceTotal(diskSpaceTotal);
	}
	
	/**
	 * 
	 * @param diskSpaceUsed 
	 */
	public void setDiskSpaceUsed(long diskSpaceUsed) {
		container.setDiskSpaceUsed(diskSpaceUsed);
	}
	
	/**
	 * 
	 * @param diskSpaceFree
	 */
	public void setDiskSpaceFree(long diskSpaceFree) {
		container.setDiskSpaceFree(diskSpaceFree);
	}
	
	/**
	 * 
	 * @return current container being used
	 */
	public Container getContainer() {
		return this.container;
	}
	
	public ContainerMan() {
		container = new Container();
	}
	
	private Container container;

}
