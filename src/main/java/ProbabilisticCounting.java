package main.java;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.io.FileNotFoundException;

public class ProbabilisticCounting implements CardinalityEstimationAlgorithm {
    // Constants for calculation
    protected int size;
    protected final int bitmapLength;
    private final double PHI = 0.77351;

    // Elements necessary for the algorithm
    protected boolean[] bitmap;

    // Constructor initializes variables
    public ProbabilisticCounting() {
        size = 0;
        bitmapLength = 32;

        bitmap = new boolean[bitmapLength];
    }

    //TODO
    // Reads a real element, hashes it, and turns it into a boolean array
    public void readElement(String element) {
        size++;
    }

    //TODO
    // Reads a random element and creates a random boolean array
    public void readSyntheticElement(double element) {
        size++;

        StringBuilder newElement = new StringBuilder();
        for (int i = 0; i < bitmapLength; i++)
            if (StdRandom.bernoulli())
                newElement.append('1');
            else
                newElement.append('0');

        count(newElement.toString());
    }

    // exact number of calls to readElement()
    public int getSize() {
        return size;
    }

    // get estimate of n *right now*
    public double getEstimateOfCardinality() {
        int p = rho(bitmap, 0);
        if (p == 0) return 0;
        else return (1 / PHI) * Math.pow(2, p);
    }

    //TODO
    // Reset the algorithm for a new trial
    public void resetAlgorithm(int newM) {
    }

    // TODO: add in multiple substreams
    // Helper method to manage a new element
    private void count(String element) {
        int rhoElement = rho(element, 0);

        bitmap[rhoElement] = true;
    }

    // Helper method to perform the rho operation
    private int rho(String bitString, int start) {
        return bitString.indexOf('0');
    }

    private int rho(boolean[] bitString, int start) {
        for (int i = start; i < bitString.length; i++) if (!bitString[i]) return i;
        return bitString.length;
    }

    public static void main(String[] args) throws FileNotFoundException {
        int size = 100000;
        int m = 64;
        int cardinality = 100000;
        boolean synthetic = true;

        ProbabilisticCounting counter = new ProbabilisticCounting();

        StdOut.println("Size = " + counter.getSize());
        StdOut.println("Cardinality = " + counter.getEstimateOfCardinality());
        StdOut.print("\n");

        // Read in the file
        if (!synthetic) {
            String inputFile = "src/datasets/mobydick.txt";
            int N = 100000;
            StringStream stream = new StringStream(inputFile, N);

            for (String line : stream) counter.readElement(line);
        } else {
            double random = 0;
            for (int i = 0; i < size; i++) counter.readSyntheticElement(random);
        }

        StdOut.println("Size = " + counter.getSize());
        StdOut.println("Cardinality = " + counter.getEstimateOfCardinality());
        StdOut.println(
                "Abs. Error = " + Math.abs(counter.getEstimateOfCardinality() - counter.getSize()));
        StdOut.println(
                "Rel. Error = "
                        + (Math.abs(counter.getEstimateOfCardinality() - counter.getSize()) / counter.getSize()));
    }
}
