package utils;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

//import java.util.Optional;

public class ParserUtils {
    public static String getInnerClass(ClassOrInterfaceDeclaration n) {
        for (Node childNode : n.getChildNodes()) {
            return getClassOfNode(childNode);
        }
        return "";
    }
//    public static String getParentClass(MethodDeclaration n) {
//        Optional<Node> node = n.getParentNode();
//        return node.isPresent() ? getClassOfNode(n.getParentNode().get()) : "";
//    }

    public static String getClassOfNode(Node node) {
        if (node.getClass().equals(ClassOrInterfaceDeclaration.class)) {
            ClassOrInterfaceDeclaration innerClass = (ClassOrInterfaceDeclaration) node;
            return innerClass.getNameAsString();
        } else if (node.getClass().equals(ClassOrInterfaceType.class)) {
            ClassOrInterfaceType innerClass = (ClassOrInterfaceType) node;
            return innerClass.getNameAsString();
        } else if (node.getClass().equals(SimpleName.class)) {
            SimpleName innerClass = (SimpleName) node;
            return innerClass.asString();
        }
        return "";
    }
}
