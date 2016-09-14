package com.marantle.gallows.common.packets;

public class GallowsRequest {

	private String playerName;
	private String msg;
	private PacketType packetType;
	private String room;

	public GallowsRequest() {
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getPlayerName() {
		return playerName;
	}

	public void setPlayerName(String playerName) {
		this.playerName = playerName;
	}

	public PacketType getPacketType() {
		return packetType;
	}

	public void setPacketType(PacketType packetType) {
		this.packetType = packetType;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getRoom() {
		return room;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("GallowsRequest{");
		sb.append("playerName='").append(playerName).append('\'');
		sb.append(", msg='").append(msg).append('\'');
		sb.append(", packetType=").append(packetType);
		sb.append(", room='").append(room).append('\'');
		sb.append('}');
		return sb.toString();
	}
}

