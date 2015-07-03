package gr.unipi.ergasia.service;

import gr.unipi.ergasia.lib.manager.GameManager;
import gr.unipi.ergasia.lib.manager.StatisticManager;
import gr.unipi.ergasia.model.Environment;
import gr.unipi.ergasia.model.ScenarioState;
import gr.unipi.ergasia.model.Statistic;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.concurrent.Task;
import javafx.stage.Stage;
import org.apache.log4j.Logger;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class Scenario extends Task<Object> {

    private final static Logger logger = Logger.getLogger(Scenario.class);
    private final String title;
    private final Environment environment;
    private final ObjectProperty<ScenarioState> scenarioStateProperty;
    private int durationSeconds;

    private Scenario(ScenarioBuilder builder) {
        this.title = builder.title;
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
                logger.debug("Scenar - Check if all agent are completed.");
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
                logger.debug("Scenar - Update the duration seconds to " + durationSeconds + ".");
                durationSeconds++;

                // Update the label.
                Platform.runLater(() -> {
                    GameManager.getInstance().getDurationSecondsLabel().setText(String.format("%02d:%02d:%02d", durationSeconds / 3600, (durationSeconds % 3600) / 60, (durationSeconds % 60)));
                });
            }
        }
        return null;
    }

    private void scenarioCompleted() {
        // Create Statistics.
        Statistic statistic = new Statistic();
        statistic.setScenarioTitle(title);
        statistic.setEnvironment(environment);
        statistic.setDurationSeconds(durationSeconds);
        for (Agent agent : GameManager.getInstance().getAgentList()) {
            statistic.getAgentPlanList().add(agent.getAgentPlan());
            statistic.getAgentStepList().add(agent.getAgentStepCount());
            statistic.getAgentKnowledgeExchangeList().add(agent.getAgentKnowledgeExchangeCount());
            statistic.getAgentMoveDijkstraList().add(agent.getAgentMoveDijkstraCount());
            statistic.getAgentMoveLeastVisitedList().add(agent.getAgentMoveLeastVisitedCount());
        }

        // Send them to the statistic manager.
        StatisticManager.getInstance().export(statistic);

        // Terminate scenario.
        Platform.runLater(() -> {
            // Upadte the state to stopped.
            setScenarioState(ScenarioState.STOPPED);

            // Open the success info modal.
            Stage stage = (Stage) GameManager.getInstance().getGridPane().getScene().getWindow();
            Dialogs.create()
                    .owner(stage)
                    .title("Συγχαρητήρια")
                    .message("Όλοι οι πράκτορες ολοκλήρωσαν το πλάνο τους για το περιβάλλον που επιλέξατε.")
                    .showInformation();
        });
    }

    public void play() {
        setScenarioState(ScenarioState.RUNNING);
    }

    public void pause() {
        setScenarioState(ScenarioState.PAUSED);
    }

    public void stopPlaying() {
        // This state will cancel all the agent tasks that are running.
        setScenarioState(ScenarioState.STOPPED);
    }

    public void restart() {
        setScenarioState(ScenarioState.STOPPED);

        // Create a new scenario with the same characteristics of this.
        Scenario scenario = new Scenario.ScenarioBuilder()
                .title(title)
                .environment(environment)
                .build();
        scenario.setScenarioState(ScenarioState.READY);

        // Create the agents.
        int index = 0;
        List<Agent> agentList = new ArrayList<Agent>(environment.getAgentCount());
        for (Agent agent : GameManager.getInstance().getAgentList()) {
            Agent agentNew = new Agent(index, scenario, agent.getAgentPlan(), agent.getHomeLocation());
            agentList.add(agentNew);
            index++;
        }

        // Add them to the Game manager.
        final GameManager gameManager = GameManager.getInstance();
        gameManager.setScenario(scenario);
        gameManager.setAgentList(agentList);
        Platform.runLater(() -> {
            gameManager.initializeGame();
        });
    }

    public static class ScenarioBuilder {

        private String title;
        private Environment environment;
        private int durationSeconds;

        public ScenarioBuilder() {
            this.title = "";
            this.environment = null;
            this.durationSeconds = 0;
        }

        public ScenarioBuilder title(String title) {
            this.title = title;
            return this;
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
