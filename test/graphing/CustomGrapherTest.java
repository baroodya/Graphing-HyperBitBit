package graphing;

import org.junit.Test;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.components.Axis;
import tech.tablesaw.plotly.components.Layout;
import tech.tablesaw.plotly.components.Line;
import tech.tablesaw.plotly.traces.ScatterTrace;
import tech.tablesaw.plotly.traces.Trace;

import java.util.Arrays;

public class CustomGrapherTest {
  private final CustomGrapher grapher = new CustomGrapher();
  private final double[] someArray_Size10;
  private final double[] someOtherArray_Size10;
  private final DoubleColumn someOtherColumn_Size10;
  private final Table someTable_Size2X10;
  private final DoubleColumn someThirdColumn_Size10;
  private final Table someTable_Size3X10;

  private final double[][] someArray_someOtherArray_someThirdArray;
  private final double[][] someOtherArray_someThirdArray;
  private final String[] someHeaders;
  private final double[] averageArrayOfSomeOtherArray_someThirdArray;
  private final DoubleColumn averageColOfSomeOtherArray_someThirdArray;
  private final Table someTable_XValues_Avg_2X10;

  private final double[][] someCompArray_size4X10;
  private final Table someCompTable_Size5X10;

  private final String title;
  private final String xAxisTitle;
  private final String yAxisTitle;
  private final Axis someXAxis;
  private final Axis someYAxis;
  private final Axis relativeErrorYAxis = Axis.builder().fixedRange(true).range(0, 1.5).build();
  private final Axis normalizedYAxis = Axis.builder().fixedRange(true).range(-1.5, 1.5).build();
  private final Trace lineTraceOfSomeArray_SomeOtherArray;
  private final Trace scatterTraceOfSomeArray_SomeOtherArray;
  private final Trace lineTraceOfSomeOtherArray_SomeThirdArray;
  private final Trace scatterTraceOfSomeOtherArray_SomeThirdArray;
  private final Trace[] lineTracesOfSomeCompTable_Size5X10;
  private final Trace[] scatterTracesOfSomeCompTable_Size5X10;
  private final Layout someLayout;
  private final Layout someRelativeErrorLayout;
  private final Layout someNormalizedLayout;

  private final int n;
  private final int maxRange;
  private final Axis histogramXAxis;
  private final Layout histogramLayout;

  private final int cardinality;
  private final Axis distributionXAxis;
  private final Table distributionTable64;
  private final Table distributionTable16;
  private final Table partialDistributionTable64;
  private final Table partialDistributionTable16;
  private final Trace[] lineDistributionTraces64;
  private final Trace[] lineDistributionTraces16;
  private final Trace[] scatterDistributionTraces64;
  private final Trace[] scatterDistributionTraces16;
  private final Layout distributionLayout;

