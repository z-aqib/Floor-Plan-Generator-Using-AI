package Rooms;

public class Room implements Comparable<Room> {

    private final String name;
    private final int[] length;
    private final int[] width;
    private final int[] area;

    public Room(String name, int[] length, int[] width) {
        this.name = name;
        this.length = length;
        this.width = width;

        this.area = new int[length.length];
        for (int i = 0; i < area.length; i++) {
            area[i] = length[i] * width[i];
        }
    }

    public int getLengthOfIndex(int i) {
        if ((i - 1) < length.length) {
            return length[i - 1];
        }
        return -1;
    }

    public int getWidthOfIndex(int i) {
        if ((i - 1) < width.length) {
            return width[i - 1];
        }
        return -1;
    }

    public int[] findSize(int areaUnknown) {
        for (int i = 0; i < area.length; i++) {
            if (area[i] == areaUnknown) {
                return getLengthWidthOfIndex(i + 1);
            }
        }
        return null;
    }

    public int[] getLengthWidthOfIndex(int i) {
        if ((i - 1) < area.length) {
            int[] lw = new int[2];
            lw[0] = length[i - 1];
            lw[1] = width[i - 1];
            return lw;
        }
        return null;
    }

    public int getAreaOfIndex(int i) {
        if ((i - 1) < area.length) {
            return area[i - 1];
        }
        return -1;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public int compareTo(Room o) {
        if (o.getName().equals(name) == true) {
            return 0;
        }
        return -1;
    }

}
