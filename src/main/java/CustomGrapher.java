package main.java;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.components.*;
import tech.tablesaw.plotly.traces.HistogramTrace;
import tech.tablesaw.plotly.traces.ScatterTrace;
import tech.tablesaw.plotly.traces.Trace;

import java.util.List;

public class CustomGrapher {
    Line grey = Line.builder().color("grey").build();
    Line skyBlue = Line.builder().color("deepskyblue").build();
    Line princetonOrange = Line.builder().color("darkorange").build();
    Line purple = Line.builder().color("purple").build();
    Line green = Line.builder().color("green").build();

    // Create a 2 column table with the headers xLabel and yLabel
    public Table createTable(String xLabel, double[] xValues, String yLabel, double[] yValues1) {
        DoubleColumn col1 = DoubleColumn.create(xLabel, xValues);

        DoubleColumn col2 = DoubleColumn.create(yLabel, yValues1);

        return Table.create("Table of Values", col1, col2);
    }

    // Create a table specifically built for the comparison tool
    public Table createCompTable(String xLabel, double[] xValues, double[][] data) {
        int size = data.length;
        DoubleColumn[] columns = new DoubleColumn[size + 1];

        String[] names = new String[5];
        names[0] = xLabel;
        names[1] = "MinCount";
        names[2] = "ProbabilisticCounting";
        names[3] = "HyperBitBit";
        names[4] = "Your Algorithm";

        columns[0] = DoubleColumn.create(names[0], xValues);

        for (int i = 0; i < data.length; i++) {
            columns[i + 1] = DoubleColumn.create(names[i + 1], data[i]);
        }

        return Table.create("Table of Values", columns);
    }

    // create Table with X columns, with the first being labeled xLabel and the rest labeled with headers
    public Table createTable(String xLabel, double[] xValues, double[][] data, String[] headers) {
        int size = data.length;
        DoubleColumn[] columns = new DoubleColumn[size + 1];

        String name = xLabel;
        columns[0] = DoubleColumn.create(name, xValues);

        for (int i = 0; i < data.length; i++) {
            name = headers[i];
            columns[i + 1] = DoubleColumn.create(name, data[i]);
        }

        return Table.create("Table of Values", columns);
    }

    // Create a table with xValues, an average, and data columns
    // Meant for the background single trials and the foreground average
    public Table createTable(String xLabel, double[] xValues, double[] avg, double[][] data) {
        int size = data.length;
        DoubleColumn[] columns = new DoubleColumn[size + 2];

        String name = xLabel;
        columns[0] = DoubleColumn.create(name, xValues);
        columns[1] = DoubleColumn.create("Average", avg);

        for (int i = 0; i < data.length; i++) {
            name = "Series" + (i + 2);
            columns[i + 2] = DoubleColumn.create(name, data[i]);
        }

        return Table.create("Table of Values", columns);
    }

    // Show a line plot with the data from table and the labels as specified
    public void showLinePlot(String title, String xLabel, String yLabel, Table table) {
        List<String> columns = table.columnNames();
        int size = columns.size();
        Trace[] traces = new Trace[size - 1];

        Axis xAxis = Axis.builder().title(xLabel).build();
        Axis yAxis = Axis.builder().title(yLabel).build();

        for (int i = 1; i < size; i++) {
            traces[i - 1] =
                    ScatterTrace.builder(table.column(0), table.column(i))
                            .mode(ScatterTrace.Mode.LINE)
                            .showLegend(false)
                            .line(grey)
                            .opacity(0.25)
                            .build();
        }

        traces[traces.length - 1] =
                ScatterTrace.builder(table.column(0), table.column(1))
                        .mode(ScatterTrace.Mode.LINE)
                        .showLegend(true)
                        .line(princetonOrange)
                        .name(columns.get(1))
                        .build();

        Layout layout =
                Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(yAxis).build();
        Plot.show(new Figure(layout, traces));
    }