  public CustomGrapherTest() {
    someArray_Size10 = new double[10];
    someOtherArray_Size10 = new double[10];
    double[] someThirdArray_Size10 = new double[10];

    for (int i = 0; i < 10; i++) {
      someArray_Size10[i] = Math.random();
      someOtherArray_Size10[i] = Math.random();
      someThirdArray_Size10[i] = Math.random();
    }

    someArray_someOtherArray_someThirdArray =
        new double[][] {someArray_Size10, someOtherArray_Size10, someThirdArray_Size10};
    someOtherArray_someThirdArray = new double[][] {someOtherArray_Size10, someThirdArray_Size10};
    someHeaders = new String[] {"someOtherArray_Size10", "someThirdArray_Size10"};
    averageArrayOfSomeOtherArray_someThirdArray = new double[10];
    for (int i = 0; i < 10; i++)
      averageArrayOfSomeOtherArray_someThirdArray[i] =
          (someOtherArray_Size10[i] + someThirdArray_Size10[i]) / 2;
    averageColOfSomeOtherArray_someThirdArray =
        DoubleColumn.create(
            "averageColOfSomeOtherArray_someThirdArray",
            averageArrayOfSomeOtherArray_someThirdArray);

    DoubleColumn someColumn_Size10 = DoubleColumn.create("someArray_Size10", someArray_Size10);
    someOtherColumn_Size10 = DoubleColumn.create("someOtherArray_Size10", someOtherArray_Size10);
    someThirdColumn_Size10 = DoubleColumn.create("someThirdArray_Size10", someThirdArray_Size10);
    someTable_Size2X10 =
        Table.create("someTable_Size2X10", someColumn_Size10, someOtherColumn_Size10);
    someTable_Size3X10 =
        Table.create(
            "someTable_Size3X10",
            someColumn_Size10,
            someOtherColumn_Size10,
            someThirdColumn_Size10);
    someTable_XValues_Avg_2X10 =
        Table.create(
            "someTable_XValues_Avg_2X10",
            someColumn_Size10,
            averageColOfSomeOtherArray_someThirdArray,
            someOtherColumn_Size10,
            someThirdColumn_Size10);

    String[] names = new String[5];
    names[0] = "someArray_Size10";
    names[1] = "MinCount";
    names[2] = "ProbabilisticCounting";
    names[3] = "HyperBitBit";
    names[4] = "Your Algorithm";
    someCompArray_size4X10 = new double[4][10];
    DoubleColumn[] someColArray_Size5 = new DoubleColumn[5];
    for (int i = 0; i < 4; i++) {
      for (int j = 0; j < 10; j++) someCompArray_size4X10[i][j] = Math.random();
      someColArray_Size5[i + 1] = DoubleColumn.create(names[i + 1], someCompArray_size4X10[i]);
    }
    someColArray_Size5[0] = someColumn_Size10;
    someCompTable_Size5X10 = Table.create("someCompTable_Size5X10", someColArray_Size5);

    title = "title";
    xAxisTitle = "xAxis";
    yAxisTitle = "yAxis";

    someXAxis = Axis.builder().title(xAxisTitle).build();
    someYAxis = Axis.builder().title(yAxisTitle).build();

    Line grey = Line.builder().color("grey").build();
    lineTraceOfSomeArray_SomeOtherArray =
        ScatterTrace.builder(someColumn_Size10, someOtherColumn_Size10)
            .mode(ScatterTrace.Mode.LINE)
            .showLegend(true)
            .line(grey)
            .name("someOtherArray_Size10")
            .build();
    scatterTraceOfSomeArray_SomeOtherArray =
        ScatterTrace.builder(someColumn_Size10, someOtherColumn_Size10)
            .mode(ScatterTrace.Mode.MARKERS)
            .showLegend(true)
            .line(grey)
            .name("someOtherArray_Size10")
            .build();

    lineTraceOfSomeOtherArray_SomeThirdArray =
        ScatterTrace.builder(someOtherColumn_Size10, someThirdColumn_Size10)
            .mode(ScatterTrace.Mode.LINE)
            .showLegend(false)
            .line(grey)
            .opacity(0.25)
            .build();
    scatterTraceOfSomeOtherArray_SomeThirdArray =
        ScatterTrace.builder(someOtherColumn_Size10, someThirdColumn_Size10)
            .mode(ScatterTrace.Mode.MARKERS)
            .showLegend(false)
            .line(grey)
            .opacity(0.25)
            .build();

    lineTracesOfSomeCompTable_Size5X10 = new Trace[4];
    scatterTracesOfSomeCompTable_Size5X10 = new Trace[4];

    Line line;
    Line skyBlue = Line.builder().color("deepskyblue").build();
    Line purple = Line.builder().color("purple").build();
    Line green = Line.builder().color("green").build();
    Line dashed = Line.builder().color("grey").dash(Line.Dash.DASH).build();
    for (int i = 1; i < 4; i++) {
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
      lineTracesOfSomeCompTable_Size5X10[i - 1] =
          ScatterTrace.builder(someCompTable_Size5X10.column(0), someCompTable_Size5X10.column(i))
              .mode(ScatterTrace.Mode.LINE)
              .showLegend(true)
              .line(line)
              .opacity(0.25)
              .name(someCompTable_Size5X10.columnNames().get(i))
              .build();
      scatterTracesOfSomeCompTable_Size5X10[i - 1] =
          ScatterTrace.builder(someCompTable_Size5X10.column(0), someCompTable_Size5X10.column(i))
              .mode(ScatterTrace.Mode.LINE)
              .showLegend(true)
              .line(line)
              .opacity(0.25)
              .name(someCompTable_Size5X10.columnNames().get(i))
              .build();
    }

    Line princetonOrange = Line.builder().color("darkorange").build();
    lineTracesOfSomeCompTable_Size5X10[lineTracesOfSomeCompTable_Size5X10.length - 1] =
        ScatterTrace.builder(someCompTable_Size5X10.column(0), someCompTable_Size5X10.column(4))
            .mode(ScatterTrace.Mode.LINE)
            .showLegend(true)
            .line(princetonOrange)
            .name(someCompTable_Size5X10.columnNames().get(4))
            .build();
    scatterTracesOfSomeCompTable_Size5X10[scatterTracesOfSomeCompTable_Size5X10.length - 1] =
        ScatterTrace.builder(someCompTable_Size5X10.column(0), someCompTable_Size5X10.column(4))
            .mode(ScatterTrace.Mode.LINE)
            .showLegend(true)
            .line(princetonOrange)
            .name(someCompTable_Size5X10.columnNames().get(4))
            .build();

    someLayout =
        Layout.builder()
            .title(title)
            .height(600)
            .width(800)
            .xAxis(someXAxis)
            .yAxis(someYAxis)
            .build();

    n = 0;
    maxRange = 1;
    histogramXAxis = Axis.builder().fixedRange(true).range(n - maxRange, n + maxRange).build();
    histogramLayout =
        Layout.builder().title(title).height(600).width(800).xAxis(histogramXAxis).build();

    cardinality = 1;
    distributionXAxis = Axis.builder().range(0, 2 * cardinality).build();
    double[][] heights_SomeArray_SomeOtherArray_SomeThirdArray = new double[3][10];
    double[][] heights_SomeOtherArray_SomeThirdArray = new double[2][10];
    double[] distributionXValues = new double[10];
    String[] distributionHeaders = new String[3];
    String[] partialDistributionHeaders = new String[2];
    lineDistributionTraces64 = new Trace[3];
    lineDistributionTraces16 = new Trace[3];
    scatterDistributionTraces64 = new Trace[4];
    scatterDistributionTraces16 = new Trace[4];

    int temp;
    for (int i = 0; i < 10; i++) {
      distributionXValues[i] = ((i + 1) * (0.1) + (0.05));
      temp = (int) Math.floor(someOtherArray_Size10[i] * 10);
      heights_SomeArray_SomeOtherArray_SomeThirdArray[0][temp]++;
    }

    for (int i = 0; i < 10; i++) {
      distributionXValues[i] = ((i + 1) * (0.1) + (0.05));
      temp = (int) Math.floor(someOtherArray_Size10[i] * 10);
      heights_SomeOtherArray_SomeThirdArray[0][temp]++;
      heights_SomeArray_SomeOtherArray_SomeThirdArray[1][temp]++;
    }

    for (int i = 0; i < 10; i++) {
      temp = (int) Math.floor(someOtherArray_Size10[i] * 10);
      heights_SomeOtherArray_SomeThirdArray[1][temp]++;
      heights_SomeArray_SomeOtherArray_SomeThirdArray[2][temp]++;
    }

    distributionHeaders[0] = "m = 2";
    distributionHeaders[1] = "m = 8";
    distributionHeaders[2] = "m = 64";
    distributionTable64 =
        grapher.createTable(
            title,
            title,
            distributionXValues,
            heights_SomeArray_SomeOtherArray_SomeThirdArray,
            distributionHeaders);
    distributionHeaders[1] = "m = 4";
    distributionHeaders[2] = "m = 16";
    distributionTable16 =
        grapher.createTable(
            title,
            title,
            distributionXValues,
            heights_SomeArray_SomeOtherArray_SomeThirdArray,
            distributionHeaders);

    partialDistributionHeaders[0] = "m = 2";
    partialDistributionHeaders[1] = "m = 8";
    partialDistributionTable64 =
        grapher.createTable(
            title,
            title,
            distributionXValues,
            heights_SomeOtherArray_SomeThirdArray,
            partialDistributionHeaders);
    partialDistributionHeaders[1] = "m = 4";
    partialDistributionTable16 =
        grapher.createTable(
            title,
            title,
            distributionXValues,
            heights_SomeOtherArray_SomeThirdArray,
            partialDistributionHeaders);

    for (int i = 0; i < scatterDistributionTraces64.length - 1; i++) {
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
      scatterDistributionTraces64[i] =
          ScatterTrace.builder(distributionTable64.column(0), distributionTable64.column(i + 1))
              .mode(ScatterTrace.Mode.MARKERS)
              .showLegend(true)
              .line(line)
              .name(distributionTable64.columnNames().get(i + 1))
              .build();
      scatterDistributionTraces16[i] =
          ScatterTrace.builder(distributionTable16.column(0), distributionTable16.column(i + 1))
              .mode(ScatterTrace.Mode.MARKERS)
              .showLegend(true)
              .line(line)
              .name(distributionTable16.columnNames().get(i + 1))
              .build();
      if (i < 2) {
        lineDistributionTraces64[i] =
            ScatterTrace.builder(distributionTable64.column(0), distributionTable64.column(i + 1))
                .mode(ScatterTrace.Mode.LINE)
                .showLegend(true)
                .line(line)
                .name(distributionTable64.columnNames().get(i + 1))
                .build();
        lineDistributionTraces16[i] =
            ScatterTrace.builder(distributionTable16.column(0), distributionTable16.column(i + 1))
                .mode(ScatterTrace.Mode.LINE)
                .showLegend(true)
                .line(line)
                .name(distributionTable16.columnNames().get(i + 1))
                .build();
      }
    }

    // Create a dashed line to show the exact cardinality
    DoubleColumn dashColX = DoubleColumn.create("DashColX", cardinality, cardinality);
    DoubleColumn dashColY = DoubleColumn.create("DashColX", 0, 1);
    lineDistributionTraces64[lineDistributionTraces64.length - 1] =
        ScatterTrace.builder(dashColX, dashColY)
            .mode(ScatterTrace.Mode.LINE)
            .line(dashed)
            .name("n = " + cardinality)
            .build();
    scatterDistributionTraces64[scatterDistributionTraces64.length - 1] =
        ScatterTrace.builder(dashColX, dashColY)
            .mode(ScatterTrace.Mode.LINE)
            .line(dashed)
            .name("n = " + cardinality)
            .build();
    lineDistributionTraces16[lineDistributionTraces16.length - 1] =
        ScatterTrace.builder(dashColX, dashColY)
            .mode(ScatterTrace.Mode.LINE)
            .line(dashed)
            .name("n = " + cardinality)
            .build();
    scatterDistributionTraces16[scatterDistributionTraces16.length - 1] =
        ScatterTrace.builder(dashColX, dashColY)
            .mode(ScatterTrace.Mode.LINE)
            .line(dashed)
            .name("n = " + cardinality)
            .build();

    distributionLayout =
        Layout.builder().title(title).xAxis(distributionXAxis).height(600).width(800).build();

    someRelativeErrorLayout =
        Layout.builder()
            .title(title)
            .xAxis(someXAxis)
            .yAxis(relativeErrorYAxis)
            .height(600)
            .width(800)
            .build();
    someNormalizedLayout =
        Layout.builder()
            .title(title)
            .xAxis(someXAxis)
            .yAxis(normalizedYAxis)
            .height(600)
            .width(800)
            .build();
  }

