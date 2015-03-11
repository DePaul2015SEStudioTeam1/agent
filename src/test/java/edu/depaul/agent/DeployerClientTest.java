/*
 * The MIT License (MIT)
 * 
 * Copyright (c) <year> <copyright holders> 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package edu.depaul.agent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:beans/agent-config.xml")
public class DeployerClientTest {

	//@Autowired
	//private LocalDockerService dockerService;

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
