package edu.depaul.agent;

import java.io.File;

import org.apache.log4j.Logger;
import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInfo;
import org.hyperic.sigar.NetInterfaceConfig;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

import edu.depaul.maestro.service.MaestroService;
import edu.depaul.operations.model.Container;

/**
 * 
 * @author Deonte D Johnson
 *
 * edit - john davidson, 1/10/2015
 */
public class Agent implements Runnable {

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

	}
}
