package gr.unipi.ergasia.lib.manager;

import gr.unipi.ergasia.lib.Settings;
import gr.unipi.ergasia.model.Environment;
import gr.unipi.ergasia.model.Environment;
import gr.unipi.ergasia.model.StadiumIncredience;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class EnvironmentManager {

    private final static Logger logger = Logger.getLogger(EnvironmentManager.class);
    private static EnvironmentManager INSTANCE;
    private ObservableList<Environment> environmentList;

    private EnvironmentManager() {
        environmentList = FXCollections.observableList(new ArrayList<>());
    }

    public static EnvironmentManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EnvironmentManager();
            importData();
        }
        return INSTANCE;
    }

    private static void importData() {
        // Get the location of the environment and check if it is a directory.
        File folder = new File(Settings.getInstance().getEnvironmentPath());
        if (!folder.isDirectory()) {
            logger.error("Not valid location to read the environment.");
            return;
        }

        // Read all the files of the environment from the selected location.
        for (final File file : folder.listFiles()) {
            // Initialize the agent plan instance.
            Environment environment = new Environment();
            environment.setTitle(file.getName());
            environment.setAgentCount(0);

            // Read and load the environment map from the file.
            try {
                int width = 0;
                int height = 0;
                // For each row.
                for (String line : FileUtils.readLines(file, StandardCharsets.UTF_8)) {
                    // Initialize the row list.
                    List<StadiumIncredience> rowList = new LinkedList<>();

                    // Get the row string.
                    environment.setHeight(++height);
                    String[] incredienceLine = line.split(" ");

                    // Read the columns of this row.
                    for (String incredienceItem : incredienceLine) {
                        // Get the item.
                        environment.setWidth(++width);
                        StadiumIncredience incredience = StadiumIncredience.initFromVocabulary(incredienceItem.trim().toUpperCase());
                        if (incredience == null) {
                            logger.error("Not valid environment file with title " + environment.getTitle() + ".");
                            continue;
                        }
                        // Check if this item is an agent house.
                        if (incredience.equals(StadiumIncredience.SPITI_AGENT)) {
                            environment.setAgentCount(environment.getAgentCount() + 1);
                        }
                        // Add the item.
                        rowList.add(incredience);
                    }

                    // Add this row to the stadium list.
                    environment.getStadium().add(rowList);
                }
            } catch (IOException ex) {
                logger.log(Level.ERROR, "An error occurred with loading the evironment instance.", ex);
            }

            // Add this environment to the instance list.
            INSTANCE.environmentList.add(environment);
        }
    }

    public void exportData() {
        // Validate the location.
        File folder = new File(Settings.getInstance().getEnvironmentPath());
        if (!folder.isDirectory()) {
            logger.error("Not valid location to save the environment.");
            return;
        }

        // Remove all the plans by removing the whole directory.
        try {
            FileUtils.deleteDirectory(folder);
            FileUtils.forceMkdir(folder);
        } catch (IOException ex) {
            logger.error(ex.getMessage());
            return;
        }

        // For each plan create a file.
        for (Environment environment : environmentList) {
            try {
                File file = new File(folder, environment.getTitle());
                FileUtils.touch(file);

                // Fill this file with the stadium.
                for (List<StadiumIncredience> rowList : environment.getStadium()) {
                    for (StadiumIncredience incredience : rowList) {
                        FileUtils.writeStringToFile(file, incredience.getVocabulary().toUpperCase() + " ", StandardCharsets.UTF_8, true);
                    }
                    FileUtils.writeStringToFile(file, "\n", StandardCharsets.UTF_8, true);
                }
            } catch (IOException ex) {
                logger.error(ex.getMessage());
                return;
            }
        }
    }

    public ObservableList<Environment> getEnvironmentList() {
        return environmentList;
    }
}