  @Test
  public void createTable() {
    Table output =
        grapher.createTable(
            "someTable_Size2X10",
            "someArray_Size10",
            someArray_Size10,
            "someOtherArray_Size10",
            someOtherArray_Size10);
    assert output.toString().equals(someTable_Size2X10.toString());

    output =
        grapher.createTable(
            "someTable_Size3X10",
            "someArray_Size10",
            someArray_Size10,
            someOtherArray_someThirdArray,
            someHeaders);
    assert output.toString().equals(someTable_Size3X10.toString());

    output =
        grapher.createTable(
            "someTable_XValues_Avg_2X10",
            "someArray_Size10",
            someArray_Size10,
            averageArrayOfSomeOtherArray_someThirdArray,
            someOtherArray_someThirdArray);
    averageColOfSomeOtherArray_someThirdArray.setName("Average");
    someOtherColumn_Size10.setName("Series2");
    someThirdColumn_Size10.setName("Series3");
    assert output.toString().equals(someTable_XValues_Avg_2X10.toString());

    averageColOfSomeOtherArray_someThirdArray.setName("averageColOfSomeOtherArray_someThirdArray");
    someOtherColumn_Size10.setName("someOtherArray_Size10");
    someThirdColumn_Size10.setName("someThirdArray_Size10");
  }

