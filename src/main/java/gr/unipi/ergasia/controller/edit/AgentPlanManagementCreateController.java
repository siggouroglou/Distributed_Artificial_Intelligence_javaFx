package gr.unipi.ergasia.controller.edit;

import gr.unipi.ergasia.lib.manager.AgentPlanManager;
import gr.unipi.ergasia.model.AgentPlan;
import gr.unipi.ergasia.model.StadiumIncredience;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class AgentPlanManagementCreateController implements Initializable {

    @FXML
    private TextField titleTextField;
    @FXML
    private Label errorLabel;
    @FXML
    private VBox actionContainer;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert titleTextField != null : "fx:id=\"titleTextField\" was not injected: check your FXML file 'AgentPlanManagementCreateView.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'AgentPlanManagementCreateView.fxml'.";
        assert actionContainer != null : "fx:id=\"actionContainer\" was not injected: check your FXML file 'AgentPlanManagementCreateView.fxml'.";
    }

    private Stage getStage() {
        return (Stage) errorLabel.getScene().getWindow();
    }

    @FXML
    void addNewActionClick(ActionEvent event) {
        // The container for this specific action in this plan.
        final HBox hBox = new HBox();
        hBox.setPrefHeight(-1);
        hBox.setPrefWidth(-1);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setSpacing(10);
        HBox.setHgrow(hBox, Priority.ALWAYS);

        // The first node in the container as a label.
        final Label label = new Label("Ενέργεια:");
        hBox.getChildren().add(label);

        // The second node in the container is the combo box.
        final ComboBox<StadiumIncredience> comboBox = new ComboBox();
        comboBox.getItems().addAll(StadiumIncredience.getAllBuildings());
        hBox.getChildren().add(comboBox);

        // The third button is the remove button.
        final Button button = new Button("Διαγραφή");
        button.setOnAction((ActionEvent e) -> {
            // Remove this child from the action container.
            for (Node child : actionContainer.getChildren()) {
                if (child instanceof HBox && ((HBox) child).equals(hBox)) {
                    actionContainer.getChildren().remove(child);
                    break;
                }
            }
        });
        hBox.getChildren().add(button);

        // Add this hbox to the container.
        actionContainer.getChildren().add(hBox);
    }

    @FXML
    void saveClick(ActionEvent event) {
        // Validation.
        if (titleTextField.getText().isEmpty()) {
            errorLabel.setText("Θα πρέπει να συμπληρώσεται κάποιο τίτλο.");
            return;
        }
        if (actionContainer.getChildren().isEmpty()) {
            errorLabel.setText("Θα πρέπει να εισάγετε κάποια ενέργεια.");
            return;
        }

        // Initialize the agent plan object.
        AgentPlan agentPlan = new AgentPlan();
        agentPlan.setTitle(titleTextField.getText());

        // Get the actions saved.
        for (Node child : actionContainer.getChildren()) {
            if (!(child instanceof HBox)) {
                continue;
            }

            // Get the hbox.
            HBox hBox = (HBox) child;

            // Get the energy selected.
            for (Node node : hBox.getChildren()) {
                if (node instanceof ComboBox) {
                    ComboBox<StadiumIncredience> comboBox = (ComboBox) node;
                    StadiumIncredience stadiumIncredience = comboBox.getSelectionModel().getSelectedItem();
                    if (stadiumIncredience != null) {
                        agentPlan.getActionList().add(stadiumIncredience);
                    }
                    break;
                }
            }
        }

        // Validation again in case of null selection in the combo.
        if (agentPlan.getActionList().isEmpty()) {
            errorLabel.setText("Θα πρέπει να εισάγετε κάποια ενέργεια.");
            return;
        }

        // Save this agentPlan to the manager.
        AgentPlanManager.getInstance().getAgentPlanList().add(agentPlan);

        // CLose the stage.
        getStage().close();
    }

    @FXML
    void cancelClick(ActionEvent event) {
        getStage().close();
    }
}
