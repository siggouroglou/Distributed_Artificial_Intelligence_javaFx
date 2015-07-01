package gr.unipi.ergasia.service;

import gr.unipi.ergasia.model.AgentKnowledge;
import gr.unipi.ergasia.model.AgentPlan;
import gr.unipi.ergasia.model.Point;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class Agent extends Thread {

    private int id;
    private Scenario scenario;
    private List<AgentKnowledge> placeKnowledge;
    private AgentPlan agentPlan;
    private Point currentLocation;
    private int agentStepCount;
    private int agentKnowledgeExchangeCount;

    public Agent(int id, Scenario scenario, AgentPlan agentPlan, Point startLocation) {
        super("Agent-" + id);
        this.id = id;
        this.scenario = scenario;
        this.placeKnowledge = new LinkedList<>();
        this.agentPlan = agentPlan;
        this.currentLocation = startLocation;
        this.agentStepCount = 0;
        this.agentKnowledgeExchangeCount = 0;
    }

    public Point getCurrentLocation() {
        return currentLocation;
    }

    @Override
    public void start() {

    }

//    private boolean isLocationKnown(Point point) {
//
//    }
//
//    private void checkNeighborhood(Point point) {
//
//    }
//
//    private boolean isAnotherAgentNear(Point point) {
//
//    }
//
//    private void knowledgeExchange(Agent agent) {
//
//    }
//
//    private void getNextNearestPoint(Point location, Point target) {
//
//    }
//
//    private Point getNextPoint(Point location) {
//
//    }
//
//    private Point moveToPoint(Point point) {
//
//    }
}