  @Test
  public void createCompTable() {
    Table output =
        grapher.createCompTable(
            "someCompTable_Size5X10", "someArray_Size10", someArray_Size10, someCompArray_size4X10);
    assert output.toString().equals(someCompTable_Size5X10.toString());
  }

  @Test
  public void showLinePlot() {
    grapher.showLinePlot(title, xAxisTitle, yAxisTitle, someTable_Size2X10);

    assert grapher.columns.equals(someTable_Size2X10.columnNames());
    assert grapher.size == someTable_Size2X10.columnCount();

    assert grapher.traces.length == 1;
    assert grapher.traces[0].toString().equals(lineTraceOfSomeArray_SomeOtherArray.toString());
    assert grapher.xAxis.toString().equals(someXAxis.toString());
    assert grapher.yAxis.toString().equals(someYAxis.toString());
    assert grapher.layout.asJavascript().equals(someLayout.asJavascript());

    grapher.showLinePlot(title, xAxisTitle, yAxisTitle, someTable_Size3X10, 2);

    assert grapher.columns.equals(someTable_Size3X10.columnNames());
    assert grapher.size == someTable_Size3X10.columnCount();

    assert grapher.traces.length == 3;
    assert grapher.traces[0].toString().equals(lineTraceOfSomeOtherArray_SomeThirdArray.toString());
    assert grapher.traces[1].toString().equals(lineTraceOfSomeOtherArray_SomeThirdArray.toString());
    assert grapher.traces[2].toString().equals(lineTraceOfSomeArray_SomeOtherArray.toString());
    assert grapher.xAxis.toString().equals(someXAxis.toString());
    assert grapher.yAxis.toString().equals(someYAxis.toString());
    assert grapher.layout.asJavascript().equals(someLayout.asJavascript());

    grapher.showLinePlot(title, xAxisTitle, yAxisTitle, someTable_Size3X10, relativeErrorYAxis);

    assert grapher.columns.equals(someTable_Size3X10.columnNames());
    assert grapher.size == someTable_Size3X10.columnCount();

    assert grapher.traces.length == 2;
    assert grapher.traces[0].toString().equals(lineTraceOfSomeOtherArray_SomeThirdArray.toString());
    assert grapher.traces[1].toString().equals(lineTraceOfSomeArray_SomeOtherArray.toString());
    assert grapher.xAxis.toString().equals(someXAxis.toString());
    assert grapher.yAxis.toString().equals(someYAxis.toString());
    assert grapher.layout.asJavascript().equals(someRelativeErrorLayout.asJavascript());

    grapher.showLinePlot(title, xAxisTitle, yAxisTitle, someTable_Size3X10, 2, normalizedYAxis);

    assert grapher.columns.equals(someTable_Size3X10.columnNames());
    assert grapher.size == someTable_Size3X10.columnCount();

    assert grapher.traces.length == 3;
    assert grapher.traces[0].toString().equals(lineTraceOfSomeOtherArray_SomeThirdArray.toString());
    assert grapher.traces[1].toString().equals(lineTraceOfSomeOtherArray_SomeThirdArray.toString());
    assert grapher.traces[2].toString().equals(lineTraceOfSomeArray_SomeOtherArray.toString());
    assert grapher.xAxis.toString().equals(someXAxis.toString());
    assert grapher.yAxis.toString().equals(someYAxis.toString());
    assert grapher.layout.asJavascript().equals(someNormalizedLayout.asJavascript());
  }

