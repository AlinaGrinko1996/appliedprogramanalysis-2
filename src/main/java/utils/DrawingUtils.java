package utils;

import container.MetricsOfClass;
import graph.CustomNode;
import graph.Edge;
import graph.Graph;

import java.util.List;

public class DrawingUtils {
    //Point Graphics
    private static String POINT_GRAPHICS = "graphics\n[\n" +
            "\tx\t%d\n\ty\t%d\n\tw\t5.0\n\th\t5.0\n" +
            "\ttype\t\"ellipse\"\n\traisedBorder\t0\n" +
            "\tfill\t\"#ffffff\"\n\toutline\t\"#ffffff\"\n] ]\n";
    private static String POINT_GRAPHICS_LABELED = "graphics\n[\n" +
            "\tx\t%d\n\ty\t%d\n\tw\t5.0\n\th\t5.0\n" +
            "\ttype\t\"ellipse\"\n\traisedBorder\t0\n" +
            "\tfill\t\"#ffffff\"\n\toutline\t\"#ffffff\"\n" +
            "] LabelGraphics\n[\n\ttext\t\"%s\"\n" +
            "\tfontSize\t16\n\tfontName\t\"Dialog\"\n" +
            "\talignment\t\"left\"\n\tanchor\t\"c\"\n" +
            "\tborderDistance\t40.0\n] ]\n";
    private static String AXIS_GRAPHICS = "\tgraphics\n\t\t[\n\t\t\tfill\t\"#000000\"\n" +
            "\t\t\ttargetArrow\t\"standard\"\n\t\t] ]\n";
    private static String CLASS_NODE_GRAPHICS = "graphics\n[\n" +
            "\tx\t%d\n\ty\t%d\n\tw\t10.0\n\th\t10.0\n" +
            "\ttype\t\"ellipse\"\n\traisedBorder\t0\n" +
            "\tfill\t\"#FFCC00\"\n\thasOutline\t0\n] ]\n";
    private static String DASHED_LINE_GRAPHICS = "graphics\n[\n" +
            "\tstyle\t\"dotted\"\n\tfill\t\"#000000\"\n] ]\n";

    //Magic numbers
    private static int X_MAX = 600;
    private static int X_0 = 100;
    private static int Y_MAX = 100;
    private static int Y_0 = 600;

    //todo - i should mention that i represent it alternatively with RFC and NOM
    public static String getChartGml(List<MetricsOfClass> classes, int setOfMetric) {
        Graph chart = new Graph();
        String labelY = (setOfMetric > 0) ? "RFC" : "NOM";
        //Drawing axis
        CustomNode pointY = new CustomNode(0, labelY);
        //30 pixels added here are used to avoid overlapping
        pointY.setGraphics(String.format(POINT_GRAPHICS_LABELED, X_0, Y_MAX - 30, labelY));
        chart.nodes.add(pointY);

        CustomNode point0 = new CustomNode(1, "0");
        point0.setGraphics(String.format(POINT_GRAPHICS_LABELED, X_0, Y_0, "0"));
        chart.nodes.add(point0);

        CustomNode pointX = new CustomNode(2, "WMC");
        //30 pixels added here are used to avoid overlapping
        pointX.setGraphics(String.format(POINT_GRAPHICS_LABELED, X_MAX + 30, Y_0, "WMC"));
        chart.nodes.add(pointX);

        Edge axisY = new Edge(point0, pointY, "");
        axisY.setGraphics(AXIS_GRAPHICS);
        chart.edges.add(axisY);

        Edge axisX = new Edge(point0, pointX, "");
        axisX.setGraphics(AXIS_GRAPHICS);
        chart.edges.add(axisX);
        //End drawing axis

        MetricsOfClass maxMetrics = getMaximumMetrics(classes);

        int nodeIterator = 3;
        for (MetricsOfClass metric: classes) {
            int metricX = metric.getWMC();
            int maxMetricX = maxMetrics.getWMC();
            int metricY = (setOfMetric > 0) ? metric.getRFC() : metric.getNOM();
            int maxMetricY = (setOfMetric > 0) ? maxMetrics.getRFC() : maxMetrics.getNOM();

            int x = X_0 + (metricX * (X_MAX - X_0)) / maxMetricX;
            int y = Y_0 - (metricY * (Y_0 - Y_MAX)) / maxMetricY;

            //Begin nodes
            CustomNode classY = new CustomNode(nodeIterator++, metricY + "");
            classY.setGraphics(String.format(POINT_GRAPHICS, X_0, y));
            chart.nodes.add(classY);

            CustomNode class0 = new CustomNode(nodeIterator++, metric.getClassName());
            class0.setGraphics(String.format(CLASS_NODE_GRAPHICS, x, y));
            chart.nodes.add(class0);

            CustomNode classX = new CustomNode(nodeIterator++, metricX + "");
            classX.setGraphics(String.format(POINT_GRAPHICS, x, Y_0));
            chart.nodes.add(classX);
            //End nodes

            //Begin edges
            Edge dashY = new Edge(class0, classY, "");
            dashY.setGraphics(DASHED_LINE_GRAPHICS);
            chart.edges.add(dashY);

            Edge dashX = new Edge(class0, classX, "");
            dashX.setGraphics(DASHED_LINE_GRAPHICS);
            chart.edges.add(dashX);
            //End edges
        }
        return chart.toString();
    }

    private static MetricsOfClass getMaximumMetrics(List<MetricsOfClass> metricsOfClasses) {
        MetricsOfClass maximumMetrics = new MetricsOfClass("Maximum");
        metricsOfClasses.forEach(metric -> {
            int currentWMCMax = maximumMetrics.getWMC();
            int currentRFCMax = maximumMetrics.getRFC();
            int currentNOMMax = maximumMetrics.getNOM();
            maximumMetrics.setWMC(Math.max(currentWMCMax, metric.getWMC()));
            maximumMetrics.setRFC(Math.max(currentRFCMax, metric.getRFC()));
            maximumMetrics.setNOM(Math.max(currentNOMMax, metric.getNOM()));
        });
        return  maximumMetrics;
    }
}
