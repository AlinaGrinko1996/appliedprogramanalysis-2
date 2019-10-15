package graph;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Graph {
    public Set<Edge> edges;
    public Set<CustomNode> nodes;

    public Graph() {
        edges = new HashSet<>();
        nodes = new HashSet<>();
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("graph [\r\n\t");
        for (CustomNode node : nodes) {
            stringBuilder.append(node.toString());
        }
        for (Edge edge : edges) {
            stringBuilder.append(edge.toString());
        }
        stringBuilder.append("\r]");
        return stringBuilder.toString();
    }

    public String toGML() {
        return "";
    }
}
