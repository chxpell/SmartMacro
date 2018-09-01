/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SmartMacro;


import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.LoadException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.stage.Modality;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.List;
import javafx.scene.control.cell.PropertyValueFactory;
import java.awt.PointerInfo;
import java.awt.Point;
import java.awt.MouseInfo;
import java.lang.reflect.InvocationTargetException;
import javafx.scene.control.TextInputControl.*;
import javafx.scene.control.TextFormatter.Change;
import java.util.function.UnaryOperator;
import java.lang.reflect.InvocationTargetException;
import javafx.beans.binding.Bindings;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.beans.value.ChangeListener;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import java.io.IOException;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;


/**
 *
 * @author connorschwing
 */
public class FXMLDocumentController implements Initializable {
    
    // Styles for side menu buttons
    String unselected = "-fx-background-color: transparent; " +
            "-fx-scale-y: 1;"+
            "-fx-scale-y: 1;"+
            "-fx-border-color: transparent ;"+
            "-fx-border-width: 0px 0px 0px 0px;";
    String selected = "-fx-background-color: #4a525f; " +
            "-fx-scale-y: 1.1;"+
            "-fx-scale-y: 1.1;"+
            "-fx-border-color: #1ed7d0 ;"+
            "-fx-border-width: 0px 0px 0px 5px;";
    
    private  ObservableList<ActionObject> script = FXCollections.observableArrayList(); // List that holds all Action Objects
    private int currentIndex; // Increments up from 0 with each new action
    ScriptObject scriptObj;
    ActionThread t;
    WebEngine webEngine;
    
    // Change operator to restrict input of text boxes 
    UnaryOperator<Change> integerFilter = change -> {
     String input = change.getText();
     if (input.matches("[0-9]*")) { 
         return change;
     }
     return null;
    };
    
    //Menu buttons
    @FXML 
    private ToggleButton scriptButton, settingsButton, helpButton;
    
    @FXML
    public ToggleButton startStopButton;
    
    @FXML
    private WebView helpWebView;
            
    //Panes for each menu screen
    @FXML
    public AnchorPane scriptPane, settingsPane, helpPane;
    
    //Toggle group for menu buttons
    @FXML
    final ToggleGroup group = new ToggleGroup();

    @FXML
    private ComboBox actionBox;
    
    @FXML
    private TextField xBox, yBox, delayBox, repeatBox;
    
    @FXML
    private TextArea noteBox;
    
    @FXML
    private TableView scriptView;
    
    @FXML
    private TableColumn numCol, actionCol, xCol, yCol, delayCol, repeatCol, noteCol;
    
    @FXML
    public Button positionButton;
    
    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object"); // Used for drag and drop of script rows
    

    @FXML
    private void settingsClick()
    {
        settingsPane.setVisible(true);
        scriptPane.setVisible(false);
        helpPane.setVisible(false);      
    }
    
    @FXML
    private void helpClick()
    {
        settingsPane.setVisible(false);
        scriptPane.setVisible(false);
        helpPane.setVisible(true);
    }
    
    @FXML
    private void scriptClick()
    {       
        settingsPane.setVisible(false);
        scriptPane.setVisible(true);
        helpPane.setVisible(false);
    }
    
    @FXML
    private void getMousePosition()
    {
        
       // xBox.setText(String.valueOf());
    }
    
    @FXML 
    private void addActionHelper()
    {
        // This function is triggered when the add button is clicked
        if(xBox.getText().isEmpty() || yBox.getText().isEmpty()) 
        {
            xBox.setText("0");
            yBox.setText("0");
        }
        addAction(Integer.parseInt(xBox.getText()), Integer.parseInt(yBox.getText()));
    }
    @FXML
    public void addAction(int x, int y)
    {
        int XCoord = 0;
        int YCoord = 0;
        
        /*
        *   If the action was added by a key press, then the key hook will have sent -1 and -1 into the addAction() function,
        *   so we set the X and Y Coordinate of this action (row) to the current mouse position).
        *   If the action was added by lcicking on the add box, then we will use the values in the x and y text boxes
        */
        if(x == -1 && y == -1)
        {
            PointerInfo pi = MouseInfo.getPointerInfo();
            Point p = pi.getLocation();
            XCoord = (int) p.getX();
            YCoord = (int) p.getY();
        }
        else
        {
            XCoord = Integer.parseInt(xBox.getText());
            YCoord = Integer.parseInt(yBox.getText());
        }
       // Point p = MouseInfo.getPointerInfo().getLocation();
        checkTextFields();

        ActionObject action = new ActionObject(
            ++currentIndex,
            actionBox.getValue().toString(),
            XCoord,
            YCoord,
            Integer.parseInt(delayBox.getText()),
            Integer.parseInt(repeatBox.getText()),
            noteBox.getText().toString()
        );       
        script.add(action);
        if(!action.getAction().equals("Left Click") && !action.getAction().equals("Right Click") && !action.getAction().equals("Double Click"))
        {
            // If the action is not a click, make the x/y coordinate cells null
            action.setXCoord(-1);
            action.setYCoord(-1);
        }
        else if(action.getAction().equals("Wait For Pixel"))
        {
            // Bring up new window
        }
        else if(action.getAction().equals("Read From File"))
        {
            // Open file stream
        }
        scriptView.getItems().add(action);
        
    }
    
