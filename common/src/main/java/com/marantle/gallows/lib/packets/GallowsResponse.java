package com.marantle.gallows.lib.packets;

import com.marantle.gallows.lib.model.Player;

import java.util.ArrayList;
import java.util.List;

public class GallowsResponse {
	private String text;
	private PacketType packetType;
	private List<Player> playersList = new ArrayList<>();

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
}