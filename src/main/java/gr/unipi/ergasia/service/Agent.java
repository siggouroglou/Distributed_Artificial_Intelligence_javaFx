package gr.unipi.ergasia.service;

import gr.unipi.ergasia.lib.algorithm.DijkstraAlgorithm;
import gr.unipi.ergasia.lib.knowledge.GraphLine;
import gr.unipi.ergasia.lib.knowledge.KnowledgeGraph;
import gr.unipi.ergasia.lib.knowledge.KnowledgeNode;
import gr.unipi.ergasia.lib.manager.GameManager;
import gr.unipi.ergasia.model.AgentPlan;
import gr.unipi.ergasia.model.Environment;
import gr.unipi.ergasia.model.Point;
import gr.unipi.ergasia.model.StadiumIncredience;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import javafx.application.Platform;
import javafx.concurrent.Task;
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
    private final int id;
    private final Scenario scenario;
    private final KnowledgeGraph knowledgeGraph;
    private AgentPlan agentPlan;
    private Point currentLocation;
    private int agentStepCount;
    private int agentKnowledgeExchangeCount;
    private int currentPlanTargetIndex;

    public Agent(int id, Scenario scenario, AgentPlan agentPlan, Point startLocation) {
        this.id = id;
        this.scenario = scenario;
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
    }

    public Point getCurrentLocation() {
        return currentLocation;
    }

    public KnowledgeGraph getKnowledgeGraph() {
        return knowledgeGraph;
    }

    @Override
    protected Object call() throws Exception {
        // Point objects that store the old and the next location.
        Point oldLocation = currentLocation;
        Point nextLocation = currentLocation;

        try {
            // Loop till the agent complete his plan.
            boolean isPlanCompleted = false;
            boolean isAgentFinished = false;
            while (true) {
                // Check if agent has completed with the plan.
                if (currentPlanTargetIndex == agentPlan.getActionList().size()) {
                    logger.debug("Agent" + id + " - I completed my plan. Lets go home.");
                    isPlanCompleted = true;
                    letsGoHome();
                }

                // Get the new location from the knowledgeGraph base.
                KnowledgeNode knowledgeNode = knowledgeGraph.findByPoint(currentLocation);

                // Check if the current(new) location is known. If i know the location then i know the neighborwood too.
                if (knowledgeNode == null) {
                    logger.debug("Agent" + id + " - This location is not known" + currentLocation + ".");
                    knowledgeNode = addKnowledge(oldLocation, currentLocation);
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
                if (isLocationNeightborEqualTo(currentLocation, targetIncredience)) {
                    if (isPlanCompleted) {
                        isAgentFinished = true;
                    }
                    logger.debug("Agent" + id + " - I arrived to my plan and i will target to next plan now.");
                    currentPlanTargetIndex++;
                }

                // Check if i know the location of the target point of my plan.
                KnowledgeNode targetNode = knowledgeGraph.findByIncredience(targetIncredience);
                if (targetNode != null) {
                    logger.debug("Agent" + id + " - I know the current location" + currentLocation + " so find the nearest point to move to.");
                    nextLocation = getNextNearestPoint(targetNode, knowledgeNode);
                } else {
                    logger.debug("Agent" + id + " - I do not know the current location" + currentLocation + " so go to next less known point.");
                    nextLocation = getNextPoint(currentLocation);
                }

                // Move to the next location and update the current.
                moveToPoint(currentLocation, nextLocation);
                oldLocation = currentLocation;
                currentLocation = nextLocation;

                // Sleep for some time to allow the other agents to work.
                sleepAgent();
            }
        } catch (Exception ex) {
            logger.error(ex);
            ex.printStackTrace();
        }
        return null;
    }

    private KnowledgeNode addKnowledge(Point pointOld, Point pointNew) {
        // The knowledgeGraph that will be created.
        KnowledgeNode knowledgeNode = new KnowledgeNode();
        knowledgeNode.setPoint(pointNew);
        knowledgeNode.setVisitedTimes(1);

        // Add the new places of this point and its neighborwood.
        Environment environment = scenario.getEnvironment();
        knowledgeNode.setStadiumIncredience(environment.getStadiumIncredienceOfPoint(pointNew));
        Point[] pointToSearchArray = new Point[]{
            new Point(pointNew.getX(), pointNew.getY() - 1),
            new Point(pointNew.getX() + 1, pointNew.getY()),
            new Point(pointNew.getX(), pointNew.getY() + 1),
            new Point(pointNew.getX() - 1, pointNew.getY())
        };
        for (Point point : pointToSearchArray) {
            StadiumIncredience stadiumIncredienceOfPoint = environment.getStadiumIncredienceOfPoint(point);
            if (stadiumIncredienceOfPoint != null) {
                knowledgeNode.getNeightborList().add(stadiumIncredienceOfPoint);
            }
        }

        // Add the node to the knowledge graph.
        knowledgeGraph.getNodeSet().add(knowledgeNode);

        // Get the knoledge of the point before. In case of the point before is not existing in the graph (1st run), return.
        KnowledgeNode knowledgeNodeOld = knowledgeGraph.findByPoint(pointOld);
        if (knowledgeNodeOld == null || knowledgeNodeOld.equals(knowledgeNode)) {
            return knowledgeNode;
        }

        // In other cases connect with a line this knowledgeGraph with the one before.
        GraphLine line = new GraphLine(knowledgeNodeOld, knowledgeNode);
        knowledgeGraph.getLineSet().add(line);
        return knowledgeNode;
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

        // Clone collection of nodes to change the visited times.
        List<KnowledgeNode> cloneNodeList = new ArrayList<>(agentKnowledge.getNodeSet().size());
        for (KnowledgeNode item : agentKnowledge.getNodeSet()) {
            cloneNodeList.add(new KnowledgeNode(item));
        }
        List<GraphLine> cloneLineList = new ArrayList<>(agentKnowledge.getLineSet().size());
        for (GraphLine item : agentKnowledge.getLineSet()) {
            cloneLineList.add(new GraphLine(item));
        }

        // Merge it with the current agent's knowledgeGraph data structure. Using HashSet duplicates are not inserted.
        knowledgeGraph.getNodeSet().addAll(cloneNodeList);
        knowledgeGraph.getLineSet().addAll(cloneLineList);
    }

    private boolean isLocationNeightborEqualTo(Point point, StadiumIncredience incredience) {
        for (KnowledgeNode node : knowledgeGraph.getNodeSet()) {
            if (node.getPoint().equals(point)) {
                // Check the neightbors of this location.
                for (StadiumIncredience neightbor : node.getNeightborList()) {
                    if (neightbor.equals(incredience)) {
                        return true;
                    }
                }
                break;
            }
        }
        return false;
    }

    private Point getNextNearestPoint(KnowledgeNode sourceNode, KnowledgeNode destinationNode) {
        // Run dijkstra algorithm to find the best next point.
        DijkstraAlgorithm dijkstra = new DijkstraAlgorithm(knowledgeGraph);
        dijkstra.execute(sourceNode);
        LinkedList<KnowledgeNode> path = dijkstra.getPath(destinationNode);

        // Check the case where the first plan is next to agents house.
        if (path == null) {
            return getNextPoint(destinationNode.getPoint());
        }

        // Return the last-1 point. The last point is the current location.
        return path.get(path.size() > 1 ? path.size() - 2 : 0).getPoint();
    }

    private synchronized Point getNextPoint(Point point) {
        // Get the points i am interesting in.
        Point[] pointToSearchArray = new Point[]{
            new Point(point.getX(), point.getY() - 1),
            new Point(point.getX() + 1, point.getY()),
            new Point(point.getX(), point.getY() + 1),
            new Point(point.getX() - 1, point.getY())
        };

        // A list to store only the road points.
        List<Point> roadNodeList = new ArrayList<>(4);

        // Get only the roads from this points.
        Environment environment = scenario.getEnvironment();
        for (Point current : pointToSearchArray) {
            StadiumIncredience stadiumIncredienceOfPoint = environment.getStadiumIncredienceOfPoint(current);
            if (stadiumIncredienceOfPoint != null && StadiumIncredience.getAllRoads().contains(stadiumIncredienceOfPoint)) {
                roadNodeList.add(current);
            }
        }

        // Select the one with the min visited times.
        int visitedTimesMin = Integer.MAX_VALUE;
        Point nextLocation = null;
        for (Point currentPoint : roadNodeList) {
            // Check the graph if contains this point (re-visited point).
            KnowledgeNode node = knowledgeGraph.findByPoint(currentPoint);

            // If it is not revisited id prefered. In other case check the visited times.
            if (node == null) {
                nextLocation = currentPoint;
                break;
            } else if (node.getVisitedTimes() <= visitedTimesMin) {
                nextLocation = node.getPoint();
                visitedTimesMin = node.getVisitedTimes();
            }
        }

        // In case of an agent house that is not near to a road.
        if (nextLocation == null) {
            throw new NullPointerException("This location is not including a road near.");
        }

        return nextLocation;
    }

    private void moveToPoint(final Point pointOld, final Point pointNew) {
        // Initialize the image from the current(old) location that will override the agent.
        Image imagePlace = new Image(getClass().getResourceAsStream("/files/images/stadiumIncredience/" 
                + scenario.getEnvironment().getStadiumIncredienceOfPoint(pointOld).getFileName()));
        final ImageView imageViewPlace = new ImageView(imagePlace);
        imageViewPlace.setId(StadiumIncredience.AGENT.getVocabulary());
        imageViewPlace.setFitWidth(40);
        imageViewPlace.setPreserveRatio(true);
        imageViewPlace.setSmooth(true);
        imageViewPlace.setCache(true);

        // Initialize the agent image.
        Image imageAgent = new Image(getClass().getResourceAsStream("/files/images/stadiumIncredience/" + StadiumIncredience.AGENT.getFileName()));
        final ImageView imageViewAgent = new ImageView(imageAgent);
        imageViewAgent.setId(StadiumIncredience.AGENT.getVocabulary());
        imageViewAgent.setFitWidth(40);
        imageViewAgent.setPreserveRatio(true);
        imageViewAgent.setSmooth(true);
        imageViewAgent.setCache(true);

        // Create the agent to the next location.
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                GridPane gridPane = GameManager.getInstance().getGridPane();
                gridPane.add(imageViewPlace, pointOld.getX(), pointOld.getY());
                gridPane.add(imageViewAgent, pointNew.getX(), pointNew.getY());
            }
        });
    }

    private void sleepAgent() throws InterruptedException {
        Random random = new Random();
        int sleepTime = 100 + random.nextInt(400);
        logger.debug("Agent" + id + " - Sleeping for " + sleepTime + "ms.");
        Thread.sleep(sleepTime);
    }

    private void letsGoHome() {
        // Create a new agent plan to send the agent home.
        agentPlan = new AgentPlan();
        List<StadiumIncredience> actionList = new ArrayList<>(1);
        actionList.add(StadiumIncredience.SPITI_AGENT);
        agentPlan.setActionList(actionList);
        currentPlanTargetIndex = 0;
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
