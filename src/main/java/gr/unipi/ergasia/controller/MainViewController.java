package gr.unipi.ergasia.controller;

import gr.unipi.ergasia.controller.edit.AgentPlanManagementController;
import gr.unipi.ergasia.controller.edit.EnvironmentManagementController;
import gr.unipi.ergasia.controller.help.AboutController;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class MainViewController implements Initializable {

    @FXML
    private Label environmentDimensionsLabel;

    @FXML
    private Label environmentTitleLabel;

    @FXML
    private Button scenarioStartButton;

    @FXML
    private Button scenarioReStartButton;

    @FXML
    private ScrollPane containerNode;

    @FXML
    private Label environmentAgentCountLabel;

    @FXML
    private Label environmentBuildingTotalLabel;

    @FXML
    private Button scenarioPauseButton;

    @FXML
    private Button scenarioStopButton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert environmentDimensionsLabel != null : "fx:id=\"environmentDimensionsLabel\" was not injected: check your FXML file 'MainView.fxml'.";
        assert environmentTitleLabel != null : "fx:id=\"environmentTitleLabel\" was not injected: check your FXML file 'MainView.fxml'.";
        assert scenarioStartButton != null : "fx:id=\"scenarioStartButton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert scenarioReStartButton != null : "fx:id=\"scenarioReStartButton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert containerNode != null : "fx:id=\"containerNode\" was not injected: check your FXML file 'MainView.fxml'.";
        assert environmentAgentCountLabel != null : "fx:id=\"environmentAgentCountLabel\" was not injected: check your FXML file 'MainView.fxml'.";
        assert environmentBuildingTotalLabel != null : "fx:id=\"environmentBuildingTotalLabel\" was not injected: check your FXML file 'MainView.fxml'.";
        assert scenarioPauseButton != null : "fx:id=\"scenarioPauseButton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert scenarioStopButton != null : "fx:id=\"scenarioStopButton\" was not injected: check your FXML file 'MainView.fxml'.";

    }
    
    private Stage getStage() {
        return (Stage) containerNode.getScene().getWindow();
    }

    //<editor-fold defaultstate="collapsed" desc="Menu Bar">
    //<editor-fold defaultstate="collapsed" desc="File">
    @FXML
    void fileScenarioInitClick(ActionEvent event) {

    }

    @FXML
    void fileScenarioStartClick(ActionEvent event) {
        scenarioStartClick(null);
    }

    @FXML
    void fileScenarioRestartClick(ActionEvent event) {
        scenarioReStartClick(null);
    }

    @FXML
    void fileScenarioPauseClick(ActionEvent event) {
        scenarioPauseClick(null);
    }

    @FXML
    void fileScenarioStopClick(ActionEvent event) {
        scenarioStopClick(null);
    }

    @FXML
    void fileQuitClick(ActionEvent event) {
        getStage().close();

    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Edit">
    @FXML
    void editEnvironmentManagementClick(ActionEvent event) throws IOException {
        // Stages and owners.
        Stage currentStage = getStage();
        Stage agentPlanManagementStage = new Stage();
        agentPlanManagementStage.initModality(Modality.WINDOW_MODAL);
        agentPlanManagementStage.initOwner(currentStage);
        agentPlanManagementStage.setTitle("ΚΤΝ - Διαχείριση Περιβάλλοντος");
        agentPlanManagementStage.getIcons().add(new Image("/files/images/unipi_logo.jpg"));

        // Load the view.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit/EnvironmentManagementView.fxml"));
        Parent root = (Parent) loader.load();
        agentPlanManagementStage.setScene(new Scene(root));
        
        // Initialize the controller.
        EnvironmentManagementController controller = (EnvironmentManagementController)loader.getController();
        controller.initialize();
        
        /// Show it.
        agentPlanManagementStage.show();
    }

    @FXML
    void editAgentPlanManagementClick(ActionEvent event) throws IOException {
        // Stages and owners.
        Stage currentStage = getStage();
        Stage agentPlanManagementStage = new Stage();
        agentPlanManagementStage.initModality(Modality.WINDOW_MODAL);
        agentPlanManagementStage.initOwner(currentStage);
        agentPlanManagementStage.setTitle("ΚΤΝ - Διαχείριση Πλάνων Πρακτόρων");
        agentPlanManagementStage.getIcons().add(new Image("/files/images/unipi_logo.jpg"));

        // Load the view.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit/AgentPlanManagementView.fxml"));
        Parent root = (Parent) loader.load();
        agentPlanManagementStage.setScene(new Scene(root));
        
        // Initialize the controller.
        AgentPlanManagementController controller = (AgentPlanManagementController)loader.getController();
        controller.initialize();
        
        /// Show it.
        agentPlanManagementStage.show();
    }

    @FXML
    void editSettingsClick(ActionEvent event) throws IOException {
        // Stages and owners.
        Stage currentStage = getStage();
        Stage settingsStage = new Stage();
        settingsStage.initModality(Modality.WINDOW_MODAL);
        settingsStage.initOwner(currentStage);
        settingsStage.setTitle("Ρυθμίσεις");
        settingsStage.getIcons().add(new Image("/files/images/unipi_logo.jpg"));

        // Load the view.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit/SettingsView.fxml"));
        Parent root = (Parent) loader.load();
        settingsStage.setScene(new Scene(root));

        /// Show it.
        settingsStage.show();
    }
    //</editor-fold>

    @FXML
    void helpAboutClick(ActionEvent event) throws IOException {
        Stage aboutStage = new Stage();
        aboutStage.initModality(Modality.WINDOW_MODAL);
        aboutStage.initStyle(StageStyle.UNDECORATED);
        aboutStage.initOwner(getStage());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/help/About.fxml"));
        Parent root = (Parent) loader.load();
        aboutStage.setScene(new Scene(root));

        AboutController controller = (AboutController) loader.getController();
        controller.setStage(aboutStage);

        aboutStage.show();
    }

    //</editor-fold>
    
    @FXML
    void scenarioReStartClick(ActionEvent event) {

    }

    @FXML
    void scenarioStartClick(ActionEvent event) {

    }

    @FXML
    void scenarioPauseClick(ActionEvent event) {

    }

    @FXML
    void scenarioStopClick(ActionEvent event) {

    }

}
