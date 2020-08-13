package main.java;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Test;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.Table;

class CustomGrapherTest {
    private CustomGrapher grapher = new CustomGrapher();
    private double[] someArray_Size10;
    private DoubleColumn someColumn_Size10;
    private double[] someOtherArray_Size10;
    private DoubleColumn someOtherColumn_Size10;
    private Table someTable_Size2X10;

    CustomGrapherTest() {
        someArray_Size10 = new double[10];
        someOtherArray_Size10 = new double[10];

        for (int i = 0; i < 10; i++) {
            someArray_Size10[i] = StdRandom.uniform();
            someOtherArray_Size10[i] = StdRandom.uniform();
        }

        someColumn_Size10 = DoubleColumn.create("someArray_Size10", someArray_Size10);
        someOtherColumn_Size10 = DoubleColumn.create("someOtherArray_Size10", someOtherArray_Size10);
        someTable_Size2X10 = Table.create("someTable_Size2X10", someColumn_Size10, someOtherColumn_Size10);
    }

    @Test
    void createTable() {
        Table output = grapher.createTable("someTable_Size2X10", "someArray_Size10", someArray_Size10, "someOtherArray_Size10", someOtherArray_Size10);
        StdOut.println(output.toString());
        StdOut.println("*******************");
        StdOut.println(someTable_Size2X10.toString());
//        assert output.equals(someTable_Size2X10);
    }

    @Test
    void createCompTable() {
    }

    @Test
    void testCreateTable() {
    }

    @Test
    void testCreateTable1() {
    }

    @Test
    void showLinePlot() {
    }

    @Test
    void testShowLinePlot() {
    }

    @Test
    void showScatterPlot() {
    }

    @Test
    void testShowScatterPlot() {
    }

    @Test
    void showCompLinePlot() {
    }

    @Test
    void showCompScatterPlot() {
    }

    @Test
    void showHistogram() {
    }

    @Test
    void showLineDistributions() {
    }

    @Test
    void showScatterDistributions() {
    }
}
