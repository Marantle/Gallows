package com.marantle.gallows.server.main;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.marantle.gallows.common.data.RoomNameGenerator;
import com.marantle.gallows.common.model.Player;
import com.marantle.gallows.common.model.Room;
import com.marantle.gallows.common.networking.Config;
import com.marantle.gallows.common.packets.GallowsRequest;
import com.marantle.gallows.common.packets.GallowsResponse;
import com.marantle.gallows.common.packets.PacketType;
import com.marantle.gallows.server.storage.Pool;
import com.marantle.gallows.server.test.RequestHandler;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

import static com.marantle.gallows.server.storage.Pool.getPlayers;

/**
 * Created by mlpp on 6.9.2016.
 */
public class StartServer {

	private static final Logger logger = LoggerFactory.getLogger(StartServer.class);

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();

		Config.configure(server.getKryo());
		RequestHandler requestHandler = new RequestHandler();
		String roomName;
		int i = 0;
		while (i < 0) {
			i++;
			roomName = RoomNameGenerator.getOne();
			logger.info("We got a name {}", roomName);
		}
		Pool.saveRoom(new Room("Lobby"));
		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
//				logger.info("Received object of class: " + object.getClass().toString());
				if (object instanceof GallowsRequest) {
					if (Pool.saveConnection(connection)) {
						GallowsRequest request = (GallowsRequest) object;
						logger.info("received request from {}, text was {}", request.getPlayerName(), request.getMsg());
						GallowsResponse response = requestHandler
								.handleRequest(request, connection);
						if (response != null)
							connection.sendTCP(response);
					}
				}

			}
			public void disconnected (Connection c) {
				Player player = Pool.getPlayers().stream().filter(p -> p.getConnectionID() == c.getID()).findFirst().orElse(null);


				GallowsResponse response = new GallowsResponse("User " + player.getPlayerName() + " diconnected",
						PacketType.CHAT);
				server.sendToAllTCP(response);
				logger.info("{} disconnected", player.getPlayerName());
				Pool.evictPlayer(player);
			}
		});
		server.bind(54555, 54555 + 1);
	}
}

