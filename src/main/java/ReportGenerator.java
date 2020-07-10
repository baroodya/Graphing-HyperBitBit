package main.java;

import java.io.FileNotFoundException;

public class ReportGenerator {

    protected final int numShown;
    protected double[] xValues;
    protected String title;
    protected String xAxis;
    protected String yAxis;

    protected ExperimentLauncher launcher;
    protected CustomGrapher grapher;

    public ReportGenerator(ExperimentLauncher launcher, int numberOfSingleTrialsShown) {
        this.launcher = launcher;
        numShown = numberOfSingleTrialsShown;
        grapher = new CustomGrapher();

        xValues = launcher.getSizes();
    }

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

    public void generateBasicReport() {
        showEstCardinality();
        showNormEstCardinality();
        showAbsError();
        showRelError();
        showStdDev();
        showMEstCardinality();
        showDistributions();
    }

    public void generateDistributionReport() {
        showDistributions();
    }

    public void showEstCardinality() {
        // Graph 1: Number of Inputs Seen vs. Estimated Cardinality ($N$ vs. $Z_n$)
        title = "Average Estimated Cardinality of Data Stream";
        xAxis = "Number of Inputs Seen (N)";
        yAxis = "Estimated Cardinality (Z_n)";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(
                        xAxis,
                        manageArray(xValues),
                        manageArray(launcher.getAvgEstimates()),
                        manageArray(launcher.estimates)),
                numShown);
    }

    public void showNormEstCardinality() {
        // Graph 2: Number of Inputs Seen vs. Normalized Estimated Cardinality
        title = "Average Normalized Estimated Cardinality of Data Stream";
        xAxis = "Number of Inputs Seen (N)";
        yAxis = "Normalized Estimated Cardinality ((Z_n / n) - 1)";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(
                        xAxis,
                        manageArray(xValues),
                        manageArray(launcher.getAvgNormalizedEstimates()),
                        manageArray(launcher.getAllNormalizedEstimates())),
                numShown);
    }

    public void showAbsError() {
        // Graph 3: Number of Inputs Seen vs. Absolute Error ($N$ vs. $|Z_n - n|$)
        title = "Average Absolute Error";
        xAxis = "Number of Inputs Seen (N)";
        yAxis = "Absolute Error (|Z_n - n|)";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(
                        xAxis,
                        manageArray(xValues),
                        manageArray(launcher.getAvgAbsoluteErrors()),
                        manageArray(launcher.getAllAbsoluteErrors())),
                numShown);
    }

    public void showRelError() {
        // Graph 4: Number of Inputs Seen vs. Relative Error
        title = "Average Relative Error";
        xAxis = "Number of Inputs Seen (N)";
        yAxis = "Relative Error (|Z_n - n|/n)";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(
                        xAxis,
                        manageArray(xValues),
                        manageArray(launcher.getAvgRelativeErrors()),
                        manageArray(launcher.getAllRelativeErrors())),
                numShown);
    }

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
                grapher.createTable(
                        xAxis, xValues, launcher.getAvgEstimatesVaryM(), launcher.varyMEstimates),
                numShown);
    }

    public void showMNormEstCardinality() {
        // Graph 6: Number of Substreams vs. Normalized Estimated Cardinality
        title = "Average Normalized Estimated Cardinality of Data Stream";
        xAxis = "Number of Substreams (m)";
        yAxis = "Normalized Estimated Cardinality (Z_n)";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(
                        xAxis,
                        xValues,
                        launcher.getAvgNormalizedEstimatesVaryM(),
                        launcher.getAllNormalizedEstimatesVaryM()),
                numShown);
    }

    public void showMAbsError() {
        // Graph 7: Number of Substreams vs. Absolute Error ($N$ vs. $|Z_n - n|$)
        title = "Average Absolute Error";
        yAxis = "Absolute Error (|Z_n - n|)";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(
                        xAxis,
                        xValues,
                        launcher.getAvgAbsoluteErrorsVaryM(),
                        launcher.getAllAbsoluteErrorsVaryM()),
                numShown);
    }

    public void showMRelError() {
        // Graph 8: Number of Substreams vs. Relative Error
        title = "Average Relative Error";
        xAxis = "Number of Substreams (m)";
        yAxis = "Relative Error (|Z_n - n|/n)";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(
                        xAxis,
                        xValues,
                        launcher.getAvgRelativeErrorsVaryM(),
                        launcher.getAllMRelativeErrorsVaryM()),
                numShown);
    }

    private void showStdDev() {
        title = "Standard Deviation of Estimates";
        xAxis = "Number of Substreams (m)";
        yAxis = "Standard Deviation of " + launcher.t + " Trials";

        grapher.showLinePlot(
                title,
                xAxis,
                yAxis,
                grapher.createTable(
                        xAxis,
                        launcher.varyMs,
                        yAxis,
                        launcher.getStdDevOfAllTrials()
                ));
    }

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

        grapher.showDistribution(title, data, launcher.bigN, launcher.m, launcher.bigN, launcher.t);
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
        ExperimentLauncher experiment = new ExperimentLauncher("MC", 100000, 256, 100000, 0.5, 100);

        ReportGenerator report = new ReportGenerator(experiment, 50);
        report.generateFullReport();
    }
}
