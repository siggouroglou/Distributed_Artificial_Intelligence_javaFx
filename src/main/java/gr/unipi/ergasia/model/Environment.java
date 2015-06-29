package gr.unipi.ergasia.model;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class Environment {
    private String title;
    private int width;
    private int height;
    private int agentCount;
    private StadiumIncredience[][] stadium;

    public Environment() {
        this.title = "";
        this.width = 0;
        this.height = 0;
        this.agentCount = 0;
        this.stadium = null;
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

    public StadiumIncredience[][] getStadium() {
        return stadium;
    }

    public void setStadium(StadiumIncredience[][] stadium) {
        this.stadium = stadium;
    }
}
