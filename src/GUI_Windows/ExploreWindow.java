package GUI_Windows;

import GUI.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class ExploreWindow implements WindowInterface {

    private final ImageIcon drip;
    private final int[] panel;

    public ExploreWindow(int[] panel) {
        this.panel = panel;
        this.drip = new ImageIcon("src/pictures/drip_1.png");
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        g.drawImage(this.drip.getImage(), panel[0], panel[1], panel[2] / 2, 100, observer);
        g.drawImage(this.drip.getImage(), panel[0] + (panel[2] / 2), panel[1], panel[2] / 2, 100, observer);
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