  @Test
  public void showScatterPlot() {
    grapher.showScatterPlot(title, xAxisTitle, yAxisTitle, someTable_Size2X10);

    assert grapher.columns.equals(someTable_Size2X10.columnNames());
    assert grapher.size == someTable_Size2X10.columnCount();

    assert grapher.traces.length == 1;
    assert grapher.traces[0].toString().equals(scatterTraceOfSomeArray_SomeOtherArray.toString());
    assert grapher.xAxis.toString().equals(someXAxis.toString());
    assert grapher.yAxis.toString().equals(someYAxis.toString());
    assert grapher.layout.asJavascript().equals(someLayout.asJavascript());

    grapher.showScatterPlot(title, xAxisTitle, yAxisTitle, someTable_Size3X10, 2);

    assert grapher.columns.equals(someTable_Size3X10.columnNames());
    assert grapher.size == someTable_Size3X10.columnCount();

    assert grapher.traces.length == 3;
    assert grapher
        .traces[0]
        .toString()
        .equals(scatterTraceOfSomeOtherArray_SomeThirdArray.toString());
    assert grapher
        .traces[1]
        .toString()
        .equals(scatterTraceOfSomeOtherArray_SomeThirdArray.toString());
    assert grapher.traces[2].toString().equals(scatterTraceOfSomeArray_SomeOtherArray.toString());
    assert grapher.xAxis.toString().equals(someXAxis.toString());
    assert grapher.yAxis.toString().equals(someYAxis.toString());
    assert grapher.layout.asJavascript().equals(someLayout.asJavascript());

    grapher.showScatterPlot(title, xAxisTitle, yAxisTitle, someTable_Size2X10, normalizedYAxis);

    assert grapher.columns.equals(someTable_Size2X10.columnNames());
    assert grapher.size == someTable_Size2X10.columnCount();

    assert grapher.traces.length == 1;
    assert grapher.traces[0].toString().equals(scatterTraceOfSomeArray_SomeOtherArray.toString());
    assert grapher.xAxis.toString().equals(someXAxis.toString());
    assert grapher.yAxis.toString().equals(someYAxis.toString());
    assert grapher.layout.asJavascript().equals(someNormalizedLayout.asJavascript());

    grapher.showScatterPlot(
        title, xAxisTitle, yAxisTitle, someTable_Size3X10, 2, relativeErrorYAxis);

    assert grapher.columns.equals(someTable_Size3X10.columnNames());
    assert grapher.size == someTable_Size3X10.columnCount();

    assert grapher.traces.length == 3;
    assert grapher
        .traces[0]
        .toString()
        .equals(scatterTraceOfSomeOtherArray_SomeThirdArray.toString());
    assert grapher
        .traces[1]
        .toString()
        .equals(scatterTraceOfSomeOtherArray_SomeThirdArray.toString());
    assert grapher.traces[2].toString().equals(scatterTraceOfSomeArray_SomeOtherArray.toString());
    assert grapher.xAxis.toString().equals(someXAxis.toString());
    assert grapher.yAxis.toString().equals(someYAxis.toString());
    assert grapher.layout.asJavascript().equals(someRelativeErrorLayout.asJavascript());
  }

