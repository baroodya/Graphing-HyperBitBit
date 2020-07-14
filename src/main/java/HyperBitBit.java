package main.java; // Last checked by RS on June 29, 2020

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.io.FileNotFoundException;
import java.util.HashSet;

public class HyperBitBit implements CardinalityEstimationAlgorithm {
    // Values to be returned/changed throughout the experiment
    protected double estimate;
    protected int m;
    protected int cnt;

    // Constants for calculation
    protected final double alpha;
    protected final int length;

    // Variables for the estimation
    protected long sketch;
    protected long sketch2;
    protected int avg;

    // Hash set for randomness
    protected HashSet<String> hset;

    public HyperBitBit(double alpha, int m) {
        // Initialize variables
        estimate = 0;
        this.alpha = alpha;
        this.m = m;
        this.length = 64;
        cnt = 0;

        sketch = sketch2 = 0;
        avg = 0;

        hset = new HashSet<>();
    }

    // Read in a real element
    public void readElement(String element) {
        hset.add(element);
        cnt++;
        estimate = count(element);
    }

    // REad in a synthetic element
    public void readSyntheticElement(double element) {
        StringBuilder randoms = new StringBuilder();

        // Create a random string of bits
        int bit;
        for (int i = 0; i < length; i++) {
            if (StdRandom.bernoulli()) bit = 0;
            else bit = 1;
            randoms.append(bit);
        }

        hset.add(randoms.toString());
        cnt++;
        estimate = count(randoms.toString());
    }

    // Returns the current size (bigN) of the algorithm
    public int getSize() {
        return cnt;
    }

    // Returns the actual cardinality (n) of the algorithm
    public int getCardinality() {
        return hset.size();
    }

    // Returns the estimated cardinality of the algorithm
    public double getEstimateOfCardinality() {
        return estimate;
    }

    // Reset the algorithm for a new trial
    public void resetAlgorithm() {
        estimate = 0;
        sketch = sketch2 = 0;
        avg = 0;
        hset = new HashSet<>();
    }

    public void resetAlgorithm(int m) {
        this.m = m;
        resetAlgorithm();
    }

    // Helper method that returns the actual estimate of the cardinality
    private double estimate() {
        double beta = 1.0 - 1.0 * Bits.p(sketch) / m;
        double bias = 2 * Math.log(1.0 / alpha) / 3.0 - 2 * Math.log(beta);
        return (Math.pow(2, avg) * m * bias);
    }

    // Helper method that manages the longs after each input so that an estimate can be made
    private double count(String s) {
        if (Bits.r(Bits.hash(s)) > avg) sketch = sketch | (1L << Bits.hash2(s, m));
        if (Bits.r(Bits.hash(s)) > avg + 1) sketch2 = sketch2 | (1L << Bits.hash2(s, m));
        if (Bits.p(sketch) >= alpha * m) {
            sketch = sketch2;
            avg++;
            sketch2 = 0;
        }
        return estimate();
    }

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "src/datasets/mobydick.txt";
        int N = 1000000;
        StringStream stream = new StringStream(fileName, N);
        int M = 64;
        double alpha = 0.5;
        HyperBitBit counter = new HyperBitBit(alpha, M);

        StdOut.println("Size = " + counter.getSize());
        StdOut.println("Cardinality = " + counter.getEstimateOfCardinality());
        StdOut.print("\n");

        for (String x : stream) counter.readElement(x);

        int actualCardinality = counter.getCardinality();
        StdOut.println("Size = " + counter.getSize());
        StdOut.println("Cardinality = " + counter.getEstimateOfCardinality());
        StdOut.println("Actual Cardinality = " + actualCardinality);
        StdOut.println(
                "Abs. Error = " + Math.abs(counter.getEstimateOfCardinality() - actualCardinality));
        StdOut.println(
                "Rel. Error = "
                        + (Math.abs(counter.getEstimateOfCardinality() - actualCardinality)
                        / actualCardinality));
    }
}
