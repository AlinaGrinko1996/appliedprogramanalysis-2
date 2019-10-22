package visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import container.MetricsTables;
import container.Scope;
import graph.CustomNode;
import graph.Graph;
import graph.NodeType;

public class ContainmentVisitor extends VoidVisitorAdapter<Void> {
    private Scope scope;
    private MetricsTables metricsTables;

    public ContainmentVisitor(Graph graph, MetricsTables metricsTables) {
        super();
        scope = new Scope();
        scope.setGraph(graph);
        this.metricsTables = metricsTables;
    }

    @Override
    public void visit(final ClassOrInterfaceDeclaration n, Void arg) {
        scope.enterClass(n.getNameAsString());
        super.visit(n, arg);
        scope.leaveScope();
    }

    @Override
    public void visit(final MethodDeclaration n, Void arg) {
        scope.enterMethod(n.getNameAsString());
        super.visit(n, arg);
        metricsTables.incrementNumberOfMethods(scope.getCurrentClass());
        scope.leaveScope();
    }

    @Override
    public void visit(final ConstructorDeclaration n, Void arg) {
        scope.enterConstructor(n.getNameAsString());
        super.visit(n, arg);
        metricsTables.incrementNumberOfMethods(scope.getCurrentClass());
        scope.leaveScope();
    }

    @Override
    public void visit(final IfStmt n, Void arg) {
        scope.enterConditional("if_stmt");
        super.visit(n, arg);
        scope.leaveScope();
    }

    @Override
    public void visit(final SwitchStmt n, Void arg) {
        scope.enterConditional("switch_stmt");
        super.visit(n, arg);
        scope.leaveScope();
    }

    @Override
    public void visit(final ForStmt n, Void arg) {
        scope.enterLoop("for_loop");
        super.visit(n, arg);
        metricsTables.refreshNumberOfLoops(scope);
        scope.leaveScope();
    }

    @Override
    public void visit(final WhileStmt n, Void arg) {
        scope.enterLoop("while_loop");
        super.visit(n, arg);
        metricsTables.refreshNumberOfLoops(scope);
        scope.leaveScope();
    }

    @Override
    public void visit(final ForEachStmt n, Void arg) {
        scope.enterLoop("foreach_loop");
        super.visit(n, arg);
        metricsTables.refreshNumberOfLoops(scope);
        scope.leaveScope();
    }

    @Override
    public void visit(final MethodCallExpr n, Void arg) {
        CustomNode temporaryCallNode = new CustomNode(0, NodeType.INNER_CALL_STATEMENT, "Call: "+ n.getNameAsString());
        super.visit(n, arg);
        n.getScope().ifPresent(callScope -> {
            if (!callScope.isThisExpr()) {
                metricsTables.addMethodCalled(scope.getCurrentClass(), n.getNameAsString());
                temporaryCallNode.setNodeType(NodeType.OUTER_CALL_STATEMENT);
                temporaryCallNode.setLabel(String.format("PCall: %s", n.getNameAsString()));
            } else {
                temporaryCallNode.setLabel("Call: this." + n.getNameAsString());
            }
        });
        scope.addMethodCall(temporaryCallNode.getLabel(), temporaryCallNode.getNodeType());
        scope.leaveScope();
    }
}
