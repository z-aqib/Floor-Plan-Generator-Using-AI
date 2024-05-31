package Keypad;

import java.awt.*;
import javax.swing.*;

// commented
public class KDrawFrame extends JFrame {

    // panel features
    private KDrawPanel panel; // a panel object to add to frame
    private final int[] panelSize; // size of panel

    // keypad features
    private final UserInput input; // input by user

    public KDrawFrame(UserInput input) {
        // keypad features
        this.input = input;

        // panel features
        this.panelSize = new int[]{
            2, 2, 450, 450 // x, y, width, height
        };
    }

    public void runDrawFrame() {
        /*
        this method runs the drawPanel class. it creates a panel of KDrawPanel class 
        which is extended from JPanel. It then intializes properties of that JPsnel
         */
        // create a draw panel object (for all the buttons)
        this.panel = new KDrawPanel(panelSize, input);
        // add this draw panel object to this frame
        add(this.panel);

        intializeFrameProperties();
    }

    private void intializeFrameProperties() {
        /*
        this method declares the JFrame properties such as size, rescalability and background. 
         */
        // now declare frame properties
        this.pack(); // sizes the frame so that all its contents are at or above their preferred sizes
        this.setSize(panelSize[2] + 13, panelSize[3]); // sets the x-dimension and y-dimension of frame
        this.setResizable(false); // makes frame fixed
        this.getContentPane().setBackground(Color.WHITE); // make background white
        this.setVisible(true); // makes frame visible
//        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits out of application
        this.setTitle("KEYPAD INPUT"); // set name of frame
//        this.setLocationRelativeTo(null); //sets frame in the middle of laptop screen

        // Set frame location to bottom left corner
        int screenHeight = (int) java.awt.Toolkit.getDefaultToolkit().getScreenSize().getHeight();
        int x = 70;
        int y = screenHeight - panelSize[3] - 100;
        this.setLocation(x, y);
    }

    @Override
    public String toString() {
        return "DrawFrame{" + "panel=" + this.panel.toString() + '}';
    }

}
