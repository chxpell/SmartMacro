package SmartMacro;

import javafx.collections.ObservableList;
import java.lang.InterruptedException;
import javafx.scene.control.ToggleButton;
import java.lang.Thread;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.awt.Robot;
import java.awt.AWTException;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.PointerInfo;
import java.awt.MouseInfo;


/**
 *
 * @author connorschwing
 */

public class ScriptObject {
    
    ObservableList<ActionObject> script; /* List containint all actions */
    ToggleButton startStopButton;        /* Start/stop button from FXML controller */
    ActionThread t;                      /* Script thread */
    
    /*  Constructor */
    ScriptObject(ToggleButton b) { this.startStopButton = b; }
    
    /*  Set the ObservableList to the script ObservableList from the window     */
    public void setScript(ObservableList s) { this.script = s; }
    
    /*  Create a new thread and start it    */
    public void runScript() {   t = new ActionThread(script, startStopButton);  t.start(); }
    
    /*  Send the stop request to stop the thread    */
    public void stopScript() { t.requestStop(); }
}

/*
* This thread will run the loop that iterates through the script
*/
class ActionThread extends Thread {
    private ObservableList<ActionObject> script;    // List of ActionObjects representing the script 
    private ToggleButton startStopButton;           // The start/stop button will need to be recolored based on thread status
    private  volatile boolean stop = false;         // Boolean flag used to stop the thread
    private Robot robot;                            // Robot to execute clicks/keystrokes
    
    // Two parameter constructor
    public ActionThread(ObservableList s, ToggleButton b) {
        this.script = s;
        this.startStopButton = b;  
    }
    
    /* Return stop state of the thread  */
    public boolean getStop() { return stop;}
    
    /*  Run method. Invoked on thread.start()   */
    @Override
    public void run() {
        try { robot = new Robot(); } catch (AWTException e) {e.printStackTrace();} // Create a new robot
        
        /* Initial mouse movement. Without this, the first action will always take place at -1,-1 */
        
        for(ActionObject a : script) // Iterate through each Action object in the script
        {
            if(!stop) 
                for(int i = 0; i < a.getRepeat(); i++) // Repeat the action as many times as specified in the repeat column
                    executeAction(a);    
        }

        /* If the end of the list is reached, stop the thread   */
        requestStop();

        /* Change the button color after the thread ends    */
        Platform.runLater(new Runnable() {
            @Override public void run() {
                startStopButton.setSelected(false);
            }
        });
    }
    
