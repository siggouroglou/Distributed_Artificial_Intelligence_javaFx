package gr.unipi.ergasia.model;

import java.util.Objects;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class AgentKnowledge {
    private Point point;
    private StadiumIncredience stadiumIncredience;

    public AgentKnowledge() {
        this.point = null;
        this.stadiumIncredience = null;
    }

    public AgentKnowledge(Point point, StadiumIncredience stadiumIncredience) {
        this.point = point;
        this.stadiumIncredience = stadiumIncredience;
    }

    public Point getPoint() {
        return point;
    }

    public void setPoint(Point point) {
        this.point = point;
    }

    public StadiumIncredience getStadiumIncredience() {
        return stadiumIncredience;
    }

    public void setStadiumIncredience(StadiumIncredience stadiumIncredience) {
        this.stadiumIncredience = stadiumIncredience;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.point);
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
        final AgentKnowledge other = (AgentKnowledge) obj;
        if (!Objects.equals(this.point, other.point)) {
            return false;
        }
        return true;
    }
}
