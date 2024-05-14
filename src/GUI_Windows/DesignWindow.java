package GUI_Windows;

import GUI.*;
import Keypad.*;
import Rooms.*;
import java.awt.*;
import java.awt.image.*;
import org.python.core.*;
import org.python.util.*;

public class DesignWindow implements WindowInterface {

    private final int[] panel;
    private final FileReading rooms;
    private final ColoredButtons[] roomButtons;
    private final Point[] points;
    private final String[] strings;
    private final boolean[] print;
    private int room_counter;
    private final ColoredButtons[] userButtons;
    private Integer totalArea = null;
    private int totalRooms = 0;
    private int totalRoomArea = 0;
    private int totalAreaLeft = 0;
    private final int[][] userInputs;
    private String[][] result;
    private FloorPlanDisplayer floorPlan;
    private boolean printRooms = true;

    // user input using keypad
    private KDrawFrame keypad = null;
    private final UserInput user_returned_input;

    public DesignWindow(int[] panel, UserInput input) {
        this.rooms = new FileReading();
        this.panel = panel;
        this.user_returned_input = input;
        this.roomButtons = new ColoredButtons[rooms.getCountOfRooms()];
        int width = 250;
        int x = panel[0] + panel[2] - width - 25;
        int y = panel[1] + 10;
        Color pressed_filled = Color.yellow;
        Color unpressed_filled = Color.white;
        Color text = Color.black;

        for (int i = 0; i < roomButtons.length; i++) {
            if (rooms.getAllRooms()[i] != null) {
                roomButtons[i] = new ColoredButtons(rooms.getAllRooms()[i].getName(),
                        x, y + (i * 40), width, 30,
                        unpressed_filled, pressed_filled,
                        true, text, text);
            }
        }
        this.room_counter = 0;
//        roomButtons[room_counter].setUnpressedFillColor(Color.yellow);
        points = new Point[]{
            new Point(panel[0] + 50, panel[1] + 100), // room option message
            new Point(panel[2] / 2 - 170, panel[1] + 100), // quantity message
            new Point(panel[2] / 2 - 170, panel[1] + 100), // size message
            new Point(panel[2] / 3 - 50, ((panel[3] / 8) * 5) - 50), // error message
            new Point(panel[0] + 50, panel[1] + 100), // total area message
            new Point(panel[2] / 2 - 170, panel[1] + 100), // welcome message
            new Point(panel[0] + 150, panel[1] + 200), // welcome message
            new Point(panel[2] / 2 - 250, panel[1] + 300), // welcome message
            new Point(panel[0] + 50, panel[3]), // total area number
            new Point(panel[0] + 250, panel[3]), // total rooms count
            new Point(panel[0] + 150, panel[1] + 200), // please enter on keypad message
            new Point(panel[2] / 2 - 170, panel[1] + 100), // generating...
            new Point(panel[0] + 450, panel[3]), // total room area
            new Point(panel[0] + 700, panel[3]) // total area left
        };
        strings = new String[]{
            "Would you like a ", // room option message
            "How many? ", // quantity message
            "Of what size?", // size message
            "", // error message
            "What is the total area of your floor?", // total area message
            "Welcome! ",
            "Do you want to begin generating ",
            "your floorplan?", // welcome message
            "Total Area = ",
            "Total Rooms = ",
            "(please enter on keypad)",
            "Generating...",
            "Total Room Area = ",
            "Area Left = "
        };
        print = new boolean[]{
            false, // room option message --------- 0
            false, // quantity message ------------ 1
            false, // size message ---------------- 2
            false, // error message --------------- 3
            false, // total area message ---------- 4
            true, // welcome msg ------------------ 5
            true, // welcome msg ------------------ 6
            true, // welcome msg ------------------ 7
            false, // total area number ----------- 8
            false, // total room count ------------ 9
            false, // please enter on keypad msg -- 10
            false, // generating... --------------- 11
            false, // totsl room area ------------- 12
            false // total area left -------------- 13
        };
        userButtons = new ColoredButtons[]{
            // yes
            new ColoredButtons("Yes!!!!", (panel[2] / 2) - 150, (panel[3] / 4) * 3, 300,
            50, new Color(0x1E8069), new Color(0x29C4BC),
            true, Color.white, Color.white),
            // no
            new ColoredButtons("No", (panel[2] / 2), (panel[3] / 4) * 3, 100,
            50, Color.red, new Color(0xE86426),
            true, Color.white, Color.white),
            // enter
            new ColoredButtons("Enter", (panel[2] / 2) - 50, (panel[3] / 4) * 3, 100,
            50, Color.DARK_GRAY, Color.GRAY,
            true, Color.white, Color.white),
            // user input
            new ColoredButtons("0", (panel[2] / 2) - 150, ((panel[3] / 4) * 3) - 100, 300,
            50, Color.white, Color.white,
            true, Color.black, Color.black),
            // generate
            new ColoredButtons("Generate!!     ", panel[2] - 320, panel[3] - 30, 300,
            50, Color.PINK, Color.magenta,
            true, Color.white, Color.white)
        };
        userButtons[4].changeFontSize(15);
        userButtons[3].changeFontSize(15);
        this.userInputs = new int[roomButtons.length][2];
        for (int i = 0; i < userInputs.length; i++) {
            userInputs[i] = new int[]{0, 0};
        }
    }

