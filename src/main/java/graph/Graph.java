package graph;

import java.util.HashSet;
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
        stringBuilder.append("Creator \"Graph\" \n");
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
}
