package gr.unipi.ergasia.lib.manager;

import gr.unipi.ergasia.lib.Settings;
import gr.unipi.ergasia.model.Environment;
import gr.unipi.ergasia.model.Statistic;
import gr.unipi.ergasia.service.Scenario;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class StatisticManager {

    private final static Logger logger = Logger.getLogger(StatisticManager.class);
    private static StatisticManager INSTANCE;

    private StatisticManager() {

    }

    public static StatisticManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StatisticManager();
        }
        return INSTANCE;
    }

    public void export(Statistic statistic) {
        // Validate the location.
        File folder = new File(Settings.getInstance().getStatisticPath());
        if (!folder.isDirectory()) {
            logger.error("Not valid location to save the statistics.");
            return;
        }
        
        // Append the date to the title of the statistic.
        Date date = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss"); // File stamp.

        // Fill with content.
        try {
            Environment environment = statistic.getEnvironment();
            String title = statistic.getScenarioTitle();
            String format = dateFormat.format(date);
            File file = new File(folder, statistic.getScenarioTitle() + "_" + dateFormat.format(date));
            FileUtils.touch(file);

            // Write the environment information.
            FileUtils.writeStringToFile(file, getEnvironmentInfo(environment).toString() + "\n", StandardCharsets.UTF_8, true);

            // Write the agent's information.
            for (int i = 0; i < environment.getAgentCount(); i++) {
                FileUtils.writeStringToFile(file, getAgentInfo(statistic, i).toString() + "\n", StandardCharsets.UTF_8, true);
            }
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            return;
        }
    }

    private StringBuilder getEnvironmentInfo(Environment environment) {
        StringBuilder builder = new StringBuilder();
        builder.append("###############################").append("\n")
                .append("Στοιχεία Περιβάλλονος").append("\n")
                .append("- Τίτλος: ").append(environment.getTitle()).append("\n")
                .append("- Διαστάσεις: ").append(environment.getHeight()).append("x").append(environment.getWidth()).append("\n")
                .append("- Πλήθος Πρακτόρων: ").append(environment.getAgentCount()).append("\n");

        return builder;
    }

    private StringBuilder getAgentInfo(Statistic statistic, int index) {
        StringBuilder builder = new StringBuilder();
        builder.append("\n").append("###############################").append("\n")
                .append("Πράκτορας Νο").append(index + 1).append("\n")
                .append("- Συνολικά βήματα εκτέλεσης: ").append(statistic.getAgentStepList().get(index)).append("\n")
                .append("- Πλήθος ανταλλαγής γνώσεων: ").append(statistic.getAgentKnowledgeExchangeList().get(index)).append("\n")
                .append("- Σύνολο κινήσεων με Dijkstra: ").append(statistic.getAgentMoveDijkstraList().get(index)).append("\n")
                .append("- Σύνολο κινήσεων με Least Visited: ").append(statistic.getAgentMoveLeastVisitedList().get(index)).append("\n");

        return builder;
    }

}
