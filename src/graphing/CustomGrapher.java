package graphing;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.components.*;
import tech.tablesaw.plotly.traces.HistogramTrace;
import tech.tablesaw.plotly.traces.ScatterTrace;
import tech.tablesaw.plotly.traces.Trace;

import java.util.List;

public class CustomGrapher {
  private final Line grey = Line.builder().color("grey").build();
  private final Line skyBlue = Line.builder().color("deepskyblue").build();
  private final Line princetonOrange = Line.builder().color("darkorange").build();
  private final Line purple = Line.builder().color("purple").build();
  private final Line green = Line.builder().color("green").build();
  protected List<String> columns;
  protected int size;
  protected Trace[] traces;
  protected Axis xAxis;
  protected Axis yAxis;
  protected Layout layout;

  // Create a 2 column table with the headers xLabel and yLabel
  public Table createTable(
      String title, String xLabel, double[] xValues, String yLabel, double[] yValues1) {
    DoubleColumn col1 = DoubleColumn.create(xLabel, xValues);

    DoubleColumn col2 = DoubleColumn.create(yLabel, yValues1);

    return Table.create(title, col1, col2);
  }

  // Create a table specifically built for the comparison tool
  public Table createCompTable(String title, String xLabel, double[] xValues, double[][] data) {
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

    return Table.create(title, columns);
  }

  // create Table with X columns, with the first being labeled xLabel and the rest labeled with
  // headers
  public Table createTable(
      String title, String xLabel, double[] xValues, double[][] data, String[] headers) {
    int size = data.length;
    DoubleColumn[] columns = new DoubleColumn[size + 1];

    String name = xLabel;
    columns[0] = DoubleColumn.create(name, xValues);

    for (int i = 0; i < data.length; i++) {
      name = headers[i];
      columns[i + 1] = DoubleColumn.create(name, data[i]);
    }

    return Table.create(title, columns);
  }

  // Create a table with xValues, an average, and data columns
  // Meant for the background single trials and the foreground average
  public Table createTable(
      String title, String xLabel, double[] xValues, double[] avg, double[][] data) {
    int size = data.length;
    DoubleColumn[] columns = new DoubleColumn[size + 2];

    String name = xLabel;
    columns[0] = DoubleColumn.create(name, xValues);
    columns[1] = DoubleColumn.create("Average", avg);

    for (int i = 0; i < data.length; i++) {
      name = "Series" + (i + 2);
      columns[i + 2] = DoubleColumn.create(name, data[i]);
    }

    return Table.create(title, columns);
  }

  // Show a line plot with the data from table and the labels as specified
  public void showLinePlot(String title, String xLabel, String yLabel, Table table) {
    columns = table.columnNames();
    size = columns.size();
    traces = new Trace[size - 1];

    xAxis = Axis.builder().title(xLabel).build();
    yAxis = Axis.builder().title(yLabel).build();

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

    layout = Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(yAxis).build();

    Plot.show(new Figure(layout, traces));
  }

  // Show a line plot with the data from table and the labels as specified
  public void showLinePlot(String title, String xLabel, String yLabel, Table table, Axis newYAxis) {
    columns = table.columnNames();
    size = columns.size();
    traces = new Trace[size - 1];

    xAxis = Axis.builder().title(xLabel).build();
    yAxis = Axis.builder().title(yLabel).build();

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

    layout =
        Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(newYAxis).build();

    Plot.show(new Figure(layout, traces));
  }

  // Show a line plot like above, but only with numShown single trials
  /* I.e. if the example is run over 500 trials, the above method would show an average
  in the foreground and 500 trials in the background. This method would show only
  numShown trials in the background, sampled evenly from the 500 */
  public void showLinePlot(String title, String xLabel, String yLabel, Table table, int numShown) {
    columns = table.columnNames();
    size = columns.size();
    traces = new Trace[numShown + 1];

    xAxis = Axis.builder().title(xLabel).build();
    yAxis = Axis.builder().title(yLabel).build();

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

    layout = Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(yAxis).build();
    Plot.show(new Figure(layout, traces));
  }

  public void showLinePlot(
      String title, String xLabel, String yLabel, Table table, int numShown, Axis newYAxis) {
    columns = table.columnNames();
    size = columns.size();
    traces = new Trace[numShown + 1];

    xAxis = Axis.builder().title(xLabel).build();
    yAxis = Axis.builder().title(yLabel).build();

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

    layout =
        Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(newYAxis).build();
    Plot.show(new Figure(layout, traces));
  }

  // Show a scatter plot with the data from table and the labels as specified
  public void showScatterPlot(String title, String xLabel, String yLabel, Table table) {
    columns = table.columnNames();
    size = columns.size();
    traces = new Trace[size - 1];

    xAxis = Axis.builder().title(xLabel).build();
    yAxis = Axis.builder().title(yLabel).build();

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

    layout = Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(yAxis).build();
    Plot.show(new Figure(layout, traces));
  }

