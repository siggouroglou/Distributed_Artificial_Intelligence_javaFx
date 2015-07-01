package gr.unipi.ergasia.service;

import gr.unipi.ergasia.model.Environment;
import java.util.LinkedList;
import java.util.List;
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class Scenario extends Thread {

    private Environment environment;
    private List<Agent> agentList;
    private int durationSeconds;
    private Node containerNode;
    private Label durationSecondsLabel;
    private Label agentKnowledgeExchangelabel;
    private Label agentStepLabel;

    private Scenario(ScenarioBuilder builder) {
        this.environment = builder.environment;
        this.agentList = builder.agentList;
        this.durationSeconds = builder.durationSeconds;
        this.containerNode = builder.containerNode;
        this.durationSecondsLabel = builder.durationSecondsLabel;
        this.agentKnowledgeExchangelabel = builder.agentKnowledgeExchangelabel;
        this.agentStepLabel = builder.agentStepLabel;
    }
    
    @Override
    public void start() {
        
    }
    
    public void play() {
        
    }
    
    public void pause() {
        
    }
    
    public void stopPlaying() {
        
    }
    
    public void restart() {
        
    }

    public static class ScenarioBuilder {

        private Environment environment;
        private List<Agent> agentList;
        private int durationSeconds;
        private Node containerNode;
        private Label durationSecondsLabel;
        private Label agentKnowledgeExchangelabel;
        private Label agentStepLabel;

        public ScenarioBuilder() {
            this.environment = null;
            this.agentList = new LinkedList<>();
            this.durationSeconds = 0;
            this.containerNode = null;
            this.durationSecondsLabel = null;
            this.agentKnowledgeExchangelabel = null;
            this.agentStepLabel = null;
        }

        public ScenarioBuilder environment(Environment environment) {
            this.environment = environment;
            return this;
        }

        public ScenarioBuilder agentList(List<Agent> agentList) {
            this.agentList = agentList;
            return this;
        }

        public ScenarioBuilder durationSeconds(int durationSeconds) {
            this.durationSeconds = durationSeconds;
            return this;
        }

        public ScenarioBuilder containerNode(Node containerNode) {
            this.containerNode = containerNode;
            return this;
        }

        public ScenarioBuilder durationSecondsLabel(Label durationSecondsLabel) {
            this.durationSecondsLabel = durationSecondsLabel;
            return this;
        }

        public ScenarioBuilder agentKnowledgeExchangelabel(Label agentKnowledgeExchangelabel) {
            this.agentKnowledgeExchangelabel = agentKnowledgeExchangelabel;
            return this;
        }

        public ScenarioBuilder agentStepLabel(Label agentStepLabel) {
            this.agentStepLabel = agentStepLabel;
            return this;
        }

        public Scenario build() {
            return new Scenario(this);
        }

    }
}
