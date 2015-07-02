package gr.unipi.ergasia.controller;

import gr.unipi.ergasia.controller.edit.AgentPlanManagementController;
import gr.unipi.ergasia.controller.edit.EnvironmentManagementController;
import gr.unipi.ergasia.controller.help.AboutController;
import gr.unipi.ergasia.lib.manager.GameManager;
import gr.unipi.ergasia.service.Scenario;
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
import javafx.scene.control.MenuItem;
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
    Label environmentTitleLabel;
    @FXML
    Label environmentAgentCountLabel;
    @FXML
    Label environmentDimensionsLabel;
    @FXML
    Label environmentBuildingTotalLabel;
    @FXML
    Label durationSecondsLabel;
    @FXML
    Label agentKnowledgeExchangelabel;

    @FXML
    private ScrollPane containerNode;
    @FXML
    private Button scenarioStartButton;
    @FXML
    private Button scenarioReStartButton;
    @FXML
    private Button scenarioPauseButton;
    @FXML
    private Button scenarioStopButton;

    @FXML
    private MenuItem fileScenarioStartMenu;
    @FXML
    private MenuItem fileScenarioReStartMenu;
    @FXML
    private MenuItem fileScenarioPauseMenu;
    @FXML
    private MenuItem fileScenarioStopMenu;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert environmentTitleLabel != null : "fx:id=\"environmentTitleLabel\" was not injected: check your FXML file 'MainView.fxml'.";
        assert environmentAgentCountLabel != null : "fx:id=\"environmentAgentCountLabel\" was not injected: check your FXML file 'MainView.fxml'.";
        assert environmentDimensionsLabel != null : "fx:id=\"environmentDimensionsLabel\" was not injected: check your FXML file 'MainView.fxml'.";
        assert environmentBuildingTotalLabel != null : "fx:id=\"environmentBuildingTotalLabel\" was not injected: check your FXML file 'MainView.fxml'.";
        assert durationSecondsLabel != null : "fx:id=\"durationSecondsLabel\" was not injected: check your FXML file 'MainView.fxml'.";
        assert agentKnowledgeExchangelabel != null : "fx:id=\"agentKnowledgeExchangelabel\" was not injected: check your FXML file 'MainView.fxml'.";

        assert containerNode != null : "fx:id=\"containerNode\" was not injected: check your FXML file 'MainView.fxml'.";
        assert scenarioStartButton != null : "fx:id=\"scenarioStartButton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert scenarioReStartButton != null : "fx:id=\"scenarioReStartButton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert scenarioPauseButton != null : "fx:id=\"scenarioPauseButton\" was not injected: check your FXML file 'MainView.fxml'.";
        assert scenarioStopButton != null : "fx:id=\"scenarioStopButton\" was not injected: check your FXML file 'MainView.fxml'.";

        assert fileScenarioStartMenu != null : "fx:id=\"fileScenarioStartMenu\" was not injected: check your FXML file 'MainView.fxml'.";
        assert fileScenarioReStartMenu != null : "fx:id=\"fileScenarioReStartMenu\" was not injected: check your FXML file 'MainView.fxml'.";
        assert fileScenarioPauseMenu != null : "fx:id=\"fileScenarioPauseMenu\" was not injected: check your FXML file 'MainView.fxml'.";
        assert fileScenarioStopMenu != null : "fx:id=\"fileScenarioStopMenu\" was not injected: check your FXML file 'MainView.fxml'.";

        // Set the correct nodes to the Game Manager.
        GameManager gameManager = GameManager.getInstance();
        gameManager.setContainerNode(containerNode);
        gameManager.setEnvironmentTitleLabel(environmentTitleLabel);
        gameManager.setEnvironmentAgentCountLabel(environmentAgentCountLabel);
        gameManager.setEnvironmentDimensionsLabel(environmentDimensionsLabel);
        gameManager.setEnvironmentBuildingTotalLabel(environmentBuildingTotalLabel);
        gameManager.setDurationSecondsLabel(durationSecondsLabel);
        gameManager.setAgentKnowledgeExchangelabel(agentKnowledgeExchangelabel);

        gameManager.setScenarioStartButton(scenarioStartButton);
        gameManager.setScenarioReStartButton(scenarioReStartButton);
        gameManager.setScenarioPauseButton(scenarioPauseButton);
        gameManager.setScenarioStopButton(scenarioStopButton);
        gameManager.setFileScenarioStartMenu(fileScenarioStartMenu);
        gameManager.setFileScenarioReStartMenu(fileScenarioReStartMenu);
        gameManager.setFileScenarioPauseMenu(fileScenarioPauseMenu);
        gameManager.setFileScenarioStopMenu(fileScenarioStopMenu);

        gameManager.buttonBinding();
        
    }

    private Stage getStage() {
        return (Stage) containerNode.getScene().getWindow();
    }

    //<editor-fold defaultstate="collapsed" desc="Menu Bar">
    //<editor-fold defaultstate="collapsed" desc="File">
    @FXML
    void fileScenarioInitClick(ActionEvent event) throws IOException {
        // Stages and owners.
        Stage currentStage = getStage();
        Stage scenarioInitStage = new Stage();
        scenarioInitStage.initModality(Modality.WINDOW_MODAL);
        scenarioInitStage.initOwner(currentStage);
        scenarioInitStage.setTitle("ΚΤΝ - Διαχείριση Περιβάλλοντος");
        scenarioInitStage.getIcons().add(new Image("/files/images/unipi_logo.jpg"));
        scenarioInitStage.setMaxWidth(600);
        scenarioInitStage.setMaxHeight(430);

        // Load the view.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/file/ScenarionInitView.fxml"));
        Parent root = (Parent) loader.load();
        scenarioInitStage.setScene(new Scene(root));

        /// Show it.
        scenarioInitStage.show();
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
        EnvironmentManagementController controller = (EnvironmentManagementController) loader.getController();
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
        AgentPlanManagementController controller = (AgentPlanManagementController) loader.getController();
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
        Scenario scenario = GameManager.getInstance().getScenario();
        if (scenario != null) {
            scenario.restart();
        }
    }

    @FXML
    void scenarioStartClick(ActionEvent event) {
        Scenario scenario = GameManager.getInstance().getScenario();
        if (scenario != null) {
            scenario.play();
        }
    }

    @FXML
    void scenarioPauseClick(ActionEvent event) {
        Scenario scenario = GameManager.getInstance().getScenario();
        if (scenario != null) {
            scenario.pause();
        }
    }

    @FXML
    void scenarioStopClick(ActionEvent event) {
        Scenario scenario = GameManager.getInstance().getScenario();
        if (scenario != null) {
            scenario.stopPlaying();
        }
    }

}
