package gr.unipi.ergasia.controller.file;

import gr.unipi.ergasia.lib.manager.AgentPlanManager;
import gr.unipi.ergasia.lib.manager.EnvironmentManager;
import gr.unipi.ergasia.model.AgentPlan;
import gr.unipi.ergasia.model.Environment;
import gr.unipi.ergasia.model.StadiumIncredience;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class ScenarionInitController implements Initializable {

    @FXML
    private GridPane gridPane;
    @FXML
    private TableView<Environment> tableView;
    @FXML
    private VBox containerNode;
    @FXML
    private TextField scenarioTitleTextField;
    @FXML
    private Button continueToStep2Button;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'ScenarionInitView.fxml'.";
        assert containerNode != null : "fx:id=\"containerNode\" was not injected: check your FXML file 'ScenarionInitView.fxml'.";
        assert scenarioTitleTextField != null : "fx:id=\"scenarioTitleTextField\" was not injected: check your FXML file 'ScenarionInitView.fxml'.";
        assert continueToStep2Button != null : "fx:id=\"continueToStep2Button\" was not injected: check your FXML file 'ScenarionInitView.fxml'.";

        // Initialize the table of enviroments for selection.
        initializeEnvironment();

        // Set the binding for validation. Do not continue if nothing is selected to environment table and if the title has no text.
        continueToStep2Button.disableProperty().bind(
                Bindings
                .isNull(tableView.getSelectionModel().selectedItemProperty())
                .or(scenarioTitleTextField.textProperty().isEmpty()));
    }

    private Stage getStage() {
        return (Stage) gridPane.getScene().getWindow();
    }

    private void initializeEnvironment() {
        // Set the data to the table view.
        tableView.setItems(EnvironmentManager.getInstance().getEnvironmentList());

        TableColumn<Environment, String> column1 = new TableColumn<>("Τίτλος");
        column1.setCellValueFactory(new PropertyValueFactory("title"));
        TableColumn<Environment, String> column2 = new TableColumn("Διάσταση");
        column2.setCellValueFactory(cellData -> {
            return new SimpleStringProperty(cellData.getValue().getWidth() + "x" + cellData.getValue().getHeight());
        });
        TableColumn<Environment, Integer> column3 = new TableColumn("Πλήθος Πρακτόρων");
        column3.setCellValueFactory(cellData -> {
            return new SimpleIntegerProperty(cellData.getValue().getAgentCount()).asObject();
        });
        tableView.getColumns().setAll(column1, column2, column3);

        // Set some policies for table view.
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void initializeAgentPlanList(Environment environment, List<AgentPlan> validAgentPlanList) {
        // Empty the container.
        if (containerNode.getChildren() != null && !containerNode.getChildren().isEmpty()) {
            containerNode.getChildren().clear();
        }

        // For each agent in the environment, create an HBox with a label and combobox.
        for (int i = 0; i < environment.getAgentCount(); i++) {
            // Create the hbox.
            final HBox hBox = new HBox();
            hBox.setPrefHeight(-1);
            hBox.setPrefWidth(-1);
            hBox.setAlignment(Pos.CENTER_LEFT);
            hBox.setSpacing(10);
            HBox.setHgrow(hBox, Priority.ALWAYS);

            // The first node in the container as a label.
            final Label label = new Label("Επιλογή πλάνου για πράκτορα:");
            hBox.getChildren().add(label);

            // The second node in the container is the combo box.
            final ComboBox<AgentPlan> comboBox = new ComboBox();
            comboBox.getItems().addAll(validAgentPlanList);
            comboBox.setPrefWidth(200);
            hBox.getChildren().add(comboBox);

            // Add this hbox to the view.
            containerNode.getChildren().add(hBox);
        }
    }

    @FXML
    void continueToStep2Click(ActionEvent event) {
        // Get the selected environment.
        Environment environment = tableView.getSelectionModel().getSelectedItem();

        // Get the stadium incredience that includes this environment.
        List<StadiumIncredience> environmentBuildingList = environment.getBuildings();

        // Create an agent plan list with the valid plans for this environment. Plans that contain actionσ that are not existing in this environment are droped.
        List<AgentPlan> validAgentPlanList = new LinkedList<>();
        for (AgentPlan agentPlan : AgentPlanManager.getInstance().getAgentPlanList()) {
            if (agentPlan.containsIncredience(environmentBuildingList)) {
                validAgentPlanList.add(agentPlan);
            }
        }

        // Validatin - the valid agent plans for this environemtn.
        if (validAgentPlanList.isEmpty()) {
            Dialogs.create()
                    .owner(getStage())
                    .title("Πρόβλημα")
                    .masthead(null)
                    .message("Όλα τα διαθέσιμα πλάνα περιέχουν κτίρια που δεν υπάρχουν στο περριβάλλον που επιλέξατε. Προσθέστε κτίρια στο περιβάλλον ή αφαιρέστε κτίρια από πλάνο.")
                    .showError();
            return;
        }

        // Validation - Are there any agents in this environment.
        if (environment.getAgentCount() <= 0) {
            Dialogs.create()
                    .owner(getStage())
                    .title("Πρόβλημα")
                    .masthead(null)
                    .message("Το περιβάλλον που επιλέξατε δεν περιέχει κάποιο πράκτορα.")
                    .showError();
            return;
        }

        // Initialize the agent plan selection.
        initializeAgentPlanList(environment, validAgentPlanList);

        // Timeline animation.
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        final KeyValue keyValue = new KeyValue(gridPane.translateXProperty(), -600);
        final KeyFrame keyFrame = new KeyFrame(Duration.millis(200), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    @FXML
    void backToStep1Click(ActionEvent event) {
        // Timeline animation.
        final Timeline timeline = new Timeline();
        timeline.setCycleCount(1);
        final KeyValue keyValue = new KeyValue(gridPane.translateXProperty(), 0);
        final KeyFrame keyFrame = new KeyFrame(Duration.millis(200), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    @FXML
    void cancel2Click(ActionEvent event) {
        getStage().close();
    }

    @FXML
    void cancel1Click(ActionEvent event) {
        getStage().close();
    }

    @FXML
    void completeClick(ActionEvent event) {
        // Validation - title existance.
        if (scenarioTitleTextField.getText().isEmpty()) {
            Dialogs.create()
                    .owner(getStage())
                    .title("Πρόβλημα")
                    .masthead(null)
                    .message("Δεν έχετε επιλέξει τίτλο.")
                    .showError();
            return;
        }
    }

}
