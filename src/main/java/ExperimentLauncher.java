package main.java;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;

import java.io.FileNotFoundException;
import java.io.IOException;

public class ExperimentLauncher {
    protected CardinalityEstimationAlgorithm algorithm;

    protected StringStream stream;
    protected String fileName;

    protected final double[] sizes;
    protected final double[][] estimates;

    protected double[] varyMs;
    protected double[][] varyMEstimates;

    protected final int bigN;
    protected final int t;
    protected final int n;
    protected final int m;
    protected final int[] cardinalities;
    protected final String alg;
    protected final double alpha;

    protected boolean syntheticData;

    public ExperimentLauncher(
            String alg, int size, int m, int[] cardinalities, double alpha, int trials, String input)
            throws FileNotFoundException {
        // Save the cardinality and trials to be used in other methods
        t = trials;
        this.m = m;
        this.cardinalities = cardinalities;
        bigN = size;
        n = cardinalities[bigN - 1];
        this.alg = alg;
        this.alpha = alpha;
        fileName = input;

        syntheticData = false;

        // Read in the file
        stream = new StringStream(fileName, bigN);

        // Create 2D trial x size arrays to hold data points for each trial
        sizes = new double[bigN];
        estimates = new double[t][bigN];

        varyMs = new double[m];
        varyMEstimates = new double[t][m];

        runConstantMExperiment();
        runVariableMExperiment();
    }

    public ExperimentLauncher(String alg, int size, int m, int[] cardinalities, double alpha, int trials)
            throws FileNotFoundException {
        // Save the size, cardinality, and trials to be used in other methods
        bigN = size;
        t = trials;
        this.cardinalities = cardinalities;
        n = cardinalities[bigN - 1];
        this.m = m;
        this.alg = alg;
        this.alpha = alpha;

        stream = new StringStream("src/datasets/log.07.f3.txt", bigN);

        syntheticData = true;

        // Create 2D trial x size arrays to hold data points for each trial
        sizes = new double[bigN];
        estimates = new double[t][bigN];

        varyMs = new double[m];
        varyMEstimates = new double[t][m];

        runConstantMExperiment();
        runVariableMExperiment();
    }

    // TODO: figure out how this is going to work
    private void readElement(String word) {
        if (syntheticData) {
            double random = StdRandom.uniform();
            algorithm.readSyntheticElement(random);
        } else {
            algorithm.readElement(word);
        }
    }

    private void resetReader() throws FileNotFoundException {
        stream.resetStream();
    }

    private void runConstantMExperiment() throws FileNotFoundException {
        // Decide which algorithm to use
        switch (alg) {
            case "PC":
                algorithm = new ProbabilisticCounting(m, n);
                break;
            case "MC":
                algorithm = new MinCount(m, n);
                break;
            case "HBB":
                algorithm = new HyperBitBit(alpha, m);
                break;
            default:
                throw new IllegalArgumentException("This type of Algorithm is not supported.");
        }

        // Run trials and update 2D arrays
        int j;
        for (int i = 0; i < t; i++) {
            j = 0;
            for (String element : stream) {
                if (j > bigN) break;
                readElement(element);

                if (i == 0) sizes[j] = algorithm.getSize();

                estimates[i][j] = algorithm.getEstimateOfCardinality();
                j++;
            }
            algorithm.resetAlgorithm();
            resetReader();
        }
    }

    private void runVariableMExperiment() throws FileNotFoundException {
        // Decide which algorithm to use
        switch (alg) {
            case "PC":
                algorithm = new ProbabilisticCounting(1, n);
                break;
            case "MC":
                algorithm = new MinCount(1, n);
                break;
            case "HBB":
                algorithm = new HyperBitBit(alpha, 1);
                break;
            default:
                throw new IllegalArgumentException("This type of Algorithm is not supported.");
        }

        for (int k = 1; k <= m; k++) {
            varyMs[k - 1] = k;

            // Run trials and update 2D arrays
            for (int i = 0; i < t; i++) {
                for (String element : stream) readElement(element);

                varyMEstimates[i][k - 1] = algorithm.getEstimateOfCardinality();
                //        varyMAbsErrors[i][k - 1] = algorithm.getAbsoluteError();
                //        varyMRelErrors[i][k - 1] = algorithm.getRelativeError();

                algorithm.resetAlgorithm(k + 1);
                resetReader();
            }
        }
    }

    // returns the size array. remember that size[i] = i.
    public double[] getSizes() {
        return sizes;
    }

