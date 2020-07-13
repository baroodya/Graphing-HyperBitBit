package main.java;

import java.io.FileNotFoundException;

public class ComparisonGenerator {
    protected String title;
    protected String xAxis;
    protected String yAxis;

    protected double[] xValues;

    protected CustomGrapher grapher;
    protected ComparisonLauncher launcher;

    public ComparisonGenerator(ComparisonLauncher launcher) {
        this.launcher = launcher;
        grapher = new CustomGrapher();

        xValues = launcher.sizes;
    }

    public void generateFullReport() {
        showComparison();
    }

    public void showComparison() {
        title = "Comparison of your Algorithm with PC, MC, and HBB";
        xAxis = "Number of Inputs Seen (N)";
        yAxis = "Relative Error (|Z_n - n|/n)";

        double[][] data = new double[4][launcher.bigN];
        data[0] = launcher.getAvgMCRelativeErrors();
        data[1] = launcher.getAvgPCRelativeErrors();
        data[2] = launcher.getAvgHBBRelativeErrors();
        data[3] = launcher.getAvgNewAlgRelativeErrors();
        grapher.showCompLinePlot(title, xAxis, yAxis, grapher.createCompTable(xAxis, manageArray(xValues), manageArray(data)));
    }

    private double[] manageArray(double[] array) {
        int newLength = array.length - (array.length / 50);
        double[] returnThis = new double[newLength];

        for (int i = 0; i < newLength; i++) {
            returnThis[i] = array[i + (array.length / 50)];
        }

        return returnThis;
    }

    private double[][] manageArray(double[][] array) {
        int newLength = array[0].length - (array[0].length / 50);
        double[][] returnThis = new double[array.length][newLength];

        for (int i = 0; i < array.length; i++) returnThis[i] = manageArray(array[i]);

        return returnThis;
    }

    public static void main(String[] args) throws FileNotFoundException {
        int[] cardinalities = new int[10000];
        for (int i = 0; i < 10000; i++) cardinalities[i] = i;
        CardinalityEstimationAlgorithm hbb = new HyperBitBit(64, 10000);
        ComparisonLauncher experiment = new ComparisonLauncher(hbb, 10000, 64, cardinalities, 0.5, 100, "synthetic");

        ComparisonGenerator report = new ComparisonGenerator(experiment);
        report.generateFullReport();
    }
}
