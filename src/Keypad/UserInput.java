package Keypad;

import java.util.*;

// commented
public class UserInput extends Observable {

    /*
    this is a class that extends Observable and stores a string. this class is used 
    to allow seamless communication between the application window and the keypad. so 
    when the user pressed "enter" on the keypad, the string it has written is enclosed 
    as an object of this class, passed to the application window using Observable and 
    Observer built-in-libraries, and then the string is extracted from this class. 
     */
    private String input_by_user;

    public UserInput() {
        this.input_by_user = ""; // the default input is empty string, not null
    }

    // GETTERS AND SETTERS
    public void setInput_by_user(String value) {
        /*
        this method is called when the user presses "enter" in the keypad KeypadWindow inside KDrawPanel
        so first it checks, has the value already been updated? if yes, do not re-update. if not, 
        update its value, and call the built-in-methods to pass this value to the Observer class. 
        the application window is the Observer class. the notifyObservers() calls the update() method
        in all the Observers.
        the if-condition is put as it was throwing error of StackOverflow due to excessive setValue() calls. 
         */
        if (this.input_by_user.equalsIgnoreCase(value) == false) {
            this.input_by_user = value;
            setChanged();
            notifyObservers();
        }
    }

    public String getInput_by_user() {
        /*
        regular getter method which returns the input-by-user as this field is private. 
         */
        return input_by_user;
    }

}
