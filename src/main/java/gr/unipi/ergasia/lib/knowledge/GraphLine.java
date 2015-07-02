package gr.unipi.ergasia.lib.knowledge;

import java.util.Objects;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class GraphLine {

    private final KnowledgeNode source;
    private final KnowledgeNode destination;
    private final int weight = 1; // I am not using weights.

    public GraphLine(KnowledgeNode source, KnowledgeNode destination) {
        this.source = source;
        this.destination = destination;
    }

    public KnowledgeNode getDestination() {
        return destination;
    }

    public KnowledgeNode getSource() {
        return source;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 23 * hash + Objects.hashCode(this.source);
        hash = 23 * hash + Objects.hashCode(this.destination);
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
        final GraphLine other = (GraphLine) obj;
        if (!Objects.equals(this.source, other.source)) {
            return false;
        }
        if (!Objects.equals(this.destination, other.destination)) {
            return false;
        }
        return true;
    }
}
