package extra_codes;

import GUI.*;
import java.awt.*;
import java.awt.image.*;

public class Room_GUI implements WindowInterface {

    protected final int room_area;
    protected final int total_area;
    protected final Point point;
    protected final int length;

    public Room_GUI(int room_area, int total_area, int x, int y) {
        this(room_area, total_area, new Point(x,y));
    }
    
    public Room_GUI(int room_area, int total_area, Point point) {
        this.room_area = room_area;
        this.total_area = total_area;
        this.point = point;
        double ratio = total_area/room_area;
        this.length =  (int) (Math.sqrt(room_area) * ratio);
        /*
        int max_row = 0;
        boolean[][] coordinate = new boolean[(int) length][(int) length];
        int rowComplete = -1;
        for (int i = 0; i < roomButtons.length; i++) {
            int roomLength = (int) (sizes[i][0] * scale);
            int roomWidth = (int) (sizes[i][1] * scale);
            System.out.println("roomLength by FloorPlanWindow -> " + roomLength);
            if (x + roomWidth < stopping_x && y + roomLength < stopping_y) {
                max_row = Math.max(max_row, roomLength);
                roomButtons[i] = new Room_Button(x, y, floorPlan[i], roomLength,
                        roomWidth, rainbowColors[i], textColors[i]);
                x += roomWidth;
            } else if (x + roomLength < stopping_x && y + roomWidth < stopping_y) {
                roomButtons[i] = new Room_Button(x, y, floorPlan[i], roomWidth,
                        roomLength, rainbowColors[i], textColors[i]);
                max_row = Math.max(max_row, roomWidth);
                x += roomLength;
            } else {
                x = topLeft.x;
                y += max_row;
                max_row = 0;
                roomButtons[i] = new Room_Button(x, y, floorPlan[i], roomLength,
                        roomWidth, rainbowColors[i], textColors[i]);
                x += roomWidth;
            }

        }
        */
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        g.setColor(Color.black);
        g.drawRect(point.x, point.y, length, length);
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
