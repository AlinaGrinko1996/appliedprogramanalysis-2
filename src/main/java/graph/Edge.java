package graph;

public class Edge {
    private String source;
    private String target;
    private String label;

    public Edge(String source, String target, String label) {
        this.source = source;
        this.target = target;
        this.label = label;
    }

    public String getSource() {
        return source;
    }

    public String getTarget() {
        return target;
    }

    @Override
    public String toString() {
        return String.format("edge [\n\t\tsource %s\n\t\ttarget %s\n\t\tlabel %s\n\t]\n\t", source, target, label);
    }
}
