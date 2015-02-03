package edu.depaul.agent;

public class Agent implements Runnable {

	private String id = "";

	@Override
	public void run() {
		//TODO: are we instantiating a new LogCollector every time run() happens?
		//TODO: maybe do this once on startup
		//send cAdvisor's url location to start retrieving data
		LogCollector data = new LogCollector("http://140.192.249.16:8890/api/v1.2/docker");

		//TODO: can remove print statements
		//print a log for each container
		for(int i = 0; i < data.getNumberOfContainers(); i++) {
			ContainerLog log = data.createContainerLog(i);
			System.out.println(log.toString());
		}

		//TODO: this not being utilized
		ContainerLog l = data.createContainerLog(0);
		String id = l.getContainerID();
		String cpu_limit = l.getCpuLimit();
		String cpu_total = l.getCpuTotal();
	}

	public void setAgentId(String id) {
		this.id = id;
	}
}
