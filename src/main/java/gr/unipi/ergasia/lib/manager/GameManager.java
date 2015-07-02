package gr.unipi.ergasia.lib.manager;

import gr.unipi.ergasia.model.Environment;
import gr.unipi.ergasia.model.ScenarioState;
import gr.unipi.ergasia.model.StadiumIncredience;
import gr.unipi.ergasia.service.Agent;
import gr.unipi.ergasia.service.Scenario;
import java.util.List;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

/**
 * Singleton class that knows the current scenario and agent threads.
 *
 * @author siggouroglou@gmail.com
 */
public class GameManager {

    private static GameManager INSTANCE;
    private ObjectProperty<Scenario> scenarioProperty;
    private List<Agent> agentList;
    private ScrollPane containerNode;
    private Label environmentTitleLabel;
    private Label environmentAgentCountLabel;
    private Label environmentDimensionsLabel;
    private Label environmentBuildingTotalLabel;
    private Label durationSecondsLabel;
    private Label agentKnowledgeExchangelabel;

    private Button scenarioStartButton;
    private Button scenarioReStartButton;
    private Button scenarioPauseButton;
    private Button scenarioStopButton;
    private MenuItem fileScenarioStartMenu;
    private MenuItem fileScenarioReStartMenu;
    private MenuItem fileScenarioPauseMenu;
    private MenuItem fileScenarioStopMenu;

    public GameManager() {
        this.scenarioProperty = new SimpleObjectProperty<>(new Scenario.ScenarioBuilder().build()); // Initialize the scenario for the button Binding. NOT_READY state.
        this.agentList = null;
        this.containerNode = null;
        this.environmentTitleLabel = null;
        this.environmentAgentCountLabel = null;
        this.environmentDimensionsLabel = null;
        this.environmentBuildingTotalLabel = null;
        this.durationSecondsLabel = null;
        this.agentKnowledgeExchangelabel = null;
        this.scenarioStartButton = null;
        this.scenarioReStartButton = null;
        this.scenarioPauseButton = null;
        this.scenarioStopButton = null;
        this.fileScenarioStartMenu = null;
        this.fileScenarioReStartMenu = null;
        this.fileScenarioPauseMenu = null;
        this.fileScenarioStopMenu = null;
    }

    public static GameManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters-Setters">
    public ObjectProperty<Scenario> scenarioProperty() {
        return this.scenarioProperty;
    }

    public Scenario getScenario() {
        return scenarioProperty.get();
    }

    public void setScenario(Scenario scenario) {
        this.scenarioProperty.set(scenario);
    }

    public List<Agent> getAgentList() {
        return agentList;
    }

    public void setAgentList(List<Agent> agentList) {
        this.agentList = agentList;
    }

    public ScrollPane getContainerNode() {
        return containerNode;
    }

    public void setContainerNode(ScrollPane containerNode) {
        this.containerNode = containerNode;
    }

    public Label getEnvironmentTitleLabel() {
        return environmentTitleLabel;
    }

    public void setEnvironmentTitleLabel(Label environmentTitleLabel) {
        this.environmentTitleLabel = environmentTitleLabel;
    }

    public Label getEnvironmentAgentCountLabel() {
        return environmentAgentCountLabel;
    }

    public void setEnvironmentAgentCountLabel(Label environmentAgentCountLabel) {
        this.environmentAgentCountLabel = environmentAgentCountLabel;
    }

    public Label getEnvironmentDimensionsLabel() {
        return environmentDimensionsLabel;
    }

    public void setEnvironmentDimensionsLabel(Label environmentDimensionsLabel) {
        this.environmentDimensionsLabel = environmentDimensionsLabel;
    }

    public Label getEnvironmentBuildingTotalLabel() {
        return environmentBuildingTotalLabel;
    }

    public void setEnvironmentBuildingTotalLabel(Label environmentBuildingTotalLabel) {
        this.environmentBuildingTotalLabel = environmentBuildingTotalLabel;
    }

    public Label getDurationSecondsLabel() {
        return durationSecondsLabel;
    }

    public void setDurationSecondsLabel(Label durationSecondsLabel) {
        this.durationSecondsLabel = durationSecondsLabel;
    }

    public Label getAgentKnowledgeExchangelabel() {
        return agentKnowledgeExchangelabel;
    }

    public void setAgentKnowledgeExchangelabel(Label agentKnowledgeExchangelabel) {
        this.agentKnowledgeExchangelabel = agentKnowledgeExchangelabel;
    }

    public ObjectProperty<Scenario> getScenarioProperty() {
        return scenarioProperty;
    }

    public void setScenarioProperty(ObjectProperty<Scenario> scenarioProperty) {
        this.scenarioProperty = scenarioProperty;
    }

    public Button getScenarioStartButton() {
        return scenarioStartButton;
    }

    public void setScenarioStartButton(Button scenarioStartButton) {
        this.scenarioStartButton = scenarioStartButton;
    }

    public Button getScenarioReStartButton() {
        return scenarioReStartButton;
    }

    public void setScenarioReStartButton(Button scenarioReStartButton) {
        this.scenarioReStartButton = scenarioReStartButton;
    }

    public Button getScenarioPauseButton() {
        return scenarioPauseButton;
    }

