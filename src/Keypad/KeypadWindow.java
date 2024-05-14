package Keypad;

import GUI.*;
import java.awt.*;
import java.awt.image.*;

//commented
public class KeypadWindow implements WindowInterface {
    
    // numbered buttons
    private final ColoredButtons[][] numbered_buttons;
    private final String[] numbers_text;

    // other buttons - backspace and enter
    private final ColoredButtons backspace_button;
    private final ColoredButtons enter_button;

    // handling the current input of user
    private final ColoredButtons user_input_button;
    private final UserInput input;

    // tooltip to be displayed of the button being hovered
    private ColoredButtons toolTip_button;

    public KeypadWindow(int[] panel, UserInput input) {
        // assign values of given in the constructor
        this.input = input;
        
        // configure default values such as size of buttons
        int label_height = 70;
        int height_of_button = ((panel[3] - 65 - label_height) / 4) - 5; // 65 for mouse border
        int side_buttons_width = 140; // for the enter and backspace_button button
        int width_of_button = ((panel[2] - side_buttons_width - 10) / 3) - 5;        
        Color pressed_fill = Color.gray;
        Color unpressed_fill = Color.lightGray;
        Color pressed_text = Color.white;
        Color unpressed_text = Color.black;
        this.numbers_text = new String[]{"7", "8", "9", "4", "5", "6", "1", "2", "3", "0"};

        // intialization of user input button
        this.user_input_button = new ColoredButtons("0", panel[0], panel[1], panel[2] - 10,
                label_height - 5, pressed_text, pressed_text,
                true, unpressed_text, unpressed_text);
        this.user_input_button.changeFontSize(10);
        this.user_input_button.setPrintleft(true);

        // intialization of numbered buttons
        numbered_buttons = new ColoredButtons[4][3];
        for (int i = 0, numbers_counter = 0; i < numbered_buttons.length; i++) {
            for (int j = 0; j < 3 && numbers_counter < numbers_text.length; j++, numbers_counter++) {
                // configured values
                /*
                the default button size is same as width_of_button. but if its the last button, 
                i.e. '0' then it will be a long button equal to three times the normal width hence adjust accoridngly. 
                */
                int width = width_of_button;
                if (numbers_counter == 9) {
                    width = (width_of_button * 3) + 10;
                }
                // intialization
                numbered_buttons[i][j] = new ColoredButtons(numbers_text[numbers_counter], panel[0] + ((width_of_button + 5) * j),
                        panel[1] + label_height + ((height_of_button + 5) * (i)),
                        width, height_of_button, unpressed_fill,
                        pressed_fill, true, pressed_text, unpressed_text);
                numbered_buttons[i][j].changeFontSize(5);
            }
        }

        // intialization of side buttons: backspace_button and enter_button
        this.backspace_button = new ColoredButtons("BackSpace", panel[2] - 147,
                panel[1] + label_height, side_buttons_width, height_of_button, pressed_fill,
                Color.darkGray, true, pressed_text, pressed_text);
        this.enter_button = new ColoredButtons("Enter", panel[2] - 147,
                panel[1] + label_height + height_of_button + 5, side_buttons_width,
                (height_of_button * 3) + 10, Color.yellow,
                Color.orange, true, unpressed_text, unpressed_text);

    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {
        // paint from top-to bottom
        
        // first paint the user input button
        this.user_input_button.paint(g);
        
        // then paint each numbered button
        for (int i = 0; i < numbered_buttons.length; i++) {
            for (int j = 0; j < numbered_buttons[i].length; j++) {
                if (numbered_buttons[i][j] != null) {
                    numbered_buttons[i][j].paint(g);
                }
            }
        }
        
        // then paint the side buttons
        this.backspace_button.paint(g);
        this.enter_button.paint(g);
        
        // then paint the tooltip button if any has been hovered. 
        if (this.toolTip_button != null) {
            this.toolTip_button.paintToolTip(g);
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        // first check each numbered button, has it been pressed? if yes, add its value to user_input
        for (int i = 0; i < numbered_buttons.length; i++) {
            for (int j = 0; j < numbered_buttons[i].length; j++) {
                if (numbered_buttons[i][j] != null && numbered_buttons[i][j].ifSelected(x, y) == true) {
                    user_input_button.setName(user_input_button.getName() + numbered_buttons[i][j].getName());
                }
            }
        }
        
        // if the backspace button is selected, decrement the user_input text by one character
        if (this.backspace_button.ifSelected(x, y) == true) {
            String txt = user_input_button.getName();
            if (txt.length() != 0) {
                user_input_button.setName(txt.substring(0, txt.length() - 1));
            }
        }
        
        // if the enter button is pressed, update the UserInput input and clear the input box. 
        if (this.enter_button.ifSelected(x, y) == true) {
            input.setInput_by_user(user_input_button.getName());
            user_input_button.setName("0");
            input.notifyObservers();
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
        //if any of the buttons except the user-input one has been hovered on, make them the tooltip button. 
        for (int i = 0; i < numbered_buttons.length; i++) {
            for (int j = 0; j < numbered_buttons[i].length; j++) {
                if (numbered_buttons[i][j] != null && numbered_buttons[i][j].ifMoved(x, y) == true) {
                    this.toolTip_button = numbered_buttons[i][j];
                }
            }
        }
        if (this.enter_button.ifMoved(x, y) == true) {
            this.toolTip_button = this.enter_button;
        }
        if (this.backspace_button.ifMoved(x, y) == true) {
            this.toolTip_button = this.backspace_button;
        }
    }

}
