package gr.unipi.ergasia.controller.edit;

import gr.unipi.ergasia.model.StadiumIncredience;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
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

/**
 * FXML Controller class
 *
 * @author siggouroglou
 */
public class EnvironmentManagementCreateController implements Initializable {

    @FXML
    private TextField titleTextField;

    @FXML
    private ScrollPane containerGrid;
    @FXML
    private TextField widthTextField;
    @FXML
    private TextField heightTextField;
    @FXML
    private Label errorLabel;
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
        assert containerGrid != null : "fx:id=\"containerGrid\" was not injected: check your FXML file 'EnvironmentManagementCreateView.fxml'.";
        assert widthTextField != null : "fx:id=\"widthTextField\" was not injected: check your FXML file 'EnvironmentManagementCreateView.fxml'.";
        assert heightTextField != null : "fx:id=\"heightTextField\" was not injected: check your FXML file 'EnvironmentManagementCreateView.fxml'.";
        assert errorLabel != null : "fx:id=\"errorLabel\" was not injected: check your FXML file 'EnvironmentManagementCreateView.fxml'.";
        assert incredienceList != null : "fx:id=\"incredienceList\" was not injected: check your FXML file 'EnvironmentManagementCreateView.fxml'.";

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

    private Stage getStage() {
        return (Stage) errorLabel.getScene().getWindow();
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

        // Remove the old Grid.
        containerGrid.setContent(null);

        // Initialize the Grid.
        GridPane gridPane = new GridPane();
        gridPane.gridLinesVisibleProperty().set(true);
//        gridPane.
        Image image = new Image(getClass().getResourceAsStream("/files/images/stadiumIncredience/" + StadiumIncredience.KENOS_XOROS.getFileName()));
        for (int row = 0; row < width; row++) {
            for (int col = 0; col < height; col++) {
                ImageView imageView = new ImageView(image);
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

    @FXML
    void cleanGridClick(ActionEvent event) {

    }

    @FXML
    void cancelClick(ActionEvent event) {
        getStage().close();
    }

    @FXML
    void saveClick(ActionEvent event) {

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

                // Get the table cell.
                if (incredience != null) {
                    Image image = new Image(getClass().getResourceAsStream("/files/images/stadiumIncredience/" + incredience.getFileName()));
                    imageView.setImage(image);
                }

                event.setDropCompleted(true);
            }
        });
    }

}
