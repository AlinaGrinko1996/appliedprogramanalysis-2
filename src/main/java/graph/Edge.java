package graph;

public class Edge {
    private final String GRAPHICS_STANDARD = "\t graphics [width 1 type \"line\" fill \"#000000\" arrow \"last\"] ]"+ "\n";
    private CustomNode source;
    private CustomNode target;
    private String label;
    private String graphics;

    public Edge(CustomNode source, CustomNode target, String label) {
        this.source = source;
        this.target = target;
        this.label = label;
    }

    public void setGraphics(String graphics) {
        this.graphics = graphics;
    }

    @Override
    public String toString() {
        return String.format("edge [\n\t\tsource %d\n\t\ttarget %d\n\t\tlabel \"%s\"\n\t", source.getId(),
                target.getId(), label) + "\t graphics [width 1 type \"line\" fill \"#000000\" arrow \"last\"] ]"+ "\n";
    }
}
