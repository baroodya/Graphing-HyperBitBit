package main.java;

import algs.java.CardinalityEstimationAlgorithm;
import algs.java.HyperBitBit;
import algs.java.MinCount;
import algs.java.ProbabilisticCounting;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import helpers.java.Exact;
import helpers.java.StringStream;

import java.io.FileNotFoundException;

public class ComparisonLauncher {
    // The four algorithms: three to compare the new one to
    protected CardinalityEstimationAlgorithm MinCount;
    protected CardinalityEstimationAlgorithm ProbabilisticCounting;
    protected CardinalityEstimationAlgorithm HyperBitBit;
    protected CardinalityEstimationAlgorithm newAlgorithm;

    // The stream of input and the file from which it'll come
    protected StringStream stream;
    protected String fileName;

    // A 1D array for the xValues
    protected final double[] sizes;
    // 2D arrays for each alg to store the estimates (from which we can calculate errors)
    protected final double[][] MCEstimates;
    protected final double[][] PCEstimates;
    protected final double[][] HBBEstimates;
    protected final double[][] newAlgEstimates;

    // The number of different m values tried
    protected double[] varyMs;
    // Every estimate collected in all of the trials for each value of m (only the final value of each trial)
    protected double[][] varyMMCEstimates;
    protected double[][] varyMPCEstimates;
    protected double[][] varyMHBBEstimates;
    protected double[][] varyMNewAlgEstimates;

    // Necessary Constants needed for computation
    protected final int bigN;
    protected final int t;
    protected final int n;
    protected final int m;
    protected final int[] cardinalities;
    protected final double alpha;

    // A boolean value to help the reader decide which type of input to read
    protected boolean syntheticData;

    // A double for status updates
    protected double percent;
    protected double denom;

    public ComparisonLauncher(CardinalityEstimationAlgorithm alg, int size, int m, int[] cardinalities, double alpha, int trials, String input) throws FileNotFoundException {
        // Copy Constants
        bigN = size;
        this.m = m;
        this.t = trials;
        this.n = cardinalities[cardinalities.length - 1];
        this.cardinalities = cardinalities;
        this.alpha = alpha;

        // Initialize algorithms
        MinCount = new MinCount(m);
        ProbabilisticCounting = new ProbabilisticCounting(m, 0.77351);
        HyperBitBit = new HyperBitBit(alpha, m);
        newAlgorithm = alg;
        newAlgorithm.resetAlgorithm(m);

        // Determine type of input; create stream if necessary
        if (input.equals("synthetic")) syntheticData = true;
        else {
            syntheticData = false;
            fileName = input;
            // Read in the file
            stream = new StringStream(fileName, bigN);
        }

        // Create 2D trial x size arrays to hold data points for each trial
        sizes = new double[bigN];
        MCEstimates = new double[t][bigN];
        PCEstimates = new double[t][bigN];
        HBBEstimates = new double[t][bigN];
        newAlgEstimates = new double[t][bigN];

        varyMs = new double[m];

        varyMMCEstimates = new double[t][m];
        varyMPCEstimates = new double[t][m];
        varyMHBBEstimates = new double[t][m];
        varyMNewAlgEstimates = new double[t][m];

        percent = 0.0;
        denom = t * m * ((m + 3.0) / 2.0);

        StdOut.println();
    }

    // A helper method to read the appropriate type of data and pass it to each algorithm
    protected void readElement(String word) {
        if (syntheticData) {
            double random = StdRandom.uniform();
            MinCount.readSyntheticElement(random);
            ProbabilisticCounting.readSyntheticElement(random);
            HyperBitBit.readSyntheticElement(random);
            newAlgorithm.readSyntheticElement(random);
        } else {
            MinCount.readElement(word);
            ProbabilisticCounting.readElement(word);
            HyperBitBit.readElement(word);
            newAlgorithm.readElement(word);
        }
    }

