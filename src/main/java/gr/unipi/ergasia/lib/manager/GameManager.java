package gr.unipi.ergasia.lib.manager;

import gr.unipi.ergasia.service.Agent;
import gr.unipi.ergasia.service.Scenario;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Singleton class that knows the current scenario and agent threads.
 *
 * @author siggouroglou@gmail.com
 */
public class GameManager {

    private static GameManager INSTANCE;
    private Scenario scenario;
    private List<Agent> agentList;
    private Node containerNode;
    private Label environmentTitleLabel;
    private Label environmentAgentCountLabel;
    private Label environmentDimensionsLabel;
    private Label environmentBuildingTotalLabel;
    private Label durationSecondsLabel;
    private Label agentKnowledgeExchangelabel;

    private GameManager() {
        this.scenario = null;
        this.agentList = new LinkedList<>();
    }

    public GameManager(Scenario scenario, List<Agent> agentList, Node containerNode, Label environmentTitleLabel, Label environmentAgentCountLabel, Label environmentDimensionsLabel, Label environmentBuildingTotalLabel, Label durationSecondsLabel, Label agentKnowledgeExchangelabel) {
        this.scenario = scenario;
        this.agentList = agentList;
        this.containerNode = containerNode;
        this.environmentTitleLabel = environmentTitleLabel;
        this.environmentAgentCountLabel = environmentAgentCountLabel;
        this.environmentDimensionsLabel = environmentDimensionsLabel;
        this.environmentBuildingTotalLabel = environmentBuildingTotalLabel;
        this.durationSecondsLabel = durationSecondsLabel;
        this.agentKnowledgeExchangelabel = agentKnowledgeExchangelabel;
    }

    public static GameManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }

    //<editor-fold defaultstate="collapsed" desc="Getters-Setters">
    public Scenario getScenario() {
        return scenario;
    }

    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    public List<Agent> getAgentList() {
        return agentList;
    }

    public void setAgentList(List<Agent> agentList) {
        this.agentList = agentList;
    }

    public Node getContainerNode() {
        return containerNode;
    }

    public void setContainerNode(Node containerNode) {
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
    //</editor-fold>

    public void initializeGame() {
    }
}
