package com.marantle.gallows.common.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mlpp on 13.9.2016.
 */
public class RoomNameGenerator {
	private static List<String> usedNames = new ArrayList<>();

	public static String getOne() {
		String newName = "";
		long loopCount = 0;
		do {
			loopCount++;
			newName = Adjectives.getOne() + " " + Nouns.getOne();
		} while (usedNames.contains(newName) && loopCount < 100000);
		return newName;
	}
}