    // Show a line plot like above, but only with numShown single trials
    /* I.e. if the example is run over 500 trials, the above method would show an average
       in the foreground and 500 trials in the background. This method would show only
       numShown trials in the background, sampled evenly from the 500 */
    public void showLinePlot(String title, String xLabel, String yLabel, Table table, int numShown) {
        List<String> columns = table.columnNames();
        int size = columns.size();
        Trace[] traces = new Trace[numShown + 1];

        Axis xAxis = Axis.builder().title(xLabel).build();
        Axis yAxis = Axis.builder().title(yLabel).build();

        int whichCol = 0;
        int separation = size / numShown;
        for (int i = 1; i < numShown + 1; i++) {
            whichCol += separation;
            traces[i - 1] =
                    ScatterTrace.builder(table.column(0), table.column(whichCol))
                            .mode(ScatterTrace.Mode.LINE)
                            .showLegend(false)
                            .line(grey)
                            .opacity(0.25)
                            .build();
        }

        traces[traces.length - 1] =
                ScatterTrace.builder(table.column(0), table.column(1))
                        .mode(ScatterTrace.Mode.LINE)
                        .showLegend(true)
                        .line(princetonOrange)
                        .name(columns.get(1))
                        .build();

        Layout layout =
                Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(yAxis).build();
        Plot.show(new Figure(layout, traces));
    }

    // Show a scatter plot with the data from table and the labels as specified
    public void showScatterPlot(String title, String xLabel, String yLabel, Table table) {
        List<String> columns = table.columnNames();
        int size = columns.size();
        Trace[] traces = new Trace[size - 1];

        Axis xAxis = Axis.builder().title(xLabel).build();
        Axis yAxis = Axis.builder().title(yLabel).build();

        for (int i = 1; i < size; i++) {
            traces[i - 1] =
                    ScatterTrace.builder(table.column(0), table.column(i))
                            .mode(ScatterTrace.Mode.MARKERS)
                            .showLegend(false)
                            .line(grey)
                            .opacity(0.25)
                            .build();
        }

        traces[traces.length - 1] =
                ScatterTrace.builder(table.column(0), table.column(1))
                        .mode(ScatterTrace.Mode.MARKERS)
                        .showLegend(true)
                        .line(princetonOrange)
                        .name(columns.get(1))
                        .build();

        Layout layout =
                Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(yAxis).build();
        Plot.show(new Figure(layout, traces));
    }

    // Show a scatter plot like above, but only with numShown single trials
    /* I.e. if the example is run over 500 trials, the above method would show an average
       in the foreground and 500 trials in the background. This method would show only
       numShown trials in the background, sampled evenly from the 500 */
    public void showScatterPlot(String title, String xLabel, String yLabel, Table table, int numShown) {
        List<String> columns = table.columnNames();
        int size = columns.size();
        Trace[] traces = new Trace[numShown + 1];

        Axis xAxis = Axis.builder().title(xLabel).build();
        Axis yAxis = Axis.builder().title(yLabel).build();

        int whichCol = 0;
        int separation = size / numShown;
        for (int i = 1; i < numShown + 1; i++) {
            whichCol += separation;
            traces[i - 1] =
                    ScatterTrace.builder(table.column(0), table.column(whichCol))
                            .mode(ScatterTrace.Mode.MARKERS)
                            .showLegend(false)
                            .line(grey)
                            .opacity(0.25)
                            .build();
        }

        traces[traces.length - 1] =
                ScatterTrace.builder(table.column(0), table.column(1))
                        .mode(ScatterTrace.Mode.MARKERS)
                        .showLegend(true)
                        .line(princetonOrange)
                        .name(columns.get(1))
                        .build();

        Layout layout =
                Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(yAxis).build();
        Plot.show(new Figure(layout, traces));
    }

