package Rooms;

import java.io.*;

public class FileReading {

    private final HashingOpenAddQuadratic hashTable = new HashingOpenAddQuadratic(15);
    private final Room[] rooms;

    public FileReading() {
        filing();
        rooms = computeRooms();
    }

    private void filing() {
        try {
            FileInputStream fstream = new FileInputStream("src/Rooms/AIProjectData.csv");
            try ( DataInputStream in = new DataInputStream(fstream)) {
                BufferedReader br = new BufferedReader(new InputStreamReader(in));
                String strLine;
                int i = 0;
                int[] lengths = new int[3];
                int[] widths = new int[3];
                String name;
                while ((strLine = br.readLine()) != null) {
                    String[] components = strLine.split(",");
                    name = components[0];
                    int length = Integer.parseInt(components[2]);
                    int width = Integer.parseInt(components[3]);
                    lengths[i] = length;
                    widths[i++] = width;
                    if (i == lengths.length) {
                        Room room = new Room(name, lengths, widths);
                        hashTable.insert(room);
                        i = 0;
                        lengths = new int[3];
                        widths = new int[3];
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    private Room[] computeRooms() {
        Comparable[] table = hashTable.toArray();
        Room[] roomss = new Room[hashTable.getCountOccupied()];
        for (int i = 0, room_counter = 0; i < table.length; i++) {
            if (table[i] != null) {
                roomss[room_counter++] = (Room) table[i];
            }
        }
        return roomss;
    }

    public Room[] getAllRooms() {
        return rooms;
    }

    public int getCountOfRooms() {
        return rooms.length;
    }

    public Room getRoom(String roomName) {
        return (Room) hashTable.search(new Room(roomName, new int[]{}, new int[]{}));
    }

}
