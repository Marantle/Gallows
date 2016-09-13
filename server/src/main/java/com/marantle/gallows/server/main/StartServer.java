package com.marantle.gallows.server.main;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.esotericsoftware.minlog.Log;
import com.marantle.gallows.common.networking.Config;
import com.marantle.gallows.common.packets.GallowsRequest;
import com.marantle.gallows.common.packets.GallowsResponse;
import com.marantle.gallows.server.storage.Pool;
import com.marantle.gallows.server.test.RequestHandler;

import java.io.IOException;

/**
 * Created by mlpp on 6.9.2016.
 */
public class StartServer {

	public static void main(String[] args) throws IOException {
		Server server = new Server();
		server.start();
//		Log.set(Log.LEVEL_DEBUG);
		Config.configure(server.getKryo());
		RequestHandler requestHandler = new RequestHandler();

		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				Log.info("Received object of class: " + object.getClass().toString());
				if (object instanceof GallowsRequest) {
					if (Pool.saveConnection(connection)) {
						GallowsRequest request = (GallowsRequest) object;
						Log.info(request.getPlayerName() + ":" + request.getMsg());
						GallowsResponse response = requestHandler.handleRequest(request, connection);
						if (response != null)
							connection.sendTCP(response);
					}
				}
			}
		});
		server.bind(54555, 54555+1);
	}
}

