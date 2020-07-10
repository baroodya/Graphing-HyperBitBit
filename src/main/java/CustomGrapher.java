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
  public Table createTable(String xLabel, double[] xValues, String yLabel, double[] yValues1) {
    DoubleColumn col1 = DoubleColumn.create(xLabel, xValues);

    DoubleColumn col2 = DoubleColumn.create(yLabel, yValues1);

    return Table.create("Table of Values", col1, col2);
  }

  public Table createTable(String xLabel, double[] xValues, double[][] data) {
    int size = data.length;
    DoubleColumn[] columns = new DoubleColumn[size + 2];

    String name = xLabel;
    columns[0] = DoubleColumn.create(name, xValues);

    for (int i = 0; i < data.length; i++) {
      name = "Series" + (i + 1);
      columns[i + 1] = DoubleColumn.create(name, data[i]);
    }

    return Table.create("Table of Values", columns);
  }

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

  public void showLinePlot(String title, String xLabel, String yLabel, Table table) {
    List<String> columns = table.columnNames();
    int size = columns.size();
    Trace[] traces = new Trace[size - 1];

    Axis xAxis = Axis.builder().title(xLabel).build();
    Axis yAxis = Axis.builder().title(yLabel).build();
    Line grey = Line.builder().color("grey").build();
    Line skyBlue = Line.builder().color("deepskyblue").build();
    Line princetonOrange = Line.builder().color("darkorange").build();

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

  public void showLinePlot(String title, String xLabel, String yLabel, Table table, int numShown) {
    List<String> columns = table.columnNames();
    int size = columns.size();
    Trace[] traces = new Trace[numShown + 1];

    Axis xAxis = Axis.builder().title(xLabel).build();
    Axis yAxis = Axis.builder().title(yLabel).build();
    Line grey = Line.builder().color("grey").build();
    Line skyBlue = Line.builder().color("deepskyblue").build();
    Line princetonOrange = Line.builder().color("darkorange").build();

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

  public void showHistogram(String title, double[] data, int n, int maxRange) {
    Axis xAxis = Axis.builder().fixedRange(true).range(n - maxRange, n + maxRange).build();
    Marker skyBlue = Marker.builder().color("deepskyblue").build();
    Marker princetonOrange = Marker.builder().color("darkorange").build();
    Trace trace =
        HistogramTrace.builder(data).autoBinX(false).nBinsX(10).marker(princetonOrange).build();
    Layout layout = Layout.builder().title(title).height(600).xAxis(xAxis).width(800).build();
    Plot.show(new Figure(layout, trace));
  }

  public void showDistribution(
      String title, double[][] data, int bigN, int m, int maxRange, int bins) {
    double[] xValues = new double[bins];
    double[][] yValues = new double[data.length][bins];
    String[] headers = new String[data.length];

    int binRange = (2 * maxRange) / bins;
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

    showDistributions(createTable(title, xValues, yValues, headers));
  }

  private void showDistributions(Table table) {
    List<String> columns = table.columnNames();
    int size = columns.size();
    Trace[] traces = new Trace[table.columnCount() - 1];

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

    Layout layout = Layout.builder().title(table.name()).height(600).width(800).build();
    Plot.show(new Figure(layout, traces));
  }

  public static void main(String[] args) {
    double[] xValues = {0, 1, 2, 3, 4, 5};
    double[] yValues1 = {0, 1, 2, 3, 4, 5};
    double[] yValues2 = {0, 1, 4, 9, 16, 25};

    double[][] yValues = {{0, 1, 2, 3, 4, 5}, {0, 1, 4, 9, 16, 25}};
    double[] avg = {0, 1, 3, 6, 10, 15};
    CustomGrapher graph = new CustomGrapher();

    graph.showLinePlot("y = x", "x", "y", graph.createTable("x", xValues, avg, yValues));
    //    graph.showScatterPlot(
    //        "y = x; y = x^2", graph.createTable("x", xValues, "y", "x", yValues1, "x^2",
    // yValues2));
  }
}