    // Shows a line plot with the three established algorithms at 25% opacity and the new alg at 100% opacity
    public void showCompLinePlot(String title, String xLabel, String yLabel, Table table) {
        List<String> columns = table.columnNames();
        int size = columns.size();
        Trace[] traces = new Trace[size - 1];

        Axis xAxis = Axis.builder().title(xLabel).build();
        Axis yAxis = Axis.builder().title(yLabel).build();

        for (int i = 1; i < size; i++) {
            Line line;
            switch (i % 3) {
                case 0:
                    line = purple;
                    break;
                case 1:
                    line = skyBlue;
                    break;
                case 2:
                    line = green;
                    break;
                default:
                    line = grey;
            }
            traces[i - 1] =
                    ScatterTrace.builder(table.column(0), table.column(i))
                            .mode(ScatterTrace.Mode.LINE)
                            .showLegend(true)
                            .line(line)
                            .opacity(0.25)
                            .name(columns.get(i))
                            .build();
        }

        traces[traces.length - 1] =
                ScatterTrace.builder(table.column(0), table.column(4))
                        .mode(ScatterTrace.Mode.LINE)
                        .showLegend(true)
                        .line(princetonOrange)
                        .name(columns.get(4))
                        .build();

        Layout layout =
                Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(yAxis).build();
        Plot.show(new Figure(layout, traces));
    }

    // Shows a scatter plot with the three established algorithms at 25% opacity and the new alg at 100% opacity
    public void showCompScatterPlot(String title, String xLabel, String yLabel, Table table) {
        List<String> columns = table.columnNames();
        int size = columns.size();
        Trace[] traces = new Trace[size - 1];

        Axis xAxis = Axis.builder().title(xLabel).build();
        Axis yAxis = Axis.builder().title(yLabel).build();

        for (int i = 1; i < size; i++) {
            Line line;
            switch (i % 3) {
                case 0:
                    line = purple;
                    break;
                case 1:
                    line = skyBlue;
                    break;
                case 2:
                    line = green;
                    break;
                default:
                    line = grey;
            }
            traces[i - 1] =
                    ScatterTrace.builder(table.column(0), table.column(i))
                            .mode(ScatterTrace.Mode.MARKERS)
                            .showLegend(true)
                            .line(line)
                            .opacity(0.25)
                            .name(columns.get(i))
                            .build();
        }

        traces[traces.length - 1] =
                ScatterTrace.builder(table.column(0), table.column(4))
                        .mode(ScatterTrace.Mode.MARKERS)
                        .showLegend(true)
                        .line(princetonOrange)
                        .name(columns.get(4))
                        .build();

        Layout layout =
                Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(yAxis).build();
        Plot.show(new Figure(layout, traces));
    }

    // Shows a histogram of data
    public void showHistogram(String title, double[] data, int n, int maxRange) {
        Axis xAxis = Axis.builder().fixedRange(true).range(n - maxRange, n + maxRange).build();
        Marker princetonOrange = Marker.builder().color("darkorange").build();
        Trace trace =
                HistogramTrace.builder(data).autoBinX(false).nBinsX(10).marker(princetonOrange).build();
        Layout layout = Layout.builder().title(title).height(600).xAxis(xAxis).width(800).build();
        Plot.show(new Figure(layout, trace));
    }

    // Shows a distribution of each row in data, split into bins sections
    public void showDistribution(
            String title, double[][] data, int bigN, int m, int maxRange, int bins) {
        double[] xValues = new double[bins];
        double[][] yValues = new double[data.length][bins];
        String[] headers = new String[data.length];

        int binRange = (2 * maxRange) / bins;
        if (binRange <= 0) binRange = 1;
        for (int i = 0; i < bins; i++) xValues[i] = bigN - maxRange + (binRange * i);

        int i = 0;
        for (double[] datum : data) {

            for (double value : datum) {
                if (((int) value / binRange) < bins) yValues[i][(int) value / binRange]++;
            }

            switch (i) {
                case 0:
                    headers[i] = "m = 2";
                    break;
                case 1:
                    headers[i] = "m = " + m / 8;
                    break;
                default:
                    headers[i] = "m = " + m;
            }

            i++;
        }

        showLineDistributions(title, createTable(title, xValues, yValues, headers), maxRange);
    }