    @FXML
    private void removeClick()
    {
        int index = scriptView.getSelectionModel().getSelectedIndex();
        ActionObject selectedItem = (ActionObject) scriptView.getSelectionModel().getSelectedItem();
        script.remove(selectedItem); // Remove item from the script list
       
        // Recalculate the index values in the far left column
        fixIndexes();

    }
    
    @FXML
    private void clearClick()
    {

        Alert alert = new ClearAlert();
        

        alert.showAndWait()
        .filter(response -> response == ButtonType.YES)
        .ifPresent(response -> clearScript());

        
    }
    
    private void clearScript()
    {
        /*  Empty the Table as well as the list containing ActionObjects. Then reset currentIndex   */
        xBox.setText("0");
        yBox.setText("0");
        delayBox.setText("100");
        repeatBox.setText("1");
        noteBox.setText("");
         scriptView.getItems().clear();
         script.clear();
         currentIndex = 0;
    }

    
    @FXML
    public void startStopClick()
    {
        scriptObj.setScript(script); // Set the script inside the object to the ObservableList script
        
        if(startStopButton.isSelected()) // If the button is selected, run the script
        {
            scriptObj.runScript();
        }
        else {                          // If it is deselected, stop the script
            scriptObj.stopScript();           
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {  
        currentIndex = 0;
                 scriptObj = new ScriptObject(startStopButton);
        addDragDropBehavior();  // Set drag and drop for rows in the script view
        addClickBehavior();     // Double click rows to edit their properties. Single click rows to repopulat the action box with that rows info
        setCellFactories();     // Set cell factories to make certain cells null.
        startStopButton.textProperty().bind(Bindings.when(startStopButton.selectedProperty()).then("      STOP").otherwise("     START"));
        /*
        *   Set value factories for each cell, so that ActionObjects may be added to the script view
        */
        numCol.setCellValueFactory(
            new PropertyValueFactory<ActionObject,Integer>("index")
        );
        actionCol.setCellValueFactory(
            new PropertyValueFactory<ActionObject,String>("action")
        );
        xCol.setCellValueFactory(
            new PropertyValueFactory<ActionObject,Integer>("xCoord")
        );
        yCol.setCellValueFactory(
            new PropertyValueFactory<ActionObject,Integer>("yCoord")
        );
        delayCol.setCellValueFactory(
            new PropertyValueFactory<ActionObject,Integer>("delay")
        );
        repeatCol.setCellValueFactory(
            new PropertyValueFactory<ActionObject,Integer>("repeat")
        );
        noteCol.setCellValueFactory(
            new PropertyValueFactory<ActionObject,String>("note")
        );
            

        // Restrict input to positive integers
        xBox.setTextFormatter(new TextFormatter<String>(integerFilter));
        yBox.setTextFormatter(new TextFormatter<String>(integerFilter));
        delayBox.setTextFormatter(new TextFormatter<String>(integerFilter));
        repeatBox.setTextFormatter(new TextFormatter<String>(integerFilter));
        
        // Load HTML into the help window
        webEngine = helpWebView.getEngine();
        String htmlURL = SmartMacro.class.getResource("/Resources/helpPage.html").toExternalForm();
        webEngine.load(htmlURL);

    }
    
    /*  Ensure that user input is acceptable    */
    private void checkTextFields()
    {
        if(xBox.getText().trim().isEmpty())
        xBox.setText("0");
        if(yBox.getText().trim().isEmpty())
        yBox.setText("0");
        if(repeatBox.getText().trim().isEmpty())
        repeatBox.setText("1");
        if(delayBox.getText().trim().isEmpty())
        delayBox.setText("100");
    }
    
    private void setCellFactories()
    {
        /*
        *   If the value in the X Coordinate or Y Coordinate field is less than 0, then we set that cell to null,
        *   Since any non-click action does not need coordinates
        *
        */
            xCol.setCellFactory(tc -> new TableCell<ActionObject, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setText(null);
                } else {
                    int value = item.intValue();
                    if (value < 0) {
                        setText("");
                    } else {
                        setText(Integer.toString(value));
                    }
                }
            }
            });
            
