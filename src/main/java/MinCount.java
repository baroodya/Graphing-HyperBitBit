package main.java;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.io.FileNotFoundException;

public class MinCount implements CardinalityEstimationAlgorithm {

    // Constants  for experiment
    protected int size;
    protected int m;

    // Variable to hold the current estimate of the cardinality
    protected double estimate;

    // Double array to perform the algorithm
    protected double[] minSeen;

    // Hash function to produce reproducible randomness
    protected Bits hasher;

    // Magic Number
    private final double INFINITY = Double.POSITIVE_INFINITY;

    protected double maxRandom;
    protected double minRandom;

    // Constructor initializes constants and sets all minSeen entries to INFINITY
    public MinCount(int m) {
        size = 0;
        this.m = m;

        estimate = 0;
        minSeen = new double[m];
        for (int i = 0; i < m; i++) minSeen[i] = INFINITY;

        hasher = new Bits();

        maxRandom = 0;
        minRandom = INFINITY;
    }

    // Reads in a real element, hashes it, and calculates a new estimate
    public void readElement(String element) {
        // Increment the size of the experiment (N)
        size++;

        double random = Math.abs(hasher.hash(element) / (double) Long.MAX_VALUE);
        if (random > maxRandom) maxRandom = random;
        if (random < minRandom) minRandom = random;

        // Calculate a new estimate for the cardinality of the stream
        estimate = newEstimate(random);
    }

    // Reads in a random synthetic element and calculates a new estimate
    public void readSyntheticElement(double element) {
        // Increment the size of the experiment (N)
        size++;

        if (element > maxRandom) maxRandom = element;
        if (element < minRandom) minRandom = element;
        // Calculate a new estimate for the cardinality of the stream
        estimate = newEstimate(element);
    }


    public int getSize() { // exact number of calls to readElement()
        return size;
    }

    public double getRange() {
        return maxRandom - minRandom;
    }

    public double getEstimateOfCardinality() { // get estimate of n *right now*
        return estimate;
    }

    // Reset the algorithm for a new trial
    public void resetAlgorithm(int newM) {
        m = newM;
        size = 0;
        estimate = 0;
        minSeen = new double[m];
        for (int i = 0; i < m; i++) minSeen[i] = INFINITY;
        hasher.randomizeHash();

        maxRandom = 0;
        minRandom = INFINITY;
    }

    // Calculate a new estimate based on the addition of a new random double
    private double newEstimate(double continuousRandom) {
        double random = m * continuousRandom;
        int j = (int) random;

        double sum = 0;
        double cardinality = estimate;
        if (minSeen[j] > (random - j)) {
            minSeen[j] = random - j;

            for (int k = 0; k < m; k++)
                if (minSeen[k] != INFINITY)
                    sum += minSeen[k];
            cardinality = (((m * (m - 1)) / sum));
        }

        return cardinality;
    }

    public static void main(String[] args) throws FileNotFoundException {
        int size = 100000;
        int m = 64;
        boolean synthetic = false;
        MinCount counter = new MinCount(m);

        StdOut.println("Size = " + counter.getSize());
        StdOut.println("Cardinality = " + counter.getEstimateOfCardinality());
        StdOut.print("\n");

        // Read in the file
        if (synthetic) {
            for (int i = 0; i < size; i++)
                counter.readSyntheticElement(StdRandom.uniform());

            StdOut.println("Size = " + counter.getSize());
            StdOut.println("Cardinality = " + counter.getEstimateOfCardinality());
            StdOut.println(
                    "Abs. Error = " + Math.abs(counter.getEstimateOfCardinality() - counter.getSize()));
            StdOut.println(
                    "Rel. Error = "
                            + (Math.abs(counter.getEstimateOfCardinality() - counter.getSize()) / counter.getSize()));
        } else {
            String inputFile = "src/datasets/mobydick.txt";
            int N = 100000;
            StringStream stream = new StringStream(inputFile, N);

            for (String line : stream) counter.readElement(line);

            int cardinality = Exact.count(stream);
            StdOut.println("Size = " + counter.getSize());
            StdOut.println("Cardinality = " + counter.getEstimateOfCardinality());
            StdOut.println(
                    "Abs. Error = " + Math.abs(counter.getEstimateOfCardinality() - cardinality));
            StdOut.println(
                    "Rel. Error = "
                            + (Math.abs(counter.getEstimateOfCardinality() - cardinality) / cardinality));
            StdOut.println("\nMax = " + counter.maxRandom + "\nMin = " + counter.minRandom + "\nRange = " + (counter.maxRandom - counter.minRandom));
        }
    }
}