    public void updateString(String s) {
        /*
        this method updates our user_returned_input with the string s.
        this method is called when user presses enter on the keypad
         */
        this.user_returned_input.setInput_by_user(s);
        userButtons[3].setName(s);
    }

    private void callPython() {
        String[] requiredRooms = new String[(totalRooms * 1) + 1];
        requiredRooms[0] = totalArea + "";
        for (int i = 0, rooms_count = 1; i < userInputs.length; i++) {
            if (userInputs[i][0] != 0) {
                Room room = rooms.getAllRooms()[i];
                for (int j = 0; j < userInputs[i][0]; j++) {
                    requiredRooms[rooms_count++] = room.getName() + ", " + room.getAreaOfIndex(userInputs[i][1]);
                }
            }
        }
        try {
            System.out.println("running start python");
            PythonInterpreter interpretor = new PythonInterpreter();
            interpretor.exec("from final import main");
            interpretor.set("rd", requiredRooms);
            interpretor.exec("result = main(rd)");
            PyObject pyObject = interpretor.get("result");
            System.out.println("running end python");
            result = convertor(pyObject);
            if (result != null) {
                System.out.println("floor plan started");
                this.floorPlan = new FloorPlanDisplayer(panel, result, requiredRooms,
                        totalArea, rooms);
            } else {
                print[3] = true;
                strings[3] = "ERROR: Total area of selected rooms exceeds total area. ";
            }
        } catch (PyException f) {
            // Handle ImportError exception
            System.err.println("Error executing Python code: " + f.getMessage());
        }
    }

//    private static String[] convertor(PyObject pyObject) {
//        if (pyObject instanceof PyTuple tuple) {
//            int size = tuple.__len__();
//            String[] resultArray = new String[size];
//            for (int i = 0; i < size; i++) {
//                PyObject element = tuple.__getitem__(i);
//                resultArray[i] = element.toString();
//            }
//            return resultArray;
//        } else {
//            // Handle case where the PyObject is not a PyTuple
//            return null; // or throw an exception, depending on your requirements
//        }
//    }
    private String[][] convertor(PyObject pyObject) {
        if (pyObject instanceof PyList list) {
            int outerSize = list.__len__();
            String[][] resultArray = new String[outerSize][];
            for (int i = 0; i < outerSize; i++) {
                PyObject innerListObject = list.__getitem__(i);
                if (innerListObject instanceof PyList innerList) {
                    int innerSize = innerList.__len__();
                    String[] innerArray = new String[innerSize];

                    for (int j = 0; j < innerSize; j++) {
                        innerArray[j] = innerList.__getitem__(j).toString();
                    }
                    resultArray[i] = innerArray;
                }
            }
            return resultArray;
        } else {
            // Handle case where the inner object is not a list
            return null; // or throw an exception, depending on your requirements
        }
    }

