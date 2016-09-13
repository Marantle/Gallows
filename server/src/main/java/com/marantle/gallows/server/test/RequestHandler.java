package com.marantle.gallows.server.test;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.minlog.Log;
import com.marantle.gallows.lib.model.Player;
import com.marantle.gallows.lib.packets.GallowsRequest;
import com.marantle.gallows.lib.packets.GallowsResponse;
import com.marantle.gallows.lib.packets.PacketType;
import com.marantle.gallows.server.storage.Pool;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.marantle.gallows.server.storage.Pool.getPlayers;

/**
 * Created by mlpp on 7.9.2016.
 */
public class RequestHandler {
	public RequestHandler() {

	}

	public GallowsResponse handleRequest(GallowsRequest request, Connection connection) {
		GallowsResponse response = new GallowsResponse();
		response.setPacketType(request.getPacketType());
		Log.info(String.format("User %s requests to %s", request.getPlayerName(),
				request.getPacketType().toString()));
		switch (request.getPacketType()) {
		case LIST_PLAYERS:
			response = preparePlayerListPacket(response);
			break;
		case LIST_ROOMS:
			response.setText("This will be list of rooms");
			break;
		case CONNECT:
			Player player = new Player(request, connection);
			Pool.savePlayer(player);
			response.setText("Connection established succesfully");
			List<Player> evictees = new ArrayList<>();
			Pool.getPlayers().forEach(p -> {
				Connection con = Pool.getConnectionByID(p.getConnectionID());
				if (con != null && con.isConnected()) {
					GallowsResponse playersResponse = new GallowsResponse(
							"User " + request.getPlayerName() + " connected", PacketType.MESSAGE);
					con.sendTCP(playersResponse);
				} else {
					evictees.add(p);
				}
			});

			final GallowsResponse playersResponse = preparePlayerListPacket(new GallowsResponse());
			Pool.getConnections().forEach(c -> c.sendTCP(playersResponse));
			evictees.forEach(Pool::evictPlayer);
			break;
		case JOIN_ROOM:
			response.setText("This would join a room");
			break;
		case LEAVE_ROOM:
			response.setText("This would leave a room");
			break;
		case GUESS:
			response.setText("This would guess thing");
			break;
		case CHAT:
			response.setText("This would chat");
			String msg = String
					.format("%s %s%d: %s", LocalTime.now().toString(), request.getPlayerName(),
							connection.getID(), request.getMsg());
			String roomName = request.getRoom();
			Stream<Player> jeah = Pool.getPlayers().stream().filter(p -> {
				boolean test1 = p.getCurrentRoom().equals(roomName);
				boolean test2 = Pool.getConnectionByID(p.getConnectionID()).isConnected();
				return test1 && test2;
			});
			jeah.forEach(p -> Pool.getConnectionByID(p.getConnectionID())
					.sendTCP(new GallowsResponse(msg)));
			response = null;
			break;
		}
		return response;
	}

	private GallowsResponse preparePlayerListPacket(GallowsResponse response) {
		response.setText("This will be list of players");
		List<Player> players = getPlayers().stream()
				.filter(p -> Pool.getConnectionByID(p.getConnectionID()) != null && Pool.getConnectionByID(p.getConnectionID()).isConnected())
				.collect(Collectors.toList());
		players.forEach(System.out::println);
		response.setPlayersList(players);
		response.setPacketType(PacketType.LIST_PLAYERS);
		return response;
	}
}
