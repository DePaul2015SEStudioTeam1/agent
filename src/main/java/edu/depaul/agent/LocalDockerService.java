package edu.depaul.agent;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.api.NotFoundException;
import com.github.dockerjava.api.command.CreateContainerResponse;
import com.github.dockerjava.api.command.InspectContainerResponse;
import com.github.dockerjava.api.command.InspectImageResponse;
import com.github.dockerjava.api.model.Info;
import com.github.dockerjava.api.model.SearchItem;
import com.github.dockerjava.core.DockerClientBuilder;
import com.github.dockerjava.core.DockerClientConfig;
import org.apache.commons.io.IOUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.List;

/**
 * This class interacts with Docker to create and manage containers.
 *
 */
@Component
public class LocalDockerService {

	public static final Logger LOG = LoggerFactory
			.getLogger(LocalDockerService.class);
	private DockerClient dockerClient;

	/**
	 * Creates and connects to a local Docker client.
	 */
	public void connectToLocalDocker() {
		DockerClientConfig.DockerClientConfigBuilder b = DockerClientConfig.createDefaultConfigBuilder();
		LOG.info(b.toString());
		dockerClient = DockerClientBuilder.getInstance(b.build()).build();
		LOG.info("connected to: " + dockerClient.toString());
	}

	/**
	 * Gets information from a Docker client.
	 * @return Info
	 */
	public Info getLocalDockerInfo() {
		Info info = dockerClient.infoCmd().exec();
		LOG.info("Client info: {}", info.toString());
		return info;
	}

	/**
	 * Searches the Docker repository for an image matching the one passed
	 * as a parameter.
	 * @param imageName
	 */
	public void searchDockerRepository(String imageName) {
		List<SearchItem> dockerSearch = dockerClient.searchImagesCmd(imageName).exec();
		LOG.info("Search returned " + dockerSearch.toString());
	}

	/**
	 * Removes the Docker image whose name matches the one passed as a 
	 * parameter from the Docker repository.
	 * @param imageName docker image to be removed
	 */
	public void removeImage(String imageName) {
		LOG.info("Removing image: {}", imageName);
		try {
			dockerClient.removeImageCmd(imageName).exec();
		}
		catch (NotFoundException e) {
			// just ignore if not exist
			LOG.warn("Image " + imageName + " does not exist locally.");
		}
	}

	/**
	 * Pulls the Docker image, whose name matches the String
	 * passed as a parameter, from the repository.
	 * @param imageName docker image to be pulled
	 */
	public void pullImage(String imageName) {
		Info info = getLocalDockerInfo();

		int imgCount = info.getImages();
		LOG.info("imgCount1: {}", imgCount);

		//If the image already exists, remove it first
		removeImage(imageName);

		info = dockerClient.infoCmd().exec();
		LOG.info("Client info: {}", info.toString());
		imgCount = info.getImages();
		LOG.info("imgCount2: {}", imgCount);
		LOG.info("Pulling image: {}", imageName);

		InputStream response = dockerClient.pullImageCmd(imageName).exec();
		System.out.println(responseAsString(response));

		info = dockerClient.infoCmd().exec();
		LOG.info("Client info after pull, {}", info.toString());

		InspectImageResponse inspectImageResponse = dockerClient
				.inspectImageCmd(imageName).exec();
		LOG.info("Image Inspect: {}", inspectImageResponse.toString());
	}

	/**
	 * Creates a Docker container object.
	 * @param imageName docker image container will be created from
	 * @param containerName container to be created
	 * @param command String array of usable commands
	 * @return container String id
	 */
	public String createContainer(String imageName, String containerName, String[] command) {
		CreateContainerResponse container = dockerClient
				.createContainerCmd(imageName).withCmd(command)
				.withName(containerName).exec();

		LOG.info("Created container {}", container.toString());
		return container.getId();
	}

	/**
	 * Starts a container running.
	 * @param containerId String id of container to be started
	 */
	public void startContainer(String containerId) {
		dockerClient.startContainerCmd(containerId).exec();
		InspectContainerResponse containerResponse = inspectContainer(containerId);
		LOG.info("Container Inspect: {}", containerResponse.toString());
	}

	/**
	 * Stops a running container.
	 * @param containerId String id of container to be stopped
	 */
	public void stopContainer(String containerId) {
		dockerClient.stopContainerCmd(containerId).exec();
	}

	/**
	 * Calls the command inspectContainerCmd on the container with the id that
	 * matches the String passed as a parameter
	 * @param containerId 
	 * @return InspectContainerResponse
	 */
	public InspectContainerResponse inspectContainer(String containerId) {
		return dockerClient.inspectContainerCmd(containerId).exec();
	}

	/**
	 * Removes a container from the Docker client.
	 * @param containerId
	 */
	public void removeContainer(String containerId) {
		dockerClient.removeContainerCmd(containerId).exec();
	}

	/**
	 * Accepts an InputStream as a parameter, converts it into a String
	 * and returns that String.
	 * @param response
	 * @return String
	 */
	protected String responseAsString(InputStream response) {
		StringWriter logwriter = new StringWriter();
		try {
			LineIterator itr = IOUtils.lineIterator(
					response, "UTF-8");
			while (itr.hasNext()) {
				String line = itr.next();
				logwriter.write(line + (itr.hasNext() ? "\n" : ""));
				LOG.info("line: " + line);
			}
			return logwriter.toString();
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
		finally {
			IOUtils.closeQuietly(response);
		}
	}

	/**
	 * Closes the Docker client.
	 */
	public void cleanUp() {
		try {
			dockerClient.close();
			LOG.info("dockerClient closed");
		}
		catch (IOException ioe) {
			LOG.warn(ioe.toString());
		}
	}
}