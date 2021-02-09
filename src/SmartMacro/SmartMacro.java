/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartMacro;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import java.util.logging.*;
import javafx.scene.image.Image;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXMLLoader;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.Cursor;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.scene.control.ComboBox;


/**
 *
 * @author Connor Schwinghammer
 */
public class SmartMacro extends Application {   
    FXMLDocumentController c;
    @Override
    public void start(Stage stage) throws Exception {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLDocument.fxml"));
        Parent root = loader.load();
        c = loader.getController();
        int temp = 1;

        int my_ass = 42069;

        Scene scene = new Scene(root);
        stage.setTitle("SmartMacro v2.0");
        stage.setResizable(true);

         /* Add actions to the drop down menu    */
        ComboBox actionBox = (ComboBox) scene.lookup("#actionBox");
        actionBox.getItems().addAll("Left Click", "Right Click", "Double Click", "CTRL+F", "CTRL+C", "CTRL+V", "CTRL+A", "CTRL+S", "CTRL+X", "Escape", "Tab", "Enter", "Delete", "Backspace", "Left Arrow",
                                    "Right Arrow", "Up Arrow", "Down Arrow", "Wait For Pixel", "Type Note", "Read From File");
        
        /*   Set the default to left click  */
        actionBox.getSelectionModel().select("Left Click");
        
        /*  Set the application icon    */
        setAppIcon(stage);
        addNativeHook();
        addPositionListener(c, scene, stage); // Add functionality for the get position button
        
        /* Apply CSS and set scene  */
        scene.getStylesheets().add(getClass().getClassLoader().getResource("Resources/Style1.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
    
    /* Set application icon */
    public void setAppIcon(Stage s)
    {
        s.getIcons().add(new Image(getClass().getClassLoader().getResourceAsStream("Resources/appIcon.png")));
    }
    
    public void addNativeHook()
    {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
        }
        
        // Clear previous logging configurations
        LogManager.getLogManager().reset();

        // Disable the logger
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

         NativeKeyHook keyListener = new NativeKeyHook(c); // Create key listener
       GlobalScreen.addNativeKeyListener(keyListener);  // Add it to the screen
    }
    

    /*  Kill all threads on close   */
    @Override
    public void stop() {
        System.exit(0);
    }
    
    private void addPositionListener(FXMLDocumentController c, Scene scene, Stage s)
    {
        NativeMouseHook mouseListener = new NativeMouseHook(scene, c); // New mouse listener      
        GlobalScreen.addNativeMouseListener(mouseListener);            // Add mouse listener to the global screen
        
        /* Add listener to the crosshair button */
        c.positionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                s.setAlwaysOnTop(true);            // Window stays on top during click
                scene.setCursor(Cursor.CROSSHAIR); //Change cursor to crosshair
                mouseListener.setEnabled(true);    // Enable the listener (for one click)
            }
        });
        s.setAlwaysOnTop(false); // Even though this was already set to false in the NativEMouseHook class, it must be done again. Deleting either one will break it
    }
    
    
    public static void main(String[] args) {
        launch(args);
    }
}
