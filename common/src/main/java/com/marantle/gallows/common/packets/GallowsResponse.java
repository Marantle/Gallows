package com.marantle.gallows.common.packets;

import com.marantle.gallows.common.model.Player;
import com.marantle.gallows.common.model.Room;

import java.util.ArrayList;
import java.util.List;

public class GallowsResponse {
	private String text;
	private PacketType packetType;
	private List<Player> playersList = new ArrayList<>();
	private List<Room> roomList = new ArrayList<>();

	public GallowsResponse(String text, PacketType packetType) {
		this.text = text;
		this.packetType = packetType;
	}

	public GallowsResponse(String text) {
		this.text = text;
	}

	public GallowsResponse() {
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public PacketType getPacketType() {
		return packetType;
	}

	public void setPacketType(PacketType packetType) {
		this.packetType = packetType;
	}

	public List<Player> getPlayersList() {
		return playersList;
	}

	public void setPlayersList(List<Player> playersList) {
		this.playersList = playersList;
	}

	public List<Room> getRoomList() {
		return roomList;
	}

	public void setRoomList(List<Room> roomList) {
		this.roomList = roomList;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("GallowsResponse{");
		sb.append("text='").append(text).append('\'');
		sb.append(", packetType=").append(packetType);
		sb.append(", playersList=").append(playersList);
		sb.append(", roomList=").append(roomList);
		sb.append('}');
		return sb.toString();
	}
}