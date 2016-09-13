package com.marantle.gallows.lib.model;

import com.esotericsoftware.kryonet.Connection;
import com.marantle.gallows.lib.packets.GallowsRequest;

import java.util.Objects;

/**
 * Created by mlpp on 7.9.2016.
 */
public class Player {

	private String playerName;
	private int connectionID;
	private String currentRoom;


	public Player() {
	}

	public Player(GallowsRequest request, Connection connection) {
		this.playerName = request.getPlayerName();
		this.connectionID = connection.getID();
		this.currentRoom = request.getRoom();
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public int getConnectionID() {
		return connectionID;
	}

	public void setConnectionID(int connectionID) {
		this.connectionID = connectionID;
	}


	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Player player = (Player) o;
		return Objects.equals(getPlayerName(), player.getPlayerName()) && Objects
				.equals(getConnectionID(), player.getConnectionID());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getPlayerName(), getConnectionID());
	}

	public String getCurrentRoom() {
		return currentRoom;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Player{");
		sb.append("playerName='").append(playerName).append('\'');
		sb.append(", connectionID=").append(connectionID);
		sb.append(", currentRoom='").append(currentRoom).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
