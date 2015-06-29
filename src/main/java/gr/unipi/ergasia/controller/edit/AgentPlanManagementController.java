package gr.unipi.ergasia.controller.edit;

import gr.unipi.ergasia.lib.manager.AgentPlanManager;
import gr.unipi.ergasia.model.AgentPlan;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class AgentPlanManagementController implements Initializable {

    @FXML
    private TableView<AgentPlan> tableView;
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
        tableView.setItems(AgentPlanManager.getInstance().getAgentPlanList());

        TableColumn<AgentPlan, String> column1 = new TableColumn<>("Τίτλος");
        column1.setCellValueFactory(new PropertyValueFactory("title"));
        TableColumn<AgentPlan, Integer> column2 = new TableColumn("Πλήθος Ενεργειών");
        column2.setCellValueFactory(cellData -> {
            return new SimpleIntegerProperty(((AgentPlan) cellData.getValue()).getActionList().size()).asObject();
        });
        tableView.getColumns().setAll(column1, column2);

        // Set some policies for table view.
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
    
    private Stage getStage() {
        return (Stage)tableView.getScene().getWindow();
    }

    @FXML
    void completeCLick(ActionEvent event) {
        // Save changes to disk.
        AgentPlanManager.getInstance().exportData();
        
        // Close the stage.
        getStage().close();
    }

    @FXML
    void createRowClick(ActionEvent event) throws IOException {
        // Stages and owners.
        Stage currentStage = getStage();
        Stage createStage = new Stage();
        createStage.initModality(Modality.WINDOW_MODAL);
        createStage.initOwner(currentStage);
        createStage.setTitle("ΚΤΝ - Εισαγωγή Πλάνου Πράκτορα");
        createStage.getIcons().add(new Image("/files/images/unipi_logo.jpg"));

        // Load the view.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit/AgentPlanManagementCreateView.fxml"));
        Parent root = (Parent) loader.load();
        createStage.setScene(new Scene(root));
        
        /// Show it.
        createStage.show();
    }

    @FXML
    void editRowClick(ActionEvent event) throws IOException {
        // Stages and owners.
        Stage currentStage = getStage();
        Stage editStage = new Stage();
        editStage.initModality(Modality.WINDOW_MODAL);
        editStage.initOwner(currentStage);
        editStage.setTitle("ΚΤΝ - Επεξεργασία Πλάνου Πράκτορα");
        editStage.getIcons().add(new Image("/files/images/unipi_logo.jpg"));

        // Load the view.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/edit/AgentPlanManagementEditView.fxml"));
        Parent root = (Parent) loader.load();
        editStage.setScene(new Scene(root));
        
        AgentPlanManagementEditController controller = (AgentPlanManagementEditController)loader.getController();        
        controller.setAgentPlan(tableView.getSelectionModel().selectedItemProperty().get());
        controller.setTableView(tableView);
        controller.loadData();
        
        /// Show it.
        editStage.show();
    }

    @FXML
    void deleteRowClick(ActionEvent event) {
        Action response = Dialogs.create()
                .owner(getStage())
                .title("Διαγραφή Πλάνου Πράκτορα")
                .masthead("Είστε σίγουροι για τη διαγραφή αυτού του πλάνου?")
                .message("Πιέστε YES αν θα θέλατε να διαγράψετε το πλάνο πράκτορα.")
                .showConfirm();

        // User click to ok button.
        if (response == Dialog.Actions.YES) {
            AgentPlan agentPlan = tableView.getSelectionModel().selectedItemProperty().get();
            tableView.getItems().remove(agentPlan);
        }
    }

}
