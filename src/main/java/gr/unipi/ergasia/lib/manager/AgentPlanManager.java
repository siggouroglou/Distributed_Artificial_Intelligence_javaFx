package gr.unipi.ergasia.lib.manager;

import gr.unipi.ergasia.lib.Settings;
import gr.unipi.ergasia.model.AgentPlan;
import gr.unipi.ergasia.model.StadiumIncredience;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class AgentPlanManager {

    private final static Logger logger = Logger.getLogger(AgentPlanManager.class);
    private static AgentPlanManager INSTANCE;
    private final ObservableList<AgentPlan> agentPlanList;

    private AgentPlanManager() {
        agentPlanList = FXCollections.observableList(new ArrayList<>());
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
        if (!folder.isDirectory()) {
            logger.error("Not valid location to read the agent plans.");
            return;
        }

        // Read all the files of agent plans from the selected location.
        for (final File file : folder.listFiles()) {
            // Initialize the agent plan instance.
            AgentPlan agentPlan = new AgentPlan();
            agentPlan.setTitle(file.getName());

            // Read and load the plan from the file.
            try {
                for (String line : FileUtils.readLines(file, StandardCharsets.UTF_8)) {
                    StadiumIncredience incredience = StadiumIncredience.initFromVocabulary(line.trim().toUpperCase());
                    if (incredience != null) {
                        agentPlan.getActionList().add(incredience);
                    }
                }
            } catch (IOException ex) {
                logger.log(Level.ERROR, "An error occurred with loading the instance.", ex);
            }

            // In case of an agent plan that contains actions.
            if (!agentPlan.getActionList().isEmpty()) {
                INSTANCE.agentPlanList.add(agentPlan);
            }
        }
    }

    public void exportData() {
        // Validate the location.
        File folder = new File(Settings.getInstance().getAgentPlanPath());
        if (!folder.isDirectory()) {
            logger.error("Not valid location to save the agent plans.");
            return;
        }

        // Remove all the plans y removing the whole directory.
        try {
            FileUtils.deleteDirectory(folder);
            FileUtils.forceMkdir(folder);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            return;
        }

        // For each plan create a file.
        for (AgentPlan agentPlan : agentPlanList) {
            try {
                File file = new File(folder, agentPlan.getTitle());
                FileUtils.touch(file);

                // Fill this file with the actions.
                for (StadiumIncredience incredience : agentPlan.getActionList()) {
                    FileUtils.writeStringToFile(file, incredience.getVocabulary() + "\n", StandardCharsets.UTF_8, true);
                }
            } catch (IOException ex) {
                logger.error(ex.getMessage());
                return;
            }
        }
    }

    public ObservableList<AgentPlan> getAgentPlanList() {
        return agentPlanList;
    }

}
