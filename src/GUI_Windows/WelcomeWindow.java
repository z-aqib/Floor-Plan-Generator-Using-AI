package GUI_Windows;

import GUI.*;
import Keypad.*;
import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

public class WelcomeWindow implements WindowInterface {

    private int background_counter;
    private int next_counter;
    private final ImageIcon[] background_one;
    private final ImageIcon[] logo;
    private final String[][] headings_one;
    private final String[][] description_one;
    private final Color[][] colors_one;
    private final Point[][] points_one;
    private final boolean[][] bold_one;
    private boolean reintializedButtons = false;

    private final ColoredButtons[] menu_buttons;
    private final int[] background_panel;
    private long time_zero;

    public WelcomeWindow(ColoredButtons[] menuButtons, int[] background_panel) {

        this.menu_buttons = menuButtons;
        this.background_panel = background_panel;
        this.time_zero = System.currentTimeMillis() / 1000; //start time of how long has it been since line was printed

        this.background_one = new ImageIcon[]{
            new ImageIcon("src/pictures/menu_background_4.jpg"),
            new ImageIcon("src/pictures/menu_background_7.jpg")
        };
        this.logo = new ImageIcon[]{
            new ImageIcon("src/pictures/logo.png"),
            new ImageIcon("src/pictures/logo_3.png")
        };
        this.description_one = new String[][]{
            """
            Enter the Realm of Effortless Home Transformation. 
            Explore the enigmatic world of design, seamlessly 
            integrating daily life and work. Let our platform 
            unveil the secrets of space and comfort, guiding 
            you on a mysterious journey of crafting homes that 
            captivate and inspire.
            """.split("\n"),
            {},
            {}
        };
        this.background_counter = 1;
        next_counter = computeNextBckg(background_counter);
        this.colors_one = new Color[][]{
            {new Color(0xE376F0), new Color(0xD97089)}, // pressed
            {new Color(0x25003F), new Color(0x3E1511)} // unpressed
        };
        this.headings_one = new String[][]{
            {"DESIGNING", "Just Got Better", "Explore a whole new world of evolution. "},
            {"Customize,", "Visualize,", "Realize. ", "Designing made easy", "At your fingertips"}
        };
        this.points_one = new Point[][]{
            {new Point(background_panel[0] + 150, background_panel[1] + 450)},
            {new Point(background_panel[0] + 880, background_panel[1] + 160),
                new Point(background_panel[0] + 900, background_panel[1] + 165),
                new Point(background_panel[0] + 930, background_panel[1] + 200)}
        };
        this.bold_one = new boolean[][]{
            {true, false, false},
            {true, true, true, false, false}
        };
        for (int i = 0; i < menu_buttons.length; i++) {
            menu_buttons[i].setPressedFillColor(colors_one[0][background_counter]);
            menu_buttons[i].setUnpressedFillColor(colors_one[1][background_counter]);
        }
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        // update the text: if it has been displayed for more than 1 second, change it. 
        if ((time_zero - System.currentTimeMillis() / 1000) <= -4) {
            if (this.reintializedButtons == false) {
                for (int i = 0; i < menu_buttons.length; i++) {
                    menu_buttons[i].setPressedFillColor(colors_one[0][next_counter]);
                    menu_buttons[i].setUnpressedFillColor(colors_one[1][next_counter]);
                }
                this.reintializedButtons = true;
            }
            if (background_panel[0] > -background_panel[2]) {
                background_panel[0] -= 50;
                // if that text line was the last one, start from the beginning, or move to the next one
                next_counter = computeNextBckg(background_counter);
            } else {
                background_counter = next_counter;
                next_counter = computeNextBckg(background_counter);
                time_zero = System.currentTimeMillis() / 1000; // update the start time of displaying
                background_panel[0] = background_panel[0] + background_panel[2] + 50;
                this.reintializedButtons = false;
            }
        }

        // first paint the background_one image
        g.drawImage(background_one[background_counter].getImage(), background_panel[0],
                background_panel[1], background_panel[2], background_panel[3], observer);

        // descriptions
        Color purple = new Color(0xFCC8BB); // dark purple
        g.setColor(new Color(purple.getRed(), purple.getGreen(), purple.getBlue(), 150));
        g.fillRect(background_panel[0] + 850, background_panel[1] + 100, 400, 350);

        // heading
        g.setColor(new Color(0xFCC8BB)); //peach
//        g.setColor(new Color(0x84383D)); //DARK PINK
//        g.setColor(new Color(0x2C0709)); //dark brown
//        g.setColor(new Color(0X20C82E));
//        g.setColor(colors_one[0][background_counter]);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 70));
        g.drawString(headings_one[background_counter][0],
                background_panel[0] + this.points_one[background_counter][0].x,
                background_panel[1] + this.points_one[background_counter][0].y);

        // descriptions
        for (int i = 1; i < headings_one[background_counter].length; i++) {
            if (this.bold_one[background_counter][i] == true) {
                g.setColor(new Color(0xFCC8BB)); //peach
                g.setFont(new Font(Font.SERIF, Font.BOLD, 70));
            } else {
                g.setColor(Color.white);
                g.setFont(new Font(Font.SERIF, Font.PLAIN, 70 - (10 * i)));
            }
            if (i < this.points_one[background_counter].length) {
                g.drawString(headings_one[background_counter][i],
                        background_panel[0] + this.points_one[background_counter][i].x,
                        background_panel[1] + this.points_one[background_counter][i].y + 30 + (i * 30));
            } else {
                g.drawString(headings_one[background_counter][i],
                        background_panel[0] + this.points_one[background_counter][0].x,
                        background_panel[1] + this.points_one[background_counter][points_one[background_counter].length - 1].y + 40 + (i * 30));
            }
        }

        g.setColor(Color.white);
        g.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        for (int i = 0; i < this.description_one[background_counter].length; i++) {
            g.drawString(this.description_one[background_counter][i], background_panel[0] + 860,
                    background_panel[1] + 120 + (i * 30));
        }

        // --------------- NEXT BACKGROUND ------------------------------------
        // first paint the background_one image
        g.drawImage(background_one[next_counter].getImage(), background_panel[0] + background_panel[2],
                background_panel[1], background_panel[2], background_panel[3], observer);

        // descriptions
        g.setColor(new Color(purple.getRed(), purple.getGreen(), purple.getBlue(), 150));
        g.fillRect(background_panel[0] + 850 + background_panel[2], background_panel[1] + 100, 400, 350);

        // heading
        g.setColor(new Color(0xFCC8BB)); //peach
//        g.setColor(new Color(0x84383D)); //DARK PINK
//        g.setColor(new Color(0x2C0709)); //dark brown
//        g.setColor(new Color(0X20C82E));
//        g.setColor(colors_one[0][background_counter]);
        g.setFont(new Font(Font.SERIF, Font.BOLD, 70));
        g.drawString(headings_one[next_counter][0],
                background_panel[0] + this.points_one[next_counter][0].x + background_panel[2],
                background_panel[1] + this.points_one[next_counter][0].y);

        // descriptions
        for (int i = 1; i < headings_one[next_counter].length; i++) {
            if (this.bold_one[next_counter][i] == true) {
                g.setColor(new Color(0xFCC8BB)); //peach
                g.setFont(new Font(Font.SERIF, Font.BOLD, 70));
            } else {
                g.setColor(Color.white);
                g.setFont(new Font(Font.SERIF, Font.PLAIN, 70 - (10 * i)));
            }
            if (i < this.points_one[next_counter].length) {
                g.drawString(headings_one[next_counter][i],
                        background_panel[0] + this.points_one[next_counter][i].x + background_panel[2],
                        background_panel[1] + this.points_one[next_counter][i].y + 30 + (i * 30));
            } else {
                g.drawString(headings_one[next_counter][i],
                        background_panel[0] + this.points_one[next_counter][0].x + background_panel[2],
                        background_panel[1] + this.points_one[next_counter][points_one[next_counter].length - 1].y + 40 + (i * 30));
            }
        }

        g.setColor(Color.white);
        g.setFont(new Font(Font.SERIF, Font.PLAIN, 20));
        for (int i = 0; i < this.description_one[next_counter].length; i++) {
            g.drawString(this.description_one[next_counter][i],
                    background_panel[0] + 860 + background_panel[2],
                    background_panel[1] + 120 + (i * 30));
        }
    }

    private int computeNextBckg(int counter) {
        if (counter + 1 == background_one.length) {
            return 0;
        } else {
            return counter + 1;
        }
    }

    public Color getCurrentColor() {
        if (this.reintializedButtons == true) {
            return colors_one[1][next_counter];
        } else {
            return colors_one[1][background_counter];
        }
    }

    public ImageIcon getCurrentLogo() {
        if (this.reintializedButtons == true) {
            return logo[next_counter];
        } else {
            return logo[background_counter];
        }
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
