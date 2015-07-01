package gr.unipi.ergasia.lib.manager;

import gr.unipi.ergasia.service.Agent;
import gr.unipi.ergasia.service.Scenario;
import java.util.LinkedList;
import java.util.List;

/**
 * Singleton class that knows the current scenario and agent threads.
 *
 * @author siggouroglou@gmail.com
 */
public class GameManager {

    private static GameManager INSTANCE;
    private Scenario scenario;
    private List<Agent> agentList;

    private GameManager() {
        this.scenario = null;
        this.agentList = new LinkedList<>();
    }

    public static GameManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameManager();
        }
        return INSTANCE;
    }

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
}
