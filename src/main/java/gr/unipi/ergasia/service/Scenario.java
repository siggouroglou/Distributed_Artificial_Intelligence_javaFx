package gr.unipi.ergasia.service;

import gr.unipi.ergasia.lib.manager.GameManager;
import gr.unipi.ergasia.model.Environment;
import gr.unipi.ergasia.model.ScenarioState;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import org.apache.log4j.Logger;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class Scenario extends Task<Object> {

    private final static Logger logger = Logger.getLogger(Scenario.class);
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

    public ObjectProperty<ScenarioState> scenarioStateProperty() {
        return scenarioStateProperty;
    }

    public ScenarioState getScenarioState() {
        return scenarioStateProperty.get();
    }

    public void setScenarioState(ScenarioState scenarioState) {
        this.scenarioStateProperty.set(scenarioState);
    }

    @Override
    protected Object call() throws Exception {
        while (true) {
            Thread.sleep(1000);

            if (getScenarioState() == ScenarioState.RUNNING) {
                // Check if all agent are completed.
                int agentCompleted = 0;
                for (Agent agent : GameManager.getInstance().getAgentList()) {
                    if (agent.isDone()) {
                        agentCompleted++;
                    }
                }
                if (agentCompleted == GameManager.getInstance().getAgentList().size()) {
                    scenarioCompleted();
                    break;
                }

                // Update the duration seconds.
                durationSeconds++;

                // Update the label.
                Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                        GameManager.getInstance().getDurationSecondsLabel().setText(String.format("%02d:%02d:%02d", durationSeconds / 3600, (durationSeconds % 3600) / 60, (durationSeconds % 60)));
                    }
                });
            }
        }
        return null;
    }

    private void scenarioCompleted() {
        // Create Statistics.
        // Terminate scenario.
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void play() {
        setScenarioState(ScenarioState.RUNNING);
    }

    public void pause() {
        setScenarioState(ScenarioState.PAUSED);
    }

    public void stopPlaying() {
        setScenarioState(ScenarioState.STOPPED);
    }

    public void restart() {
        setScenarioState(ScenarioState.STOPPED);

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
