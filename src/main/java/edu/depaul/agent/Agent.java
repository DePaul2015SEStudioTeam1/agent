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
 */
public class Agent implements Runnable {
	
	private Sigar sigar = new Sigar();
	private static final Logger logger = Logger.getLogger(Agent.class);
	private MaestroService<Container> maestroService;
	
	public void setMaestroService(MaestroService<Container> maestroService) {
		this.maestroService = maestroService;
	}
	
	public void run() {
		
		try {
			Mem mem = sigar.getMem();
			CpuInfo[] cpus = sigar.getCpuInfoList();
			NetInfo netInfo = sigar.getNetInfo();
			NetInterfaceConfig config = sigar.getNetInterfaceConfig();
			
			Container container = new Container();
			
			// mem
			container.setMemFree(mem.getFree());
			container.setMemTotal(mem.getTotal());
			container.setMemUsed(mem.getUsed());
			
			// cpu
			container.setCpuCount(cpus.length);
			container.setCpuModel(cpus[0].getModel());
			container.setCpuVendor(cpus[0].getVendor());
			
			// disk
			File file = new File("/");
			container.setDiskSpaceFree(file.getFreeSpace());
			container.setDiskSpaceUsed(file.getTotalSpace() - file.getFreeSpace());
			container.setDiskSpaceTotal(file.getTotalSpace());
			
			// os
			container.setOsDataModel(System.getProperty("sun.arch.data.model"));
			container.setOsDescription(System.getProperty("os.version"));
			container.setOsName(System.getProperty("os.name"));
			
			// system info
			container.setHostName(netInfo.getHostName());
			container.setPrimaryIpAddress(sigar.getFQDN());
			container.setPrimaryMacAddress(config.getHwaddr());
			container.setAgentId(config.getHwaddr());
			
			maestroService.store(container);
		}
		catch(SigarException se) {
			logger.error(se.getMessage(), se);
		}
	}

}
