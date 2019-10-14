package visitors;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import container.MetricsTables;
import graph.Edge;
import graph.Graph;
import graph.GraphNode;
import graph.NodeType;

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
        GraphNode node = new GraphNode(n.getNameAsString(), nodeType, n.getNameAsString());
        graph.nodes.add(node);

        for (Node childNode: n.getChildNodes()) {
            Class nodeClass = node.getClass();
            if (nodeClass.equals(ConstructorDeclaration.class)) {
                processConstructor((ConstructorDeclaration) childNode, n.getNameAsString());
            } else if (nodeClass.equals(MethodDeclaration.class)) {
                processMethod((MethodDeclaration) childNode, n.getNameAsString());
            }
        }
    }

    private void processConstructor(ConstructorDeclaration constructor, String className) {
        String constructorName =  String.format("%s.%s", className, constructor.getNameAsString());
        GraphNode node = new GraphNode(constructor.getNameAsString(), NodeType.CONSTRUCTOR, constructorName);
        graph.nodes.add(node);

        Edge edge = new Edge(constructor.getNameAsString(), className, "");
        graph.edges.add(edge);

        processMethodOrConstructorBody(constructor.getBody(), constructorName);
    }

    private void processMethod(MethodDeclaration method, String className) {
        String methodName =  String.format("%s.%s", className, method.getNameAsString());
        GraphNode node = new GraphNode(method.getNameAsString(), NodeType.METHOD, methodName);
        graph.nodes.add(node);

        Edge edge = new Edge(method.getNameAsString(), className, "");
        graph.edges.add(edge);

        method.getBody().ifPresent(body -> processMethodOrConstructorBody(body, methodName));
    }

    private void processMethodOrConstructorBody(BlockStmt blockStmt, String methodName) {
        for (Statement statement: blockStmt.getStatements()) {
            processStatementRecursively(statement, methodName, methodName);
        }
    }

    private void processStatementRecursively(Statement statement, String methodName, String parentNode) {
        //todo - here we need process block statement
        if (statement.isForEachStmt() || statement.isForStmt() || statement.isWhileStmt() || statement.isDoStmt()) {
            String newNodeName = "loop_" + statement.hashCode();
            for (Node node: statement.getChildNodes()) {
                if (Statement.class.isAssignableFrom(node.getClass())) {
                    processStatementRecursively(statement, methodName, newNodeName);
                }
            }
        } else if (statement.isIfStmt()) {
            String newNodeName = "if_" + statement.hashCode();
            for (Node node: statement.getChildNodes()) {
                if (Statement.class.isAssignableFrom(node.getClass())) {
                    processStatementRecursively(statement, methodName, newNodeName);
                }
            }
            //statement.asIfStmt().getElseStmt();
            //statement.asIfStmt().getThenStmt();
        } else if (statement.isBlockStmt() || statement.isSwitchStmt() || statement.isSynchronizedStmt()
                || statement.isTryStmt()) {
            for (Node node: statement.getChildNodes()) {
                if (Statement.class.isAssignableFrom(node.getClass())) {
                    processStatementRecursively(statement, methodName, parentNode);
                }
            }
        }
//        statement.ifForEachStmt(st -> {
//            String name = "for_each_node_" + st.hashCode();
//            addIfNode(parentNode, name);
//            for (Statement child: st.getBody().asBlockStmt().getStatements()) {
//                processStatementRecursively(child, methodName, name);
//            }
//        });
//        statement.ifForStmt(st -> {
//            String name = "for_node_" + st.hashCode();
//            addIfNode(parentNode, name);
//            for (Statement child: st.getBody().asBlockStmt().getStatements()) {
//                processStatementRecursively(child, methodName, name);
//            }
//        });
//        statement.ifWhileStmt(st -> {
//            String name = "while_node_" + st.hashCode();
//            addIfNode(parentNode, name);
//            for (Statement child: st.getBody().asBlockStmt().getStatements()) {
//                processStatementRecursively(child, methodName, name);
//            }
//        });
//        statement.ifIfStmt(st -> {
//            String name = "is_node_" + st.hashCode();
//            addIfNode(parentNode, name);
//            for (Statement child: st.asIfStmt().getStatements()) {
//                processStatementRecursively(child, methodName, name);
//            }
//        });
    }

    private void addLoopNode(String fromNode, String toNode) {
        GraphNode node = new GraphNode(toNode, NodeType.LOOP_STATEMENT, toNode);
        graph.nodes.add(node);

        Edge edge = new Edge(fromNode, toNode, "");
        graph.edges.add(edge);
    }

    private void addIfNode(String fromNode, String toNode) {
        GraphNode node = new GraphNode(toNode, NodeType.IF_STATEMENT, toNode);
        graph.nodes.add(node);

        Edge edge = new Edge(fromNode, toNode, "");
        graph.edges.add(edge);
    }
}
