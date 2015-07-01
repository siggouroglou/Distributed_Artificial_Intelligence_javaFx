package gr.unipi.ergasia.model;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

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

    public boolean containsIncredience(List<StadiumIncredience> environmentBuildingList) {
        boolean isExisting = false;

        // Loop through the actions of this plan.
        for (StadiumIncredience action : actionList) {
            // Check if this incredience is existing in the plan.
            isExisting = false;
            for (StadiumIncredience incredience : environmentBuildingList) {
                if (incredience.equals(action)) {
                    isExisting = true;
                    break;
                }
            }
            // In case of this incredience is not contained in the plan.
            if (!isExisting) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.title);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final AgentPlan other = (AgentPlan) obj;
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return title;
    }
}
