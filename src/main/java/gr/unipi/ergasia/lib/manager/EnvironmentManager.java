package gr.unipi.ergasia.lib.manager;

import gr.unipi.ergasia.model.Environment;
import java.util.List;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class EnvironmentManager {

    private static EnvironmentManager INSTANCE;
    private List<Environment> environmentList;

    private EnvironmentManager() {

    }

    public static EnvironmentManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new EnvironmentManager();
        }
        return INSTANCE;
    }

    private void importData() {

    }
    
    public void exportData() {
        
    }

    public List<Environment> getEnvironmentList() {
        return environmentList;
    }
}
