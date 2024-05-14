package GUI;

import java.awt.*;
import java.awt.image.*;

public interface WindowInterface {

    public void paint(Graphics g, ImageObserver observer);

    public void mouseClicked(int x, int y);

    public void mousePressed(int x, int y);

    public void mouseDragged(int x, int y);

    public void mouseEntered(int x, int y);

    public void mouseReleased(int x, int y);

    public void mouseExited(int x, int y);

    public void mouseMoved(int x, int y);

}
