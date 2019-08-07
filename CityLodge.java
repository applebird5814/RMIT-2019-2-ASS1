package com.company.CityLodge;

import java.util.ArrayList;

public class CityLodge {
    private ArrayList<Room> allRooms;
    private int numOfRooms;

    public CityLodge() {
        allRooms = new ArrayList<Room>();
    }

    public void addRoom(Room newRoom) {
        newRoom.setTempRecord();
        allRooms.add(numOfRooms, newRoom);
        numOfRooms++;
    }

    public int findRoom(String roomID) {
        int i;
        for (i = 0; i < numOfRooms; i++) {
            if (allRooms.get(i).getRoomId().equals(roomID)) {
                return i;
            }
        }
        return -1;
    }

    public Room getRoom(int index) {
        return allRooms.get(index);
    }


}