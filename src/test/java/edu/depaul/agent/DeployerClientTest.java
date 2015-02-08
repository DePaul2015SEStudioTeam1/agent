package edu.depaul.agent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import edu.depaul.agent.LocalDockerService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:beans/agent-config.xml")
public class DeployerClientTest {

	@Autowired
	private LocalDockerService dockerService;

	@Before
	public void setUp() {

//		dockerService.connectToLocalDocker();
	}

	@After
	public void tearDown() {
//		dockerService.cleanUp();
	}

	@Test
	public void testSearchImage() {

//		dockerService.searchDockerRepository("helloworldtest");
	}

	@Test
	public void testPullImage() {
//		dockerService.pullImage("armadaproject/helloworldtest");
	}

	@Test
	public void testRemoveImage() {
//		dockerService.removeImage("armadaproject/helloworldtest");
	}

	@Test
	public void testCreateContainer() {
		String[] command = { "ping", "127.0.0.1"};
//
//		//TODO: make sure we give new containers dynamic names
//		String id = dockerService.createContainer(
//				"armadaproject/helloworldtest",
//				"helloworldcontainer-3", command);
	}

	@Test
	public void testStartContainer() {
//		dockerService.startContainer("c58c31136741");
	}

	@Test
	public void testStopContainer() {
//		dockerService.stopContainer("c58c31136741");
	}

	@Test
	public void testRemoveContainer() {
//		dockerService.removeContainer("202b8b8ae809");
//		dockerService.removeContainer("9233332d95b6");
	}

}
