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
class AdjectiveData {

	private static List<String> adjectiveList = new ArrayList<>();

	public static String getOne() {
		if (adjectiveList.isEmpty()) {
			adjectiveList.addAll(DataAssist.readData("adjectives.txt"));
		}
		int index = ThreadLocalRandom.current().nextInt(adjectiveList.size());
		return adjectiveList.get(index);
	}
}