  public void showScatterPlot(
      String title, String xLabel, String yLabel, Table table, Axis newYAxis) {
    columns = table.columnNames();
    size = columns.size();
    traces = new Trace[size - 1];

    xAxis = Axis.builder().title(xLabel).build();
    yAxis = Axis.builder().title(yLabel).build();

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

    layout =
        Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(newYAxis).build();
    Plot.show(new Figure(layout, traces));
  }

  // Show a scatter plot like above, but only with numShown single trials
  /* I.e. if the example is run over 500 trials, the above method would show an average
  in the foreground and 500 trials in the background. This method would show only
  numShown trials in the background, sampled evenly from the 500 */
  public void showScatterPlot(
      String title, String xLabel, String yLabel, Table table, int numShown) {
    columns = table.columnNames();
    size = columns.size();
    traces = new Trace[numShown + 1];

    xAxis = Axis.builder().title(xLabel).build();
    yAxis = Axis.builder().title(yLabel).build();

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

    layout = Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(yAxis).build();
    Plot.show(new Figure(layout, traces));
  }

  public void showScatterPlot(
      String title, String xLabel, String yLabel, Table table, int numShown, Axis newYAxis) {
    columns = table.columnNames();
    size = columns.size();
    traces = new Trace[numShown + 1];

    xAxis = Axis.builder().title(xLabel).build();
    yAxis = Axis.builder().title(yLabel).build();

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

    layout =
        Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(newYAxis).build();
    Plot.show(new Figure(layout, traces));
  }

