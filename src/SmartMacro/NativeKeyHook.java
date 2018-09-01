/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartMacro;


import javafx.scene.Cursor;
import javafx.scene.Scene;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import javafx.application.Platform;

/**
 *
 * @author connorschwing
 */
public class NativeKeyHook implements NativeKeyListener{
    
    FXMLDocumentController c;
    
    NativeKeyHook(FXMLDocumentController controller)
    {
        this.c = controller;
    }
    
    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
       //System.out.print(NativeKeyEvent.getKeyText(e.getKeyCode()) + " + ");
  
        if(e.getKeyText(e.getKeyCode()) == "F6")
        {
            c.addAction(-1,-1);
        }
        if(e.getKeyText(e.getKeyCode()) == "F7")
        {
            /*
                When the global key is pressed to start the script, we must fire the start stop button in a new thread
            */
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    c.startStopButton.fire(); 
                }
            });
         }
        
    }
     @Override
     public void nativeKeyReleased(NativeKeyEvent e) {
         /*
        try{
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {}*/
	}
     @Override
     public void nativeKeyTyped(NativeKeyEvent e) {
		
	}

}
