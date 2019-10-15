package visitors;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;
import container.Scope;
import graph.Graph;

public class ContainmentVisitor extends VoidVisitorAdapter<Void> {
    private Scope scope;

    //todo - class hierarchy?
    public ContainmentVisitor(Graph graph) {
        super();
        scope = new Scope();
        scope.setGraph(graph);
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
        scope.leaveScope();
    }

    @Override
    public void visit(final ConstructorDeclaration n, Void arg) {
        scope.enterConstructor(n.getNameAsString());
        super.visit(n, arg);
        scope.leaveScope();
    }

    @Override
    public void visit(final IfStmt n, Void arg) {
        scope.enterConditional("if_" + n.hashCode());
        super.visit(n, arg);
        scope.leaveScope();
    }

    @Override
    public void visit(final SwitchStmt n, Void arg) {
        scope.enterConditional("switch_" + n.hashCode());
        super.visit(n, arg);
        scope.leaveScope();
    }

    @Override
    public void visit(final ForStmt n, Void arg) {
        scope.enterLoop("for_" + n.hashCode());
        super.visit(n, arg);
        scope.leaveScope();
    }

    @Override
    public void visit(final WhileStmt n, Void arg) {
        scope.enterLoop("while_" + n.hashCode());
        super.visit(n, arg);
        scope.leaveScope();
    }

    @Override
    public void visit(final ForEachStmt n, Void arg) {
        scope.enterLoop("foreach_" + n.hashCode());
        super.visit(n, arg);
        scope.leaveScope();
    }
}
