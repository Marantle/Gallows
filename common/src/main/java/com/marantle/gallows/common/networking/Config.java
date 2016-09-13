package com.marantle.gallows.common.networking;

import com.esotericsoftware.kryo.Kryo;
import com.marantle.gallows.common.model.Player;
import com.marantle.gallows.common.model.Room;
import com.marantle.gallows.common.packets.GallowsRequest;
import com.marantle.gallows.common.packets.GallowsResponse;
import com.marantle.gallows.common.packets.PacketType;

import java.util.ArrayList;

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
	}
}
