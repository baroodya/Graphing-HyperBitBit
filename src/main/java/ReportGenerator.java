package main.java;

import graphing.java.CustomGrapher;

public class ReportGenerator {
    // variables for graphing
    protected final int numShown;
    protected double[] xValues;
    protected String title;
    protected String xAxis;
    protected String yAxis;

    // launcher and grapher classes for collecting and outputting data
    protected ExperimentLauncher launcher;
    protected CustomGrapher grapher;

    // Constructor initializes variables, sets first values of xValues
    public ReportGenerator(ExperimentLauncher launcher, int numberOfSingleTrialsShown) {
        this.launcher = launcher;
        numShown = numberOfSingleTrialsShown;
        grapher = new CustomGrapher();

        xValues = launcher.getSizes();
    }

    // Produces every graph it can
    public void generateFullReport() {
        showEstCardinality();
        showNormEstCardinality();
        showAbsError();
        showRelError();
        showMEstCardinality();
        showMNormEstCardinality();
        showMAbsError();
        showMRelError();
        showStdDev();
        showDistributions();
    }

    // Produces a basic 7 graph report
    public void generateBasicReport() {
        showEstCardinality();
        showNormEstCardinality();
        showAbsError();
        showRelError();
        showMRelError();
        showStdDev();
        showDistributions();
    }

