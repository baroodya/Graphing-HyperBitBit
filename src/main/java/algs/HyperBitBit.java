package main.java.algs; // Last checked by RS on June 29, 2020

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import main.java.Bits;
import main.java.StringStream;
import randomhash.RandomHashes;

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
    protected RandomHashes hasher;

    protected double maxRandom;
    protected double minRandom;

    public HyperBitBit(double alpha, int cardinality) {
        // Initialize variables
        this.alpha = alpha;
        this.length = 64;
        resetAlgorithm(cardinality);
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
    public void resetAlgorithm(int newM) {
        m = newM;
        estimate = 0;
        cnt = 0;
        sketch = sketch2 = 0;
        avg = 0;
        hset = new HashSet<>();
        hasher = new RandomHashes(1);
    }

    // Helper method that returns the actual estimate of the cardinality
    protected double estimate() {
        double beta = 1.0 - 1.0 * Bits.p(sketch) / m;
        double bias = 2 * Math.log(1.0 / alpha) / 3.0 - 2 * Math.log(beta);
        return (Math.pow(2, avg) * m * bias);
    }

    // Helper method that manages the longs after each input so that an estimate can be made
    protected double count(String s) {
        if (Bits.r(hasher.hash(s)) > avg) sketch = sketch | (1L << hasher.hash2(s, m));
        if (Bits.r(hasher.hash(s)) > avg + 1)
            sketch2 = sketch2 | (1L << hasher.hash2(s, m));
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