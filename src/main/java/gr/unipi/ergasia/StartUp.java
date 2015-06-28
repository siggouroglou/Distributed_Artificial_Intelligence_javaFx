package gr.unipi.ergasia;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class StartUp extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        primaryStage.setTitle("ΠΑΝΕΠΙΣΤΗΜΙΟ ΠΕΙΡΑΙΑ - ΤΜΗΜΑ ΠΛΗΡΟΦΟΡΙΚΗΣ - ΚΤΝ");
        primaryStage.getIcons().add(new Image("/files/images/unipi_logo.jpg"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/MainView.fxml"));
        
        Parent root = (Parent)loader.load();
        primaryStage.setScene(new Scene(root));
        
        primaryStage.show();
    }


}
