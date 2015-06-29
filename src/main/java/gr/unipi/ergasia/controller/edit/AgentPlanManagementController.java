package gr.unipi.ergasia.controller.edit;

import gr.unipi.ergasia.lib.manager.AgentPlanManager;
import gr.unipi.ergasia.model.AgentPlan;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class AgentPlanManagementController implements Initializable {

    @FXML
    private TableView tableView;
    @FXML
    private Button editRowButton;
    @FXML
    private Button deleteRowButton;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert tableView != null : "fx:id=\"tableView\" was not injected: check your FXML file 'EvironmentManagementView.fxml'.";
        assert editRowButton != null : "fx:id=\"editRowButton\" was not injected: check your FXML file 'EvironmentManagementView.fxml'.";
        assert deleteRowButton != null : "fx:id=\"deleteRowButton\" was not injected: check your FXML file 'EvironmentManagementView.fxml'.";
    }

    public void initialize() {
        // Enable or disable the edit/delete buttons.
        editRowButton.visibleProperty().bind(Bindings.isNotNull(tableView.getSelectionModel().selectedItemProperty()));
        deleteRowButton.visibleProperty().bind(Bindings.isNotNull(tableView.getSelectionModel().selectedItemProperty()));

        // Set the data to the table view.
        tableView.setItems(FXCollections.observableList(AgentPlanManager.getInstance().getAgentPlanList()));

        TableColumn<AgentPlan, String> column1 = new TableColumn<>("Τίτλος");
        column1.setCellValueFactory(new PropertyValueFactory("title"));
        TableColumn<AgentPlan, Integer> column2 = new TableColumn("Πληθος Ενεργειών");
        column2.setCellValueFactory(cellData -> {
            return new SimpleIntegerProperty(((AgentPlan) cellData.getValue()).getActionList().size()).asObject();
        });
        tableView.getColumns().setAll(column1, column2);

        // Set some policies for table view.
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    @FXML
    void cancelClick(ActionEvent event) {
        ((Stage) tableView.getScene().getWindow()).close();
    }

    @FXML
    void deleteRowClick(ActionEvent event) {

    }

    @FXML
    void editRowClick(ActionEvent event) {

    }

    @FXML
    void createRowClick(ActionEvent event) {

    }

}
