import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import container.MetricsTables;
import graph.Graph;
import visitors.ContainmentVisitor;

import java.io.*;
import java.util.Objects;

public class Main {
    public static File DIRECTORY = new File(".\\examples");

    public static void main(String... args) throws IOException {

        Graph classContainmentGraph = new Graph();
        MetricsTables metricsTables = new MetricsTables();

        for (final File fileEntry : Objects.requireNonNull(DIRECTORY.listFiles())) {
            CompilationUnit compilationUnit = StaticJavaParser.parse(fileEntry);

            VoidVisitor<?> containmentVisitorTest = new ContainmentVisitor(classContainmentGraph);
            containmentVisitorTest.visit(compilationUnit, null);
//            VoidVisitor<?> classContainmentVisitor = new ClassContainmentVisitor(classHierarchyGraph, metricsTables);
//            classContainmentVisitor.visit(compilationUnit, null);
        }
        outputToFile(classContainmentGraph.toGML(), "graph.gml");
    }

    private static void outputToFile(String output, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(output);
        writer.close();
    }
}
