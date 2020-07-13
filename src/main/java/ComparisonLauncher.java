package main.java;

import edu.princeton.cs.algs4.StdRandom;

import java.io.FileNotFoundException;

public class ComparisonLauncher {
    protected CardinalityEstimationAlgorithm MinCount;
    protected CardinalityEstimationAlgorithm ProbabilisticCounting;
    protected CardinalityEstimationAlgorithm HyperBitBit;
    protected CardinalityEstimationAlgorithm newAlgorithm;

    protected StringStream stream;
    protected String fileName;

    protected final double[] sizes;
    protected final double[][] MCEstimates;
    protected final double[][] PCEstimates;
    protected final double[][] HBBEstimates;
    protected final double[][] newAlgEstimates;

    protected double[] varyMs;
    protected double[][] varyMEstimates;

    protected final int bigN;
    protected final int t;
    protected final int n;
    protected final int m;
    protected final int[] cardinalities;
    protected final double alpha;

    protected boolean syntheticData;

    public ComparisonLauncher(CardinalityEstimationAlgorithm alg, int size, int m, int[] cardinalities, double alpha, int trials, String input) throws FileNotFoundException {
        bigN = size;
        this.m = m;
        this.t = trials;
        this.n = cardinalities[cardinalities.length - 1];
        this.cardinalities = cardinalities;
        this.alpha = alpha;

        MinCount = new MinCount(m, n);
        ProbabilisticCounting = new ProbabilisticCounting(m, n);
        HyperBitBit = new HyperBitBit(alpha, m);
        newAlgorithm = alg;

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

        runConstantMExperiment();
    }

    private void readElement(String word) {
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

    private void resetReader() throws FileNotFoundException {
        stream.resetStream();
    }

    private void runConstantMExperiment() throws FileNotFoundException {
        // Run trials and update 2D arrays
        int j;
        for (int i = 0; i < t; i++) {
            j = 0;
            if (syntheticData) {
                while (j < bigN) {
                    readElement("");

                    if (i == 0) sizes[j] = newAlgorithm.getSize();

                    MCEstimates[i][j] = MinCount.getEstimateOfCardinality();
                    PCEstimates[i][j] = ProbabilisticCounting.getEstimateOfCardinality();
                    HBBEstimates[i][j] = HyperBitBit.getEstimateOfCardinality();
                    newAlgEstimates[i][j] = newAlgorithm.getEstimateOfCardinality();
                    j++;
                }
            } else {
                for (String element : stream) {
                    if (j > bigN) break;
                    readElement(element);

                    if (i == 0) sizes[j] = newAlgorithm.getSize();

                    MCEstimates[i][j] = MinCount.getEstimateOfCardinality();
                    PCEstimates[i][j] = ProbabilisticCounting.getEstimateOfCardinality();
                    HBBEstimates[i][j] = HyperBitBit.getEstimateOfCardinality();
                    newAlgEstimates[i][j] = newAlgorithm.getEstimateOfCardinality();
                    j++;
                }
            }
            MinCount.resetAlgorithm();
            ProbabilisticCounting.resetAlgorithm();
            HyperBitBit.resetAlgorithm();
            newAlgorithm.resetAlgorithm();
            if (!syntheticData) resetReader();
        }
    }

    public double[][] getAllMCRelativeErrors() {
        double[][] relErrors = new double[t][bigN];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < bigN; j++) {
                relErrors[i][j] = (Math.abs(MCEstimates[i][j] - cardinalities[j])) / cardinalities[j];
            }
        }
        return relErrors;
    }

    // returns a pure estimate of the cardinality after each new element is read
    public double[] getAvgMCRelativeErrors() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllMCRelativeErrors(), bigN);
    }

    public double[][] getAllPCRelativeErrors() {
        double[][] relErrors = new double[t][bigN];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < bigN; j++) {
                relErrors[i][j] = (Math.abs(PCEstimates[i][j] - cardinalities[j])) / cardinalities[j];
            }
        }
        return relErrors;
    }

    // returns a pure estimate of the cardinality after each new element is read
    public double[] getAvgPCRelativeErrors() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllPCRelativeErrors(), bigN);
    }

    public double[][] getAllNewAlgRelativeErrors() {
        double[][] relErrors = new double[t][bigN];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < bigN; j++) {
                relErrors[i][j] = (Math.abs(newAlgEstimates[i][j] - cardinalities[j])) / cardinalities[j];
            }
        }
        return relErrors;
    }

    // returns a pure estimate of the cardinality after each new element is read
    public double[] getAvgNewAlgRelativeErrors() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllNewAlgRelativeErrors(), bigN);
    }

    public double[][] getAllHBBRelativeErrors() {
        double[][] relErrors = new double[t][bigN];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < bigN; j++) {
                relErrors[i][j] = (Math.abs(HBBEstimates[i][j] - cardinalities[j])) / cardinalities[j];
            }
        }
        return relErrors;
    }

    // returns a pure estimate of the cardinality after each new element is read
    public double[] getAvgHBBRelativeErrors() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllHBBRelativeErrors(), bigN);
    }

    private double[] averageOverTrials(double[][] values, int arraySize) {
        double[] returnThis = new double[arraySize];

        // for each element i, average the trials
        double sum = 0;
        for (int i = 0; i < arraySize; i++) {
            for (int j = 0; j < t; j++) {
                sum += values[j][i];
            }
            returnThis[i] = sum / t;
            sum = 0;
        }

        return returnThis;
    }

    public static void main(String[] args) throws FileNotFoundException {
        String file = "mobydick.txt";
        boolean synthetic = false;

        int maxRead = 100000;
        int m = 64;
        int trials = 100;
        double alpha = 0.5;

        String input;
        String dataType;
        int size;
        int[] cardinalities;
        if (synthetic) {
            input = "synthetic";
            dataType = "Synthetic";
            size = maxRead;
            cardinalities = new int[size];
            for (int i = 0; i < size; i++) cardinalities[i] = i;
        } else {
            input = "src/datasets/" + file;
            dataType = "Real: " + input;
            size = Exact.total(input, maxRead);
            cardinalities = Exact.countArray(input, maxRead);
        }

        // This is what will change for the "client"'s algorithm
        CardinalityEstimationAlgorithm alg = new HyperBitBit(alpha, m);

        ComparisonLauncher launcher = new ComparisonLauncher(alg, size, m, cardinalities, alpha, trials, input);

        ComparisonGenerator report = new ComparisonGenerator(launcher);
        report.generateFullReport();
    }
}