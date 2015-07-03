package gr.unipi.ergasia.controller;

import gr.unipi.ergasia.model.StadiumIncredience;
import gr.unipi.ergasia.service.Agent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class GameEditAgentPlanController implements Initializable {

    private Agent agent;

    @FXML
    private Label agentNameLabel;
    @FXML
    private Label agentPlanIndexLabel;
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
        assert agentNameLabel != null : "fx:id=\"agentNameLabel\" was not injected: check your FXML file 'AgentPlanManagementCreateView.fxml'.";
        assert agentPlanIndexLabel != null : "fx:id=\"agentPlanIndexLabel\" was not injected: check your FXML file 'AgentPlanManagementCreateView.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'AgentPlanManagementCreateView.fxml'.";
        assert actionContainer != null : "fx:id=\"actionContainer\" was not injected: check your FXML file 'AgentPlanManagementCreateView.fxml'.";
    }

    public void loadData() {
        // Set the label text.
        agentNameLabel.setText("Πάρκτορας Νο" + agent.getId());
        int index = agent.getCurrentAgentPlanIndex();
        agentPlanIndexLabel.setText("Τρεχουσα ενέργεια η " + (index + 1) + "η -> " + agent.getCurrentAgentPlan().getActionList().get(index).toString());

        // Set the actions.
        index = 0;
        for (StadiumIncredience incredience : agent.getCurrentAgentPlan().getActionList()) {
            // Create the node.
            addNewActionClick(null);

            // Get this node's combo box and select the correct item.
            HBox hBox = (HBox) actionContainer.getChildren().get(index++);
            for (Node node : hBox.getChildren()) {
                if (node instanceof ComboBox) {
                    ComboBox<StadiumIncredience> comboBox = (ComboBox) node;
                    comboBox.getSelectionModel().select(incredience);
                    break;
                }
            }
        }
    }

    private Stage getStage() {
        return (Stage) errorLabel.getScene().getWindow();
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
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
        if (actionContainer.getChildren().isEmpty()) {
            errorLabel.setText("Θα πρέπει να εισάγετε κάποια ενέργεια.");
            return;
        }

        // Get the actions saved.
        List<StadiumIncredience> actionList = new ArrayList<>();
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
                        actionList.add(stadiumIncredience);
                    }
                    break;
                }
            }
        }

        // Validation again in case of null selection in the combo.
        if (actionList.isEmpty()) {
            errorLabel.setText("Θα πρέπει να εισάγετε κάποια ενέργεια.");
            return;
        }

        // Save the agent plan to the agent thread - not thread safe.
        agent.getCurrentAgentPlan().setActionList(actionList);

        // CLose the stage.
        getStage().close();
    }

    @FXML
    void cancelClick(ActionEvent event) {
        getStage().close();
    }
}
