package com.marantle.gallows.common.data;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by mlpp on 13.9.2016.
 */
class NounData {

	private static List<String> nounList = new ArrayList<>();

	public static String getOne() {
		if (nounList.isEmpty()) {
			nounList.addAll(DataAssist.readData("nouns.txt"));
		}
		int index = ThreadLocalRandom.current().nextInt(nounList.size());
		return nounList.get(index);
	}
}