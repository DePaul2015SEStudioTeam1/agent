package edu.depaul.agent;

//import edu.depaul.maestro.service.MaestroService;

/**
 * 
 * @author Deonte D Johnson
 *
 * edit - john davidson, 1/10/2015
 * edit - Deonte Johnson, 2/2/15
 */
public class Agent implements Runnable {
	
	@Override
	public void run() {
			
		//send cAdvisor's url location to start retrieving data
		Cadvisor data = new Cadvisor("http://140.192.249.16:8890/api/v1.2/docker");
				
		//print a log for each container
		for(int i = 0; i < data.getNumberOfContainers(); i++) {
			Container_log log = data.CreateContainerLog(i);
			System.out.println(log.toString());
		}
				
		//retrieving data from an individual container
		Container_log l = data.CreateContainerLog(0);
		String id = l.getContainerID(); //add to DB
		String cpu_limit = l.getCpuLimit(); //add to DB
		String cpu_total = l.getCpuTotal(); //add to DB
/*
	private static final Logger logger = Logger.getLogger(Agent.class);
	private Sigar sigar = new Sigar();
	private MaestroService<Container> maestroService;
	private Container container = new Container();
	private String agentId;
	private Mem mem;
	private CpuInfo[] cpus;
	private NetInfo netInfo;
	private NetInterfaceConfig config;
	
	public void setMaestroService(MaestroService<Container> maestroService) {
		this.maestroService = maestroService;
	}
	
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
	public void run() {
		collectStatistics();
	}

	private void collectStatistics() {
		getSigarStatistics();
		setMem();
		setCPU();
		setDiskUsage();
		setOperatingSystem();
		setSystemInfo();
		maestroService.store(container);
	}

	private void getSigarStatistics() {
		try {
			mem = sigar.getMem();
			cpus = sigar.getCpuInfoList();
			netInfo = sigar.getNetInfo();
			config = sigar.getNetInterfaceConfig();
		}
		catch(SigarException se) {
			logger.error(se.getMessage(), se);
		}
	}

	private void setMem() {
		// mem
		container.setMemFree(mem.getFree());
		container.setMemTotal(mem.getTotal());
		container.setMemUsed(mem.getUsed());
	}

	private void setCPU() {
		// cpu
		container.setCpuCount(cpus.length);
		container.setCpuModel(cpus[0].getModel());
		container.setCpuVendor(cpus[0].getVendor());
	}

	private void setDiskUsage() {
		// disk
		File file = new File("/");
		container.setDiskSpaceFree(file.getFreeSpace());
		container.setDiskSpaceUsed(file.getTotalSpace() - file.getFreeSpace());
		container.setDiskSpaceTotal(file.getTotalSpace());

	}
	private void setOperatingSystem() {

		// os
		container.setOsDataModel(System.getProperty("sun.arch.data.model"));
		container.setOsDescription(System.getProperty("os.version"));
		container.setOsName(System.getProperty("os.name"));

	}
	private void setSystemInfo() {
		try {
			// system info
			container.setHostName(netInfo.getHostName());
			container.setPrimaryIpAddress(sigar.getFQDN());
			container.setPrimaryMacAddress(config.getHwaddr());
			container.setAgentId(agentId);
		}
		catch(SigarException se) {
			logger.error(se.getMessage(), se);
		}

	} */
	}
}
