package graphing;

import org.junit.Test;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.StringColumn;
import tech.tablesaw.api.Table;

public class BasicGrapherTest {
    private final BasicGrapher grapher = new BasicGrapher();

    private final double[] someArray_Size10;
    private final double[] someOtherArray_Size10;
    private final double[] someThirdArray_Size10;
    private final Table someTable_Size2X10;
    private final Table someTable_Size3X10;

    private final String title;


    public BasicGrapherTest() {
        someArray_Size10 = new double[10];
        someOtherArray_Size10 = new double[10];
        someThirdArray_Size10 = new double[10];
        String[] someLabelArray = new String[20];

        for (int i = 0; i < 10; i++) {
            someArray_Size10[i] = Math.random();
            someOtherArray_Size10[i] = Math.random();
            someThirdArray_Size10[i] = Math.random();
            someLabelArray[i] = "label";
            someLabelArray[i + 10] = "label";
        }

        DoubleColumn someColumn_Size10 = DoubleColumn.create("someArray_Size10", someArray_Size10);
        DoubleColumn someOtherColumn_Size10 = DoubleColumn.create("someOtherArray_Size10", someOtherArray_Size10);
        DoubleColumn someThirdColumn_Size10 = DoubleColumn.create("someThirdArray_Size10", someThirdArray_Size10);
        DoubleColumn someColumn_Size20 = DoubleColumn.create("someArray_Size20", someArray_Size10);
        someColumn_Size20.append(someColumn_Size10);
        DoubleColumn someOtherColumn_Size20 = DoubleColumn.create("someOtherArray_Size20", someOtherArray_Size10);
        someOtherColumn_Size20.append(someThirdColumn_Size10);
        StringColumn someLabelColumn = StringColumn.create("Series Names", someLabelArray);
        someTable_Size2X10 = Table.create("someTable_Size2X10", someColumn_Size10, someOtherColumn_Size10);
        someTable_Size3X10 = Table.create("someTable_Size3X10", someColumn_Size20, someOtherColumn_Size20, someLabelColumn);

        title = "title";
    }


    @Test
    public void createTable() {
        Table output = grapher.createTable("someTable_Size2X10", "someArray_Size10", someArray_Size10, "someOtherArray_Size10", someOtherArray_Size10);
        assert output.toString().equals(someTable_Size2X10.toString());

        output = grapher.createTable("someTable_Size3X10", "someArray_Size20", someArray_Size10, "someOtherArray_Size20", "label", someOtherArray_Size10, "label", someThirdArray_Size10);
        assert output.toString().equals(someTable_Size3X10.toString());
    }

    @Test
    public void showLinePlot() {
        grapher.showLinePlot(title, someTable_Size2X10);
        assert grapher.columns.equals(someTable_Size2X10.columnNames());
    }

    @Test
    public void showScatterPlot() {
        grapher.showScatterPlot(title, someTable_Size2X10);
        assert grapher.columns.equals(someTable_Size2X10.columnNames());
    }

    @Test
    public void showHistogram() {
        grapher.showHistogram(title, someArray_Size10);
    }
}
