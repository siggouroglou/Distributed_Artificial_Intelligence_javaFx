package gr.unipi.ergasia.model;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class AgentPlan {
    private String title;
    private List<StadiumIncredience> actionList;

    public AgentPlan() {
        this.title = "";
        this.actionList = new LinkedList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<StadiumIncredience> getActionList() {
        return actionList;
    }

    public void setActionList(List<StadiumIncredience> actionList) {
        this.actionList = actionList;
    }
}
