package main.java;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.io.FileNotFoundException;
import java.util.zip.CRC32;

public class ProbabilisticCounting implements CardinalityEstimationAlgorithm {

    protected int size;
    protected int m;
    protected final int n;
    protected final int length;

    protected double estimate;

    protected final int lgM;
    boolean[][] bitmaps;
    int[] bitmapRhos;

    protected CRC32 hash;

    public ProbabilisticCounting(int m, int cardinality) {
        size = 0;
        this.m = m;
        n = cardinality;
        length = 64;

        estimate = 0;

        lgM = (int) (Math.log(m) / Math.log(2));
        bitmaps = new boolean[m][length - lgM + 1];
        bitmapRhos = new int[m];

        hash = new CRC32();
    }

    /* input methods */
    // TODO: make this work
    // TODO: if this works, remove hash instnace variable and all assignments
    public void readElement(String element) {
        // Increment the size of the experiment (N)
        size++;

        long random = Bits.hash(element);

        boolean[] bits = new boolean[length];
        long mask = 1L;
        long bitval;
        for (int i = 0; i < length; i++) {
            bitval = random & mask;
            bits[length - 1 - i] = (bitval == 0);
            mask <<= 1;
        }

        // Calculate a new estimate for the cardinality of the stream
        estimate = newEstimate(bits);
    }

    public void readSyntheticElement(double element) {
        size++;

        boolean[] randoms = new boolean[length];

        for (int i = 0; i < length; i++) randoms[i] = StdRandom.bernoulli();

        estimate = newEstimate(randoms);
    }

    /* output methods */
    public int getSize() { // exact number of calls to readElement()
        return size;
    }

    public double getEstimateOfCardinality() { // get estimate of n *right now*
        return estimate;
    }

    public void resetAlgorithm() {
        size = 0;
        estimate = 0;
        bitmapRhos = new int[m];
        bitmaps = new boolean[m][length - lgM + 1];
    }

    public void resetAlgorithm(int m) {
        this.m = m;
        resetAlgorithm();
    }

    // Helper method to perform the estimation
    private double newEstimate(boolean[] hOfX) {
        int j = lgM - 1;
        int multiplier = 1;
        int whichMap = 0;

        // figure out which map to use based on the first lgM bits of the bitstring
        while (j >= 0) {
            if (hOfX[j]) whichMap += multiplier;
            multiplier *= 2;
            j--;
        }

        // update the proper bitmap and bitmap rho
        bitmaps[whichMap][rho(hOfX, lgM)] = true;

        bitmapRhos[whichMap] = rho(bitmaps[whichMap], 0);

        return (m * arithmeticMean(bitmapRhos));
    }

    // Helper method to find leftmost 0 in array of random bits
    private int rho(boolean[] bits, int start) {
        for (int i = start; i < bits.length; i++) if (!bits[i]) return (i - start);
        return (bits.length - start);
    }

    // Helper method to calculate the harmonic mean
    private double harmonicMean(int[] values) {
        double reciprocalSum = 0;
        for (int value : values) reciprocalSum += (1.0 / estimate(value));

        return (values.length / reciprocalSum);
    }

    // Helper method to calculate the arithmetic mean
    private double arithmeticMean(int[] values) {
        double sum = 0;
        for (int value : values) sum += estimate(value);

        return sum / values.length;
    }

    // Helper method to perform the estimation calculation to go from a bitmap index to a
    // cardinality value
    private double estimate(int p) {
        double phi = 0.77351;
        return (1.0 / phi) * Math.pow(2.0, p);
    }

    public static void main(String[] args) throws FileNotFoundException {
        int size = 100000;
        int m = 64;
        int cardinality = size;

        ProbabilisticCounting counter = new ProbabilisticCounting(m, cardinality);

        StdOut.println("Size = " + counter.getSize());
        StdOut.println("Cardinality = " + counter.getEstimateOfCardinality());
        StdOut.print("\n");

        // Read in the file
        String inputFile = "src/datasets/mobydick.txt";
        int N = 100000;
        StringStream stream = new StringStream(inputFile, N);

        for (String line : stream) counter.readElement(line);

//        double random = 0;
//        for (int i = 0; i < size; i++) counter.readSyntheticElement(random);

        StdOut.println("Size = " + counter.getSize());
        StdOut.println("Cardinality = " + counter.getEstimateOfCardinality());
        StdOut.println(
                "Abs. Error = " + Math.abs(counter.getEstimateOfCardinality() - counter.getSize()));
        StdOut.println(
                "Rel. Error = "
                        + (Math.abs(counter.getEstimateOfCardinality() - counter.getSize()) / counter.getSize()));
    }
}
