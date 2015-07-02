package gr.unipi.ergasia.lib.knowledge;

import gr.unipi.ergasia.model.Point;
import gr.unipi.ergasia.model.StadiumIncredience;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class KnowledgeNode {
    private Point point;
    private StadiumIncredience stadiumIncredience;
    private List<StadiumIncredience> neightborList; // Maximum 4 items.
    private int visitedTimes;

    public KnowledgeNode() {
        this.point = null;
        this.stadiumIncredience = null;
        this.neightborList = null;
        this.visitedTimes = 0;
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

    public List<StadiumIncredience> getNeightborList() {
        return neightborList;
    }

    public void setNeightborList(List<StadiumIncredience> neightborList) {
        this.neightborList = neightborList;
    }

    public int getVisitedTimes() {
        return visitedTimes;
    }

    public void setVisitedTimes(int visitedTimes) {
        this.visitedTimes = visitedTimes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 47 * hash + Objects.hashCode(this.point);
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
        final KnowledgeNode other = (KnowledgeNode) obj;
        if (!Objects.equals(this.point, other.point)) {
            return false;
        }
        return true;
    }
}
