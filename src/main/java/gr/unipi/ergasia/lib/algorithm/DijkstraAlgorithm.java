package gr.unipi.ergasia.lib.algorithm;

import gr.unipi.ergasia.lib.knowledge.GraphLine;
import gr.unipi.ergasia.lib.knowledge.KnowledgeGraph;
import gr.unipi.ergasia.lib.knowledge.KnowledgeNode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class DijkstraAlgorithm {

    private final List<KnowledgeNode> nodes;
    private final List<GraphLine> edges;
    private Set<KnowledgeNode> settledNodes;
    private Set<KnowledgeNode> unSettledNodes;
    private Map<KnowledgeNode, KnowledgeNode> predecessors;
    private Map<KnowledgeNode, Integer> distance;

    public DijkstraAlgorithm(KnowledgeGraph graph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<KnowledgeNode>(graph.getNodeSet());
        this.edges = new ArrayList<GraphLine>(graph.getLineSet());
    }

    public void execute(KnowledgeNode source) {
        settledNodes = new HashSet<KnowledgeNode>();
        unSettledNodes = new HashSet<KnowledgeNode>();
        distance = new HashMap<KnowledgeNode, Integer>();
        predecessors = new HashMap<KnowledgeNode, KnowledgeNode>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            KnowledgeNode node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(KnowledgeNode node) {
        List<KnowledgeNode> adjacentNodes = getNeighbors(node);
        for (KnowledgeNode target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(KnowledgeNode node, KnowledgeNode target) {
        for (GraphLine edge : edges) {
            if (edge.getSource().equals(node)
                    && edge.getDestination().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<KnowledgeNode> getNeighbors(KnowledgeNode node) {
        List<KnowledgeNode> neighbors = new ArrayList<KnowledgeNode>();
        for (GraphLine edge : edges) {
            if (edge.getSource().equals(node)
                    && !isSettled(edge.getDestination())) {
                neighbors.add(edge.getDestination());
            }
        }
        return neighbors;
    }

    private KnowledgeNode getMinimum(Set<KnowledgeNode> vertexes) {
        KnowledgeNode minimum = null;
        for (KnowledgeNode vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(KnowledgeNode vertex) {
        return settledNodes.contains(vertex);
    }

    private int getShortestDistance(KnowledgeNode destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public LinkedList<KnowledgeNode> getPath(KnowledgeNode target) {
        LinkedList<KnowledgeNode> path = new LinkedList<KnowledgeNode>();
        KnowledgeNode step = target;
        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        return path;
    }

}
