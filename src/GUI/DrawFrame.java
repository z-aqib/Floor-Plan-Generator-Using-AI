package GUI;

import Keypad.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;

// commented
public class DrawFrame extends JFrame implements Observer {
    
    // panel features
    private DrawPanel panel; // a panel object to add to frame
    private final int[] panelSize; // the size of our panel
    
    // window-application features
    private final UserInput input; // the input by user

    public DrawFrame(UserInput input) {
        // panel features
        this.panelSize = new int[]{
            2, 2, 1300, 700 // x, y, width, height
        };
        
        // window-application features
        this.input = input;
        input.addObserver(this); 
    }

    public void runDrawFrame() {
        /*
        this method runs the drawPanel class. it creates a panel of DrawPanel class 
        which is extended from JPanel. It then intializes properties of that JPsnel
         */
        // create a draw panel object (for all the buttons)
        this.panel = new DrawPanel(panelSize, input);
        // add this draw panel object to this frame
        add(this.panel);

        intializeFrameProperties();
    }

    @Override
    public void update(Observable o, Object arg) {
        /*
        this is our built in ovveride method. it is called by the system itself. it goes into our panel.window.userinput
        and updates the string there. 
        */
        this.panel.updateString(input.getInput_by_user());
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
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exits out of application
        this.setTitle("INTRODUCTION TO ARTIFICIAL INTELLIGENCE - PROJECT"); // set name of frame
        this.setLocationRelativeTo(null); //sets frame in the middle of laptop screen
    }

    @Override
    public String toString() {
        return "DrawFrame{" + "panel=" + this.panel.toString() + '}';
    }

}
