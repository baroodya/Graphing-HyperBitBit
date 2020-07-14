package main.java;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.zip.CRC32;

public class MinCount implements CardinalityEstimationAlgorithm {

    protected int size;
    protected int m;
    protected final int n;

    protected double estimate;

    protected double[] minSeen;

    protected CRC32 hash;

    private final double INFINITY = Double.POSITIVE_INFINITY;

    public MinCount(int m, int cardinality) {
        size = 0;
        this.m = m;
        n = cardinality;

        estimate = 0;
        minSeen = new double[m];
        for (int i = 0; i < m; i++) minSeen[i] = INFINITY;

        hash = new CRC32();
    }

    /* input methods */
    // TODO: make this work
    // TODO: if this works, remove hash instnace variable and all assignments
    public void readElement(String element) {
        // Increment the size of the experiment (N)
        size++;

        double random = Math.abs(Bits.hash(element) / (double) Long.MAX_VALUE);

        // Calculate a new estimate for the cardinality of the stream
        estimate = newEstimate(random);
    }

    public void readSyntheticElement(double element) {
        // Increment the size of the experiment (N)
        size++;

        // Calculate a new estimate for the cardinality of the stream
        estimate = newEstimate(element);
    }

    /* output methods */
    public int getSize() { // exact number of calls to readElement()
        return size;
    }

    public double getEstimateOfCardinality() { // get estimate of n *right now*
        return estimate;
    }

    /* derived methods from above */
    public double getAbsoluteError() {
        return Math.abs(estimate - size);
    }

    public double getRelativeError() {
        return (getAbsoluteError() / size);
    }

    public void resetAlgorithm(int newM) {
        m = newM;
        size = 0;
        estimate = 0;
        minSeen = new double[m];
        for (int i = 0; i < m; i++) minSeen[i] = INFINITY;
    }

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
        int cardinality = size;
        MinCount counter = new MinCount(m, cardinality);

        StdOut.println("Size = " + counter.getSize());
        StdOut.println("Cardinality = " + counter.getEstimateOfCardinality());
        StdOut.print("\n");

        for (int i = 0; i < size; i++) {
            counter.readSyntheticElement(StdRandom.uniform());
        }

        StdOut.println("Size = " + counter.getSize());
        StdOut.println("Cardinality = " + counter.getEstimateOfCardinality());
        StdOut.println("Abs. Error = " + counter.getAbsoluteError());
        StdOut.println("Rel. Error = " + counter.getRelativeError());
    }
}
