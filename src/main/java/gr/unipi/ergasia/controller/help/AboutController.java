package gr.unipi.ergasia.controller.help;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class AboutController implements Initializable {
    private Stage stage;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    // Handler for VBox[VBox[id=null]] onDragDetected
    public void contentCLick(MouseEvent event) {
        stage.close();
    }
    
    public void setStage(Stage stage){
        this.stage = stage;
    }
}
