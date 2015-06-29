package gr.unipi.ergasia.controller.edit;

import gr.unipi.ergasia.model.AgentPlan;
import gr.unipi.ergasia.model.StadiumIncredience;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
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
public class AgentPlanManagementEditController implements Initializable {

    private AgentPlan agentPlan;
    private TableView<AgentPlan> tableView;

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

    public void loadData() {
        // Set the title.
        titleTextField.setText(agentPlan.getTitle());

        // Set the actions.
        int index = 0;
        for (StadiumIncredience incredience : agentPlan.getActionList()) {
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

    public void setAgentPlan(AgentPlan agentPlan) {
        this.agentPlan = agentPlan;
    }

    public void setTableView(TableView<AgentPlan> tableView) {
        this.tableView = tableView;
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
        agentPlan.setTitle(titleTextField.getText());

        // Get the actions saved.
        agentPlan.getActionList().clear();
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
        
        // REfresh the table data. THere is a bug for auto refresing.
        ObservableList<AgentPlan> items = tableView.getItems();
        List itemList = new ArrayList<>(items);
        tableView.getItems().clear();
        tableView.getItems().addAll(itemList);

        // CLose the stage.
        tableView = null;
        getStage().close();
    }

    @FXML
    void cancelClick(ActionEvent event) {
        tableView = null;
        getStage().close();
    }
}
