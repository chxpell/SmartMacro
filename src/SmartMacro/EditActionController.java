/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartMacro;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
/**
 *
 * @author connorschwing
 */
public class EditActionController implements Initializable{
    
    @FXML private TextField xField, yField, delayField, repeatField;
    @FXML private TextArea noteField;
    @FXML private Button sendButton, cancelButton;
    @FXML private ComboBox actionBox;
    private Stage stage = null;
    private int index = 0;
    private int currIndex, currX,currY,currDelay,currRepeat;
    private String currAction, currNote;
    
    ActionObject newObject;
    ActionObject currentObject; // Holds the current state of the row
    
     UnaryOperator<TextFormatter.Change> integerFilter = change -> {
     String input = change.getText();
     if (input.matches("[0-9]*")) { 
         return change;
     }
     return null;
    };
    EditActionController(ActionObject s) {     
        currentObject = s;  
    }

    public ActionObject getResult() {
        return newObject;
    }

    /**
     * setting the stage of this view
     * @param stage
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Closes the stage of this view
     */
    private void closeStage() {
        if(stage!=null) {
            stage.close();
        }
    }
    
    @Override
     public void initialize(URL url, ResourceBundle rb) {
        xField.setTextFormatter(new TextFormatter<String>(integerFilter));
        yField.setTextFormatter(new TextFormatter<String>(integerFilter));
        delayField.setTextFormatter(new TextFormatter<String>(integerFilter));
        repeatField.setTextFormatter(new TextFormatter<String>(integerFilter));
         
        actionBox.getItems().addAll("Left Click", "Right Click", "Double Click", "CTRL+F", "CTRL+C", "CTRL+V", "CTRL+A", "CTRL+S", "CTRL+X", "Escape", "Tab", "Enter", "Delete", "Backspace", "Left Arrow",
                    "Right Arrow", "Up Arrow", "Down Arrow", "Wait For Pixel", "Type Note", "Read From File");

         currIndex = currentObject.getIndex();
         currAction = currentObject.getAction();
         currX = currentObject.getXCoord();
         currY = currentObject.getYCoord();
         currDelay = currentObject.getDelay();
         currRepeat = currentObject.getRepeat();
         currNote = currentObject.getNote();

         if(currX == -1) {
             xField.setText("");
             yField.setText("");
         }
         else {
             xField.setText(String.valueOf(currX));
             yField.setText(String.valueOf(currY));
         }
         delayField.setText(String.valueOf(currDelay));
         repeatField.setText(String.valueOf(currRepeat));
         noteField.setText(String.valueOf(currNote));
         actionBox.getSelectionModel().select(currAction);

            
        if(sendButton != null)
            sendButton.setOnAction((event)->{
                if(xField.getText().trim().isEmpty()) xField.setText("0");
                if(yField.getText().trim().isEmpty()) yField.setText("0");
                newObject = new ActionObject(index, actionBox.getValue().toString(), Integer.parseInt(xField.getText()), Integer.parseInt(yField.getText()), Integer.parseInt(delayField.getText()), Integer.parseInt(repeatField.getText()), noteField.getText());
                closeStage();
            });
        
        if(cancelButton != null)
            cancelButton.setOnAction((event)->{
                closeStage();
            });

     }
}