  @Test
  public void showCompLinePlot() {
    grapher.showCompLinePlot(title, xAxisTitle, yAxisTitle, someCompTable_Size5X10);

    assert grapher.columns.equals(someCompTable_Size5X10.columnNames());
    assert grapher.size == someCompTable_Size5X10.columnCount();

    assert grapher.traces.length == 4;
    assert Arrays.toString(grapher.traces)
        .equals(Arrays.toString(lineTracesOfSomeCompTable_Size5X10));
    assert grapher.xAxis.toString().equals(someXAxis.toString());
    assert grapher.yAxis.toString().equals(someYAxis.toString());
    assert grapher.layout.asJavascript().equals(someLayout.asJavascript());

    grapher.showCompLinePlot(
        title, xAxisTitle, yAxisTitle, someCompTable_Size5X10, relativeErrorYAxis);

    assert grapher.columns.equals(someCompTable_Size5X10.columnNames());
    assert grapher.size == someCompTable_Size5X10.columnCount();

    assert grapher.traces.length == 4;
    assert Arrays.toString(grapher.traces)
        .equals(Arrays.toString(lineTracesOfSomeCompTable_Size5X10));
    assert grapher.xAxis.toString().equals(someXAxis.toString());
    assert grapher.yAxis.toString().equals(someYAxis.toString());
    assert grapher.layout.asJavascript().equals(someRelativeErrorLayout.asJavascript());

    grapher.showCompLinePlot(
        title, xAxisTitle, yAxisTitle, someCompTable_Size5X10, normalizedYAxis);

    assert grapher.columns.equals(someCompTable_Size5X10.columnNames());
    assert grapher.size == someCompTable_Size5X10.columnCount();

    assert grapher.traces.length == 4;
    assert Arrays.toString(grapher.traces)
        .equals(Arrays.toString(lineTracesOfSomeCompTable_Size5X10));
    assert grapher.xAxis.toString().equals(someXAxis.toString());
    assert grapher.yAxis.toString().equals(someYAxis.toString());
    assert grapher.layout.asJavascript().equals(someNormalizedLayout.asJavascript());
  }

