import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.visitor.VoidVisitor;
import container.MetricsTables;
import graph.Graph;
import utils.DrawingUtils;
import visitors.ContainmentVisitor;

import java.io.*;
import java.util.Objects;
import java.util.Scanner;

public class Main {
    private static File DIRECTORY_WINDOWS = new File(".\\examples");
    private static File DIRECTORY_LINUX = new File("./examples");

    public static void main(String... args) throws IOException {
        Scanner in = new Scanner(System.in);
        File directory = getDirectoryFromUser(in);
        in.close();
        analyzeFilesFromDirectory(directory);
  //      outputResults(nodesHashContainer);
    }

    private static File getDirectoryFromUser(Scanner in) {
        System.out.println("To use a standard input directory, type 's', otherwise type your own directory :\n");
        File directory = (System.getProperty("os.name").contains("Windows"))
                ? DIRECTORY_WINDOWS
                : DIRECTORY_LINUX;
        try {
            String inputDirectory = in.next();

            if (!"s".equals(inputDirectory)) {
                File customDirectory = new File(inputDirectory);
                if (customDirectory.exists() && customDirectory.canRead()
                        && (Objects.requireNonNull(directory.listFiles()).length > 0)) {
                    directory = customDirectory;
                } else {
                    System.out.println("Directory not found, please try again...\n");
                    directory = getDirectoryFromUser(in);
                }
            }
        } catch (NullPointerException e) {
            System.out.println("Directory not found, please try again...\n");
            directory = getDirectoryFromUser(in);
        }
        return directory;
    }

    private static void analyzeFilesFromDirectory(File directory)
            throws IOException {
        Graph classContainmentGraph = new Graph();
        MetricsTables metricsTables = new MetricsTables();

        for (final File fileEntry : Objects.requireNonNull(directory.listFiles())) {
            CompilationUnit compilationUnit = StaticJavaParser.parse(fileEntry);

            VoidVisitor<?> containmentVisitor = new ContainmentVisitor(classContainmentGraph, metricsTables);
            containmentVisitor.visit(compilationUnit, null);
        }

        outputToFile(classContainmentGraph.toString(), "graph.gml");
        outputToFile(DrawingUtils.getChartGml(metricsTables.getMetricsOfClasses(), 1), "chartRFC.gml");
        outputToFile(DrawingUtils.getChartGml(metricsTables.getMetricsOfClasses(), 0), "chartNOM.gml");
        System.out.println(metricsTables.getMetricsOfClasses().toString());
    }

    private static void outputToFile(String output, String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        writer.write(output);
        writer.close();
    }
}
