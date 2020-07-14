package main.java;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class MinCount implements CardinalityEstimationAlgorithm {

    // Constants  for experiment
    protected int size;
    protected int m;
    protected final int n;

    // Variable to hold the current estimate of the cardinality
    protected double estimate;

    // Double array to perform the algorithm
    protected double[] minSeen;

    // Magic Number
    private final double INFINITY = Double.POSITIVE_INFINITY;

    // Constructor initializes constants and sets all minSeen entries to INFINITY
    public MinCount(int m, int cardinality) {
        size = 0;
        this.m = m;
        n = cardinality;

        estimate = 0;
        minSeen = new double[m];
        for (int i = 0; i < m; i++) minSeen[i] = INFINITY;
    }

    // Reads in a real element, hashes it, and calculates a new estimate
    public void readElement(String element) {
        // Increment the size of the experiment (N)
        size++;

        double random = Math.abs(Bits.hash(element) / (double) Long.MAX_VALUE);

        // Calculate a new estimate for the cardinality of the stream
        estimate = newEstimate(random);
    }

    // Reads in a random synthetic element and calculates a new estimate
    public void readSyntheticElement(double element) {
        // Increment the size of the experiment (N)
        size++;

        // Calculate a new estimate for the cardinality of the stream
        estimate = newEstimate(element);
    }


    public int getSize() { // exact number of calls to readElement()
        return size;
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
    }

    // Calculate a new estimate based on the addition of a new random double
    private double newEstimate(double continuousRandom) {
        double random = m * continuousRandom;
        int j = (int) random;

        double sum = 0;
        double cardinality = estimate;
        if (minSeen[j] > ((random) - (j))) {
            minSeen[j] = random - (j);

            for (int k = 0; k < m; k++) if (minSeen[k] != INFINITY) sum += minSeen[k];
            cardinality = (((m * (m - 1)) / sum)) - 1;
        }

        return cardinality;
    }

    public static void main(String[] args) {
        int size = 100000;
        int m = 100;
        int cardinality = 100000;
        MinCount counter = new MinCount(m, cardinality);

        StdOut.println("Size = " + counter.getSize());
        StdOut.println("Cardinality = " + counter.getEstimateOfCardinality());
        StdOut.print("\n");

        for (int i = 0; i < size; i++) {
            counter.readSyntheticElement(StdRandom.uniform());
        }

        StdOut.println("Size = " + counter.getSize());
        StdOut.println("Cardinality = " + counter.getEstimateOfCardinality());
        StdOut.println("Actual Cardinality = " + cardinality);
        StdOut.println(
                "Abs. Error = " + Math.abs(counter.getEstimateOfCardinality() - cardinality));
        StdOut.println(
                "Rel. Error = "
                        + (Math.abs(counter.getEstimateOfCardinality() - cardinality)
                        / cardinality));
    }
}