  // Shows a line plot with the three established algorithms at 25% opacity and the new alg at 100%
  // opacity
  public void showCompLinePlot(String title, String xLabel, String yLabel, Table table) {
    columns = table.columnNames();
    size = columns.size();
    traces = new Trace[size - 1];

    xAxis = Axis.builder().title(xLabel).build();
    yAxis = Axis.builder().title(yLabel).build();

    for (int i = 1; i < size; i++) {
      Line line = grey;
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

    layout = Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(yAxis).build();
    Plot.show(new Figure(layout, traces));
  }

  public void showCompLinePlot(
      String title, String xLabel, String yLabel, Table table, Axis newYAxis) {
    columns = table.columnNames();
    size = columns.size();
    traces = new Trace[size - 1];

    xAxis = Axis.builder().title(xLabel).build();
    yAxis = Axis.builder().title(yLabel).build();

    for (int i = 1; i < size; i++) {
      Line line = grey;
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

    layout =
        Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(newYAxis).build();
    Plot.show(new Figure(layout, traces));
  }

  // Shows a scatter plot with the three established algorithms at 25% opacity and the new alg at
  // 100% opacity
  public void showCompScatterPlot(String title, String xLabel, String yLabel, Table table) {
    columns = table.columnNames();
    size = columns.size();
    traces = new Trace[size - 1];

    xAxis = Axis.builder().title(xLabel).build();
    yAxis = Axis.builder().title(yLabel).build();

    for (int i = 1; i < size; i++) {
      Line line = grey;
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

    layout = Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(yAxis).build();
    Plot.show(new Figure(layout, traces));
  }

  public void showCompScatterPlot(
      String title, String xLabel, String yLabel, Table table, Axis newYAxis) {
    columns = table.columnNames();
    size = columns.size();
    traces = new Trace[size - 1];

    xAxis = Axis.builder().title(xLabel).build();
    yAxis = Axis.builder().title(yLabel).build();

    for (int i = 1; i < size; i++) {
      Line line = grey;
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

    layout =
        Layout.builder().title(title).height(600).width(800).xAxis(xAxis).yAxis(newYAxis).build();
    Plot.show(new Figure(layout, traces));
  }

  // Shows a histogram of data
  public void showHistogram(String title, double[] data, int n, int maxRange) {
    xAxis = Axis.builder().fixedRange(true).range(n - maxRange, n + maxRange).build();
    Marker princetonOrange = Marker.builder().color("darkorange").build();
    Trace trace =
        HistogramTrace.builder(data).autoBinX(false).nBinsX(10).marker(princetonOrange).build();
    layout = Layout.builder().title(title).height(600).xAxis(xAxis).width(800).build();
    Plot.show(new Figure(layout, trace));
  }

  // Shows a distribution of each row in data, split into bins sections
  public void showLineDistributions(
      String title, double[][] data, int bins, int m, int trueCardinality) {
    double[] xValues = new double[bins];
    double[][] heights = new double[data.length][bins];
    String[] headers = new String[data.length];

    int trials = data[0].length;

    double min = Double.POSITIVE_INFINITY;
    double max = Double.NEGATIVE_INFINITY;
    for (double[] dataRow : data) {
      for (double datum : dataRow) {
        if (datum > max) max = datum + 1;
        if (datum < min) min = datum - 1;
      }
    }

    double binRange = (max - min) / bins;

    double temp;
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < trials; j++) {
        if (i == 0 && j < bins) xValues[j] = ((j + 1) * (binRange) + (binRange / 2));
        temp = Math.floor((data[i][j] - min) / binRange);

        heights[i][(int) temp]++;
      }

      headers[i] = "m = " + m;
      switch (i) {
        case 0:
          headers[i] = "m = 2";
          break;
        case 1:
          if ((m / 8) > 2) headers[i] = "m = " + m / 8;
          else headers[i] = "m = " + 4;
          break;
      }

      for (int j = 0; j < bins; j++) {
        heights[i][j] = heights[i][j] / trials;
      }
    }

    double champ = 0;
    for (int j = 0; j < data.length; j++)
      for (int k = 0; k < bins; k++) {
        temp = heights[j][k];
        heights[j][k] = temp;
        if (temp > champ) champ = temp;
      }

    Table table = createTable(title, title, xValues, heights, headers);
    xAxis = Axis.builder().range(0, 2 * trueCardinality).build();
    columns = table.columnNames();
    traces = new Trace[columns.size()];

    Line dashed = Line.builder().color("grey").dash(Line.Dash.DASH).build();

    Line line = grey;
    for (int i = 0; i < traces.length - 1; i++) {
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
      }
      traces[i] =
          ScatterTrace.builder(table.column(0), table.column(i + 1))
              .mode(ScatterTrace.Mode.LINE)
              .showLegend(true)
              .line(line)
              .name(columns.get(i + 1))
              .build();
    }

    // Create a dashed line to show the exact cardinality
    DoubleColumn dashColX = DoubleColumn.create("DashColX", trueCardinality, trueCardinality);
    DoubleColumn dashColY = DoubleColumn.create("DashColX", 0, champ + 0.15);
    traces[traces.length - 1] =
        ScatterTrace.builder(dashColX, dashColY)
            .mode(ScatterTrace.Mode.LINE)
            .line(dashed)
            .name("n = " + trueCardinality)
            .build();

    layout = Layout.builder().title(title).xAxis(xAxis).height(600).width(800).build();
    Plot.show(new Figure(layout, traces));
  }

  // Shows a distribution of each row in data, split into bins sections
  public void showScatterDistributions(
      String title, double[][] data, int bins, int m, int trueCardinality) {
    double[] xValues = new double[bins];
    double[][] heights = new double[data.length][bins];
    String[] headers = new String[data.length];

    int trials = data[0].length;

    double min = Double.POSITIVE_INFINITY;
    double max = Double.NEGATIVE_INFINITY;
    for (double[] dataRow : data) {
      for (double datum : dataRow) {
        if (datum > max) max = datum + 1;
        if (datum < min) min = datum - 1;
      }
    }

    double binRange = (max - min) / bins;

    double temp;
    for (int i = 0; i < data.length; i++) {
      for (int j = 0; j < trials; j++) {
        if (i == 0 && j < bins) xValues[j] = ((j + 1) * (binRange / 2));
        temp = Math.floor((data[i][j] - min) / binRange);

        heights[i][(int) temp]++;
      }

      switch (i) {
        case 0:
          headers[i] = "m = 2";
          break;
        case 1:
          if (m / 8 != 2) headers[i] = "m = " + m / 8;
          else headers[i] = "m = " + 4;
          break;
        default:
          headers[i] = "m = " + m;
      }

      for (int j = 0; j < bins; j++) {
        heights[i][j] = heights[i][j] / trials;
      }
    }

    double champ = 0;
    for (int j = 0; j < data.length; j++)
      for (int k = 0; k < bins; k++) {
        temp = heights[j][k];
        heights[j][k] = temp;
        if (temp > champ) champ = temp;
      }

    Table table = createTable(title, title, xValues, heights, headers);
    xAxis = Axis.builder().range(0, 2 * trueCardinality).build();
    columns = table.columnNames();
    traces = new Trace[columns.size()];

    Line dashed = Line.builder().color("grey").dash(Line.Dash.DASH).build();

    Line line = grey;
    for (int i = 0; i < traces.length - 1; i++) {
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
      }
      traces[i] =
          ScatterTrace.builder(table.column(0), table.column(i + 1))
              .mode(ScatterTrace.Mode.MARKERS)
              .showLegend(true)
              .line(line)
              .name(columns.get(i + 1))
              .build();
    }

    // Create a dashed line to show the exact cardinality
    DoubleColumn dashColX = DoubleColumn.create("DashColX", trueCardinality, trueCardinality);
    DoubleColumn dashColY = DoubleColumn.create("DashColX", 0, champ + 0.15);
    traces[traces.length - 1] =
        ScatterTrace.builder(dashColX, dashColY)
            .mode(ScatterTrace.Mode.LINE)
            .line(dashed)
            .name("n = " + trueCardinality)
            .build();

    layout = Layout.builder().title(title).xAxis(xAxis).height(600).width(800).build();
    Plot.show(new Figure(layout, traces));
  }
}
