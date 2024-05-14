package GUI_Windows;

import GUI.*;
import Rooms.*;
import java.awt.*;
import java.awt.image.*;

public class FloorPlanWindow implements WindowInterface {

    // given values
    private final int totalArea;
    private final int[] panel;
    private final String[] floorPlan;
    private final String[] roomAreas;
    private final FileReading hashTable;

    // computed values 
    private final Room_Button[] roomButtons;
    private double height;
    private double width;
    private final double scale;
    private final Point topLeft;

    public FloorPlanWindow(int[] panel, String[] floorPlan, String[] rooms, int totalArea, FileReading hashTable) {
        this.panel = panel;
        this.floorPlan = floorPlan;
        this.roomAreas = rooms;
        this.totalArea = totalArea;
        this.hashTable = hashTable;

        this.roomButtons = new Room_Button[floorPlan.length];
        this.height = (int) Math.sqrt(totalArea);
        this.width = height;
//        System.out.println("length by FloorPlanWindow -> " + length);
        scale = (Math.min(panel[2], panel[3]) - 50) / height;
//        System.out.println("scale by FloorPlanWindow -> " + scale);
        int[][] sizes = new int[floorPlan.length][2];
        for (int i = 0; i < floorPlan.length; i++) {
            String roomName = floorPlan[i];
//            System.out.println("roomName in FloorPlanWindow -> " + roomName);
            Room room = hashTable.getRoom(roomName);
            for (int j = 1; j < rooms.length; j++) {
//                System.out.println("rooms[j] in FloorPlanWindow -> " + rooms[j]);
                if (rooms[j].contains(roomName) == true) {
                    String[] area = rooms[j].split(",");
                    sizes[i] = room.findSize(Integer.parseInt(area[1].substring(1)));
//                    System.out.println("asigned roomlength in FloorPlanWindow -> " + sizes[i][0] + " , " + sizes[i][1]);
                    break;
                }
            }
        }
        Color[] rainbowColors = new Color[floorPlan.length];
        Color[] textColors = new Color[floorPlan.length];

        // Generate rainbow colors
        for (int i = 0; i < floorPlan.length; i++) {
            float hue = (float) i / floorPlan.length;
            rainbowColors[i] = Color.getHSBColor(hue, 1, 1);
            textColors[i] = getContrastColor(rainbowColors[i]);
        }
        height = height * scale;
        width = height;
//        System.out.println("length -> " + length);
        topLeft = new Point(panel[0] + 50, panel[1] + 25);
        int stopping_x = (int) (topLeft.x + width);
        int stopping_y = (int) (topLeft.y + height);
        boolean[][] coordinate = new boolean[(int) width][(int) height];
        int rowComplete = -1;
        for (int i = 0; i < roomButtons.length; i++) {
            int roomLength = (int) (sizes[i][0] * scale);
            int roomWidth = (int) (sizes[i][1] * scale);
            int x = 0;
            int y = rowComplete + 1;
            boolean fitted;
            do {
                fitted = checkFit(coordinate, x, y, roomLength, roomWidth);
                if (fitted == false) {
                    fitted = checkFit(coordinate, x, y, roomWidth, roomLength);
                }
                if (fitted == false) {
                    x++;
                } else {
                    int temp = roomLength;
                    roomLength = roomWidth;
                    roomWidth = temp;
                }
                if (x >= stopping_x && y >= stopping_y) {
                    fitted = true;
                } else if (x > stopping_x) {
                    x = 0;
                    y++;
                }

            } while (fitted == false);

//            System.out.println(i + ": " + floorPlan[i] + " -> (" + (x + topLeft.x) + ", " + (y + topLeft.y) + "), width = " + roomWidth + ", length = " + roomLength);
            if (x >= stopping_x && y >= stopping_y) {
                int calculatedArea = (int) (width * height);
                width++;
                height = calculatedArea / width;
                coordinate = new boolean[(int) width][(int) height];
                i = -1;
                rowComplete = -1;
                stopping_x = (int) (topLeft.x + width);
                stopping_y = (int) (topLeft.y + height);
                continue;
            }
            for (int j = y; j < y + roomLength && j < coordinate.length; j++) {
                for (int k = x; k < x + roomWidth && k < coordinate.length; k++) {
                    coordinate[j][k] = true;
                }
            }
//            System.out.println(i + " is intializing...");
            roomButtons[i] = new Room_Button(x + topLeft.x, y + topLeft.y, floorPlan[i], roomLength,
                    roomWidth, rainbowColors[i], textColors[i]);
//            System.out.println(i + " is intialized! ");
            // check if row is full
            boolean check = false;
            for (int j = 0; j < coordinate[0].length && check == false; j++) {
                if (coordinate[rowComplete + 1][j] == false) {
                    check = true;
                }
            }
            if (check == false) {
                rowComplete++;
            }
        }
    }

    private boolean checkFit(boolean[][] grid, int startX, int startY, int length, int width) {
        // Check if the object fits within the grid boundaries
        if (startX < 0 || startY < 0 || startX + width > grid[0].length || startY + length > grid.length) {
            return false;
        }

        // Check if any cell within the object's area is already occupied
        for (int i = startY; i < startY + length; i++) {
            for (int j = startX; j < startX + width; j++) {
                if (grid[i][j]) {
                    return false;
                }
            }
        }

        // If no occupied cell is found, the object can fit
        return true;
    }

    // Method to get contrasting text color based on brightness of background color
    private Color getContrastColor(Color color) {
        double luminance = (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue()) / 255;
        return luminance > 0.5 ? Color.BLACK : Color.WHITE;
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        // first draw the main area
        g.setColor(Color.red);
        g.drawRect(topLeft.x, topLeft.y, (int) width, (int) height);

        // then paint each room
        for (int i = 0; i < roomButtons.length; i++) {
            roomButtons[i].paint(g, observer);
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
    }

    @Override
    public void mousePressed(int x, int y) {
    }

    @Override
    public void mouseDragged(int x, int y) {
    }

    @Override
    public void mouseEntered(int x, int y) {
    }

    @Override
    public void mouseReleased(int x, int y) {
    }

    @Override
    public void mouseExited(int x, int y) {
    }

    @Override
    public void mouseMoved(int x, int y) {
    }

}