    // Helper method to run t trials of m = 1, m = 2, ... , m = m
    protected void runExperiments() throws FileNotFoundException {
        MinCount.resetAlgorithm(1);
        ProbabilisticCounting.resetAlgorithm(1);
        HyperBitBit.resetAlgorithm(1);
        newAlgorithm.resetAlgorithm(1);
        int counter = m * t;
        for (int k = 1; k <= m; k++) {
            varyMs[k - 1] = k;

            // Run trials and update 2D arrays
            for (int i = 0; i < t; i++) {
                percent = ((double) (counter + k) / (denom)) * 100;
                StdOut.print("\r" + "Running Variable m = " + k + "/" + m + ". On trial " + (i + 1) + "/" + t + ". (");
                StdOut.printf("%.2f", percent);
                StdOut.print("%)");

                int j = 0;
                if (syntheticData) {
                    while (j < bigN) {
                        readElement("");

                        if (k == m) {
                            if (i == 0) sizes[j] = MinCount.getSize();

                            MCEstimates[i][j] = MinCount.getEstimateOfCardinality();
                            PCEstimates[i][j] = ProbabilisticCounting.getEstimateOfCardinality();
                            HBBEstimates[i][j] = HyperBitBit.getEstimateOfCardinality();
                            newAlgEstimates[i][j] = newAlgorithm.getEstimateOfCardinality();
                        }
                        j++;
                    }

                } else {
                    for (String element : stream) {
                        readElement(element);

                        if (k == m) {
                            if (i == 0) sizes[j] = MinCount.getSize();

                            MCEstimates[i][j] = MinCount.getEstimateOfCardinality();
                            PCEstimates[i][j] = ProbabilisticCounting.getEstimateOfCardinality();
                            HBBEstimates[i][j] = HyperBitBit.getEstimateOfCardinality();
                            newAlgEstimates[i][j] = newAlgorithm.getEstimateOfCardinality();
                        }
                        j++;
                    }

                }
                varyMMCEstimates[i][k - 1] = MinCount.getEstimateOfCardinality();
                varyMPCEstimates[i][k - 1] = ProbabilisticCounting.getEstimateOfCardinality();
                varyMHBBEstimates[i][k - 1] = HyperBitBit.getEstimateOfCardinality();
                varyMNewAlgEstimates[i][k - 1] = newAlgorithm.getEstimateOfCardinality();

                // reset the algorithm and the stream
                MinCount.resetAlgorithm(k + 1);
                ProbabilisticCounting.resetAlgorithm(k + 1);
                HyperBitBit.resetAlgorithm(k + 1);
                newAlgorithm.resetAlgorithm(k + 1);
                if (!syntheticData) stream.resetStream();
                counter += k;
            }
        }
    }