    // shows the estimated cardinality graph
    public void showEstCardinality() {
        // Graph 1: Number of Inputs Seen vs. Estimated Cardinality ($N$ vs. $Z_n$)
        xValues = launcher.getSizes();
        title = "Average Estimated Cardinality of Data Stream";
        xAxis = "Number of Inputs Seen (N)";
        yAxis = "Estimated Cardinality (Z_n)";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(title,
                        xAxis,
                        manageArray(xValues),
                        manageArray(launcher.getAvgEstimates()),
                        manageArray(launcher.estimates)),
                numShown);
    }

    // Shows the normalized estimated cardinality graph
    public void showNormEstCardinality() {
        // Graph 2: Number of Inputs Seen vs. Normalized Estimated Cardinality
        xValues = launcher.getSizes();
        title = "Average Normalized Estimated Cardinality of Data Stream";
        xAxis = "Number of Inputs Seen (N)";
        yAxis = "Normalized Estimated Cardinality ((Z_n / n) - 1)";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(title,
                        xAxis,
                        manageArray(xValues),
                        manageArray(launcher.getAvgNormalizedEstimates()),
                        manageArray(launcher.getAllNormalizedEstimates())),
                numShown);
    }

    // Shows the absolute error graph
    public void showAbsError() {
        // Graph 3: Number of Inputs Seen vs. Absolute Error ($N$ vs. $|Z_n - n|$)
        xValues = launcher.getSizes();
        title = "Average Absolute Error";
        xAxis = "Number of Inputs Seen (N)";
        yAxis = "Absolute Error (|Z_n - n|)";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(title,
                        xAxis,
                        manageArray(xValues),
                        manageArray(launcher.getAvgAbsoluteErrors()),
                        manageArray(launcher.getAllAbsoluteErrors())),
                numShown);
    }

    // Shows the relative error graph
    public void showRelError() {
        // Graph 4: Number of Inputs Seen vs. Relative Error
        xValues = launcher.getSizes();
        title = "Average Relative Error";
        xAxis = "Number of Inputs Seen (N)";
        yAxis = "Relative Error (|Z_n - n|/n)";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(title,
                        xAxis,
                        manageArray(xValues),
                        manageArray(launcher.getAvgRelativeErrors()),
                        manageArray(launcher.getAllRelativeErrors())),
                numShown);
    }

    // Shows the estimated cardinality graph for m = 1 thru m = m
    public void showMEstCardinality() {
        // Graph 5: Number of Substreams vs. Estimated Cardinality ($N$ vs. $Z_n$)
        xValues = launcher.varyMs;
        title = "Average Estimated Cardinality of Data Stream";
        xAxis = "Number of Substreams (m)";
        yAxis = "Estimated Cardinality (Z_n)";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(title,
                        xAxis, xValues, launcher.getAvgEstimatesVaryM(), launcher.varyMEstimates),
                numShown);
    }

    // Shows the normalized estimated cardinality graph for m = 1 thru m = m
    public void showMNormEstCardinality() {
        // Graph 6: Number of Substreams vs. Normalized Estimated Cardinality
        xValues = launcher.varyMs;
        title = "Average Normalized Estimated Cardinality of Data Stream";
        xAxis = "Number of Substreams (m)";
        yAxis = "Normalized Estimated Cardinality ((Z_n / n) - 1)";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(title,
                        xAxis,
                        xValues,
                        launcher.getAvgNormalizedEstimatesVaryM(),
                        launcher.getAllNormalizedEstimatesVaryM()),
                numShown);
    }

    // Shows the absolute error graph for m = 1 thru m = m
    public void showMAbsError() {
        // Graph 7: Number of Substreams vs. Absolute Error ($N$ vs. $|Z_n - n|$)
        xValues = launcher.varyMs;
        title = "Average Absolute Error";
        xAxis = "Number of Substreams (m)";
        yAxis = "Absolute Error (|Z_n - n|)";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(title,
                        xAxis,
                        xValues,
                        launcher.getAvgAbsoluteErrorsVaryM(),
                        launcher.getAllAbsoluteErrorsVaryM()),
                numShown);
    }

    // Shows the relative error graph for m = 1 thru m = m
    public void showMRelError() {
        // Graph 8: Number of Substreams vs. Relative Error
        xValues = launcher.varyMs;
        title = "Average Relative Error";
        xAxis = "Number of Substreams (m)";
        yAxis = "Relative Error (|Z_n - n|/n)";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(title,
                        xAxis,
                        xValues,
                        launcher.getAvgRelativeErrorsVaryM(),
                        launcher.getAllRelativeErrorsVaryM()),
                numShown);
    }

    // Shows the standard deviation of the trials as a function of m
    protected void showStdDev() {
        xValues = launcher.varyMs;
        title = "Standard Deviation of Estimates";
        xAxis = "Number of Substreams (m)";
        yAxis = "Standard Deviation of " + launcher.t + " Trials";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(title,
                        xAxis,
                        manageArray(launcher.varyMs),
                        yAxis,
                        manageArray(launcher.getStdDevOfAllTrials())
                ));
    }

    // Shows the distributions of the final estimates for three different values of m
    public void showDistributions() {
        title = "Distribution of Estimates";

        double[][] data = new double[3][launcher.t];

        int mValue;
        for (int i = 0; i < data.length; i++) {
            switch (i) {
                case 0:
                    mValue = 2;
                    break;
                case 1:
                    mValue = launcher.m / 8 - 1;
                    if (mValue < 0) mValue = 1;
                    break;
                default:
                    mValue = launcher.m - 1;
                    if (mValue < 0) mValue = launcher.m - 1;
                    break;
            }

            for (int j = 0; j < data[i].length; j++) {
                data[i][j] = launcher.varyMEstimates[j][mValue];
            }
        }

        grapher.showLineDistributions(title, data, launcher.t / 4, launcher.m, launcher.n);
    }

    // Helper method to truncate arrays and get rid of non-useful data
    protected double[] manageArray(double[] array) {
        int newLength = array.length - (array.length / 50);
        double[] returnThis = new double[newLength];

        if (newLength >= 0)
            System.arraycopy(array, (array.length / 50), returnThis, 0, newLength);

        return returnThis;
    }

    // See above
    protected double[][] manageArray(double[][] array) {
        int newLength = array[0].length - (array[0].length / 50);
        double[][] returnThis = new double[array.length][newLength];

        for (int i = 0; i < array.length; i++) returnThis[i] = manageArray(array[i]);

        return returnThis;
    }
}
