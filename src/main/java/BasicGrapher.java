package main.java;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.Histogram;
import tech.tablesaw.plotly.api.LinePlot;
import tech.tablesaw.plotly.api.ScatterPlot;

import java.util.List;

public class BasicGrapher {
  public Table createTable(String xLabel, double[] xValues, String yLabel1, double[] yValues1) {
    DoubleColumn col1 = DoubleColumn.create(xLabel, xValues);

    DoubleColumn col2 = DoubleColumn.create(yLabel1, yValues1);

    return Table.create("Table of Values", col1, col2);
  }

  public Table createTable(
      String xLabel,
      double[] xValues,
      String yLabel,
      String yLabel1,
      double[] yValues1,
      String yLabel2,
      double[] yValues2) {
    DoubleColumn col1 = DoubleColumn.create(xLabel, xValues);
    col1.append(col1);

    DoubleColumn col2 = DoubleColumn.create(yLabel, yValues1);

    DoubleColumn col3 = DoubleColumn.create(yLabel, yValues2);
    col2.append(col3);

    String[] labels = new String[col2.size()];
    for (int i = 0; i < (col2.size() / 2); i++) {
      labels[i] = yLabel1;
      labels[i + (col2.size() / 2)] = yLabel2;
    }

    StringColumn series = StringColumn.create("Series Names", labels);

    return Table.create("Table of Values", col1, col2, series);
  }

  public void showLinePlot(String title, Table table) {
    List<String> columns = table.columnNames();
    if (columns.size() > 2) {
      Plot.show(
          LinePlot.create(
              title, // Title
              table,
              columns.get(0), // x-Axis Label
              columns.get(1), // y-Axis Label
              columns.get(2))); // Group Name Column
    } else {
      Plot.show(
          LinePlot.create(
              title,
              table,
              columns.get(0), // x-Axis Label
              columns.get(1))); // y-Axis Label
    }
  }

  public void showScatterPlot(String title, Table table) {
    List<String> columns = table.columnNames();
    if (columns.size() > 2) {
      Plot.show(
          ScatterPlot.create(
              title, // Title
              table,
              columns.get(0), // x-Axis Label
              columns.get(1), // y-Axis Label
              columns.get(2))); // Group Name Column
    } else {
      Plot.show(
          ScatterPlot.create(
              title,
              table,
              columns.get(0), // x-Axis Label
              columns.get(1))); // y-Axis Label
    }
  }

  public void showHistogram(String title, double[] data) {
    Plot.show(Histogram.create(title, data));
  }

  public static void main(String[] args) {
    double[] xValues = {0, 1, 2, 3, 4, 5};
    double[] yValues1 = {0, 1, 2, 3, 4, 5};
    double[] yValues2 = {0, 1, 4, 9, 16, 25};

    double[][] yValues = {{0, 1, 2, 3, 4, 5}, {0, 1, 4, 9, 16, 25}};
    double[] avg = {0, 1, 3, 6, 10, 15};
    BasicGrapher graph = new BasicGrapher();

    graph.showLinePlot(
        "y = x", graph.createTable("x", xValues, "y", "x", yValues1, "x^2", yValues2));
    graph.showScatterPlot(
        "y = x; y = x^2", graph.createTable("x", xValues, "y", "x", yValues1, "x^2", yValues2));
  }
}
