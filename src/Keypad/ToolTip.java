package Keypad;

import java.awt.*;

// commented
public class ToolTip {

    private ColoredButtons label;
    static ToolTip toolTip = new ToolTip();

    // make constructor private so the instance is not instantiated from outside
    private ToolTip() {

    }

    public static ToolTip getToolTip() {
        return toolTip;
    }

    public void declareLabel(String text, Point position) {
        // the default width has a formula but if the length is too small then the width 
        // of tooltip will not cover the enter length. hence if less than 3, make it specifclly 30. 
        int width = text.length() * 10;
        if (text.length() < 3) {
            width = 30;
        }
        this.label = new ColoredButtons(text, position, width,
                20, Color.white, Color.white,
                false, Color.black, Color.black);
    }

    public void paint(Graphics g) {
        if (this.label == null) {
            declareLabel("", new Point());
        }
        this.label.paint(g);
    }

    public void setPosition(Point newPosition) {
        if (this.label == null) {
            declareLabel("", new Point());
        }
        this.label.setTopLeft(newPosition);
    }

    @Override
    public String toString() {
        return "ToolTip{" + "label=" + label + '}';
    }

}