    @Override
    public void paint(Graphics g, ImageObserver observer) {

        if (this.floorPlan != null) {
            this.floorPlan.paint(g, observer);
        }

        // if the keypad has been closed, make sure to make it null to re-open it again when needed. 
        if (keypad != null && (keypad.isShowing() == false || keypad.isAlwaysOnTop() == false)) {
            keypad = null;
        }
        // if the welcome buttons are not being painted, paint the room list
        if (print[4] == false && print[5] == false && print[11] == false && printRooms == true) {
            for (int i = 0; i < roomButtons.length; i++) {
                roomButtons[i].paint(g);
            }
        }

        for (int i = 0; i < print.length; i++) {
            if (print[i] == true) {
                if (i != 3 && i != 4 && i != 5 && i != 6 && i != 7 && i != 11) {
                    userButtons[4].paint(g); // generate button
                }
                g.setColor(Color.BLACK);
                g.setFont(new Font(Font.SERIF, Font.BOLD, 70));
                if (i == 3) {
                    g.setColor(Color.red);
                }
                if (i == 3 || i == 8 || i == 9 || i == 12 || i == 13) {
                    g.setFont(new Font(Font.SERIF, Font.BOLD, 20));
                }
                g.drawString(strings[i], points[i].x, points[i].y);

                switch (i) {
                    // room option message
                    case 0 -> {
                        g.setColor(new Color(0x005C29)); // dark green
                        String[] room_string = (rooms.getAllRooms()[room_counter].getName() + "?").split(" ");
                        for (int j = 0; j < room_string.length; j += 2) {
                            if (j + 1 != room_string.length) {
                                g.drawString(room_string[j] + " " + room_string[j + 1],
                                        (panel[2] / 2) - ((room_string[j].length() / 2) * 80), points[i].y + 100 + (j * 40));
                            } else {
                                g.drawString(room_string[j] + " ", (panel[2] / 2) - ((room_string[j].length() / 2) * 50),
                                        points[i].y + 100 + (j * 40));
                            }
                        }
                        g.setFont(new Font(Font.SERIF, Font.PLAIN, 50));
                        g.drawString("Size 1: " + rooms.getAllRooms()[room_counter].getLengthOfIndex(1)
                                + " by " + rooms.getAllRooms()[room_counter].getWidthOfIndex(1), 50, 350);
                        g.drawString("Size 2: " + rooms.getAllRooms()[room_counter].getLengthOfIndex(1)
                                + " by " + rooms.getAllRooms()[room_counter].getWidthOfIndex(2), 50, 400);
                        g.drawString("Size 3: " + rooms.getAllRooms()[room_counter].getLengthOfIndex(1)
                                + " by " + rooms.getAllRooms()[room_counter].getWidthOfIndex(3), 50, 450);
                        userButtons[0].paint(g);
                        userButtons[1].paint(g);
                    }
                    // quantity message
                    case 1 -> {
                        userButtons[2].paint(g); // enter
                        userButtons[3].paint(g); // user input on keypad
                    }
                    // size message
                    case 2 -> {
                        g.setFont(new Font(Font.SERIF, Font.PLAIN, 50));
                        g.drawString("Size 1: " + rooms.getAllRooms()[room_counter].getLengthOfIndex(1)
                                + " by " + rooms.getAllRooms()[room_counter].getWidthOfIndex(1), 500, 200);
                        g.drawString("Size 2: " + rooms.getAllRooms()[room_counter].getLengthOfIndex(1)
                                + " by " + rooms.getAllRooms()[room_counter].getWidthOfIndex(2), 500, 250);
                        g.drawString("Size 3: " + rooms.getAllRooms()[room_counter].getLengthOfIndex(1)
                                + " by " + rooms.getAllRooms()[room_counter].getWidthOfIndex(3), 500, 300);
                        userButtons[2].paint(g); // enter
                        userButtons[3].paint(g); // user input on keypad
                    }
                    // welcome message
                    case 5 -> {
                        userButtons[0].paint(g); // yes
                    }
                    // total area message
                    case 4 -> {
                        userButtons[2].paint(g); //enter
                        userButtons[3].paint(g); // user input
                    }
                    // total rooms message
                    case 9 -> {
                        g.drawString(totalRooms + "", points[i].x + 125, points[i].y);
                    }
                    // total rooms area message
                    case 12 -> {
                        g.drawString(totalRoomArea + "", points[i].x + 165, points[i].y);
                    }
                    // total area left message
                    case 13 -> {
                        g.drawString(totalAreaLeft + "", points[i].x + 100, points[i].y);
                    }
                    // generating message
                    case 11 -> {
                        callPython();
                        print[11] = false;
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (this.floorPlan != null) {
            this.floorPlan.mouseClicked(x, y);
        }
        if (print[4] == false && print[5] == false && print[11] == false) {
            if (x >= roomButtons[0].getX()
                    && x <= roomButtons[0].getX() + roomButtons[0].getWidth()
                    && y >= roomButtons[0].getY()
                    && y <= roomButtons[0].getY() + (roomButtons[0].getHeight() * (roomButtons.length + 3))) {
                for (int i = 0; i < roomButtons.length; i++) {
                    if (roomButtons[i].ifSelected(x, y) == true) {
                        print[0] = true;
                        print[1] = false;
                        print[2] = false;
                        print[3] = false;
                        print[10] = false;
                        room_counter = i;
                    }
                }
            }
            if (userButtons[4].ifSelected(x, y) == true) {
                print[11] = true;
                printRooms = false;
                for (int i = 0; i < 11; i++) {
                    print[i] = false;
                }
                print[12] = false;
                print[13] = false;
            }
        }
        if (print[0] == true) {
            if (userButtons[0].ifSelected(x, y) == true) {
                print[0] = false;
                print[1] = true;
                // so when a room comes, and u press yes, remove all of its previous instances and make it one
                if (userInputs[room_counter][0] != 0) {
                    totalRooms -= userInputs[room_counter][0];
                    totalRoomArea -= (rooms.getAllRooms()[room_counter].getAreaOfIndex(userInputs[room_counter][1]))
                            * userInputs[room_counter][0];
                    totalAreaLeft = totalArea - totalRoomArea;
                }
                userInputs[room_counter][0] = 1;
                userInputs[room_counter][1] = 1;
                totalRooms += userInputs[room_counter][0];
                totalRoomArea += (rooms.getAllRooms()[room_counter].getAreaOfIndex(userInputs[room_counter][1]))
                        * userInputs[room_counter][0];
                totalAreaLeft = totalArea - totalRoomArea;
                print[10] = true;
                if (keypad == null) {
                    keypad = new KDrawFrame(user_returned_input);
                    keypad.runDrawFrame();
                }
            } else if (userButtons[1].ifSelected(x, y) == true) {
                room_counter++;
                if (room_counter < roomButtons.length) {
                    roomButtons[room_counter].make(true);
                    roomButtons[room_counter - 1].make(false);
                } else {
                    room_counter--;
                }
            }
        } else if (print[1] == true) {
            if (userButtons[2].ifSelected(x, y) == true) {
                int quantity = Integer.parseInt(userButtons[3].getName());
                if (quantity > 0) {
                    print[1] = false;
                    print[10] = false;
                    print[2] = true;
                    // first remove jitne thay
                    totalRooms -= userInputs[room_counter][0];
                    totalRoomArea -= (rooms.getAllRooms()[room_counter].getAreaOfIndex(userInputs[room_counter][1]))
                            * userInputs[room_counter][0];
                    totalAreaLeft = totalArea - totalRoomArea;
                    // add jitne aane thay
                    userInputs[room_counter][0] = quantity;
                    totalRooms += userInputs[room_counter][0];
                    totalRoomArea += (rooms.getAllRooms()[room_counter].getAreaOfIndex(userInputs[room_counter][1]))
                            * userInputs[room_counter][0];
                    totalAreaLeft = totalArea - totalRoomArea;
                    userButtons[3].setName("0");
                    print[3] = false; // error band karo
                    strings[3] = "";  // error message null kardo
                } else {
                    // display error message
                    print[3] = true;
                    strings[3] = "ERROR: Please enter either 1, 2 or 3. ";
                    if (keypad == null) {
                        keypad = new KDrawFrame(user_returned_input);
                        keypad.runDrawFrame();
                    }
                }
            }
            if (keypad == null) {
                keypad = new KDrawFrame(user_returned_input);
                keypad.runDrawFrame();
            }
        } else if (print[2] == true) {
            if (userButtons[2].ifSelected(x, y) == true) {
                Integer size = Integer.parseInt(userButtons[3].getName());
                if (size == 1 || size == 2 || size == 3) {
                    // first remove jitne thay
                    totalRoomArea -= (rooms.getAllRooms()[room_counter].getAreaOfIndex(userInputs[room_counter][1]))
                            * userInputs[room_counter][0];
                    totalAreaLeft = totalArea - totalRoomArea;
                    // add jitne aane thay
                    userInputs[room_counter][1] = size;
                    totalRoomArea += (rooms.getAllRooms()[room_counter].getAreaOfIndex(userInputs[room_counter][1]))
                            * userInputs[room_counter][0];
                    totalAreaLeft = totalArea - totalRoomArea;
                    userButtons[3].setName("0");
                    room_counter++;
                    if (room_counter < roomButtons.length) {
                        roomButtons[room_counter].make(true);
                        roomButtons[room_counter - 1].make(false);
                    } else {
                        room_counter--;
                    }
                    print[2] = false; // size band karo
                    print[0] = true; // next room option
                    print[3] = false; // error band karo
                    strings[3] = "";  // error message null kardo
                } else {
                    // display error message
                    print[3] = true;
                    strings[3] = "ERROR: Please enter either 1, 2 or 3. ";
                    if (keypad == null) {
                        keypad = new KDrawFrame(user_returned_input);
                        keypad.runDrawFrame();
                    }
                }
            } else if (keypad == null) {
                keypad = new KDrawFrame(user_returned_input);
                keypad.runDrawFrame();
            }
        } else if (print[5] == true) { // welcome message is being displayed
            if (userButtons[0].ifSelected(x, y) == true) { // if yes is selected
                print[5] = false;
                print[6] = false;
                print[7] = false;
                print[4] = true;
                userButtons[0].setWidth(100);
                userButtons[0].setName("Yes");
                if (keypad == null) {
                    keypad = new KDrawFrame(user_returned_input);
                    keypad.runDrawFrame();
                }
            }
        } else if (print[4] == true) {
            if (userButtons[2].ifSelected(x, y) == true) {
                Integer area = Integer.parseInt(userButtons[3].getName());
                if (area > 100) {
                    totalArea = area;
                    totalAreaLeft = totalArea;
                    userButtons[3].setName("0");
                    print[4] = false;
                    print[0] = true;
                    roomButtons[room_counter].make(true);
                    print[3] = false; // error band karo
                    strings[3] = "";  // error message null kardo
                    print[8] = true;
                    strings[8] = strings[8] + totalArea;
                    print[9] = true;
                    print[12] = true;
                    print[13] = true;
                } else {
                    // display error message
                    print[3] = true;
                    strings[3] = "ERROR: Minimum room area is 100! ";
                    if (keypad == null) {
                        keypad = new KDrawFrame(user_returned_input);
                        keypad.runDrawFrame();
                    }
                }
            } else if (keypad == null) {
                keypad = new KDrawFrame(user_returned_input);
                keypad.runDrawFrame();
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
        if (this.floorPlan != null) {
            this.floorPlan.mouseMoved(x, y);
        }
        if (print[0] == true || print[5] == true) {
            userButtons[0].ifMoved(x, y);
            userButtons[1].ifMoved(x, y);
        } else if (print[1] == true || print[2] == true || print[4] == true) {
            userButtons[2].ifMoved(x, y);
        }
        if (print[4] == false && print[5] == false && print[11] == false) {
            for (int i = 0; i < roomButtons.length; i++) {
                roomButtons[i].ifMoved(x, y);
            }
            userButtons[4].ifMoved(x, y);
        }
    }

}