    public void setScenarioPauseButton(Button scenarioPauseButton) {
        this.scenarioPauseButton = scenarioPauseButton;
    }

    public Button getScenarioStopButton() {
        return scenarioStopButton;
    }

    public void setScenarioStopButton(Button scenarioStopButton) {
        this.scenarioStopButton = scenarioStopButton;
    }

    public MenuItem getFileScenarioStartMenu() {
        return fileScenarioStartMenu;
    }

    public void setFileScenarioStartMenu(MenuItem fileScenarioStartMenu) {
        this.fileScenarioStartMenu = fileScenarioStartMenu;
    }

    public MenuItem getFileScenarioReStartMenu() {
        return fileScenarioReStartMenu;
    }

    public void setFileScenarioReStartMenu(MenuItem fileScenarioReStartMenu) {
        this.fileScenarioReStartMenu = fileScenarioReStartMenu;
    }

    public MenuItem getFileScenarioPauseMenu() {
        return fileScenarioPauseMenu;
    }

    public void setFileScenarioPauseMenu(MenuItem fileScenarioPauseMenu) {
        this.fileScenarioPauseMenu = fileScenarioPauseMenu;
    }

    public MenuItem getFileScenarioStopMenu() {
        return fileScenarioStopMenu;
    }

    public void setFileScenarioStopMenu(MenuItem fileScenarioStopMenu) {
        this.fileScenarioStopMenu = fileScenarioStopMenu;
    }
    //</editor-fold>
    
    public void buttonBinding() {
        // Bindings for play/pause/stop/restart buttons and menu items.
        ObjectProperty<ScenarioState> scenarioStateProperty = GameManager.getInstance().scenarioProperty().get().scenarioStateProperty();
        // All the available states as bindings. I created this objects for readability that helps the debugging.
        BooleanBinding stateNotReady = Bindings.equal(scenarioStateProperty, ScenarioState.NOT_READY);
        BooleanBinding stateReady = Bindings.equal(scenarioStateProperty, ScenarioState.READY);
        BooleanBinding stateRunning = Bindings.equal(scenarioStateProperty, ScenarioState.RUNNING);
        BooleanBinding statePaused = Bindings.equal(scenarioStateProperty, ScenarioState.PAUSED);
        BooleanBinding stateStopped = Bindings.equal(scenarioStateProperty, ScenarioState.STOPPED);
        // Unbind the deprecated bindings.
        scenarioStartButton.disableProperty().unbind();
        fileScenarioStartMenu.disableProperty().unbind();
        scenarioReStartButton.disableProperty().unbind();
        fileScenarioReStartMenu.disableProperty().unbind();
        scenarioPauseButton.disableProperty().unbind();
        fileScenarioPauseMenu.disableProperty().unbind();
        scenarioStopButton.disableProperty().unbind();
        fileScenarioStopMenu.disableProperty().unbind();
        // Bind the buttons.
        scenarioStartButton.disableProperty().bind(stateNotReady.or(stateRunning).or(stateStopped));
        fileScenarioStartMenu.disableProperty().bind(stateNotReady.or(stateRunning).or(stateStopped));
        scenarioReStartButton.disableProperty().bind(stateNotReady.or(stateReady));
        fileScenarioReStartMenu.disableProperty().bind(stateNotReady.or(stateReady));
        scenarioPauseButton.disableProperty().bind(stateNotReady.or(stateReady).or(statePaused).or(stateStopped));
        fileScenarioPauseMenu.disableProperty().bind(stateNotReady.or(stateReady).or(statePaused).or(stateStopped));
        scenarioStopButton.disableProperty().bind(stateNotReady.or(stateReady).or(stateStopped));
        fileScenarioStopMenu.disableProperty().bind(stateNotReady.or(stateReady).or(stateStopped));
    }

    public void initializeGame() {
        Environment environment = getScenario().getEnvironment();

        // Initialize the Grid.
        containerNode.setContent(null);
        GridPane gridPane = new GridPane();
        gridPane.gridLinesVisibleProperty().set(true);
        int row = 0;
        for (List<StadiumIncredience> rowList : environment.getStadium()) {
            int col = 0;
            for (StadiumIncredience incredience : rowList) {
                Image image = new Image(getClass().getResourceAsStream("/files/images/stadiumIncredience/" + incredience.getFileName()));
                ImageView imageView = new ImageView(image);
                imageView.setId(incredience.getVocabulary());
                imageView.setFitWidth(40);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                imageView.setCache(true);
                // Add it to grid.
                gridPane.add(imageView, col++, row);
            }
            row++;
        }

        // Add the grid inside the container.
        containerNode.setContent(gridPane);

        // Initialize the labels.
        environmentTitleLabel.setText(environment.getTitle());
        environmentAgentCountLabel.setText(String.valueOf(environment.getAgentCount()));
        environmentDimensionsLabel.setText(environment.getWidth() + "x" + environment.getHeight());
        environmentBuildingTotalLabel.setText(String.valueOf(environment.getBuildings().size()));
        durationSecondsLabel.setText("00:00:00");
        agentKnowledgeExchangelabel.setText("0");
        
        // Update the binding.
        buttonBinding();

        // Start the agents.
        for (Agent agent : agentList) {
            agent.start();
        }

        // Start the scenario.
        getScenario().start();
    }
}
