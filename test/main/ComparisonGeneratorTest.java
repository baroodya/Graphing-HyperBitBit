package main;

import algs.HyperBitBit;
import helpers.Exact;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;

class ComparisonGeneratorTest {
    private final HyperBitBit newAlg = new HyperBitBit(0.5, 16);
    private final ComparisonLauncher launcher = new ComparisonLauncher(newAlg, 100, 16, Exact.countArray("src/datasets/f0", 100000), 0.5, 100, "src/datasets/f0");
    private ComparisonGenerator generator = new ComparisonGenerator(launcher);

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

    ComparisonGeneratorTest() throws FileNotFoundException {
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
        generator = new ComparisonGenerator(launcher);
    }

    @Test
    void generateFullReport() {
        double[] xValuesBefore = generator.xValues;
        String title = generator.title;
        String xAxis = generator.xAxis;
        String yAxis = generator.yAxis;

        generator.generateFullReport();

        assert !Arrays.equals(xValuesBefore, generator.xValues);
        assert !generator.title.equals(title);
        assert !generator.xAxis.equals(xAxis);
        assert !generator.yAxis.equals(yAxis);
    }

    @Test
    void showErrorComparison() {
        generator.showErrorComparison();

        assert generator.title.equals("Comparison of the error of your Algorithm with PC, MC, and HBB");
        assert generator.xAxis.equals("Number of Inputs Seen (N)");
        assert generator.yAxis.equals("Relative Error (|Z_n - n|/n)");
        assert Arrays.equals(generator.xValues, oneThruOneHundred);
    }

    @Test
    void showVaryMComparison() {
        generator.showVaryMComparison();

        assert generator.title.equals("Comparison of the accuracy of your Algorithm with PC, MC, and HBB");
        assert generator.xAxis.equals("Number of Substreams (m)");
        assert generator.yAxis.equals("Relative Error (|Z_n - n|/n)");
        assert Arrays.equals(generator.xValues, oneThruSixteen);
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
