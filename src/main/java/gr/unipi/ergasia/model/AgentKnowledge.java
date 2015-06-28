package gr.unipi.ergasia.model;

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
}
