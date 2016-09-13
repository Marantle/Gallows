package com.marantle.gallows.common.data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mlpp on 13.9.2016.
 */
class DataAssist {
	private static final Logger logger = LoggerFactory.getLogger(DataAssist.class);
	static List<String> readData(String fileName) {
		List<String> data = new ArrayList<>();
		URI fileUri = null;
		try {
			logger.info("Opening file {}", fileName);
			fileUri = UsernameData.class.getClassLoader().getResource(fileName).toURI();
			data.addAll(Files.readAllLines(Paths.get(fileUri)));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return data;
	}
}
