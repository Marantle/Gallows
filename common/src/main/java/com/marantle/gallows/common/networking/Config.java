package com.marantle.gallows.common.networking;

import com.esotericsoftware.kryo.Kryo;
import com.marantle.gallows.common.model.Player;
import com.marantle.gallows.common.model.Room;
import com.marantle.gallows.common.packets.GallowsRequest;
import com.marantle.gallows.common.packets.GallowsResponse;
import com.marantle.gallows.common.packets.PacketType;
import org.apache.log4j.PropertyConfigurator;

import java.util.ArrayList;
import java.util.Properties;

/**
 * Created by mlpp on 7.9.2016.
 */
public class Config {
	public static void configure(Kryo kryo) {
		kryo.register(GallowsRequest.class);
		kryo.register(GallowsResponse.class);
		kryo.register(PacketType.class);
		kryo.register(Player.class);
		kryo.register(Room.class);
		kryo.register(ArrayList.class);

		Properties prop = new Properties();
		PropertyConfigurator.configure(Config.class.getClassLoader().getResource("log4j.properties"));
	}
}
