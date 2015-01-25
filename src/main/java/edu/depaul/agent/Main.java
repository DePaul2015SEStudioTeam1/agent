package edu.depaul.agent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.handler.DefaultHandler;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Starts the agent.
 * 
 * @author Deonte D Johnson
 */
public class Main {

	private static final String WEB_APP_DIR = "src/main/webapp/";
	private static final int PORT_NUM = 8083;
	private static final Server SERVER = new Server(PORT_NUM);
	private static final WebAppContext ROOT_CONTEXT = new WebAppContext();
	
	public static void main(String[] args) {
//		oldSetup(); //TODO: this is the old call we were making, refactor this out

		setupServer();
		setupResourceHandlers();
		startServer();

	}

	public static void setupServer() {
		ROOT_CONTEXT.setContextPath("/");
		ROOT_CONTEXT.setDescriptor(WEB_APP_DIR + "/WEB-INF/web.xml");
		ROOT_CONTEXT.setResourceBase(WEB_APP_DIR);
		ROOT_CONTEXT.setParentLoaderPriority(true);
		SERVER.setHandler(ROOT_CONTEXT);
	}

	public static void setupResourceHandlers() {
		ResourceHandler resourceHandler = new ResourceHandler();
		resourceHandler.setDirectoriesListed(true);
		resourceHandler.setWelcomeFiles(new String[]{ "index.html" });
		resourceHandler.setResourceBase(".");

		HandlerList handlers = new HandlerList();
		handlers.setHandlers(new Handler[] { resourceHandler, new DefaultHandler() });
		SERVER.setHandler(handlers);
	}

	public static void startServer() {
		try {
			SERVER.start();
			SERVER.join();
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

	public static void oldSetup() {
		System.setProperty("java.library.path", "/etc/armada/sigar");
		ApplicationContext context = new ClassPathXmlApplicationContext("beans/agent-config.xml");
		Agent agent = (Agent) context.getBean("agent");
//		agent.setAgentId((args == null || args.length < 1)? UUID.randomUUID().toString() : args[0]);
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(agent, 3, 3, TimeUnit.SECONDS);
	}
}
