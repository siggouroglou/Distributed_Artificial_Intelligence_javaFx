package gr.unipi.ergasia.service;

import gr.unipi.ergasia.model.Environment;
import gr.unipi.ergasia.model.ScenarioState;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class Scenario extends Thread {

    private Environment environment;
    private ObjectProperty<ScenarioState> scenarioStateProperty;
    private int durationSeconds;

    private Scenario(ScenarioBuilder builder) {
        this.environment = builder.environment;
        this.scenarioStateProperty = new SimpleObjectProperty<>(ScenarioState.NOT_READY);
        this.durationSeconds = builder.durationSeconds;
    }

    public Environment getEnvironment() {
        return environment;
    }
    
    public ObjectProperty<ScenarioState> scenarioStateProperty(){
        return scenarioStateProperty;
    }

    public ScenarioState getScenarioState() {
        return scenarioStateProperty.get();
    }

    public void setScenarioState(ScenarioState scenarioState) {
        this.scenarioStateProperty.set(scenarioState);
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
        private int durationSeconds;

        public ScenarioBuilder() {
            this.environment = null;
            this.durationSeconds = 0;
        }

        public ScenarioBuilder environment(Environment environment) {
            this.environment = environment;
            return this;
        }

        public ScenarioBuilder durationSeconds(int durationSeconds) {
            this.durationSeconds = durationSeconds;
            return this;
        }

        public Scenario build() {
            return new Scenario(this);
        }

    }
}
