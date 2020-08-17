package main.java;

import edu.princeton.cs.algs4.StdOut;
import helpers.java.Exact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;

class ReportGeneratorTest {
    private final ExperimentLauncher launcher = new ExperimentLauncher("MC", 100, 16, Exact.countArray("src/datasets/f0", 100000), 0.5, 0.77351, 100, "src/datasets/f0");
    private ReportGenerator generator = new ReportGenerator(launcher, 100);

    private final double[] arrayOfTenZeros = new double[10];
    private final double[] arrayOfOneHundredZeros = new double[100];

    private final double[] arrayOfTenRandomDoubles = new double[10];
    private final double[] arrayOfOneHundredRandomDoubles = new double[100];

    private final double[][] arrayOfTenXTenZeros = new double[10][10];
    private final double[][] arrayOfOneHundredXOneHundredZeros = new double[100][100];

    private final double[][] arrayOfTenXTenRandomDoubles = new double[10][10];
    private final double[][] arrayOfOneHundredXOneHundredRandomDoubles = new double[100][100];

    private final double[] oneThruSixteen = new double[16];
    private final double[] oneThruOneHundred = new double[100];

    ReportGeneratorTest() throws FileNotFoundException {
        for (int i = 0; i < 10; i++) arrayOfTenRandomDoubles[i] = Math.random();
        for (int i = 0; i < 100; i++) arrayOfOneHundredRandomDoubles[i] = Math.random();

        for (int i = 0; i < 10; i++)
            for (int j = 0; j < 10; j++)
                arrayOfTenXTenRandomDoubles[i][j] = Math.random();

        for (int i = 0; i < 100; i++)
            for (int j = 0; j < 100; j++)
                arrayOfOneHundredXOneHundredRandomDoubles[i][j] = Math.random();

        for (int i = 0; i < 16; i++) oneThruSixteen[i] = i + 1;
        for (int i = 0; i < 100; i++) oneThruOneHundred[i] = i + 1;

        launcher.runExperiments();
    }

    @BeforeEach
    void setUp() {
        generator = new ReportGenerator(launcher, 100);
    }

    @Test
    void generateFullReport() {
        int numShownBefore = generator.numShown;
        double[] xValuesBefore = generator.xValues;
        String title = generator.title;
        String xAxis = generator.xAxis;
        String yAxis = generator.yAxis;

        generator.generateFullReport();

        assert numShownBefore == generator.numShown;
        assert !Arrays.equals(xValuesBefore, generator.xValues);
        assert !generator.title.equals(title);
        assert !generator.xAxis.equals(xAxis);
        assert !generator.yAxis.equals(yAxis);
    }

    @Test
    void generateBasicReport() {
        int numShownBefore = generator.numShown;
        double[] xValuesBefore = generator.xValues;
        String title = generator.title;
        String xAxis = generator.xAxis;
        String yAxis = generator.yAxis;

        generator.generateBasicReport();

        assert numShownBefore == generator.numShown;
        assert !Arrays.equals(xValuesBefore, generator.xValues);
        assert !generator.title.equals(title);
        assert !generator.xAxis.equals(xAxis);
        assert !generator.yAxis.equals(yAxis);
    }

    @Test
    void showEstCardinality() {
        generator.showEstCardinality();

        assert generator.title.equals("Average Estimated Cardinality of Data Stream");
        assert generator.xAxis.equals("Number of Inputs Seen (N)");
        assert generator.yAxis.equals("Estimated Cardinality (Z_n)");
        assert Arrays.equals(generator.xValues, oneThruOneHundred);
    }

    @Test
    void showNormEstCardinality() {
        generator.showNormEstCardinality();

        assert generator.title.equals("Average Normalized Estimated Cardinality of Data Stream");
        assert generator.xAxis.equals("Number of Inputs Seen (N)");
        assert generator.yAxis.equals("Normalized Estimated Cardinality ((Z_n / n) - 1)");
        for (int i = 0; i < 100; i++) StdOut.println(generator.xValues[i]);
        assert Arrays.equals(generator.xValues, oneThruOneHundred);
    }

