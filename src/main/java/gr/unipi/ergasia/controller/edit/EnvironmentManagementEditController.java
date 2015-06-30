package gr.unipi.ergasia.controller.edit;

import gr.unipi.ergasia.lib.manager.EnvironmentManager;
import gr.unipi.ergasia.model.AgentPlan;
import gr.unipi.ergasia.model.Environment;
import gr.unipi.ergasia.model.StadiumIncredience;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.apache.log4j.Logger;

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class EnvironmentManagementEditController implements Initializable {

    private final static Logger logger = Logger.getLogger(EnvironmentManagementCreateController.class);
    private Environment environment;
    private TableView<Environment> tableView;
    @FXML
    private TextField titleTextField;
    @FXML
    private TextField widthTextField;
    @FXML
    private TextField heightTextField;
    @FXML
    private Button cleanGridButton;
    @FXML
    private Label errorLabel;
    @FXML
    private ScrollPane containerGrid;
    @FXML
    private ListView incredienceList;

    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        assert titleTextField != null : "fx:id=\"titleTextField\" was not injected: check your FXML file 'EnvironmentManagementCreateView.fxml'.";
        assert widthTextField != null : "fx:id=\"widthTextField\" was not injected: check your FXML file 'EnvironmentManagementCreateView.fxml'.";
        assert heightTextField != null : "fx:id=\"heightTextField\" was not injected: check your FXML file 'EnvironmentManagementCreateView.fxml'.";
        assert cleanGridButton != null : "fx:id=\"cleanGridButton\" was not injected: check your FXML file 'EnvironmentManagementCreateView.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'EnvironmentManagementCreateView.fxml'.";
        assert containerGrid != null : "fx:id=\"containerGrid\" was not injected: check your FXML file 'EnvironmentManagementCreateView.fxml'.";
        assert incredienceList != null : "fx:id=\"incredienceList\" was not injected: check your FXML file 'EnvironmentManagementCreateView.fxml'.";

        // Bindings.
        cleanGridButton.disableProperty().bind(Bindings.isNull(containerGrid.contentProperty()));

        // Load the images to the contener incredience.
        initializeList();
    }

    private void initializeList() {
        ObservableList<String> list = FXCollections.observableArrayList();
        StadiumIncredience.getAllNodes().stream().forEach((incredience) -> {
            list.add(incredience.getVocabulary());
        });
        incredienceList.getItems().addAll(list);
        incredienceList.setCellFactory(listView -> new ListCell<String>() {
            @Override
            public void updateItem(String vocabulary, boolean empty) {
                super.updateItem(vocabulary, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    // Get the image and set the text.
                    StadiumIncredience incredience = StadiumIncredience.initFromVocabulary(vocabulary);
                    Image image = new Image(getClass().getResourceAsStream("/files/images/stadiumIncredience/" + incredience.getFileName()));
                    setText(incredience.toString());

                    // Resize and set the image.
                    ImageView imageView = new ImageView(image);
                    imageView.setFitWidth(35);
                    imageView.setPreserveRatio(true);
                    imageView.setSmooth(true);
                    imageView.setCache(true);
                    setGraphic(imageView);

                    // On drag detect. This hbox start the dragging.
                    final ListCell<String> listCell = this;
                    listCell.setOnDragDetected((MouseEvent event) -> {
                        // Allow any transfer mode.
                        Dragboard db = listCell.startDragAndDrop(TransferMode.ANY);

                        // Put a string on dragboard.
                        ClipboardContent content = new ClipboardContent();
                        content.putString(vocabulary);
                        db.setContent(content);

                        event.consume();
                    });
                    // Drag completed. This hbox drag completed.
                    listCell.setOnDragDone((DragEvent event) -> {
                        event.consume();
                    });
                }
            }
        });
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public void setTableView(TableView<Environment> tableView) {
        this.tableView = tableView;
    }

    private Stage getStage() {
        return (Stage) errorLabel.getScene().getWindow();
    }

    void loadData() {
        // Set the text fields.
        titleTextField.setText(environment.getTitle());
        widthTextField.setText(String.valueOf(environment.getWidth()));
        heightTextField.setText(String.valueOf(environment.getHeight()));

        // Initialize the Grid.
        resetGridClick(null);

        // Fill the Grid with the correct images.
        GridPane gridPane = (GridPane) containerGrid.getContent();
        for (Node node : gridPane.getChildren()) {
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;

                // Get the index of this child.
                Integer row = GridPane.getRowIndex(node);
                Integer column = GridPane.getColumnIndex(node);
                if (column >= environment.getWidth() || row >= environment.getHeight()) {
                    continue;
                }

                // For efficiency do not continue if this is an empty space.
                StadiumIncredience incredience = environment.getStadium().get(row).get(column);
                if (incredience.equals(StadiumIncredience.KENOS_XOROS)) {
                    continue;
                }

                // Update the imageView.
                imageView.setId(incredience.getVocabulary());
                Image imageKeno = new Image(getClass().getResourceAsStream("/files/images/stadiumIncredience/" + incredience.getFileName()));
                imageView.setImage(imageKeno);
            }
        }
    }

    @FXML
    void resetGridClick(ActionEvent event) {
        // Validation.
        int width = 0;
        int height = 0;
        try {
            width = Integer.parseInt(widthTextField.getText());
            height = Integer.parseInt(heightTextField.getText());
        } catch (NumberFormatException ex) {
            errorLabel.setText("Μη αποδεκτές τιμές για το πεδίο διάσταση.");
            return;
        }

        // Max and min values.
        if (width <= 0 || width > 30) {
            errorLabel.setText("Η διάσταση του πλάτους θα πρέπει να είναι μεταξύ των τιμών 1-30.");
            return;
        }
        if (height <= 0 || height > 30) {
            errorLabel.setText("Η διάσταση του ύψους θα πρέπει να είναι μεταξύ των τιμών 1-30.");
            return;
        }

        // Inform the environment object.
        environment.setWidth(width);
        environment.setHeight(height);

        // Remove the old Grid.
        containerGrid.setContent(null);

        // Initialize the Grid.
        GridPane gridPane = new GridPane();
        gridPane.gridLinesVisibleProperty().set(true);
        Image image = new Image(getClass().getResourceAsStream("/files/images/stadiumIncredience/" + StadiumIncredience.KENOS_XOROS.getFileName()));
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                ImageView imageView = new ImageView(image);
                imageView.setId(StadiumIncredience.KENOS_XOROS.getVocabulary());
                imageView.setFitWidth(40);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                imageView.setCache(true);
                // Drag utiliti.
                onDropAction(imageView);
                // Add it to grid.
                gridPane.add(imageView, col, row);
            }
        }

        // Add the grid inside the container.
        containerGrid.setContent(gridPane);
    }

    private void onDropAction(final ImageView imageView) {
        // On drag over. This hbox receives a drag that happened to another.
        imageView.setOnDragOver((DragEvent event) -> {
            // Accept it only if it is not dragged from the same node and if it has a string data.
            if (event.getGestureSource() != imageView && event.getDragboard().hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });
        // Drag enter the hbox. This hbox receives a drag that happened to another.
        imageView.setOnDragEntered((DragEvent event) -> {
            event.consume();
        });
        // Drag exit the hbox. This hbox receives a drag that happened to another.
        imageView.setOnDragExited((DragEvent event) -> {
            event.consume();
        });
        // Drag drop inside the hbox. This hbox receives a drag that happened to another.
        imageView.setOnDragDropped((DragEvent event) -> {
            // Get the source hbox.
            ListCell<String> sourceListCell = (ListCell) event.getGestureSource();
            if (sourceListCell != null) {
                // Get the incredience of this list view.
                String vocabularyWord = sourceListCell.getItem();
                StadiumIncredience incredience = StadiumIncredience.initFromVocabulary(vocabularyWord);

                // Get the table cell and update the id and the image.
                if (incredience != null) {
                    imageView.setId(incredience.getVocabulary());
                    Image image = new Image(getClass().getResourceAsStream("/files/images/stadiumIncredience/" + incredience.getFileName()));
                    imageView.setImage(image);
                }

                event.setDropCompleted(true);
            }
        });
    }

    @FXML
    void cleanGridClick(ActionEvent event) {
        // Get the node and validate.
        Node node = containerGrid.getContent();
        if (!(node instanceof GridPane)) {
            logger.warn("A clead grid requested but the content of the scroll pane was not including a grid pane.");
            return;
        }

        // Create the image.
        Image image = new Image(getClass().getResourceAsStream("/files/images/stadiumIncredience/" + StadiumIncredience.KENOS_XOROS.getFileName()));

        // Reset the grid pane contents.
        GridPane gridPane = (GridPane) node;
        for (Node child : gridPane.getChildren()) {
            // Validate the child.
            if (!(child instanceof ImageView)) {
                logger.warn("A gridPane child was not an ImageView object.");
                continue;
            }
            // Add it to grid.
            ImageView imageView = (ImageView) child;
            imageView.setImage(image);

        }
    }

    @FXML
    void cancelClick(ActionEvent event) {
        tableView = null;
        getStage().close();
    }

    @FXML
    void saveClick(ActionEvent event) {
        // Validation - Title fill.
        if (titleTextField.getText().isEmpty()) {
            errorLabel.setText("Θα πρέπει να ορίσετε τίτλο για το περιβάλλον.");
            return;
        }

        // Validation - GridPane existance.
        if (containerGrid.getContent() == null || !(containerGrid.getContent() instanceof GridPane)) {
            errorLabel.setText("Θα πρέπει να σχεδίασετε την πίστα του περιβάλλοντος.");
            return;
        }

        // Initialize an array mapping the GridPane.
        GridPane gridPane = (GridPane) containerGrid.getContent();
        StadiumIncredience[][] gridPaneArray = new StadiumIncredience[environment.getHeight()][environment.getWidth()];
        for (Node node : gridPane.getChildren()) {
            if (node instanceof ImageView) {
                // Fill the array.
                ImageView imageView = (ImageView) node;
                Integer column = GridPane.getColumnIndex(node);
                Integer row = GridPane.getRowIndex(node);
                if (column != null && row != null) {
                    gridPaneArray[row][column] = StadiumIncredience.initFromVocabulary(imageView.getId());
                }
            }
        }

        // Initialize the environment.
        // Loop throught the array by fillling the stadium of the environment. Also get the agent count.
        int agentCount = 0;
        environment.getStadium().clear();
        for (int row = 0; row < environment.getHeight(); row++) {
            List<StadiumIncredience> rowList = new ArrayList<>(environment.getWidth());
            for (int col = 0; col < environment.getWidth(); col++) {
                // Get the agent count.
                if (gridPaneArray[row][col].equals(StadiumIncredience.SPITI_AGENT)) {
                    agentCount++;
                }

                rowList.add(gridPaneArray[row][col]);
            }
            environment.getStadium().add(rowList);
        }
        environment.setTitle(titleTextField.getText());
        environment.setAgentCount(agentCount);

        // Refresh the table data. THere is a bug for auto refresing.
        ObservableList<Environment> items = tableView.getItems();
        List itemList = new ArrayList<>(items);
        tableView.getItems().clear();
        tableView.getItems().addAll(itemList);

        // Close the stage.
        tableView = null;
        getStage().close();
    }

}
