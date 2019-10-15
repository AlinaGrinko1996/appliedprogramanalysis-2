package container;

import graph.Edge;
import graph.Graph;
import graph.GraphNode;
import graph.NodeType;

import java.util.Stack;

public class Scope {
    private String currentClass;
    private String currentMethod;
    private Stack<GraphNode> lastNodes = new Stack<>();

    private Graph graph;

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public Graph getGraph() {
        return graph;
    }

    public void enterClass(String className) {
        GraphNode node = new GraphNode(className, NodeType.CLASS, className);
        currentClass = className;
        lastNodes.push(node);
    }

    public void enterConstructor(String constructorName) {
        currentMethod = constructorName + ".init";
        GraphNode node = new GraphNode(currentMethod, NodeType.CONSTRUCTOR, constructorName);
        lastNodes.push(node);
    }

    public void enterMethod(String methodName) {
        currentMethod = String.format("%s.%s", currentClass, methodName);
        GraphNode node = new GraphNode(currentMethod, NodeType.METHOD, methodName);
        lastNodes.push(node);
    }

    public void enterLoop(String loopName) {
        GraphNode node = new GraphNode(loopName, NodeType.LOOP_STATEMENT, "loop");
        lastNodes.push(node);
    }

    public void enterConditional(String ifName) {
        GraphNode node = new GraphNode(ifName, NodeType.IF_STATEMENT, "if");
        lastNodes.push(node);
    }

    public void leaveScope() {
        GraphNode lastNode = lastNodes.pop();
        graph.nodes.add(lastNode);

        switch (lastNode.getType()) {
            case CLASS : {
                currentClass = "";
            }
            case METHOD:
            case CONSTRUCTOR: {
                currentMethod = "";
            }
        }

        if (!lastNodes.empty()) {
            GraphNode nodeBefore = lastNodes.peek();
            Edge edge = new Edge(nodeBefore.getId(), lastNode.getId(), "");
            graph.edges.add(edge);
        }
    }
}
