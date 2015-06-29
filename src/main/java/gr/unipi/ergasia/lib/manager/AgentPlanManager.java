package gr.unipi.ergasia.lib.manager;

import gr.unipi.ergasia.model.AgentPlan;
import java.util.List;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class AgentPlanManager {

    private static AgentPlanManager INSTANCE;
    private List<AgentPlan> agentPlanList;

    private AgentPlanManager() {

    }

    public static AgentPlanManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AgentPlanManager();
        }
        return INSTANCE;
    }

    private void importData() {

    }
    
    public void exportData() {
        
    }

    public List<AgentPlan> getAgentPlanList() {
        return agentPlanList;
    }

}
