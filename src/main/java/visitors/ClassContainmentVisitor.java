package visitors;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import container.MetricsTables;
import graph.Edge;
import graph.Graph;
import graph.CustomNode;
import graph.NodeType;

import java.util.Arrays;
import java.util.List;

public class ClassContainmentVisitor extends VoidVisitorAdapter<Void> {
    private Graph graph;
    private MetricsTables metricsTables;

    public ClassContainmentVisitor(Graph graph, MetricsTables metricsTables) {
        super();
        this.graph = graph;
        this.metricsTables = metricsTables;
    }

    @Override
    public void visit(final ClassOrInterfaceDeclaration n, Void arg) {
        super.visit(n, arg);

        NodeType nodeType  = n.isInterface() ? NodeType.INTERFACE : NodeType.CLASS;
        CustomNode node = new CustomNode(n.getNameAsString(), nodeType, n.getNameAsString());
        graph.nodes.add(node);

        for (Node childNode: n.getChildNodes()) {
            Class nodeClass = childNode.getClass();
            if (nodeClass.equals(ConstructorDeclaration.class)) {
                processConstructor((ConstructorDeclaration) childNode, n.getNameAsString());
            } else if (nodeClass.equals(MethodDeclaration.class)) {
                processMethod((MethodDeclaration) childNode, n.getNameAsString());
            }
        }
    }

    private void processConstructor(ConstructorDeclaration constructor, String className) {
        String constructorName =  String.format("%s.%s", className, constructor.getNameAsString());
        CustomNode node = new CustomNode(constructor.getNameAsString(), NodeType.CONSTRUCTOR, constructorName);
        graph.nodes.add(node);

        Edge edge = new Edge(constructor.getNameAsString(), className, "");
        graph.edges.add(edge);

        processMethodOrConstructorBody(constructor.getBody(), constructorName);
    }

    private void processMethod(MethodDeclaration method, String className) {
        String methodName =  String.format("%s.%s", className, method.getNameAsString());
        CustomNode node = new CustomNode(method.getNameAsString(), NodeType.METHOD, methodName);
        graph.nodes.add(node);

        Edge edge = new Edge(method.getNameAsString(), className, "");
        graph.edges.add(edge);

        metricsTables.incrementNumberOfMethods(className);
        method.getBody().ifPresent(body -> processMethodOrConstructorBody(body, methodName));
    }

    private void processMethodOrConstructorBody(BlockStmt blockStmt, String methodName) {
        for (Statement statement: blockStmt.getStatements()) {
            processStatementRecursively(statement, methodName, methodName);
        }
    }

    private void processStatementRecursively(Statement statement, String methodName, String parentNode) {
        //todo - stack overflow
        if (statement.isForEachStmt() || statement.isForStmt() || statement.isWhileStmt() || statement.isDoStmt()) {
            String newNodeName = "loop_" + statement.hashCode();
            addLoopNode(parentNode, newNodeName);
            metricsTables.incrementNumberOfNestedLoops(methodName);
            for (Node node: statement.getChildNodes()) {
                if (isNodeWithBody(node)) {
                    processStatementRecursively(statement, methodName, newNodeName);
                }
                checkNodeForMethodCall(node, methodName);
            }
        } else if (statement.isIfStmt()) {
            String newNodeName = "if_" + statement.hashCode();
            addIfNode(parentNode, newNodeName);
            for (Node node: statement.getChildNodes()) {
                if (isNodeWithBody(node)) {
                    processStatementRecursively(statement, methodName, newNodeName);
                }
                checkNodeForMethodCall(node, methodName);
            }
        } else if (statement.isBlockStmt() || statement.isSwitchStmt() || statement.isSynchronizedStmt()
                || statement.isTryStmt()) {
            for (Node node: statement.getChildNodes()) {
                if (isNodeWithBody(node)) {
                    processStatementRecursively(statement, methodName, parentNode);
                }
                checkNodeForMethodCall(node, methodName);
            }
        }
    }

    private void addLoopNode(String fromNode, String toNode) {
        CustomNode node = new CustomNode(toNode, NodeType.LOOP_STATEMENT, toNode);
        graph.nodes.add(node);

        Edge edge = new Edge(fromNode, toNode, "");
        graph.edges.add(edge);
    }

    private void addIfNode(String fromNode, String toNode) {
        CustomNode node = new CustomNode(toNode, NodeType.IF_STATEMENT, toNode);
        graph.nodes.add(node);

        Edge edge = new Edge(fromNode, toNode, "");
        graph.edges.add(edge);
    }

    private void checkNodeForMethodCall(Node node, String methodName) {
        if (node instanceof MethodCallExpr) {
            //assert node instanceof MethodCallExpr;
            MethodCallExpr methodCall = (MethodCallExpr) node;
            methodCall.getScope().ifPresent(scope -> {
                if (!scope.isThisExpr()) {
                    metricsTables.incrementNumberOfMethodsCalled(methodName);
                }
            });
        }
    }

    private boolean isNodeWithBody(Node node) {
        Class nodeClass = node.getClass();
        List<Class> nodesList = Arrays.asList(BlockStmt.class, TryStmt.class, WhileStmt.class, IfStmt.class,
                ForStmt.class, ForEachStmt.class, SwitchStmt.class, SynchronizedStmt.class,
                ConditionalExpr.class, LambdaExpr.class, InitializerDeclaration.class);
        return nodesList.contains(nodeClass);
    }
}
