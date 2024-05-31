package GUI;

import Keypad.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.time.*;
import java.time.format.*;
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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                // Call the method to create and write to the file
                // create the file we will be writing in
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
                LocalDateTime now = LocalDateTime.now();
                String name = "src/Main/Analyzing User Feedback - " + dtf.format(now) + ".txt";
                try ( PrintWriter writer = new PrintWriter(new FileWriter(
                        createFile("src/Main/Analyzing User Feedback - " + dtf.format(now) + ".txt")))) {
                    System.out.println("");
//                    File fileWrite = new File("src/Main/Analyzing the Library.txt");
//                    FileWriter fileWriter = new FileWriter(fileWrite);

                    // prepare the file we will be writing in
                    writer.write("File Written on " + dtf.format(now));

                    writer.write("\nUser Ratings on FloorPlans: \n");
                    String[][] floorPlans = panel.getWindow().getDesignWindow().getFloorPlans();
                    int[] ratings = panel.getWindow().getDesignWindow().getUserRating();
                    double averageRating = 0;
                    int count = 0;
                    for (int i = 0; i < ratings.length; i++) {
                        String[] floorPlan = floorPlans[i];
                        for (int j = 0; j < floorPlan.length - 1; j++) {
                            writer.write(floorPlan[j] + ",");
                        }
                        writer.write(floorPlan[floorPlan.length - 1] + ": ");
                        if (ratings[i] == 0) {
                            writer.write("No rating. ");
                        } else {
                            writer.write("Rating = " + ratings[i]);
                            averageRating += ratings[i];
                            count++;
                        }
                        writer.write("\n");
                    }
                    averageRating = averageRating / count;
                    writer.write("Average Rating = " + averageRating);

//                    System.out.println("SUCCESS: File '" + fileWrite.getName() + "' has been written on successfully. ");
                } catch (FileNotFoundException ef) {
                    System.out.println("ERROR: FileNotFound error occured '" + ef.getMessage() + "'. ");
                } catch (IOException ef) {
                    System.out.println("ERROR: IOException error occured '" + ef.getMessage() + "' . ");
                } catch (Exception ef) {
                    System.out.println("ERROR: General error occured '" + ef.getMessage() + "'. ");
                } finally {
                    System.out.println("""
                                       A file has been created known as 'Analyzing User Feedback - <date>.txt'. 
                                       It contains statistics of the User Feedback just before closing. 
                                       You can view it on the path 'src/Main/Analyzing User Feedback.txt'""");
                }
            }

        });

    }

    private File createFile(String name) {
        File file = new File(name);
        try {
            if (file.createNewFile() == true) {
                System.out.println("SUCCESS: File '" + file.getName() + "' is created successfully. ");
            } else {
                System.out.println("ERROR: File '" + file.getName() + "' not created, it already exists. ");
            }
        } catch (IOException e) {
            System.out.println("ERROR: IOException error occured '" + e.getMessage() + "' . ");
        } catch (Exception e) {
            System.out.println("ERROR: General error occured '" + e.getMessage() + "' . ");
        }
        return file;

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