    // A method that returns all the relative errors from MinCount
    public double[][] getAllMCRelativeErrors() {
        double[][] relErrors = new double[t][bigN];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < bigN; j++) {
                relErrors[i][j] = (Math.abs(MCEstimates[i][j] - cardinalities[j])) / cardinalities[j];
            }
        }
        return relErrors;
    }

    // returns a pure estimate of the Relative Error after each new element is read
    public double[] getAvgMCRelativeErrors() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllMCRelativeErrors());
    }

    // A method that returns all the relative errors from Probabilistic Counting
    public double[][] getAllPCRelativeErrors() {
        double[][] relErrors = new double[t][bigN];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < bigN; j++) {
                relErrors[i][j] = (Math.abs(PCEstimates[i][j] - cardinalities[j])) / cardinalities[j];
            }
        }
        return relErrors;
    }

    // returns a pure estimate of the relative error after each new element is read
    public double[] getAvgPCRelativeErrors() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllPCRelativeErrors());
    }

    // A method that returns all the relative errors from HyperBitBit
    public double[][] getAllHBBRelativeErrors() {
        double[][] relErrors = new double[t][bigN];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < bigN; j++) {
                relErrors[i][j] = (Math.abs(HBBEstimates[i][j] - cardinalities[j])) / cardinalities[j];
            }
        }
        return relErrors;
    }

    // returns a pure estimate of the relative error after each new element is read
    public double[] getAvgHBBRelativeErrors() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllHBBRelativeErrors());
    }

    // A method that returns all the relative errors from the new Algorithm
    public double[][] getAllNewAlgRelativeErrors() {
        double[][] relErrors = new double[t][bigN];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < bigN; j++) {
                relErrors[i][j] = (Math.abs(newAlgEstimates[i][j] - cardinalities[j])) / cardinalities[j];
            }
        }
        return relErrors;
    }

    // returns a pure estimate of the relative error after each new element is read
    public double[] getAvgNewAlgRelativeErrors() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllNewAlgRelativeErrors());
    }

    // returns an average estimate for the final cardinality for each value of m
    public double[] getAvgMCEstimatesVaryM() {
        double[][] relErrors = new double[t][m];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < m; j++) {
                relErrors[i][j] = (Math.abs(varyMMCEstimates[i][j] - cardinalities[bigN - 1])) / cardinalities[bigN - 1];
            }
        }
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(relErrors);
    }

    // returns an average estimate for the final cardinality for each value of m
    public double[] getAvgPCEstimatesVaryM() {
        double[][] relErrors = new double[t][m];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < m; j++) {
                relErrors[i][j] = (Math.abs(varyMPCEstimates[i][j] - cardinalities[bigN - 1])) / cardinalities[bigN - 1];
            }
        }
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(relErrors);
    }

    // returns an average estimate for the final cardinality for each value of m
    public double[] getAvgHBBEstimatesVaryM() {
        double[][] relErrors = new double[t][m];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < m; j++) {
                relErrors[i][j] = (Math.abs(varyMHBBEstimates[i][j] - cardinalities[bigN - 1])) / cardinalities[bigN - 1];
            }
        }
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(relErrors);
    }

    // returns an average estimate for the final cardinality for each value of m
    public double[] getAvgNewAlgEstimatesVaryM() {
        double[][] relErrors = new double[t][m];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < m; j++) {
                relErrors[i][j] = (Math.abs(varyMNewAlgEstimates[i][j] - cardinalities[bigN - 1])) / cardinalities[bigN - 1];
            }
        }
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(relErrors);
    }

    // Helper method to average a 2D array vertically
    protected double[] averageOverTrials(double[][] values) {
        int trials = values.length;
        int arraySize = values[0].length;
        double[] returnThis = new double[arraySize];

        // for each element i, average the trials
        double sum = 0;
        for (int i = 0; i < arraySize; i++) {
            for (double[] value : values) {
                sum += value[i];
            }
            returnThis[i] = sum / trials;
            sum = 0;
        }

        return returnThis;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String file = "f0";
        boolean synthetic = Boolean.parseBoolean(args[0]);

        int maxRead = 1000;
        int m = 64;
        int trials = 100;
        double alpha = 0.5;
        double phi = 1;

        String input;
        int size;
        int[] cardinalities;
        if (synthetic) {
            input = "synthetic";
            size = maxRead;
            cardinalities = new int[size];
            for (int i = 0; i < size; i++) cardinalities[i] = i;
        } else {
            input = "src/datasets/" + file;
            size = Exact.total(input, maxRead);
            cardinalities = Exact.countArray(input, maxRead);
        }

        // This is what will change for the "client"'s algorithm
        CardinalityEstimationAlgorithm alg = new algs.java.ProbabilisticCounting(m, phi);

        ComparisonLauncher launcher = new ComparisonLauncher(alg, size, m, cardinalities, alpha, trials, input);
        launcher.runExperiments();

        ComparisonGenerator report = new ComparisonGenerator(launcher);
        report.generateFullReport();
    }
}
