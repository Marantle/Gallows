package com.marantle.gallows.common.model;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by mlpp on 7.9.2016.
 */
public class Room {
	private String roomName;
	private int roomID;
	private static AtomicInteger roomIdCounter = new AtomicInteger();
	public Room(){

	}

	public Room(String roomName){
		this.roomName = roomName;
		this.roomID = roomIdCounter.incrementAndGet();
	}

	public String getRoomName() {
		return roomName;
	}

	public void setRoomName(String roomName) {
		this.roomName = roomName;
	}

	public int getRoomID() {
		return roomID;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Room room = (Room) o;
		return getRoomID() == room.getRoomID() && Objects.equals(getRoomName(), room.getRoomName());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getRoomName(), getRoomID());
	}

	public void setRoomID(int roomID) {
		this.roomID = roomID;
	}
}
