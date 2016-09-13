package com.marantle.gallows.server.storage;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.minlog.Log;
import com.marantle.gallows.common.model.Player;
import com.marantle.gallows.common.model.Room;
import com.marantle.gallows.common.packets.GallowsResponse;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mlpp on 7.9.2016.
 */
public class Pool {
	private static Vector<Connection> connections = new Vector<>();
	private static Vector<Room> rooms = new Vector<>();
	private static Set<Player> players = new HashSet<>();

	public static Vector<Connection> getConnections() {
		return connections;
	}

	public static Vector<Room> getRooms() {
		return rooms;
	}

	public static Set<Player> getPlayers() {
		return players;
	}

	public static Connection getConnectionByID(int id) {
		return connections.stream().filter(c -> c.isConnected() && c.getID() == id).findFirst()
				.orElse(null);
	}

	public static boolean saveConnection(Connection connection) {
		cleanDeadConnections();
		Log.info(connection.getRemoteAddressTCP().getHostName());
		boolean connectionExists = connections.stream()
				.filter(c -> c.isConnected() && c == connection).findAny().isPresent();

		if (connectionExists) {
			GallowsResponse response = new GallowsResponse();
			response.setText("Existing connection");
			//connection.sendTCP(response);
		} else {
			Log.info("added connection " + connection);
			connections.add(connection);
		}
		return true;
	}

	private static synchronized void cleanDeadConnections() {
		Vector<Connection> connectionList = new Vector<>();
		connections.stream().filter(c -> c == null || !c.isConnected()).forEach(c -> {
			Log.info("Removing dead connection: " + c);
			connectionList.add(c);
		});
		connectionList.forEach(c -> {
			connections.remove(c);
		});
	}

	public static synchronized Player findPlayerByConnection(Connection connection) {
		return players.stream().filter(p -> p.getConnectionID() == connection.getID()).findFirst()
				.orElse(null);
	}

	public static synchronized void savePlayer(Player player) {
		Log.info("Saving playername " + player.getPlayerName());
		players.add(player);
	}

	public static synchronized Room findRoomByID(int id) {
		return rooms.stream().filter(r -> r.getRoomID() == id).findFirst().orElse(null);
	}

	public static synchronized void evictPlayer(Player evictee) {
		Log.info(String.format("Evicting player: %s[%d]", evictee.getPlayerName(), evictee.getConnectionID()));
		Connection evicteeConnection = getConnectionByID(evictee.getConnectionID());
		if (evicteeConnection != null && evicteeConnection.isConnected())
			evicteeConnection.close();
		cleanDeadConnections();
		players.remove(evictee);
	}

	public static synchronized void saveRoom(Room room) {
		rooms.add(room);
	}

}
