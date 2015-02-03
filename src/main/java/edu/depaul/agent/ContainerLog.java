package edu.depaul.agent;

public class ContainerLog {

	/**
	 * @author Jet2kus84
	 *
	 */
	
	private String id;
	private String container_id;
	private String timeStamp;
	private String mem_usage;
	private String cpu_total;
	private String fileSystem_usage;
	private String mem_limit;
	private String cpu_limit;
	private String fileSystem_capacity;
	
	public ContainerLog() {
		id = null;
		container_id = null;
		timeStamp = null;
		mem_usage = null;
		cpu_total = null;
		fileSystem_usage = null;
		mem_limit = null;
		cpu_limit = null;
		fileSystem_capacity = null;
	}
	
	/**
	 *  
	 * @param mem container memory total
	 */
	void setMemLimit(String mem) {
		mem_limit = mem;
	}
	
	/**
	 * 
	 * @param limit container cpu total
	 */
	void setCpuLimit(String limit) {
		cpu_limit = limit;
	}
	
	/**
	 * 
	 * @param cap file system capacity
	 */
	void setFileSystemCapacity(String cap) {
		fileSystem_capacity = cap;
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
	 * @param _id container id
	 */
	void setContainerID(String _id) {
		this.container_id = _id;
	}
	
	/**
	 * 
	 * @param _time timestamp when container data was recorded
	 */
	void setTimeStamp(String _time) {
		this.timeStamp = _time;
	}
	
	/**
	 * 
	 * @param _mem container memory used
	 */
	void setMemUsage(String _mem) {
		mem_usage = _mem;
	}
	
	void setCpuTotal(String _total) {
		this.cpu_total = _total;
	}
	
	/**
	 * 
	 * @param _usage container file system usage
	 */
	void setFileSystem_Usage(String _usage) {
		this.fileSystem_usage = _usage;
	}
	
	/**
	 * 
	 * @return memory total
	 */
	public String getMemLimit() {
		return mem_limit;
	}
	
	/**
	 * 
	 * @return cpu total
	 */
	public String getCpuLimit() {
		return cpu_limit;
	}
	
	/**
	 * 
	 * @return file system capacity
	 */
	public String getFileSystemCapacity() {
		return fileSystem_capacity;
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
		return container_id;
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
		return mem_usage;
	}
	
	/**
	 * 
	 * @return container cpu total
	 */
	public String getCpuTotal() {
		return cpu_total;
	}
	
	/**
	 * 
	 * @return container file system usage
	 */
	public String getFileSystem_Usage() {
		return fileSystem_usage;
	}

	public String toString() {
		StringBuilder b = new StringBuilder();
		b.append("ID: " + id + "\n");
		b.append("Container ID: " + container_id + "\n");
		b.append("Timestamp: " + timeStamp + "\n");
		b.append("Memory Usage: " + mem_usage + "\n");
		b.append("CPU Usage: " + cpu_limit + "\n");
		b.append("FileSystem Usage: " + fileSystem_usage + "\n");
		
		b.append("CPU Toal: " + cpu_total + "\n");
		b.append("FileSystem Capacity: " + fileSystem_capacity + "\n");
		b.append("Memory Limit: " + mem_limit + "\n");
		return b.toString();
	}
}
