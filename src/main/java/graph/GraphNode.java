package graph;

public class GraphNode {
    private String id;
    private String label;
    private NodeType nodeType;

    public GraphNode(String id, NodeType nodeType, String label) {
        this.id = id;
        this.nodeType = nodeType;
        this.label = label;
    }

    public String getId() {
        return id;
    }

    public NodeType getType() {
        return nodeType;
    }

    @Override
    public String toString() {
        return String.format("node [\n\t\tid %s\n\t\tlabel %s\n\t]\n\t", id, label);
    }
}