    private void executeAction(ActionObject a)
    {
        /*  Sleep for delay time in the current action object   */
        try{
            Thread.sleep(a.getDelay());
        } catch (InterruptedException e){}

        
        System.out.printf("----------\n%s\t%d,%d\n", "moving to: ", a.getXCoord(), a.getYCoord());
        if(a.getXCoord() != -1)
        for(int count = 0;(MouseInfo.getPointerInfo().getLocation().getX() != a.getXCoord() || 
            MouseInfo.getPointerInfo().getLocation().getY() != a.getYCoord()) &&
            count < 1000; count++) {
            if(count == 999) System.out.println("cucced");
            robot.mouseMove(a.getXCoord(), a.getYCoord());
    }

        /* Cases for each action    */
        if(a.getAction().equals("Left Click"))
        {
            
            //robot.mouseMove(a.getXCoord(), a.getYCoord());
            robot.delay(1);
            robot.mousePress(InputEvent.BUTTON1_MASK);
            robot.delay(1);
            robot.mouseRelease(InputEvent.BUTTON1_MASK);
            System.out.printf("----------\n");
        } 
        else if(a.getAction().equals("Right Click"))
        {
            System.out.println(a.getAction());
            robot.mouseMove(a.getXCoord(), a.getYCoord());
            robot.mousePress(InputEvent.BUTTON3_MASK);
            robot.mouseRelease(InputEvent.BUTTON3_MASK);
            
        }
        else if(a.getAction().equals("Double Click"))
        {
            System.out.println(a.getAction());
            robot.mouseMove(a.getXCoord(), a.getYCoord());
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            try { Thread.sleep(10); } catch (InterruptedException e) {}
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        }
        else if(a.getAction().equals("CTRL+F"))
        {
            System.out.println(a.getAction());
            robot.keyPress(KeyEvent.VK_CONTROL); 
            robot.keyPress(KeyEvent.VK_F); 
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_F);
            
        }
        else if(a.getAction().equals("CTRL+A"))
        {
            System.out.println(a.getAction());
            robot.keyPress(KeyEvent.VK_CONTROL); 
            robot.keyPress(KeyEvent.VK_A); 
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_A);
            
        }
        else if(a.getAction().equals("CTRL+C"))
        {
            System.out.println(a.getAction());
            robot.keyPress(KeyEvent.VK_CONTROL); 
            robot.keyPress(KeyEvent.VK_C); 
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_C);
            
        }
        else if(a.getAction().equals("CTRL+V"))
        {
            System.out.println(a.getAction());
            robot.keyPress(KeyEvent.VK_CONTROL); 
            robot.keyPress(KeyEvent.VK_V); 
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            
        }
        else if(a.getAction().equals("CTRL+S"))
        {
            System.out.println(a.getAction());
            robot.keyPress(KeyEvent.VK_CONTROL); 
            robot.keyPress(KeyEvent.VK_S); 
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_X);           
        }
        else if(a.getAction().equals("CTRL+X"))
        {
            System.out.println(a.getAction());
            robot.keyPress(KeyEvent.VK_CONTROL); 
            robot.keyPress(KeyEvent.VK_X); 
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_X);            
        }
        else if(a.getAction().equals("Escape"))
        {
            System.out.println(a.getAction());
            robot.keyPress(KeyEvent.VK_ESCAPE); 
            robot.keyRelease(KeyEvent.VK_ESCAPE);           
        }
        else if(a.getAction().equals("Tab"))
        {
            System.out.println(a.getAction());
            robot.keyPress(KeyEvent.VK_TAB); 
            robot.keyRelease(KeyEvent.VK_TAB);           
        }
        else if(a.getAction().equals("Enter"))
        {
            System.out.println(a.getAction());
            robot.keyPress(KeyEvent.VK_ENTER); 
            robot.keyRelease(KeyEvent.VK_ENTER);            
        }
        else if(a.getAction().equals("Backspace"))
        {
            System.out.println(a.getAction());
            robot.keyPress(KeyEvent.VK_BACK_SPACE); 
            robot.keyRelease(KeyEvent.VK_BACK_SPACE);            
        }
        else if(a.getAction().equals("Delete"))
        {
            System.out.println(a.getAction());
            robot.keyPress(KeyEvent.VK_DELETE); 
            robot.keyRelease(KeyEvent.VK_DELETE);            
        }
        else if(a.getAction().equals("Left Arrow"))
        {
            System.out.println(a.getAction());
            robot.keyPress(KeyEvent.VK_LEFT); 
            robot.keyRelease(KeyEvent.VK_LEFT);            
        }
        else if(a.getAction().equals("Right Arrow"))
        {
            System.out.println(a.getAction());
            robot.keyPress(KeyEvent.VK_RIGHT); 
            robot.keyRelease(KeyEvent.VK_RIGHT);            
        }
        else if(a.getAction().equals("Up Arrow"))
        {
            System.out.println(a.getAction());
            robot.keyPress(KeyEvent.VK_UP); 
            robot.keyRelease(KeyEvent.VK_UP);            
        }
        else if(a.getAction().equals("Down Arrow"))
        {
            System.out.println(a.getAction());
            robot.keyPress(KeyEvent.VK_DOWN); 
            robot.keyRelease(KeyEvent.VK_DOWN);            
        }
        else if(a.getAction().equals("Wait For Pixel"))
        {     
            System.out.println(a.getAction());
        }
        else if(a.getAction().equals("Type Note"))
        {
           System.out.println(a.getAction());
        }
        else if(a.getAction().equals("Read From File"))
        {
            System.out.println(a.getAction());
        }
    }
    
    /*  Stop the thread  */
    public void requestStop()
    {
        stop = true;
    }
}



