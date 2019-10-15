import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import container.MetricsTables;
import graph.Graph;
import visitors.ContainmentVisitor;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;

public class Main {
    public static File DIRECTORY = new File(".\\examples");

    public static void main(String... args) throws FileNotFoundException {

        Graph classHierarchyGraph = new Graph();
        MetricsTables metricsTables = new MetricsTables();

        for (final File fileEntry : Objects.requireNonNull(DIRECTORY.listFiles())) {
            CompilationUnit compilationUnit = StaticJavaParser.parse(fileEntry);

            VoidVisitor<?> containmentVisitorTest = new ContainmentVisitor(classHierarchyGraph);
            containmentVisitorTest.visit(compilationUnit, null);
//            VoidVisitor<?> classContainmentVisitor = new ClassContainmentVisitor(classHierarchyGraph, metricsTables);
//            classContainmentVisitor.visit(compilationUnit, null);
        }
        System.out.println(classHierarchyGraph.toString());
    }
}
