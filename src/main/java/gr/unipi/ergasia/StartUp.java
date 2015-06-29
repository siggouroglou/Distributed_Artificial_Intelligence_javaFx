package gr.unipi.ergasia;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class StartUp extends Application {

    // Local final information.
    private static final String PATH_TO_CONF = "." + File.separator + "conf";
    private static final String LOGGING_PROPERTIES_FILENAME = "logging.properties";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(StartUp.class.getName());

        // Initialize logger.
        try {
            // Set the correct path for the logging properties.
            File loggingFile = new File(PATH_TO_CONF + File.separator + LOGGING_PROPERTIES_FILENAME);
            if (loggingFile.exists() && loggingFile.isFile()) {
                PropertyConfigurator.configure(loggingFile.toString());
                logger.log(Level.INFO, "-----------------------------------------");
                logger.log(Level.INFO, "The logger initialized successfully.");
            } else {
                throw new FileNotFoundException("Log file path not found:" + LOGGING_PROPERTIES_FILENAME);
            }
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
            System.exit(1);
        }
        
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
