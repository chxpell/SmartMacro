
package SmartMacro;


import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.stage.StageStyle;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.layout.GridPane;
/**
 *
 * @author connorschwing
 * 
 * This class serves as an extension of Alert, and will be used when the clear script button is pressed
 */
public class ClearAlert extends Alert{

    ClearAlert()
    {
        super(AlertType.WARNING, "Clear the script?", ButtonType.YES, ButtonType.NO); // Call the Alert constructor
        setStyle();
    }
    
    /*
        Change the style of the default alert dialog
    */    
    private void setStyle()
    {
        this.setTitle("Are you sure?");
        this.initStyle(StageStyle.UTILITY);
        DialogPane dialogPane = this.getDialogPane();
        dialogPane.setStyle("-fx-background-color: white;");
        dialogPane.getStyleClass().remove("alert");
        GridPane grid = (GridPane)dialogPane.lookup(".header-panel"); 
        grid.setStyle("-fx-background-color: white; " + "-fx-font-size:1px;" );
        dialogPane.lookup(".content.label").setStyle("-fx-font-size: 24px; " + "-fx-fill: #f9f9f9;" + "-fx-font-family:\"Segoe UI Light\";");

        ButtonBar buttonBar = (ButtonBar)this.getDialogPane().lookup(".button-bar");
        buttonBar.setStyle("-fx-font-size: 20px;" + "-fx-background-color: white;" );
        buttonBar.getButtons().forEach(b->b.setStyle("-fx-font-family: \"Segoe UI Light\";" + "-fx-background-color:white;" + "-fx-border-color:#d4d4d4;"));

        // Set hover color on buttons
        buttonBar.getButtons().forEach(b->b.styleProperty().bind(Bindings.when(b.hoverProperty())
                                      .then("-fx-background-color: #f3f3f3")
                                      .otherwise("-fx-background-color: white")));
         
    }
}
