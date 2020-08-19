package graphing;

import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.plotly.Plot;
import tech.tablesaw.plotly.api.Histogram;
import tech.tablesaw.plotly.api.LinePlot;
import tech.tablesaw.plotly.api.ScatterPlot;

import java.util.List;

public class BasicGrapher {
    protected List<String> columns;

    // Create a 2 column table with the headers xLabel and yLabel1
    public Table createTable(String title, String xLabel, double[] xValues, String yLabel1, double[] yValues1) {
        DoubleColumn col1 = DoubleColumn.create(xLabel, xValues);
        DoubleColumn col2 = DoubleColumn.create(yLabel1, yValues1);
        return Table.create(title, col1, col2);
    }

    // Create a "3" column table with the headers xLabel and yLabel, and teh series titles yLabel1 and yLabel2
    // This table has 2 columns of data and one column of labels
    // The data columns are twice as long as the data sets: a weird quirk of tablesaw
    public Table createTable(String title,
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

        // Fill the column of labels appropriately
        String[] labels = new String[col2.size()];
        for (int i = 0; i < (col2.size() / 2); i++) {
            labels[i] = yLabel1;
            labels[i + (col2.size() / 2)] = yLabel2;
        }

        StringColumn series = StringColumn.create("Series Names", labels);
        return Table.create(title, col1, col2, series);
    }

    // Show a basic linePlot: works for 2 or 3 column tables
    public void showLinePlot(String title, Table table) {
        columns = table.columnNames();
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

    // Same as above, but for a scatterPlot
    public void showScatterPlot(String title, Table table) {
        columns = table.columnNames();
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

    // Same as Above, but for a histogram. This does not take a table as input, rather a 1D array
    public void showHistogram(String title, double[] data) {
        Plot.show(Histogram.create(title, data));
    }
}
