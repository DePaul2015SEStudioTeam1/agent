package edu.depaul.agent;

public class ContainerLog {

	/**
	 * @author Jet2kus84
	 *
	 */
	
	private String id= "";
	private String containerId= "";
	private String timeStamp= "";
	private String memUsage= "";
	private String cpuTotal= "";
	private String fileSystemUsage= "";
	private String memLimit= "";
	private String cpuLimit= "";
	private String filesystemCapacity = "";
	
	/**
	 *  
	 * @param mem container memory total
	 */
	void setMemLimit(String mem) {
		memLimit = mem;
	}
	
	/**
	 * 
	 * @param limit container cpu total
	 */
	void setCpuLimit(String limit) {
		cpuLimit = limit;
	}
	
	/**
	 * 
	 * @param cap file system capacity
	 */
	void setFileSystemCapacity(String cap) {
		filesystemCapacity = cap;
	}
	
	/**
	 * 
	 * @param _id container second aliases
	 */
	void setID(String _id) {
		this.id = _id;
	}
	
	/**
	 * 
	 * @param id container id
	 */
	void setContainerID(String id) {
		this.containerId = id;
	}
	
	/**
	 * 
	 * @param time timestamp when container data was recorded
	 */
	void setTimeStamp(String time) {
		this.timeStamp = time;
	}
	
	/**
	 * 
	 * @param mem container memory used
	 */
	void setMemUsage(String mem) {
		memUsage = mem;
	}
	
	void setCpuTotal(String total) {
		this.cpuTotal = total;
	}
	
	/**
	 * 
	 * @param usage container file system usage
	 */
	void setFileSystem_Usage(String usage) {
		this.fileSystemUsage = usage;
	}
	
	/**
	 * 
	 * @return memory total
	 */
	public String getMemLimit() {
		return memLimit;
	}
	
	/**
	 * 
	 * @return cpu total
	 */
	public String getCpuLimit() {
		return cpuLimit;
	}
	
	/**
	 * 
	 * @return file system capacity
	 */
	public String getFileSystemCapacity() {
		return filesystemCapacity;
	}
	
	/**
	 * 
	 * @return container second aliases id
	 */
	public String getID() {
		return id;
	}
	
	/**
	 * 
	 * @return container id
	 */
	public String getContainerID() {
		return containerId;
	}
	
	/**
	 * 
	 * @return timestamp data was recorded
	 */
	public String getTimeStamp() {
		return timeStamp;
	}
	
	/**
	 * 
	 * @return container memory used
	 */
	public String getMemUsage() {
		return memUsage;
	}
	
	/**
	 * 
	 * @return container cpu total
	 */
	public String getCpuTotal() {
		return cpuTotal;
	}
	
	/**
	 * 
	 * @return container file system usage
	 */
	public String getFilesystemUsage() {
		return fileSystemUsage;
	}

	@Override
	public String toString() {
		return "ID: " + id + "\n" +
			"Container ID: " + containerId + "\n" +
			"Timestamp: " + timeStamp + "\n" +
			"Memory Usage: " + memUsage + "\n" +
			"CPU Usage: " + cpuLimit + "\n" +
			"FileSystem Usage: " + fileSystemUsage + "\n" +
			"CPU Toal: " + cpuTotal + "\n" +
			"FileSystem Capacity: " + filesystemCapacity + "\n" +
			"Memory Limit: " + memLimit + "\n";
	}
}
