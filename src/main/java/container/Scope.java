package container;

import graph.Edge;
import graph.Graph;
import graph.CustomNode;
import graph.NodeType;

import java.util.Stack;

public class Scope {
    private String currentClass;
    private String currentMethod;
    private Stack<CustomNode> lastNodes = new Stack<>();

    private Graph graph;
    private int nodeCounter = 0;

    public void setGraph(Graph graph) {
        this.graph = graph;
        this.nodeCounter = graph.nodes.size();
    }

    public String getCurrentClass() {
        return currentClass;
    }

    public String getCurrentMethod() {
        return currentMethod;
    }

    public Graph getGraph() {
        return graph;
    }

    public void enterClass(String className) {
        CustomNode node = new CustomNode(++nodeCounter, NodeType.CLASS, className);
        currentClass = className;
        lastNodes.push(node);
    }

    public void enterConstructor(String constructorName) {
        currentMethod = constructorName + ".init";
        CustomNode node = new CustomNode(++nodeCounter, NodeType.CONSTRUCTOR, currentMethod);
        lastNodes.push(node);
    }

    public void enterMethod(String methodName) {
        currentMethod = String.format("%s.%s", currentClass, methodName);
        CustomNode node = new CustomNode(++nodeCounter, NodeType.METHOD, currentMethod);
        lastNodes.push(node);
    }

    public void enterLoop(String loopName) {
        CustomNode node = new CustomNode(++nodeCounter, NodeType.LOOP_STATEMENT, loopName);
        lastNodes.push(node);
    }

    public void enterConditional(String ifName) {
        CustomNode node = new CustomNode(++nodeCounter, NodeType.IF_STATEMENT, ifName);
        lastNodes.push(node);
    }

    public void addMethodCall(String methodCallName, NodeType nodeType) {
        CustomNode node = new CustomNode(++nodeCounter, nodeType, methodCallName);
        lastNodes.push(node);
    }

    public void leaveScope() {
        CustomNode lastNode = lastNodes.pop();
        graph.nodes.add(lastNode);

        switch (lastNode.getType()) {
            case CLASS : {
                currentClass = getNextClassInStack();
            }
            case METHOD:
            case CONSTRUCTOR: {
                currentMethod = "";
                break;
            }
        }

        if (!lastNodes.empty()) {
            CustomNode nodeBefore = lastNodes.peek();
            Edge edge = new Edge(nodeBefore, lastNode, "");
            graph.edges.add(edge);
        }
    }

    int getAmountOfLoopsInStack() {
        int counter = 0;
        for (CustomNode node: lastNodes) {
            if (node.getType() == NodeType.LOOP_STATEMENT) {
                counter++;
            }
        }
        return counter;
    }

    String getNextClassInStack() {
        for (CustomNode node: lastNodes) {
            if ((node.getType() == NodeType.CLASS)
                    || node.getType() == NodeType.INTERFACE) {
                return node.getLabel();
            }
        }
        return "";
    }
}
