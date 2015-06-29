package gr.unipi.ergasia.model;

import java.util.List;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class Statistic {

    private Environment environment;
    private List<AgentPlan> agentPlanList;
    private int durationSeconds;
    private List<Integer> agentStepList;
    private List<Integer> agentKnowledgeExchangeList;

    public Statistic() {
        this.environment = null;
        this.agentPlanList = null;
        this.durationSeconds = 0;
        this.agentStepList = null;
        this.agentKnowledgeExchangeList = null;
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
}