            yCol.setCellFactory(tc -> new TableCell<ActionObject, Number>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty) ;
                if (empty) {
                    setText(null);
                } else {
                    int value = item.intValue();
                    if (value < 0) {
                        setText("");
                    } else {
                        setText(Integer.toString(value));
                    }
                }
            }
            });
    }
    
    private void fixIndexes()
    {
        /*
        *   Iterate through the script and recalculate every index starting from 0
        */
        currentIndex = 0;
        for(ActionObject a : script)
           a.setIndex(++currentIndex); // Increment the index of each action

        scriptView.getItems().clear();          // Clear script                
        scriptView.getItems().addAll(script);   // Re add everything from the list
    }
    
    private void addDragDropBehavior()
    {
            scriptView.setRowFactory(tv -> {
            TableRow<ActionObject> row = new TableRow<>();

            row.setOnDragDetected(event -> {
                if (! row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });

            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer)db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });

            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    ActionObject draggedPerson = (ActionObject) scriptView.getItems().remove(draggedIndex);

                    int dropIndex ; 

                    if (row.isEmpty()) {
                        dropIndex = scriptView.getItems().size() ;
                    } else {
                        dropIndex = row.getIndex();
                    }

                    // Remove the dragged item from the observable list and add it to the drop index
                    script.remove(draggedIndex);
                    script.add(dropIndex, draggedPerson);
                    event.setDropCompleted(true); // Complete the drop
                    scriptView.getSelectionModel().select(dropIndex); // Select the item that was just dropped
                    fixIndexes(); // Recalculate action index values
                    event.consume();
                }
            });
            
            return row ;
        });       
    }
    
    private void addClickBehavior()
    {   
        scriptView.setOnMouseClicked(new EventHandler<MouseEvent>() {
        ActionObject currentRow;
        
            @Override
            public void handle(MouseEvent click) {
                currentRow = (ActionObject) scriptView.getSelectionModel().getSelectedItem(); // Store current action
                if (click.getClickCount() == 2) { // Double click a row
                   
                    try{
                        ActionObject newAction = showEditWindow(currentRow); // Create a new window allowing the user to edit this row
                        
                        /*  Disable X and Y cells in the table if the action is not a click */
                        if(!newAction.getAction().equals("Left Click") && !newAction.getAction().equals("Right Click") && !newAction.getAction().equals("Double Click")){
                            newAction.setXCoord(-1);
                            newAction.setYCoord(-1);                        
                        }
                       
                        /* Set this row in the observable list to the new action    */
                        script.set(currentRow.getIndex()-1, newAction);      

                        fixIndexes(); // Refresh the list
                    } catch (NullPointerException ex)
                    {
                        scriptView.getSelectionModel().clearSelection();
                        System.out.println("Double clicked blank row");
                    }
                }
                else if(click.getClickCount() == 1)
                {
                    /*  If a row is lcicked once, refresh the information in the action pane    */
                    try
                    {
                        xBox.setText(String.valueOf(currentRow.getXCoord()));
                        yBox.setText(String.valueOf(currentRow.getYCoord())); 
                        delayBox.setText(String.valueOf(currentRow.getDelay())); 
                        actionBox.getSelectionModel().select(currentRow.getAction());
                    } catch (NullPointerException ex)
                    {
                        System.out.println("Blank row clicked");
                    }
                }
            }
        });
                
    }
    private ActionObject showEditWindow(ActionObject a)
    {
            ActionObject result;      
            FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("EditAction.fxml"));
            
            // Initialize controller
            EditActionController editController = new EditActionController(a);
            loader.setController(editController);
            Parent layout;
            try {
                layout = loader.load();
                Scene scene = new Scene(layout);
                // this is the popup stage
                Stage popupStage = new Stage();
                popupStage.setTitle("Edit Action");
                // Giving the popup controller access to the popup stage (to allow the controller to close the stage) 
                editController.setStage(popupStage);

                popupStage.initModality(Modality.WINDOW_MODAL);
                popupStage.setScene(scene);
                popupStage.showAndWait();
            } catch (IOException e) {
                System.out.println("Blank row");
            } 
            return editController.getResult();
            
            
       
    }
    
    /*  Used by the global mouse hook to set the x and y coordinates to the click location  */
    public void setXY(int x, int y)
    {
        xBox.setText(String.valueOf(x));
        yBox.setText(String.valueOf(y));
    }

}

