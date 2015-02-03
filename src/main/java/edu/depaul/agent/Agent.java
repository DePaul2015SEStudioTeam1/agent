package edu.depaul.agent;

public class Agent implements Runnable {
	
	@Override
	public void run() {
		//send cAdvisor's url location to start retrieving data
		LogCollector data = new LogCollector("http://140.192.249.16:8890/api/v1.2/docker");
				
		//print a log for each container
		for(int i = 0; i < data.getNumberOfContainers(); i++) {
			ContainerLog log = data.createContainerLog(i);
			System.out.println(log.toString());
		}
				
		//retrieving data from an individual container
		ContainerLog l = data.createContainerLog(0);
		String id = l.getContainerID(); //add to DB
		String cpu_limit = l.getCpuLimit(); //add to DB
		String cpu_total = l.getCpuTotal(); //add to DB
	}
}
