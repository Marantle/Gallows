package com.marantle.gallows.common.data;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by mlpp on 13.9.2016.
 */
class UsernameData {

	private static List<String> usernameList = new ArrayList<>();

	public static String getOne() {
		if (usernameList.isEmpty()) {
			usernameList.addAll(DataAssist.readData("usernames.txt"));
		}
		int index = ThreadLocalRandom.current().nextInt(usernameList.size());
		return usernameList.get(index);
	}


}