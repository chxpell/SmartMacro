/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartMacro;

/**
 *
 * @author connorschwing
 */
import javafx.scene.Cursor;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.application.Platform;

public class NativeMouseHook implements NativeMouseInputListener {
        private boolean enabled = false;
        Scene scene;
        FXMLDocumentController c;
        
        NativeMouseHook(Scene s, FXMLDocumentController controller)
        {
            this.c = controller;
            this.scene = s;
            
        }
        public void setEnabled(Boolean b)
        {
            this.enabled = b;
        }
        
        public boolean getEnabled()
        {
            return this.enabled;
        }
	public void nativeMouseClicked(NativeMouseEvent e) {
            if(enabled)
            {
                scene.setCursor(Cursor.DEFAULT); // Reset the cursor
                c.setXY(e.getX(), e.getY());     // Set the X and Y text boxes in the frame via the FXML controller
                
                /* Reset the alwaysOnTop property of the stage after the NativeHook thread expires  */
                Platform.runLater(new Runnable() {
                    @Override public void run() {
                       Stage stage = (Stage) c.scriptPane.getScene().getWindow();
                       stage.setAlwaysOnTop(false);
                    }
                });
            }

            scene.setCursor(Cursor.DEFAULT); 
            enabled = false;
	}

	public void nativeMousePressed(NativeMouseEvent e) {
            System.out.println("Just clicked at: " + e.getX() + ", " + e.getY());    
	}

	public void nativeMouseReleased(NativeMouseEvent e) {
    
	}

	public void nativeMouseMoved(NativeMouseEvent e) {
		//System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
	}

	public void nativeMouseDragged(NativeMouseEvent e) {
		//System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
	}
	
}