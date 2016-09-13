package com.marantle.gallows.common.data;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by mlpp on 13.9.2016.
 */
class Adjectives {

	private static List<String> adjectiveList = new ArrayList<>();

	public static String getOne() {
		if (adjectiveList.isEmpty()) {
			readAdjectives();
		}
		int index = ThreadLocalRandom.current().nextInt(adjectiveList.size());

		return adjectiveList.get(index);
	}

	private static void readAdjectives() {
		URI fileUri = null;
		try {
			fileUri = Adjectives.class.getClassLoader().getResource("adjectives.txt").toURI();
			adjectiveList.addAll(Files.readAllLines(Paths.get(fileUri)));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
