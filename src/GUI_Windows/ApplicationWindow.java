package GUI_Windows;

import GUI.WindowInterface;
import Keypad.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

// commented
public class ApplicationWindow implements WindowInterface {

    private int window_number; // what window we are currently on
    private final int[] panel;
    private final int[] background_panel;
    private final int limit_to_decrease; // how much the bckg shortens
    private final int y_point; // where the menu starts

    // menu buttons
    private final ColoredButtons[] menu_buttons;
    private final String[] menu_strings;

    // window number 0 - starting screen
    private int text_zero_index; // what text index are we currently displaying
    private long time_zero;
    private final String[] text_zero; // list of texts we have to display
    private final ImageIcon background_zero; // background_zero of start screen

    // window number 1 - main page
    private final WelcomeWindow welcomeWindow;

    // window number 2 - explore page
    private final ExploreWindow exploreWindow;

    // window number 3 - design page
    private final DesignWindow designWindow;

    public ApplicationWindow(int[] panel, UserInput input) {
        // default values of window of project
        this.window_number = 0;
        this.panel = panel;
        this.background_panel = new int[]{panel[0], panel[1], panel[2], panel[3] - 65};
        this.y_point = 0;
        this.limit_to_decrease = y_point + 40;
        exploreWindow = new ExploreWindow(new int[]{background_panel[0], limit_to_decrease - 2,
            background_panel[2], background_panel[3] - limit_to_decrease});
        designWindow = new DesignWindow(new int[]{background_panel[0], limit_to_decrease - 2,
            background_panel[2], background_panel[3] - limit_to_decrease}, input);

        // menu buttons
        this.menu_strings = new String[]{"Welcome", "Explore", "Design"};
        this.menu_buttons = new ColoredButtons[menu_strings.length];
        int width_buttons = 100;
        for (int i = 0; i < menu_buttons.length; i++) {
            menu_buttons[i] = new ColoredButtons(menu_strings[i], 500 + (i * width_buttons), y_point,
                    width_buttons, limit_to_decrease - y_point, Color.white,
                    Color.white, true, Color.white, Color.white);
            menu_buttons[i].setBorder(false);
        }

        // window number 0
        this.background_zero = new ImageIcon("src/pictures/bck3.jpg");
        this.text_zero = new String[]{
            "Welcome  ", "Innovation begins here", /*add spaces after small words to adjust alignment*/
            "Begin your journey", "Step Inside", "Create your Heaven"
        };
        this.text_zero_index = 0;
        this.time_zero = System.currentTimeMillis() / 1000; //start time of how long has it been since line was printed

        // window number 1
        this.welcomeWindow = new WelcomeWindow(menu_buttons, background_panel);

    }

