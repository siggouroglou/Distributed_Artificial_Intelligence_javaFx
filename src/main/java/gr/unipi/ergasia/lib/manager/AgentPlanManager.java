package gr.unipi.ergasia.lib.manager;

import com.google.gson.Gson;
import gr.unipi.ergasia.lib.Settings;
import gr.unipi.ergasia.model.AgentPlan;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class AgentPlanManager {

    private final static Logger logger = Logger.getLogger(AgentPlanManager.class);
    private static AgentPlanManager INSTANCE;
    private final List<AgentPlan> agentPlanList;

    private AgentPlanManager() {
        agentPlanList = new ArrayList<>();
    }

    public static AgentPlanManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new AgentPlanManager();
            importData();
        }
        return INSTANCE;
    }

    private static void importData() {
        // Get the location of the agent plans and check if it is a directory.
        File folder = new File(Settings.getInstance().getAgentPlanPath());
        if (folder.isDirectory()) {

            // Read all the files of agent plans from the selected location.
            for (final File file : folder.listFiles()) {
                try {
                    // Read json from file.
                    byte[] encoded = Files.readAllBytes(file.toPath());
                    String content = new String(encoded, StandardCharsets.UTF_8);

                    // Load json to instance.
                    Gson gson = new Gson();
                    AgentPlan agentPlan = gson.fromJson(content, AgentPlan.class);
                    if (agentPlan != null) {
                        INSTANCE.agentPlanList.add(agentPlan);
                    }
                } catch (IOException ex) {
                    logger.log(Level.ERROR, "An error occurred with loading the instance.", ex);
                }
            }
        }
    }

    public void exportData() {
    }

    public List<AgentPlan> getAgentPlanList() {
        return agentPlanList;
    }

}
