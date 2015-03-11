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

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * An agent object.
 *
 */
public class Agent {
	
	/**
	 * The main method for the Agent class. It outputs error messages if Armada itself is not running.
	 * @param args
	 */
	public static void main(String[] args) {
		if(args == null || args.length < 1) {
			System.err.println("Usage: java -jar agent.jar <armada.service.url> <c.advisor.url>");
			System.err.println("Example: java -jar agent.jar http://localhost:8083/ http://localhost:8890/api/v1.2/docker");
			System.exit(1);
		}
		
		System.getProperties().setProperty("armada.service.url", args[0]);
		System.getProperties().setProperty("cadvisor.url", args[1]);
		new ClassPathXmlApplicationContext("beans/agent-config.xml");
	}
}
