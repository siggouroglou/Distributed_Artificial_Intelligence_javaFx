package gr.unipi.ergasia.model;

import gr.unipi.ergasia.service.Scenario;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class Statistic {

    private String scenarioTitle;
    private Environment environment;
    private List<AgentPlan> agentPlanList;
    private int durationSeconds;
    private List<Integer> agentStepList;
    private List<Integer> agentKnowledgeExchangeList;
    private List<Integer> agentMoveDijkstraList;
    private List<Integer> agentMoveLeastVisitedList;

    public Statistic() {
        this.scenarioTitle = null;
        this.environment = null;
        this.agentPlanList = new ArrayList<>();
        this.durationSeconds = 0;
        this.agentStepList = new ArrayList<>();
        this.agentKnowledgeExchangeList = new ArrayList<>();
        this.agentMoveDijkstraList = new ArrayList<>();
        this.agentMoveLeastVisitedList = new ArrayList<>();
    }

    public String getScenarioTitle() {
        return scenarioTitle;
    }

    public void setScenarioTitle(String scenarioTitle) {
        this.scenarioTitle = scenarioTitle;
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public List<AgentPlan> getAgentPlanList() {
        return agentPlanList;
    }

    public void setAgentPlanList(List<AgentPlan> agentPlanList) {
        this.agentPlanList = agentPlanList;
    }

    public int getDurationSeconds() {
        return durationSeconds;
    }

    public void setDurationSeconds(int durationSeconds) {
        this.durationSeconds = durationSeconds;
    }

    public List<Integer> getAgentStepList() {
        return agentStepList;
    }

    public void setAgentStepList(List<Integer> agentStepList) {
        this.agentStepList = agentStepList;
    }

    public List<Integer> getAgentKnowledgeExchangeList() {
        return agentKnowledgeExchangeList;
    }

    public void setAgentKnowledgeExchangeList(List<Integer> agentKnowledgeExchangeList) {
        this.agentKnowledgeExchangeList = agentKnowledgeExchangeList;
    }

    public List<Integer> getAgentMoveDijkstraList() {
        return agentMoveDijkstraList;
    }

    public void setAgentMoveDijkstraList(List<Integer> agentMoveDijkstraList) {
        this.agentMoveDijkstraList = agentMoveDijkstraList;
    }

    public List<Integer> getAgentMoveLeastVisitedList() {
        return agentMoveLeastVisitedList;
    }

    public void setAgentMoveLeastVisitedList(List<Integer> agentMoveLeastVisitedList) {
        this.agentMoveLeastVisitedList = agentMoveLeastVisitedList;
    }
}
