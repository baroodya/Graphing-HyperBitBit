package main.java;

import java.io.FileNotFoundException;

public class ComparisonGenerator {
    protected String title; // Title of the graph (should be updated in each method)
    protected String xAxis; // Title of xAxis (see above)
    protected String yAxis; // Title of yAxis (see above)

    protected final double[] xValues; // Array that holds 0-bigN

    protected final CustomGrapher grapher; // The class we will use to graph
    protected final ComparisonLauncher launcher; // The launcher we will get the data from

    // Constructor initializes everything we need to be initialized
    public ComparisonGenerator(ComparisonLauncher launcher) {
        this.launcher = launcher;
        grapher = new CustomGrapher();

        xValues = launcher.sizes;
    }

    // Full report is kinda lame...may be updated in the future
    public void generateFullReport() {
        showComparison();
    }

    // Shows the comparison of the new algorithm with the three old algorithms
    public void showComparison() {
        title = "Comparison of your Algorithm with PC, MC, and HBB";
        xAxis = "Number of Inputs Seen (N)";
        yAxis = "Relative Error (|Z_n - n|/n)";

        double[][] data = new double[4][launcher.bigN];
        data[0] = launcher.getAvgMCRelativeErrors();
        data[1] = launcher.getAvgPCRelativeErrors();
        data[2] = launcher.getAvgHBBRelativeErrors();
        data[3] = launcher.getAvgNewAlgRelativeErrors();
        // Pass the relevant data and Strings to CustomGrapher.java
        grapher.showCompLinePlot(title, xAxis, yAxis, grapher.createCompTable(xAxis, manageArray(xValues), manageArray(data)));
    }

    // A helper method to get rid of data that messes with the display window
    private double[] manageArray(double[] array) {
        int newLength = array.length - (array.length / 50);
        double[] returnThis = new double[newLength];

        if (newLength >= 0)
            System.arraycopy(array, (array.length / 50), returnThis, 0, newLength);

        return returnThis;
    }

    // See above, but for 2D arrays
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
