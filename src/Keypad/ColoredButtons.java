package Keypad;

import java.awt.*;

// commented
public class ColoredButtons {

    // input values of button 
    private String name; // the name of the color button - text to be displayed
    private Point topLeft; // its topleft point
    private int width; // width of the rectangle
    private int height; // height of the rectangle
    private Color pressedFillColor;
    private Color pressedTextColor;
    private Color unpressedFillColor;
    private Color unpressedTextColor;

    // configured values of button
    private boolean printLeft; // does the text print at the left of the button? default is middle
    private boolean movedStatus;
    private boolean clickedStatus;
    private ToolTip toolTip;
    private int fontSize; // default fontsize is 13, but it can be configured by code
    private boolean border; // does the button have a black border or no border? default is yes
    private boolean roundedEdges; // default is rectangle, do u want rounded rectangle?

    public ColoredButtons(String name, int x, int y, int width, int height,
            Color unpressedFillColor, Color pressedFillColor, boolean tooltip, Color pressedTextColor,
            Color unpressedTextColor) {
        this(name, new Point(x, y), width, height, unpressedFillColor, pressedFillColor, tooltip,
                pressedTextColor, unpressedTextColor);
    }

    public ColoredButtons(String name, Point topLeft, int width, int height,
            Color unpressedFillColor, Color pressedFillColor, boolean tooltip, Color pressedTextColor,
            Color unpressedTextColor) {
        // input values
        this.name = name;
        this.topLeft = topLeft;
        this.width = width;
        this.height = height;
        // if this button has a tooltip, create a tooltip for it. 
        if (tooltip == true) {
            this.toolTip = ToolTip.getToolTip();
            this.toolTip.declareLabel(name, topLeft);
        }
        this.pressedFillColor = pressedFillColor;
        this.unpressedFillColor = unpressedFillColor;
        this.pressedTextColor = pressedTextColor;
        this.unpressedTextColor = unpressedTextColor;

        // configured values
        this.fontSize = 13;
        this.border = true;
        this.printLeft = false;
        this.movedStatus = false;
        this.clickedStatus = false;
    }

    public boolean ifMoved(int x, int y) {
        if (x >= topLeft.x && x <= (topLeft.x) + width && y >= topLeft.y && y <= (topLeft.y) + height) {
            this.movedStatus = true;
            if (this.toolTip != null) {
                this.toolTip.declareLabel(this.name, new Point(x, y));
            }
        } else {
            this.movedStatus = false;
        }
        return this.movedStatus;
    }

    public boolean ifSelected(int x, int y) {
        this.clickedStatus = ifMoved(x, y);
        return this.clickedStatus;
    }

    public void make(boolean clicked) {
        /*
        this method is called when the user wants to make the button in
        a) clicked form
        b) unclicked form
        if in clicked, it runs the ifSelected() with random_value, y values such that they are in the
        region of the button. if unclicked, run the ifSelected() with random_value, y values
        such that are outside the region. these values are calculated using the generateValue() method. 
         */
        if (clicked == true) {
            ifSelected(generateValue(topLeft.x, topLeft.x + width, true),
                    generateValue(topLeft.y, topLeft.y + height, true));
        } else {
            ifSelected(generateValue(topLeft.x, topLeft.x + width, false),
                    generateValue(topLeft.y, topLeft.y + height, false));
        }
    }

    private int generateValue(int start, int end, boolean within) {
        /*
        this method generates values inside or outside a specific area. first you specify
        your start and end range, then you specify if you want a random value inside it
        or outside it. 
         */
        int random_value = (int) (Math.random() * (2 * end));
        if (within == true) {
            while (random_value < start || random_value > end) {
                random_value = (int) (Math.random() * end);
            }
        } else {
            while (random_value >= start && random_value <= end) {
                random_value = (int) (Math.random() * (2 * end));
            }
        }
        return random_value;
    }

    public void setPrintleft(boolean printleft) {
        /*
        this method is called when the button text is required to be printed at the 
        left of the button. by default it is in the middle. 
         */
        this.printLeft = printleft;
    }

    public void setBorder(boolean border) {
        /*
        this method is called when the button does not need a border or if they need it, 
        default is black border but when turned off there will be same fill color
         */
        this.border = border;
    }