    public void updateString(String s) {
        /*
        this method updates our user_returned_input with the string s.
        this method is called when user presses enter on the keypad
         */
        this.designWindow.updateString(s);
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {

        // paint which window we require
        switch (window_number) {
            case 0 -> {
                paintWindow0(g, observer);
            }
            case 1 -> {
//                if (floorPlan != null) {
//                    floorPlan.paint(g, observer); 
//                }
                //drawGradientText(g, "EVOLUTION AT ITS PEAK", 200, 200, Color.green, Color.blue);
                welcomeWindow.paint(g, observer);
            }
            case 2 -> {
                exploreWindow.paint(g, observer);
            }
            case 3 -> {
                designWindow.paint(g, observer);
            }
        }

        paintMenuButtons(g, observer);

        // so if the user has entered some value on the keypad, display it
//        if (user_returned_input.getInput_by_user() != null) {
//            g.drawString(user_returned_input.getInput_by_user(), panel[2] / 3, panel[3] / 2);
//        }
//        // if the keypad has been closed, make sure to make it null to re-open it again when needed. 
//        if (keypad != null && keypad.isShowing() == false) {
//            keypad = null;
////            floorPlan = new FloorPlanWindow(30, 50);
//        }
    }

    private void paintWindow0(Graphics g, ImageObserver observer) {
        // first paint the background_zero image
        g.drawImage(background_zero.getImage(), background_panel[0], background_panel[1], background_panel[2], background_panel[3], observer);

        // decrease size of background
        if (background_panel[1] < limit_to_decrease) {
            background_panel[1]++; // move picture down
            background_panel[3]--; // make height smaller
        }

        // set the size of the font
        g.setFont(new Font("Arial", Font.BOLD, 100));

        // set the color of the font
//        g.setColor(new Color(1,50,32)); // extreme dark green
//        g.setColor(new Color(0x005C29)); // dark green
//        g.setColor(new Color(0x522e92)); // light purple
        g.setColor(new Color(0x482980)); // dark purple

        // paint the text according to which text to print
        g.drawString(text_zero[text_zero_index], (background_panel[2] / 2) - ((text_zero[text_zero_index].length() / 2) * 50),
                (background_panel[3] / 2) + background_panel[1]); // middle point par aakar half length of string subtract kardo to be in immediate middle

        // update the text: if it has been displayed for more than 1 second, change it. 
        if ((time_zero - System.currentTimeMillis() / 1000) <= -1) {
            // if that text line was the last one, start from the beginning, or move to the next one
            if (++text_zero_index == text_zero.length) {
                text_zero_index = 0;
            }
            time_zero = System.currentTimeMillis() / 1000; // update the start time of displaying
        }
    }

    private void paintMenuButtons(Graphics g, ImageObserver observer) {

        // paint a line for the menu buttons
        g.setColor(welcomeWindow.getCurrentColor());
        g.fillRect(panel[0], y_point, panel[2], limit_to_decrease - y_point);

        // paint logo
        g.drawImage(welcomeWindow.getCurrentLogo().getImage(), panel[0], panel[1], 300, 38, observer);

        for (int i = 0; i < menu_buttons.length; i++) {
            menu_buttons[i].paint(g);
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        switch (window_number) {
            case 0 -> {
                // if the user is on start screen and he clicks anywhere, move to the homepage. 
                window_number = 1;
//            keypad = new KDrawFrame(user_returned_input);
//            keypad.runDrawFrame();
                background_panel[1] = limit_to_decrease;
                background_panel[3] = panel[3] - 65 - limit_to_decrease;
                menu_buttons[0].make(true);
            }
            case 1 -> {
                welcomeWindow.mouseClicked(x, y);
            }
            case 2 -> {
                exploreWindow.mouseClicked(x, y);
            }
            case 3 -> {
                designWindow.mouseClicked(x, y);
            }
        }

        // menu buttons clicked
        if (y < limit_to_decrease) {
            for (int i = 0; i < menu_buttons.length; i++) {
                if (menu_buttons[i].ifSelected(x, y) == true) {
                    window_number = i + 1;
                }
            }
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        switch (window_number) {
            case 0 -> {

            }
            case 1 -> {
                welcomeWindow.mousePressed(x, y);
            }
            case 2 -> {
                exploreWindow.mousePressed(x, y);
            }
            case 3 -> {
                designWindow.mousePressed(x, y);
            }
        }
    }

    @Override
    public void mouseDragged(int x, int y) {
        switch (window_number) {
            case 0 -> {

            }
            case 1 -> {
                welcomeWindow.mouseDragged(x, y);
            }
            case 2 -> {
                exploreWindow.mouseDragged(x, y);
            }
            case 3 -> {
                designWindow.mouseDragged(x, y);
            }
        }
    }

    @Override
    public void mouseEntered(int x, int y) {
        switch (window_number) {
            case 0 -> {

            }
            case 1 -> {
                welcomeWindow.mouseEntered(x, y);
            }
            case 2 -> {
                exploreWindow.mouseEntered(x, y);
            }
            case 3 -> {
                designWindow.mouseEntered(x, y);
            }
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        switch (window_number) {
            case 0 -> {

            }
            case 1 -> {
                welcomeWindow.mouseReleased(x, y);
            }
            case 2 -> {
                exploreWindow.mouseReleased(x, y);
            }
            case 3 -> {
                designWindow.mouseReleased(x, y);
            }
        }
    }

    @Override
    public void mouseExited(int x, int y) {
        switch (window_number) {
            case 0 -> {

            }
            case 1 -> {
                welcomeWindow.mouseExited(x, y);
            }
            case 2 -> {
                exploreWindow.mouseExited(x, y);
            }
            case 3 -> {
                designWindow.mouseExited(x, y);
            }
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        for (int i = 0; i < menu_buttons.length; i++) {
            menu_buttons[i].ifMoved(x, y);
        }
        switch (window_number) {
            case 0 -> {

            }
            case 1 -> {
                welcomeWindow.mouseMoved(x, y);
            }
            case 2 -> {
                exploreWindow.mouseMoved(x, y);
            }
            case 3 -> {
                designWindow.mouseMoved(x, y);
            }
        }
    }

    private void drawGradientText(Graphics g, String text, int x, int y, Color startColor, Color endColor) {
        // Set font and get the FontMetrics
        Font font = new Font("Arial", Font.BOLD, 24);
        g.setFont(font);
        FontMetrics metrics = g.getFontMetrics(font);

        // Calculate the total width of the text
        int textWidth = metrics.stringWidth(text);

        // Calculate the x-coordinate to center the text horizontally
        x -= textWidth / 2;

        // Calculate the y-coordinate to center the text vertically
        y += metrics.getAscent() / 2;

        // Define the width of each gradient segment
        float segmentWidth = (float) textWidth / text.length();

        // Define the start and end hues for the gradient
        float[] hsvStart = new float[3];
        Color.RGBtoHSB(startColor.getRed(), startColor.getGreen(), startColor.getBlue(), hsvStart);
        float[] hsvEnd = new float[3];
        Color.RGBtoHSB(endColor.getRed(), endColor.getGreen(), endColor.getBlue(), hsvEnd);

        // Iterate through each character in the text
        for (int i = 0; i < text.length(); i++) {
            // Calculate the hue for the current character using the gradient
            float ratio = (float) i / (text.length() - 1);
            float hue = interpolate(hsvStart[0], hsvEnd[0], ratio);

            // Set the color for drawing the current character
            g.setColor(Color.getHSBColor(hue, 1f, 1f));

            // Draw the current character at the appropriate position
            g.drawString(String.valueOf(text.charAt(i)), x + Math.round(i * segmentWidth), y);

            // Calculate the width of the current character
            int charWidth = metrics.charWidth(text.charAt(i));

            // Add extra spacing between characters to ensure equal spacing
            x += charWidth - 15; // Adjust this value as needed for desired spacing
        }
    }

    private float interpolate(float start, float end, float ratio) {
        // Interpolate between start and end values
        return start + (end - start) * ratio;
    }
}
