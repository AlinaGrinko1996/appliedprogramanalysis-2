package graph;

import com.google.common.base.Strings;

public class CustomNode {
    private final String GRAPHICS_STANDARD = "\t graphics [ type \"roundrectangle\" fill \"#FFFF88\" ] ]\n";
    private int id;
    private String label;
    private NodeType nodeType;
    private String graphics;

    public CustomNode(int id, NodeType nodeType, String label) {
        this.id = id;
        this.nodeType = nodeType;
        this.label = label;
    }

    public CustomNode(int id, String label) {
        this.id = id;
        this.label = label;
    }

    public int getId() {
        return id;
    }

    public NodeType getType() {
        return nodeType;
    }

    public void setNodeType(NodeType nodeType) {
        this.nodeType = nodeType;
    }

    public NodeType getNodeType() {
        return nodeType;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    @Override
    public String toString() {
        String graphic = Strings.isNullOrEmpty(graphics) ? GRAPHICS_STANDARD : graphics;
        return String.format("node [\n\t\tid %d\n\t\tlabel \"%s\"\n\t", id, label) + graphic;
    }
}
