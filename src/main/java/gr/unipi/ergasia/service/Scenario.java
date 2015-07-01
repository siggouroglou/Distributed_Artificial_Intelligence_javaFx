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
    private int durationSeconds;

    private Scenario(ScenarioBuilder builder) {
        this.environment = builder.environment;
        this.durationSeconds = builder.durationSeconds;
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
