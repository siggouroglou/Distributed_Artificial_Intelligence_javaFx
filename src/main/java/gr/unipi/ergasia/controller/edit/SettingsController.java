package gr.unipi.ergasia.controller.edit;

import gr.unipi.ergasia.lib.Settings;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class SettingsController implements Initializable {

    @FXML
    private TextField environmentPathTextField;

    @FXML
    private TextField agentPlanPathTextField;

    @FXML
    private TextField statisticPathTextField;

    @FXML
    private Label errorLabel;

    @FXML
    void environmentButtonClick(ActionEvent event) {
        // Get the stage.
        Stage stage = (Stage) errorLabel.getScene().getWindow();

        // Select the folder path.
        DirectoryChooser choose = new DirectoryChooser();
        choose.setTitle("Επιλογή φακέλου για αποθήκευση των δομών για το περιβάλλον");
        File directory = choose.showDialog(stage);
        if (directory == null || !directory.isDirectory()) {
            errorLabel.setText("Ο φάκελος που επιλέξατε για αποθήκευση των δομών για το περιβάλλον, δεν είναι αποδεκτός.");
        } else {
            errorLabel.setText("");
            environmentPathTextField.setText(directory.getAbsolutePath());
        }
    }

    @FXML
    void agentPlanButtonClick(ActionEvent event) {
        // Get the stage.
        Stage stage = (Stage) errorLabel.getScene().getWindow();

        // Select the folder path.
        DirectoryChooser choose = new DirectoryChooser();
        choose.setTitle("Επιλογή φακέλου για αποθήκευση των πλάνων των πρακτόρων");
        File directory = choose.showDialog(stage);
        if (directory == null || !directory.isDirectory()) {
            errorLabel.setText("Ο φάκελος που επιλέξατε για αποθήκευση των πλάνων των πρακτόρων, δεν είναι αποδεκτός.");
        } else {
            errorLabel.setText("");
            agentPlanPathTextField.setText(directory.getAbsolutePath());
        }
    }

    @FXML
    void statisticButtonClick(ActionEvent event) {
        // Get the stage.
        Stage stage = (Stage) errorLabel.getScene().getWindow();

        // Select the folder path.
        DirectoryChooser choose = new DirectoryChooser();
        choose.setTitle("Επιλογή φακέλου για αποθήκευση των στατιστικών κάθε σεναρίου");
        File directory = choose.showDialog(stage);
        if (directory == null || !directory.isDirectory()) {
            errorLabel.setText("Ο φάκελος που επιλέξατε για αποθήκευση των στατιστικών κάθε σεναρίου, δεν είναι αποδεκτός.");
        } else {
            errorLabel.setText("");
            statisticPathTextField.setText(directory.getAbsolutePath());
        }
    }

    @FXML
    void cancelClick(ActionEvent event) {
        Stage stage = (Stage) errorLabel.getScene().getWindow();
        stage.close();
    }

    @FXML
    void saveClick(ActionEvent event) throws IOException {
        // Validate the paths.
        if (!new File(environmentPathTextField.getText()).isDirectory()) {
            errorLabel.setText("Ο φάκελος που επιλέξατε για αποθήκευση των δομών για το περιβάλλον, δεν είναι αποδεκτός.");
            return;
        }
        if (!new File(agentPlanPathTextField.getText()).isDirectory()) {
            errorLabel.setText("Ο φάκελος που επιλέξατε για αποθήκευση των πλάνων των πρακτόρων, δεν είναι αποδεκτός.");
            return;
        }
        if (!new File(statisticPathTextField.getText()).isDirectory()) {
            errorLabel.setText("Ο φάκελος που επιλέξατε για αποθήκευση των στατιστικών κάθε σεναρίου, δεν είναι αποδεκτός.");
            return;
        }

        // Save settings.
        Settings settings = Settings.getInstance();
        settings.setEnvironmentPath(environmentPathTextField.getText());
        settings.setAgentPlanPath(agentPlanPathTextField.getText());
        settings.setStatisticPath(statisticPathTextField.getText());
        try {
            settings.save();

            // Close the stage.
            Stage stage = (Stage) errorLabel.getScene().getWindow();
            stage.close();
        } catch (IOException ex) {
            errorLabel.setText(ex.getMessage());
        }
    }

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert environmentPathTextField != null : "fx:id=\"environmentPathLabel\" was not injected: check your FXML file 'SettingsView.fxml'.";
        assert agentPlanPathTextField != null : "fx:id=\"agentPlanPathLabel\" was not injected: check your FXML file 'SettingsView.fxml'.";
        assert statisticPathTextField != null : "fx:id=\"statisticPathLabel\" was not injected: check your FXML file 'SettingsView.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'SettingsView.fxml'.";

        // Load correct values to the text fields.
        Settings settings = Settings.getInstance();
        environmentPathTextField.setText(settings.getEnvironmentPath());
        agentPlanPathTextField.setText(settings.getAgentPlanPath());
        statisticPathTextField.setText(settings.getStatisticPath());

    }

}
