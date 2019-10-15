package graph;

public class CustomNode {
    private int id;
    private String label;
    private NodeType nodeType;

    public CustomNode(int id, NodeType nodeType, String label) {
        this.id = id;
        this.nodeType = nodeType;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public NodeType getType() {
        return nodeType;
    }

    @Override
    public String toString() {
        return String.format("node [\n\t\tid %d\n\t\tlabel \"%s\"\n\t", id, label)
                + "\t graphics [ type \"roundrectangle\" fill \"#FFFF88\" ] ]\n";
    }
}