    public void setRoundedEdges(boolean edges) {
        /*
        this method is called when user wants a rounded rectangle print and not a typical
        rectangle buttons
         */
        this.roundedEdges = edges;
    }

    public void paint(Graphics g) {
        if (roundedEdges == false) {
            // first paint the border of the button IF it is on
            if (border == true) {
                g.setColor(Color.black);
                g.fillRect(topLeft.x, topLeft.y, width, height);
            }
            // now paint the inner color of button. select the color according to pressed or unpressed
            if (this.movedStatus == true || this.clickedStatus == true) {
                g.setColor(pressedFillColor);
            } else {
                g.setColor(unpressedFillColor);
            }
            // if border was off, make sure to paint a FULL rectangle instead of one degree smaller
            if (border == true) {
                g.fillRect(topLeft.x + 1, topLeft.y + 1, width - 2, height - 2); // the inner color is one size smaller for a perfect border
            } else {
                g.fillRect(topLeft.x, topLeft.y, width, height);
            }
        } else {
            // first paint the border of the button IF it is on
            if (border == true) {
                g.setColor(Color.black);
                g.fillRoundRect(topLeft.x, topLeft.y, width, height, 5, 5);
            }
            // now paint the inner color of button. select the color according to pressed or unpressed
            if (this.movedStatus == true || this.clickedStatus == true) {
                g.setColor(pressedFillColor);
            } else {
                g.setColor(unpressedFillColor);
            }
            // if border was off, make sure to paint a FULL rectangle instead of one degree smaller
            if (border == true) {
                g.fillRoundRect(topLeft.x + 1, topLeft.y + 1, width - 2, height - 2,
                        15, 15); // the inner color is one size smaller for a perfect border
            } else {
                g.fillRoundRect(topLeft.x, topLeft.y, width, height, 60, 60);
            }
        }

        // now paint the text of the button, pick the text color according to moved or clicked
        if (this.movedStatus == true || this.clickedStatus == true) {
            g.setColor(pressedTextColor);
        } else {
            g.setColor(unpressedTextColor);
        }
        g.setFont(new Font("SansSherif", Font.BOLD, fontSize));

        // first configure normal button printing. this is NOT tooltip printing
        // if we arent printing left and the text is smaller than the width, print it in the middle
        if (this.toolTip != null && printLeft == false && (name.length() * 6) < width - 5) {
            g.drawString(name, topLeft.x + (width / 2) - ((name.length() / 2) * 7) - 3, topLeft.y + (height / 2) + 5);
        } // else paint the text normally from the left. 
        else if (this.toolTip != null) {
            g.drawString(name, topLeft.x + 3, topLeft.y + (height / 2) + 5);
        } // now configure tooltip printing
        else if (this.toolTip == null) {
            g.setColor(Color.BLACK);
            g.setFont(new Font("SansSherif", Font.PLAIN, 12));
            g.drawString(name, topLeft.x + 15, topLeft.y + (height / 2) + 5);
        }
    }

    public void paintToolTip(Graphics g) {
        /*
        this method is called when we have to paint the tooltip specifically and not the button
         */
        if (this.movedStatus == true) {
            this.toolTip.paint(g);
        }
    }

    public void changeFontSize(int change) {
        /*
        this method is called to adjust the font size of this button
         */
        this.fontSize = this.fontSize + change;
    }

    @Override
    public String toString() {
        return "";
    }

    // ---------------- GETTERS AND SETTERS --------------------
    // disclaimer: do not remove. they are being used. check usages. 
    public String getName() {
        return name;
    }

    public void setTopLeft(Point topLeft) {
        this.topLeft = topLeft;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isClickedStatus() {
        return clickedStatus;
    }

    public Color getPressedFillColor() {
        return pressedFillColor;
    }

    public void setPressedFillColor(Color pressedFillColor) {
        this.pressedFillColor = pressedFillColor;
    }

    public void setUnpressedFillColor(Color unpressedFillColor) {
        this.unpressedFillColor = unpressedFillColor;
    }

    public void setWidth(int width) {
        this.width = width;
    }
    
    public int getX() {
        return this.topLeft.x;
    }
    public int getY() {
        return this.topLeft.y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

}
