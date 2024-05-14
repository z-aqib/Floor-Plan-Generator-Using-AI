package GUI_Windows;

import GUI.*;
import Keypad.*;
import Rooms.*;
import java.awt.*;
import java.awt.image.*;

public class FloorPlanDisplayer implements WindowInterface {

    private final int[] panel;

    private final FloorPlanWindow[] windows;
    private int window_counter = 0;
    private final ColoredButtons nextButton;
    private final ColoredButtons previousButton;

    public FloorPlanDisplayer(int[] panel, String[][] floorPlans, String[] roomSizes,
            int totalArea, FileReading hashTable) {
        this.panel = panel;
        int length = Math.min(50, floorPlans.length);
        windows = new FloorPlanWindow[length];
        for (int i = 0; i < windows.length; i++) {
            windows[i] = new FloorPlanWindow(panel, floorPlans[i], roomSizes, totalArea, hashTable);
            System.out.println("intialized "+i);
        }
        this.previousButton = new ColoredButtons("Previous", 900, 500, 100, 50,
                Color.BLUE, Color.cyan, true,
                Color.white, Color.white);
        this.nextButton = new ColoredButtons("Next", 1050, 500, 100, 50,
                Color.magenta, Color.red, true,
                Color.white, Color.white);
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        nextButton.paint(g);
        previousButton.paint(g);
        windows[window_counter].paint(g, observer);

        g.setColor(Color.black);
        g.setFont(new Font("Arial", Font.BOLD, 50));
        g.drawString((window_counter+1) + " out of " + windows.length, panel[0] + panel[2] - 350, panel[1] + 100);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (nextButton.ifSelected(x, y) == true) {
            window_counter++;
            if (window_counter >= windows.length) {
                window_counter--;
            }
        }
        if (previousButton.ifSelected(x, y) == true) {
            window_counter--;
            if (window_counter < 0) {
                window_counter++;
            }
        }
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
        nextButton.ifMoved(x, y);
        previousButton.ifMoved(x, y);
    }

}
