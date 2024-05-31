package GUI_Windows;

import GUI.*;
import java.awt.*;
import java.awt.image.*;

public class Room_Button implements WindowInterface {

    // given values
    private final Point topLeft;
    private final String name;
    private final int length;
    private final int width;
    private final Color fillColor;
    private final Color textColor;

    // configured values
    private Color borderColor;

    public Room_Button(int x, int y, String name, int length, int width, Color fillColor, Color textColor) {
        this(new Point(x, y), name, length, width, fillColor, textColor);
    }

    public Room_Button(Point point, String name, int length, int width, Color fillColor, Color textColor) {
        this.topLeft = point;
        this.name = name;
        this.length = length;
        this.width = width;
        this.fillColor = fillColor;
        this.textColor = textColor;

        //configured values
        this.borderColor = Color.black;
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        // paint the room border
        g.setColor(borderColor);
        g.drawRect(topLeft.x, topLeft.y, width, length);
        g.setColor(fillColor);
        g.fillRect(topLeft.x + 1, topLeft.y + 1, width - 2, length - 2);
        // paint the room name
        g.setColor(textColor);
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.drawString(name, topLeft.x + (width / 2) - (name.length() / 2) * 5,
                topLeft.y + (length / 2));
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
        if (x >= topLeft.x && x <= topLeft.x + length
                && y >= topLeft.y && y <= topLeft.y + length) {
            this.borderColor = Color.BLUE;
        } else {
            this.borderColor = Color.black;
        }
    }

}
