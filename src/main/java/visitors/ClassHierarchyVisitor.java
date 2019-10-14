package visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import graph.Edge;
import graph.Graph;
import graph.GraphNode;
import graph.NodeType;
import utils.ParserUtils;

public class ClassHierarchyVisitor extends VoidVisitorAdapter<Void> {
    private Graph graph;

    public ClassHierarchyVisitor(Graph graph) {
        super();
        this.graph = graph;
    }

    @Override
    public void visit(final ClassOrInterfaceDeclaration n, Void arg) {
        super.visit(n, arg);

        NodeType nodeType  = n.isInterface() ? NodeType.INTERFACE : NodeType.CLASS;
        GraphNode node = new GraphNode(n.getNameAsString(), nodeType, n.getNameAsString());
        graph.nodes.add(node);

        for (ClassOrInterfaceType classOrInterface : n.getExtendedTypes()) {
            Edge edge = new Edge(n.getNameAsString(), classOrInterface.getNameAsString(), "<extents>");
            graph.edges.add(edge);
        }

        for (ClassOrInterfaceType classOrInterface : n.getImplementedTypes()) {
            Edge edge = new Edge(n.getNameAsString(), classOrInterface.getNameAsString(), "<implements>");
            graph.edges.add(edge);
        }

        String innerClass = ParserUtils.getInnerClass(n);
        String outerClass = n.getNameAsString();
        if (!innerClass.equalsIgnoreCase(outerClass)) {
            Edge edge = new Edge(ParserUtils.getInnerClass(n), n.getNameAsString(), "<inner class>");
            graph.edges.add(edge);
        }
    }
}
