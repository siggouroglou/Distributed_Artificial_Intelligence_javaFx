package gr.unipi.ergasia.lib.knowledge;

import gr.unipi.ergasia.model.Point;
import gr.unipi.ergasia.model.StadiumIncredience;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author siggouroglou@gmail.com
 */
public class KnowledgeGraph {

    private final Set<KnowledgeNode> nodeSet;
    private final Set<GraphLine> lineSet;

    public KnowledgeGraph() {
        this.nodeSet = new HashSet<>();
        this.lineSet = new HashSet<>();
    }

    public Set<KnowledgeNode> getNodeSet() {
        return nodeSet;
    }

    public Set<GraphLine> getLineSet() {
        return lineSet;
    }

    public KnowledgeNode findByPoint(Point point) {
        for (KnowledgeNode node : nodeSet) {
            if (node.getPoint().equals(point)) {
                return node;
            }
        }
        return null;
    }

    public KnowledgeNode findByIncredience(StadiumIncredience incredience) {
        for (KnowledgeNode node : nodeSet) {
            if (node.getStadiumIncredience().equals(incredience)) {
                return node;
            }
            for (StadiumIncredience current : node.getNeightborList()) {
                if (current.equals(incredience)) {
                    return node;
                }
            }
        }
        return null;
    }
}