    // returns a pure estimate of the cardinality after each new element is read
    public double[] getAvgEstimates() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(estimates, bigN);
    }

    public double[][] getAllAbsoluteErrors() {
        double[][] absErrors = new double[t][bigN];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < bigN; j++) {
                absErrors[i][j] = Math.abs(estimates[i][j] - cardinalities[j]);
            }
        }
        return absErrors;
    }

    // returns a pure estimate of the cardinality after each new element is read
    public double[] getAvgAbsoluteErrors() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllAbsoluteErrors(), bigN);
    }

    public double[][] getAllRelativeErrors() {
        double[][] relErrors = new double[t][bigN];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < bigN; j++) {
                relErrors[i][j] = (Math.abs(estimates[i][j] - cardinalities[j])) / cardinalities[j];
            }
        }
        return relErrors;
    }

    // returns a pure estimate of the cardinality after each new element is read
    public double[] getAvgRelativeErrors() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllRelativeErrors(), bigN);
    }

    // returns a normalized estimate of the cardinality after each new element is read
    public double[] getAvgNormalizedEstimates() {
        double[] returnThis = new double[bigN];

        // Take the regular estimates and normalize them
        // 0 means that the estimate is exactly correct
        double[] regEstimates = getAvgEstimates();
        for (int i = 0; i < bigN; i++)
            returnThis[i] = (regEstimates[i] / cardinalities[i]) - 1;

        // return the normalized estimates. This is a 1D array
        return returnThis;
    }

    // Returns a 2D array of all normalized estimates for all trials
    public double[][] getAllNormalizedEstimates() {
        double[][] returnThis = new double[t][bigN];

        for (int i = 0; i < t; i++) {
            for (int j = 0; j < bigN; j++) {
                returnThis[i][j] = (estimates[i][j] / cardinalities[j]) - 1;
            }
        }

        return returnThis;
    }

    // returns a pure estimate of the cardinality after each new element is read
    public double[] getAvgEstimatesVaryM() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(varyMEstimates, m);
    }

    public double[][] getAllAbsoluteErrorsVaryM() {
        double[][] varyMAbsErrors = new double[t][m];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < m; j++) {
                varyMAbsErrors[i][j] = Math.abs(varyMEstimates[i][j] - n);
            }
        }

        return varyMAbsErrors;
    }

    // returns a pure estimate of the cardinality after each new element is read
    public double[] getAvgAbsoluteErrorsVaryM() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllAbsoluteErrorsVaryM(), m);
    }

    public double[][] getAllMRelativeErrorsVaryM() {
        double[][] varyMRelErrors = new double[t][m];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < m; j++) {
                varyMRelErrors[i][j] =
                        (Math.abs(varyMEstimates[i][j] - n)) / n;
            }
        }

        return varyMRelErrors;
    }

    // returns a pure estimate of the cardinality after each new element is read
    public double[] getAvgRelativeErrorsVaryM() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllMRelativeErrorsVaryM(), m);
    }

    // returns a normalized estimate of the cardinality after each new element is read
    public double[] getAvgNormalizedEstimatesVaryM() {
        double[] returnThis = new double[m];

        // Take the regular estimates and normalize them
        // 0 means that the estimate is exactly correct
        double[] regEstimates = getAvgEstimatesVaryM();
        for (int i = 0; i < m; i++) returnThis[i] = (regEstimates[i] / n) - 1;

        // return the normalized estimates. This is a 1D array
        return returnThis;
    }

    // Returns a 2D array of all normalized estimates for all trials
    public double[][] getAllNormalizedEstimatesVaryM() {
        double[][] returnThis = new double[t][m];

        for (int i = 0; i < t; i++) {
            for (int j = 0; j < m; j++) {
                returnThis[i][j] = (varyMEstimates[i][j] / n) - 1;
            }
        }

        return returnThis;
    }

    public double[] getFinalTrial() {
        double[] returnThis = new double[t];
        for (int i = 0; i < t; i++) returnThis[i] = estimates[i][bigN - 1];
        return returnThis;
    }

    public double getMeanOfFinalTrial() {
        return arithmeticMean(getFinalTrial());
    }

    public double[] getStdDevOfAllTrials() {
        double[] means = getAvgEstimatesVaryM();
        double[] data = new double[m];

        double sum;
        for (int i = 0; i < m; i++) {
            sum = 0;
            for (int j = 0; j < t; j++) {
                sum += Math.pow((means[i] - estimates[j][i]), 2);
            }
            data[i] = Math.sqrt(sum / i);
        }

        return data;
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

    // Helper method to calculate the arithmetic mean
    private double arithmeticMean(double[] values) {
        double sum = 0;
        for (double value : values) sum += value;

        return sum / values.length;
    }

    public static void main(String[] args) throws IOException {
        Stopwatch watch = new Stopwatch();

        String alg = "MC";
        String file = "mobydick.txt";
        boolean synthetic = false;

        int maxRead = 100000;
        int m = 64;
        int trials = 500;
        double alpha = 0.5;
        int numberOfTrialsShown = 100;

        String input;
        if (synthetic)
            input = "synthetic";
        else
            input = "src/datasets/" + file;

        StdOut.println(TimingTracker.timing(alg, input, m, trials));

        int size;
        int[] cardinalities;
        String dataType = "";
        ExperimentLauncher launcher;
        if (synthetic) {
            dataType = "Synthetic";
            size = maxRead;
            cardinalities = new int[size];
            for (int i = 0; i < size; i++) cardinalities[i] = i;
            launcher = new ExperimentLauncher(alg, size, m, cardinalities, alpha, trials);
        } else {
            dataType = "Real: " + input;
            size = Exact.total(input, maxRead);
            cardinalities = Exact.countArray(input, maxRead);
            launcher = new ExperimentLauncher(alg, size, m, cardinalities, alpha, trials, input);
        }

        String algFull = "";
        switch (alg) {
            case "HBB":
                algFull = "HyperBitBit";
                break;
            case "PC":
                algFull = "Probabilistic Counting";
                break;
            case "MC":
                algFull = "MinCount";
                break;
            default:
                algFull = "Well, Well, Well. Somehow, you've broken the whole program. Congratulations! Feel free to email me (abaroody@princeton.edu) and we can talk about it!";
                break;
        }

        ReportGenerator report = new ReportGenerator(launcher, numberOfTrialsShown);
        report.generateBasicReport();

        String alphaString = "";
        if (alg.equals("HBB")) alphaString += alpha;
        else alphaString = "N/A";

        StdOut.println("Algorithm: " + algFull);
        StdOut.println("Data type: " + dataType);
        StdOut.println("Size of Stream (N): " + size);
        StdOut.println("Cardinality (n): " + cardinalities[cardinalities.length - 1]);
        StdOut.println("Substreams (m): " + m);
        StdOut.println("Trials (T): " + trials);
        StdOut.println("𝛼: " + alphaString);

        StdOut.println("\nThis experiment took " + TimingTracker.add(alg, file, m, trials, watch.elapsedTime()));
    }
}
