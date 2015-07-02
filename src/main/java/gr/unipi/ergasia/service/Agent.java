package gr.unipi.ergasia.service;

import gr.unipi.ergasia.lib.algorithm.DijkstraAlgorithm;
import gr.unipi.ergasia.lib.knowledge.GraphLine;
import gr.unipi.ergasia.lib.knowledge.KnowledgeGraph;
import gr.unipi.ergasia.lib.knowledge.KnowledgeNode;
import gr.unipi.ergasia.lib.manager.GameManager;
import gr.unipi.ergasia.model.AgentPlan;
import gr.unipi.ergasia.model.Point;
import gr.unipi.ergasia.model.StadiumIncredience;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import org.apache.log4j.Logger;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class Agent extends Task<Object> {

    private final static Logger logger = Logger.getLogger(Agent.class);
    private int id;
    private Scenario scenario;
    private KnowledgeGraph knowledgeGraph;
//    private List<AgentKnowledge> placeKnowledge;
    private AgentPlan agentPlan;
    private Point currentLocation;
    private int agentStepCount;
    private int agentKnowledgeExchangeCount;
    private int currentPlanTargetIndex;
    private ImageView agentImage;
    private ImageView overridenImage;

    public Agent(int id, Scenario scenario, AgentPlan agentPlan, Point startLocation) {
        this.id = id;
        this.scenario = scenario;
//        this.placeKnowledge = new LinkedList<>();
        this.agentPlan = agentPlan;
        this.currentLocation = startLocation;
        this.agentStepCount = 0;
        this.agentKnowledgeExchangeCount = 0;
        this.currentPlanTargetIndex = 0;

        // Set the knoledge root node.
        KnowledgeNode rootNode = new KnowledgeNode();
        rootNode.setPoint(startLocation);
        rootNode.setStadiumIncredience(StadiumIncredience.SPITI_AGENT);
        this.knowledgeGraph = new KnowledgeGraph();

        // Images.
        Image image = new Image(getClass().getResourceAsStream("/files/images/stadiumIncredience/" + StadiumIncredience.AGENT.getFileName()));
        ImageView imageView = new ImageView(image);
        imageView.setId(StadiumIncredience.AGENT.getVocabulary());
        imageView.setFitWidth(40);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);
        this.agentImage = imageView;
        this.overridenImage = null;
    }

    public Point getCurrentLocation() {
        return currentLocation;
    }

    public KnowledgeGraph getKnowledgeGraph() {
        return knowledgeGraph;
    }

    @Override
    protected Object call() throws Exception {
        Point nextLocation = currentLocation;
        while (true) {
            // Check if agent has completed with the plan.
            if (currentPlanTargetIndex == agentPlan.getActionList().size()) {
                logger.debug("Agent" + id + " - I completed my plan.");
                // Go to home..

                // Complete with the agent thread.
                break;
            }

            // Get the new location from the knowledgeGraph base.
            KnowledgeNode knowledgeNode = knowledgeGraph.findByPoint(currentLocation);

            // Check if the current(new) location is known. If i know the location then i know the neighborwood too.
            if (knowledgeNode == null) {
                logger.debug("Agent" + id + " - This location is not known" + currentLocation + ".");
                addKnowledge(nextLocation, currentLocation);
            } else {
                knowledgeNode.setVisitedTimes(knowledgeNode.getVisitedTimes() + 1);
            }

            // Check if there are any agents near me and exchange knowledgeGraph with them.
            List<Agent> agentsNearList = getAgentsNear(currentLocation);
            if (!agentsNearList.isEmpty()) {
                logger.debug("Agent" + id + " - Found" + agentsNearList.size() + " agent(s) near me.");
                agentsNearList.stream().forEach((agent) -> {
                    knowledgeExchange(agent);
                });
            }

            // Check if i arrived to my plan.
            StadiumIncredience targetIncredience = agentPlan.getActionList().get(currentPlanTargetIndex);
            if (isLocationIncredienceEqualTo(currentLocation, targetIncredience)) {
                logger.debug("Agent" + id + " - I arrived to my plan and i target to next plan now.");
                currentPlanTargetIndex++;
            }

            // Check if i know the location of the target point of my plan.
            KnowledgeNode targetNode = knowledgeGraph.findByIncredience(targetIncredience);
            if (targetNode != null) {
                logger.debug("Agent" + id + " - I know it so get the newarest point to move to.");
                nextLocation = getNextNearestPoint(knowledgeNode, targetNode);
            } else {
                logger.debug("Agent" + id + " - I do not know it so go to next less known point.");
                nextLocation = getNextPoint(currentLocation);
            }

            // Move to the next location and update the current.
            moveToPoint(currentLocation, nextLocation);
            currentLocation = nextLocation;

            // Sleep for some time to allow the other agents to work.
            Random random = new Random(500);
            int sleepTime = 200 + random.nextInt();
            logger.debug("Agent" + id + " - Sleeping for " + sleepTime + "ms.");
            Thread.sleep(sleepTime);
        }
        return null;
    }

    private void addKnowledge(Point pointOld, Point pointNew) {
        // Get the points i am interesting in.
        Point[] pointArray = new Point[]{
            new Point(pointNew.getX(), pointNew.getY() - 1),
            new Point(pointNew.getX() + 1, pointNew.getY()),
            new Point(pointNew.getX(), pointNew.getY() + 1),
            new Point(pointNew.getX() - 1, pointNew.getY())
        };

        // The knowledgeGraph that will be created.
        KnowledgeNode knowledgeNode = new KnowledgeNode();
        knowledgeNode.setPoint(pointNew);
        knowledgeNode.setVisitedTimes(0);

        // Scan the grid and find what is hiding in this points. Add the new places in the neighborwood of this point.
        GridPane gridPane = GameManager.getInstance().getGridPane();
        for (Node node : gridPane.getChildren()) {
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;

                // Get the Point of this node.
                Integer row = GridPane.getRowIndex(node);
                Integer column = GridPane.getColumnIndex(node);
                Point gridLocation = new Point(column, row);

                // Check if this point is the center of this knowledgeGraph.
                if (pointNew.equals(gridLocation)) {
                    knowledgeNode.setStadiumIncredience(StadiumIncredience.initFromVocabulary(imageView.getId()));
                    continue;
                }

                // Check if this point is equal with the up/right/down/left and save the loation to the knowledgeGraph data structure.
                for (Point current : pointArray) {
                    if (current.equals(gridLocation)) {
                        knowledgeNode.getNeightborList().add(StadiumIncredience.initFromVocabulary(imageView.getId()));
                        break;
                    }
                }
            }
        }

        // Add the node to the knoeledge graph.
        knowledgeGraph.getNodeSet().add(knowledgeNode);

        // Get the knoledge of the point before. In case of the point before is not existing in the graph (1st run), return.
        KnowledgeNode knowledgeNodeOld = knowledgeGraph.findByPoint(pointOld);
        if (knowledgeNodeOld == null) {
            return;
        }

        // In other cases connect with a line this knowledgeGraph with the one before.
        GraphLine line = new GraphLine(knowledgeNodeOld, knowledgeNode);
        knowledgeGraph.getLineSet().add(line);
    }

    private List<Agent> getAgentsNear(Point point) {
        List<Agent> allAgentList = GameManager.getInstance().getAgentList();
        List<Agent> agentNearList = new ArrayList<>(allAgentList.size());

        // Get the points i am interesting in.
        Point[] pointToSearchArray = new Point[]{
            new Point(point.getX(), point.getY() - 1),
            new Point(point.getX() + 1, point.getY()),
            new Point(point.getX(), point.getY() + 1),
            new Point(point.getX() - 1, point.getY()),
            point
        };

        // Check the current locations of all the agents.
        for (Agent agent : allAgentList) {
            if (!agent.equals(this)) {
                for (Point location : pointToSearchArray) {
                    if (agent.getCurrentLocation().equals(location)) {
                        agentNearList.add(agent);
                    }
                }
            }
        }

        return agentNearList;
    }

    private synchronized void knowledgeExchange(Agent agent) {
        // Get this agents' knowledgeGraph data structure.
        KnowledgeGraph agentKnowledge = agent.getKnowledgeGraph();

        // Merge it with the current agent's knowledgeGraph data structure.
        knowledgeGraph.getNodeSet().addAll(agentKnowledge.getNodeSet());
        knowledgeGraph.getLineSet().addAll(agentKnowledge.getLineSet());
    }

    private boolean isLocationIncredienceEqualTo(Point point, StadiumIncredience incredience) {
        for (KnowledgeNode node : knowledgeGraph.getNodeSet()) {
            if (node.getPoint().equals(point)) {
                return incredience.equals(node.getStadiumIncredience());
            }
        }
        return false;
    }

    private Point getNextNearestPoint(KnowledgeNode sourceNode, KnowledgeNode destinationNode) {
        // Run dijkstra algorithm to find the best next point.
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(knowledgeGraph);
        dijkstra.execute(sourceNode);
        LinkedList<KnowledgeNode> path = dijkstra.getPath(destinationNode);
        return path.get(0).getPoint();
    }

    private Point getNextPoint(Point point) {
        // Get the points i am interesting in.
        Point[] pointToSearchArray = new Point[]{
            new Point(point.getX(), point.getY() - 1),
            new Point(point.getX() + 1, point.getY()),
            new Point(point.getX(), point.getY() + 1),
            new Point(point.getX() - 1, point.getY())
        };

        // Get only the roads from this points.
        List<KnowledgeNode> roadNodeList = new ArrayList<>(4);
        for (Point currentPoint : pointToSearchArray) {
            for (KnowledgeNode node : knowledgeGraph.getNodeSet()) {
                if (node.getPoint().equals(currentPoint)) {
                    // Check if this point is a road.
                    if (StadiumIncredience.getAllRoads().contains(node.getStadiumIncredience())) {
                        roadNodeList.add(node);
                    }
                    break;
                }
            }
        }

        // Select the one with the min visited times.
        int visitedTimesMin = Integer.MAX_VALUE;
        Point nextLocation = null;
        for (KnowledgeNode node : roadNodeList) {
            if (node.getVisitedTimes() < visitedTimesMin) {
                nextLocation = node.getPoint();
            }
        }

        // In case of an agent house that is not near to a road.
        if (nextLocation == null) {
            throw new NullPointerException("This location is not including a road near.");
        }

        return nextLocation;
    }

    private void moveToPoint(Point pointOld, Point pointNew) {
        // Get the grid pane.
        GridPane gridPane = GameManager.getInstance().getGridPane();

        // Load the saved image to the current location.
        if (overridenImage != null) {
            gridPane.add(overridenImage, pointOld.getX(), pointOld.getY());
        }

        // Save the image from the next location.
        for (Node node : gridPane.getChildren()) {
            if (node instanceof ImageView) {
                ImageView imageView = (ImageView) node;

                // Get the index of this child.
                Integer row = GridPane.getRowIndex(node);
                Integer column = GridPane.getColumnIndex(node);
                if (column == pointOld.getX() && row == pointOld.getY()) {
                    // Do not change this image in case of another agent image. This image will be replaced from the first agent that moved to this point.
                    if (imageView.getId() == StadiumIncredience.AGENT.getVocabulary()) {
                        overridenImage = null;
                    } else {
                        overridenImage = imageView;
                    }
                    break;
                }
            }
        }

        // Create the agent to the next location.
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gridPane.add(agentImage, pointNew.getX(), pointNew.getY());
            }
        });
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 47 * hash + this.id;
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
        final Agent other = (Agent) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
}
