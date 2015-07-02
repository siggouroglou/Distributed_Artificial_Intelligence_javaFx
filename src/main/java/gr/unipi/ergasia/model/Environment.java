package gr.unipi.ergasia.model;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class Environment {

    private String title;
    private int width;
    private int height;
    private int agentCount;
    private List<List<StadiumIncredience>> stadium;

    public Environment() {
        this.title = "";
        this.width = 0;
        this.height = 0;
        this.agentCount = 0;
        this.stadium = new LinkedList<>();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getAgentCount() {
        return agentCount;
    }

    public void setAgentCount(int agentCount) {
        this.agentCount = agentCount;
    }

    public List<List<StadiumIncredience>> getStadium() {
        return stadium;
    }

    public void setStadium(List<List<StadiumIncredience>> stadium) {
        this.stadium = stadium;
    }

    public List<StadiumIncredience> getBuildings() {
        List<StadiumIncredience> environmentBuildingList = new LinkedList<>();

        // For each element of this stadium.
        for (List<StadiumIncredience> rowList : stadium) {
            for (StadiumIncredience incredience : rowList) {
                if (incredience.isBuilding()) {
                    environmentBuildingList.add(incredience);
                }
            }
        }

        return environmentBuildingList;
    }

    public List<Point> getPointsOfItem(StadiumIncredience stadiumIncredience) {
        final List<Point> pointOfItemList = new ArrayList<>();

        // Scan the stadium and capture the location of this incredience.
        int row = 0;
        for (List<StadiumIncredience> rowList : stadium) {
            int col = 0;
            for (StadiumIncredience incredience : rowList) {
                if (incredience.equals(stadiumIncredience)) {
                    pointOfItemList.add(new Point(col++, row));
                }
            }
            row++;
        }

        return pointOfItemList;
    }
}