  @Test
  public void showCompScatterPlot() {
    grapher.showCompScatterPlot(title, xAxisTitle, yAxisTitle, someCompTable_Size5X10);

    assert grapher.columns.equals(someCompTable_Size5X10.columnNames());
    assert grapher.size == someCompTable_Size5X10.columnCount();

    assert grapher.traces.length == 4;
    assert Arrays.toString(grapher.traces)
        .equals(Arrays.toString(scatterTracesOfSomeCompTable_Size5X10));
    assert grapher.xAxis.toString().equals(someXAxis.toString());
    assert grapher.yAxis.toString().equals(someYAxis.toString());
    assert grapher.layout.asJavascript().equals(someLayout.asJavascript());

    grapher.showCompScatterPlot(
        title, xAxisTitle, yAxisTitle, someCompTable_Size5X10, relativeErrorYAxis);

    assert grapher.columns.equals(someCompTable_Size5X10.columnNames());
    assert grapher.size == someCompTable_Size5X10.columnCount();

    assert grapher.traces.length == 4;
    assert Arrays.toString(grapher.traces)
        .equals(Arrays.toString(scatterTracesOfSomeCompTable_Size5X10));
    assert grapher.xAxis.toString().equals(someXAxis.toString());
    assert grapher.yAxis.toString().equals(someYAxis.toString());
    assert grapher.layout.asJavascript().equals(someRelativeErrorLayout.asJavascript());

    grapher.showCompScatterPlot(
        title, xAxisTitle, yAxisTitle, someCompTable_Size5X10, normalizedYAxis);

    assert grapher.columns.equals(someCompTable_Size5X10.columnNames());
    assert grapher.size == someCompTable_Size5X10.columnCount();

    assert grapher.traces.length == 4;
    assert Arrays.toString(grapher.traces)
        .equals(Arrays.toString(scatterTracesOfSomeCompTable_Size5X10));
    assert grapher.xAxis.toString().equals(someXAxis.toString());
    assert grapher.yAxis.toString().equals(someYAxis.toString());
    assert grapher.layout.asJavascript().equals(someNormalizedLayout.asJavascript());
  }

  @Test
  public void showHistogram() {
    grapher.showHistogram(title, someArray_Size10, n, maxRange);

    assert grapher.xAxis.toString().equals(histogramXAxis.toString());
    assert grapher.layout.asJavascript().equals(histogramLayout.asJavascript());
  }

  @Test
  public void showLineDistributions() {
    grapher.showLineDistributions(title, someOtherArray_someThirdArray, 10, 64, cardinality);

    assert grapher.xAxis.asJavascript().equals(distributionXAxis.asJavascript());
    assert grapher.columns.equals(partialDistributionTable64.columnNames());
    assert Arrays.toString(grapher.traces).equals(Arrays.toString(lineDistributionTraces64));
    assert grapher.layout.asJavascript().equals(distributionLayout.asJavascript());

    grapher.showLineDistributions(title, someOtherArray_someThirdArray, 10, 16, cardinality);

    assert grapher.xAxis.asJavascript().equals(distributionXAxis.asJavascript());
    assert grapher.columns.equals(partialDistributionTable16.columnNames());
    assert Arrays.toString(grapher.traces).equals(Arrays.toString(lineDistributionTraces16));
    assert grapher.layout.asJavascript().equals(distributionLayout.asJavascript());
  }

  @Test
  public void showScatterDistributions() {
    grapher.showScatterDistributions(
        title, someArray_someOtherArray_someThirdArray, 10, 64, cardinality);

    assert grapher.xAxis.asJavascript().equals(distributionXAxis.asJavascript());
    assert grapher.columns.equals(distributionTable64.columnNames());
    assert Arrays.toString(grapher.traces).equals(Arrays.toString(scatterDistributionTraces64));
    assert grapher.layout.asJavascript().equals(distributionLayout.asJavascript());

    grapher.showScatterDistributions(
        title, someArray_someOtherArray_someThirdArray, 10, 16, cardinality);

    assert grapher.xAxis.asJavascript().equals(distributionXAxis.asJavascript());
    assert grapher.columns.equals(distributionTable16.columnNames());
    assert Arrays.toString(grapher.traces).equals(Arrays.toString(scatterDistributionTraces16));
    assert grapher.layout.asJavascript().equals(distributionLayout.asJavascript());
  }
}
