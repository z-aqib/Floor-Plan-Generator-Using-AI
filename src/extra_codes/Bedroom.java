package extra_codes;

import java.awt.*;
import java.awt.image.ImageObserver;

public class Bedroom extends Room_GUI {

    public Bedroom(int room_area, int total_area, int x, int y) {
        super(room_area, total_area, x, y);
    }
    
    public Bedroom(int room_area, int total_area, Point point) {
        super(room_area, total_area, point);
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        super.paint(g, observer);
    }

}
