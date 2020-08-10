package main.java;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Stopwatch;
import main.java.algs.CardinalityEstimationAlgorithm;
import main.java.algs.HyperBitBit;
import main.java.algs.MinCount;
import main.java.algs.ProbabilisticCounting;
import main.java.helpers.Exact;
import main.java.helpers.StringStream;
import main.java.helpers.TimingTracker;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class ExperimentLauncher {
    // The algorithm on which the experiment is being performed
    protected CardinalityEstimationAlgorithm algorithm;

    // The stream and the file from which the stream is created
    protected StringStream stream;
    protected String fileName;

    // The number of inputs seen
    protected final double[] sizes;
    // every estimate collected in all of the trials
    protected final double[][] estimates;

    // The number of different m values tried
    protected double[] varyMs;
    // Every estimate collected in all of the trials for each value of m (only the final value of each trial)
    protected double[][] varyMEstimates;

    // Constants necessary for computation
    protected final int bigN;
    protected final int t;
    protected final int n;
    protected final int m;
    protected final int[] cardinalities;
    protected final String alg;
    protected final double alpha;
    protected final double phi;

    // A boolean for determining the type of input data
    protected boolean syntheticData;

    // A double for status updates
    protected double percent;
    protected double denom;

    // A constructor for real data
    public ExperimentLauncher(
            String alg, int size, int m, int[] cardinalities, double alpha, double phi, int trials, String input)
            throws FileNotFoundException {
        // Save the constants to be used in other methods
        t = trials;
        this.m = m;
        this.cardinalities = cardinalities;
        bigN = size;
        n = cardinalities[bigN - 1];
        this.alg = alg;
        this.alpha = alpha;
        this.phi = phi;
        fileName = input;

        // the data is real
        syntheticData = false;

        percent = 0.0;
        denom = t * m * ((m + 3.0) / 2.0);

        // Read in the file
        stream = new StringStream(fileName, bigN);

        // Create 2D trial x size arrays to hold data points for each trial
        sizes = new double[bigN];
        estimates = new double[t][bigN];

        varyMs = new double[m];
        varyMEstimates = new double[t][m];

        runExperiments();

        StdOut.print("\rProducing Graphs. Almost done! \n");
    }

    // A Constructor for Synthetic data
    public ExperimentLauncher(String alg, int size, int m, int[] cardinalities, double alpha, double phi, int trials)
            throws FileNotFoundException {
        // Save the constants to be used in other methods
        bigN = size;
        t = trials;
        this.cardinalities = cardinalities;
        n = cardinalities[bigN - 1];
        this.m = m;
        this.alg = alg;
        this.alpha = alpha;
        this.phi = phi;

        // Create a new stream of data
        stream = new StringStream("src/datasets/log.07.f3.txt", bigN);

        // This data is synthetic
        syntheticData = true;

        percent = 0.0;
        denom = t * m * ((m + 3.0) / 2.0);

        // Create 2D trial x size arrays to hold data points for each trial
        sizes = new double[bigN];
        estimates = new double[t][bigN];

        varyMs = new double[m];
        varyMEstimates = new double[t][m];

        runExperiments();

        StdOut.print("\rProducing Graphs. Almost done! \n");
    }

    // A helper method to read an element, no matter what kind
    private void readElement(String word) {
        if (syntheticData) {
            double random = StdRandom.uniform();
            algorithm.readSyntheticElement(random);
        } else {
            algorithm.readElement(word);
        }
    }

    // Helper method to run t trials of m = 1, m = 2, ... , m = m
    private void runExperiments() throws FileNotFoundException {
        // Decide which algorithm to use
        switch (alg) {
            case "PC":
                algorithm = new ProbabilisticCounting(m, phi);
                break;
            case "MC":
                algorithm = new MinCount(1);
                break;
            case "HBB":
                algorithm = new HyperBitBit(alpha, 1);
                break;
            default:
                throw new IllegalArgumentException("This type of Algorithm is not supported.");
        }

        int counter = m * t;
        for (int k = 1; k <= m; k++) {
            varyMs[k - 1] = k;

            // Run trials and update 2D arrays
            for (int i = 0; i < t; i++) {
                percent = ((double) (counter + 1) / (denom)) * 100;
                StdOut.print("\r" + "Running Variable m = " + k + "/" + m + ". On trial " + (i + 1) + "/" + t + ". (");
                StdOut.printf("%.2f", percent);
                StdOut.print("%)");
                int j = 0;
                for (String element : stream) {
                    readElement(element);

                    if (k == m) {
                        if (j > bigN) break;
                        if (i == 0) sizes[j] = algorithm.getSize();

                        estimates[i][j] = algorithm.getEstimateOfCardinality();
                        j++;
                    }
                }

                varyMEstimates[i][k - 1] = algorithm.getEstimateOfCardinality();
                algorithm.resetAlgorithm(k + 1);
                stream.resetStream();
                counter += k;
            }
        }
    }

    // returns the size array. remember that size[i] = i.
    public double[] getSizes() {
        return sizes;
    }

    // returns an average estimate after each input is read
    public double[] getAvgEstimates() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(estimates, bigN);
    }

    // returns all absolute errors for every estimate in every trial
    public double[][] getAllAbsoluteErrors() {
        double[][] absErrors = new double[t][bigN];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < bigN; j++) {
                absErrors[i][j] = Math.abs(estimates[i][j] - cardinalities[j]);
            }
        }
        return absErrors;
    }

    // returns an average absolute error after each input is read
    public double[] getAvgAbsoluteErrors() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllAbsoluteErrors(), bigN);
    }

    // returns all relative errors for every estimate in every trial
    public double[][] getAllRelativeErrors() {
        double[][] relErrors = new double[t][bigN];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < bigN; j++) {
                relErrors[i][j] = (Math.abs(estimates[i][j] - cardinalities[j])) / cardinalities[j];
            }
        }
        return relErrors;
    }

    // returns an average relative error after each input is read
    public double[] getAvgRelativeErrors() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllRelativeErrors(), bigN);
    }

    // returns a average normalized estimate of the cardinality after each new element is read
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

    // returns an average estimate for the final cardinality for each value of m
    public double[] getAvgEstimatesVaryM() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(varyMEstimates, m);
    }

    // returns all absolute errors for every value of m in every trial
    public double[][] getAllAbsoluteErrorsVaryM() {
        double[][] varyMAbsErrors = new double[t][m];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < m; j++) {
                varyMAbsErrors[i][j] = Math.abs(varyMEstimates[i][j] - n);
            }
        }

        return varyMAbsErrors;
    }

    // returns an average absolute error for the final cardinality for each value of m
    public double[] getAvgAbsoluteErrorsVaryM() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllAbsoluteErrorsVaryM(), m);
    }

    // returns all relative errors for every value of m in every trial
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

    // returns an average relative error for the final cardinality for each value of m
    public double[] getAvgRelativeErrorsVaryM() {
        // return the average of the trials for each element. This is a 1D array
        return averageOverTrials(getAllMRelativeErrorsVaryM(), m);
    }

    // returns a average normalized estimate of the cardinality after each new element is read
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

    // Get the standard deviation of all trials in a 1D array
    public double[] getStdDevOfAllTrials() {
        double[][] data = new double[m][t];
        double[] returned = new double[m];
        for (int i = 0; i < t; i++) {
            for (int j = 0; j < m; j++) {
                data[j][i] = varyMEstimates[i][j];
            }
        }

        for (int i = 0; i < m; i++) returned[i] = getStdDev(data[i]);

        return returned;
    }

    // Helper method to average a 2D array vertically
    protected double[] averageOverTrials(double[][] values, int arraySize) {
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

    protected double getStdDev(double[] values) {
        double mean = arithmeticMean(values);
        double sum = 0;
        for (double value : values) {
            sum += Math.pow((value - mean), 2);
        }
        return Math.sqrt(sum / values.length);
    }

    // Helper method to calculate the arithmetic mean of an array
    protected double arithmeticMean(double[] values) {
        double sum = 0;
        for (double value : values) sum += value;
        return sum / values.length;
    }

    private static void readInputs() throws IOException {
        Scanner keyboardInput = new Scanner(System.in);

        StdOut.print("Hello! Welcome to the Cardinality Estimation Algorithm Experiment Launcher! ");
        StdOut.println("The default values for each parameter are below.");

        String alg = "HBB";
        String file = "f0";
        boolean synthetic = false;

        int maxRead = 100000;
        int m = 64;
        int trials = 100;
        double alpha = 0.5;
        double phi = 0.77351;
        int numberOfTrialsShown = 100;

        StdOut.println("------------------------------------------------");
        StdOut.println("Algorithm: " + alg);
        StdOut.println("file: " + file);
        StdOut.println("maxRead: " + maxRead);
        StdOut.println("Synthetic?: " + false);
        StdOut.println("Substreams (m): " + m);
        StdOut.println("Trials (T): " + trials);
        StdOut.println("𝛼: " + alpha);
        StdOut.println("\uD835\uDF11: " + phi);
        StdOut.println("Number of Trials Shown: " + numberOfTrialsShown);
        StdOut.println("------------------------------------------------");

        StdOut.println("\n" + "Do you want to change these values?");
        boolean newValues;
        String s = keyboardInput.nextLine();
        switch (s) {
            case "Y":
            case "yes":
            case "Yes":
            case "YES":
                newValues = true;
                break;
            default:
                newValues = Boolean.parseBoolean(s);
                break;
        }

        if (newValues) {
            StdOut.print("\n" + "Enter Algorithm: ");
            s = keyboardInput.nextLine();
            if (!s.equals(""))
                alg = s;
            StdOut.print("Enter file: ");
            s = keyboardInput.nextLine();
            if (!s.equals(""))
                file = s;
            StdOut.print("Enter max Read: ");
            s = keyboardInput.nextLine();
            if (!s.equals(""))
                maxRead = Integer.parseInt(s);
            StdOut.print("Enter Synthetic?: ");
            s = keyboardInput.nextLine();
            if (!s.equals(""))
                if (s.equals("Y")) synthetic = true;
                else if (s.equals("Yes")) synthetic = true;
                else if (s.equals("yes")) synthetic = true;
                else if (s.equals("YES")) synthetic = true;
                else synthetic = Boolean.parseBoolean(s);
            StdOut.print("Enter Substreams (m): ");
            s = keyboardInput.nextLine();
            if (!s.equals(""))
                m = Integer.parseInt(s);
            StdOut.print("Enter Trials (T): ");
            s = keyboardInput.nextLine();
            if (!s.equals(""))
                trials = Integer.parseInt(s);
            StdOut.print("Enter 𝛼: ");
            s = keyboardInput.nextLine();
            if (!s.equals(""))
                alpha = Double.parseDouble(s);
            StdOut.print("Enter \uD835\uDF11: ");
            s = keyboardInput.nextLine();
            if (!s.equals(""))
                phi = Double.parseDouble(s);
            StdOut.print("Enter Number of Trials Shown: ");
            s = keyboardInput.nextLine();
            if (!s.equals(""))
                numberOfTrialsShown = Integer.parseInt(s);
        }

        StdOut.println("\n" + "Running the Experiment with the following parameters:");

        createOutput(alg, file, synthetic, maxRead, m, alpha, phi, trials, numberOfTrialsShown);
    }

    private static void createOutput(String alg, String file, boolean synthetic, int maxRead, int m, double alpha, double phi, int trials, int numberOfTrialsShown) throws IOException {
        int size;
        int[] cardinalities;
        String input;
        String dataType;
        if (synthetic) {
            input = "synthetic";
            dataType = "Synthetic";
            size = maxRead;
            cardinalities = new int[size];
            for (int i = 0; i < size; i++) cardinalities[i] = i + 1;
        } else {
            input = "src/datasets/" + file;
            dataType = "Real: " + input;
            size = Exact.total(input, maxRead);
            cardinalities = Exact.countArray(input, maxRead);
        }

        String algFull;
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
                StdOut.println();
                algFull = "Algorithm = " + alg + "\n" + "Well, Well, Well. Somehow, you've broken the whole program. Congratulations! Feel free to email me (abaroody@princeton.edu) and we can talk about it!";
                break;
        }

        String alphaString = "";
        String phiString = "";
        if (alg.equals("HBB")) alphaString += alpha;
        else alphaString = "N/A";
        if (alg.equals("PC")) phiString += phi;
        else phiString = "N/A";

        StdOut.println("------------------------------------------------");
        StdOut.println("Algorithm: " + algFull);
        StdOut.println("Data type: " + dataType);
        StdOut.println("Size of Stream (N): " + size);
        StdOut.println("Cardinality (n): " + cardinalities[cardinalities.length - 1]);
        StdOut.println("Substreams (m): " + m);
        StdOut.println("Trials (T): " + trials);
        StdOut.println("𝛼: " + alphaString);
        StdOut.println("\uD835\uDF11: " + phiString);
        StdOut.println("------------------------------------------------");
        StdOut.println();

        runExperiments(alg, input, synthetic, size, m, cardinalities, alpha, phi, trials, numberOfTrialsShown);
    }

    private static void runExperiments(String alg, String input, boolean synthetic, int size, int m, int[] cardinalities, double alpha, double phi, int trials, int numberOfTrialsShown) throws IOException {
        Stopwatch watch = new Stopwatch();


        StdOut.println(TimingTracker.timing(alg, "'" + input + "'", m, trials) + "\n");

        ExperimentLauncher launcher;
        if (synthetic)
            launcher = new ExperimentLauncher(alg, size, m, cardinalities, alpha, phi, trials);
        else
            launcher = new ExperimentLauncher(alg, size, m, cardinalities, alpha, phi, trials, input);

        Toolkit.getDefaultToolkit().beep();
        ReportGenerator report = new ReportGenerator(launcher, numberOfTrialsShown);
        report.generateBasicReport();

        StdOut.println("\nThis experiment took " + TimingTracker.add(alg, "'" + input + "'", m, trials, watch.elapsedTime()));
    }

    public static void main(String[] args) throws IOException {
        readInputs();
    }
}
