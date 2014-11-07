package edu.depaul.data;

import edu.depaul.maestroService.ContainerMan;

/**
 * 
 * @author Deonte D Johnson
 *
 */
public interface Data {
	public void getData(StringBuilder b);
	public void getData(ContainerMan _container);
	public DataName getDataName();
}
