package gr.unipi.ergasia.lib.manager;

import gr.unipi.ergasia.model.Statistic;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class StatisticManager {

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
        
    }
    
}