    // produces a plot with the distributions from above overlaid on each other
    private void showLineDistributions(String title, Table table, int maxX) {
        Axis xAxis = Axis.builder().range(0, maxX).build();
        List<String> columns = table.columnNames();
        Trace[] traces = new Trace[columns.size() - 1];

        Line grey = Line.builder().color("grey").build();
        Line purple = Line.builder().color("purple").build();
        Line skyBlue = Line.builder().color("deepskyblue").build();
        Line princetonOrange = Line.builder().color("darkorange").build();

        Line line;
        for (int i = 0; i < traces.length; i++) {
            switch (i % 3) {
                case 0:
                    line = purple;
                    break;
                case 1:
                    line = skyBlue;
                    break;
                case 2:
                    line = princetonOrange;
                    break;
                default:
                    line = grey;
            }
            traces[i] =
                    ScatterTrace.builder(table.column(0), table.column(i + 1))
                            .mode(ScatterTrace.Mode.LINE)
                            .showLegend(true)
                            .line(line)
                            .name(columns.get(i + 1))
                            .build();
        }

        Layout layout = Layout.builder().title(title).xAxis(xAxis).height(600).width(800).build();
        Plot.show(new Figure(layout, traces));
    }

    // produces a plot with the distributions from above overlaid on each other
    private void showScatterDistributions(String title, Table table, int maxX) {
        Axis xAxis = Axis.builder().range(0, maxX).build();
        List<String> columns = table.columnNames();
        Trace[] traces = new Trace[columns.size() - 1];

        Line grey = Line.builder().color("grey").build();
        Line purple = Line.builder().color("purple").build();
        Line skyBlue = Line.builder().color("deepskyblue").build();
        Line princetonOrange = Line.builder().color("darkorange").build();

        Line line;
        for (int i = 0; i < traces.length; i++) {
            switch (i % 3) {
                case 0:
                    line = purple;
                    break;
                case 1:
                    line = skyBlue;
                    break;
                case 2:
                    line = princetonOrange;
                    break;
                default:
                    line = grey;
            }
            traces[i] =
                    ScatterTrace.builder(table.column(0), table.column(i + 1))
                            .mode(ScatterTrace.Mode.MARKERS)
                            .showLegend(true)
                            .line(line)
                            .name(columns.get(i + 1))
                            .build();
        }

        Layout layout = Layout.builder().title(title).xAxis(xAxis).height(600).width(800).build();
        Plot.show(new Figure(layout, traces));
    }

    public static void main(String[] args) {
        double[] xValues = {0, 1, 2, 3, 4, 5};

        double[][] yValues = {{0, 1, 2, 3, 4, 5}, {0, 1, 4, 9, 16, 25}};
        double[] avg = {0, 1, 3, 6, 10, 15};
        String[] headers = {"x", "x^2"};
        CustomGrapher graph = new CustomGrapher();

        graph.showLinePlot("y = x", "x", "y", graph.createTable("x", xValues, avg, yValues));
        graph.showScatterPlot(
                "y = x; y = x^2", headers[0], headers[1], graph.createTable("x", xValues, yValues, headers));
        graph.showScatterPlot(
                "y = x; y = x^2", headers[0], headers[1], graph.createTable("x", xValues, yValues, headers), 1);
        graph.showCompLinePlot("y = x; y = avg", headers[0], "avg", graph.createTable("x", xValues, yValues, headers));
        graph.showCompScatterPlot("y = x; y = avg", headers[0], "avg", graph.createTable("x", xValues, yValues, headers));

        graph.showHistogram("y = x", xValues, 6, 10);
        graph.showScatterDistributions("y = x", graph.createTable("x", xValues, "y", yValues[0]), 10);
        graph.showScatterDistributions("y = x^2", graph.createTable("x", xValues, "y", yValues[0]), 30);
    }
}