    @Test
    void showAbsError() {
        generator.showAbsError();

        assert generator.title.equals("Average Absolute Error");
        assert generator.xAxis.equals("Number of Inputs Seen (N)");
        assert generator.yAxis.equals("Absolute Error (|Z_n - n|)");
        assert Arrays.equals(generator.xValues, oneThruOneHundred);
    }

    @Test
    void showRelError() {
        generator.showRelError();

        assert generator.title.equals("Average Relative Error");
        assert generator.xAxis.equals("Number of Inputs Seen (N)");
        assert generator.yAxis.equals("Relative Error (|Z_n - n|/n)");
        assert Arrays.equals(generator.xValues, oneThruOneHundred);
    }

    @Test
    void showMEstCardinality() {
        generator.showMEstCardinality();

        assert generator.title.equals("Average Estimated Cardinality of Data Stream");
        assert generator.xAxis.equals("Number of Substreams (m)");
        assert generator.yAxis.equals("Estimated Cardinality (Z_n)");
        assert Arrays.equals(generator.xValues, oneThruSixteen);
    }

    @Test
    void showMNormEstCardinality() {
        generator.showMNormEstCardinality();

        assert generator.title.equals("Average Normalized Estimated Cardinality of Data Stream");
        assert generator.xAxis.equals("Number of Substreams (m)");
        assert generator.yAxis.equals("Normalized Estimated Cardinality ((Z_n / n) - 1)");
        assert Arrays.equals(generator.xValues, oneThruSixteen);
    }

    @Test
    void showMAbsError() {
        generator.showMAbsError();

        assert generator.title.equals("Average Absolute Error");
        assert generator.xAxis.equals("Number of Substreams (m)");
        assert generator.yAxis.equals("Absolute Error (|Z_n - n|)");
        assert Arrays.equals(generator.xValues, oneThruSixteen);
    }

    @Test
    void showMRelError() {
        generator.showMRelError();

        assert generator.title.equals("Average Relative Error");
        assert generator.xAxis.equals("Number of Substreams (m)");
        assert generator.yAxis.equals("Relative Error (|Z_n - n|/n)");
        assert Arrays.equals(generator.xValues, oneThruSixteen);
    }

    @Test
    void showStdDev() {
        generator.showStdDev();

        assert generator.title.equals("Standard Deviation of Estimates");
        assert generator.xAxis.equals("Number of Substreams (m)");
        assert generator.yAxis.equals("Standard Deviation of 100 Trials");
        assert Arrays.equals(generator.xValues, oneThruSixteen);
    }

    @Test
    void showDistributions() {
        generator.showDistributions();

        assert generator.title.equals("Distribution of Estimates");
    }

    @Test
    void manageArray() {
        double[] trimmed = generator.manageArray(arrayOfTenZeros);
        assert trimmed.length == 10;

        trimmed = generator.manageArray(arrayOfOneHundredZeros);
        assert trimmed.length == 98;

        trimmed = generator.manageArray(arrayOfTenRandomDoubles);
        assert trimmed.length == 10;

        trimmed = generator.manageArray(arrayOfOneHundredRandomDoubles);
        assert trimmed.length == 98;

        double[][] bigTrimmed = generator.manageArray(arrayOfTenXTenZeros);
        assert bigTrimmed.length == 10;
        assert bigTrimmed[0].length == 10;

        bigTrimmed = generator.manageArray(arrayOfOneHundredXOneHundredZeros);
        assert bigTrimmed.length == 100;
        assert bigTrimmed[0].length == 98;

        bigTrimmed = generator.manageArray(arrayOfTenXTenRandomDoubles);
        assert bigTrimmed.length == 10;
        assert bigTrimmed[0].length == 10;

        bigTrimmed = generator.manageArray(arrayOfOneHundredXOneHundredRandomDoubles);
        assert bigTrimmed.length == 100;
        assert bigTrimmed[0].length == 98;
    }
}
