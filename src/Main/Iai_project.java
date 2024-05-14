package Main;

import GUI.*;
import Keypad.*;

public class Iai_project {

    public static void main(String[] args) {
        // TODO code application logic here
        UserInput input = new UserInput();
        DrawFrame frame = new DrawFrame(input);
        frame.runDrawFrame();
    }
    
}
