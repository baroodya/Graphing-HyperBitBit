package main.java;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class ProbabilisticCounting implements CardinalityEstimationAlgorithm {
    // Constants for calculation
    protected int size;
    protected final int bitmapLength;
    protected int m;
    protected int lgM;
    protected final double phi;

    // Elements necessary for the algorithm
    protected boolean[][] bitmaps;

    // Hash Function to randomize the elements
    protected Bits hasher;

    // Constructor initializes variables
    public ProbabilisticCounting(int cardinality, double phi) {
        size = 0;
        m = cardinality;
        lgM = (int) Math.floor(Math.log(m) / Math.log(2));
        bitmapLength = 32;
        this.phi = phi;

        bitmaps = new boolean[m][bitmapLength - lgM];

        hasher = new Bits();
    }

    // Reads a real element, hashes it, and turns it into a binary String
    public void readElement(String element) {
        size++;

        count(Long.toBinaryString(hasher.hash(element)));
    }

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
        int[] allPs = rho(bitmaps);
        double[] allEstimates = new double[m];
        for (int i = 0; i < allEstimates.length; i++)
            allEstimates[i] = (1 / phi) * Math.pow(2, (allPs[i] + lgM));

        return arithmeticMean(allEstimates);
    }

    // Reset the algorithm for a new trial
    public void resetAlgorithm(int newM) {
        m = newM;
        lgM = (int) Math.floor(Math.log(m) / Math.log(2));
        size = 0;
        bitmaps = new boolean[m][bitmapLength - lgM];
        hasher.randomizeHash();
    }

    // Helper method to manage a new element
    private void count(String element) {
        int multiplier = 1;
        int whichMap = 0;

        int i = lgM;
        while (i > 0) {
            if (element.charAt(i - 1) == '1') whichMap += multiplier;
            multiplier *= 2;
            i--;
        }

        int rhoElement = rho(element, lgM);

        bitmaps[whichMap][rhoElement] = true;
    }

    // Helper method to perform the rho operation
    private int rho(String bitString, int start) {
        int index = bitString.indexOf('0', start) - start;
        return Math.max(index, 0);
    }

    // Helper method to calculate a rho value for every bitMap
    private int[] rho(boolean[][] bitStrings) {
        int[] rhos = new int[bitStrings.length];
        Arrays.fill(rhos, bitStrings.length);

        int j = 0;
        for (boolean[] bitString : bitStrings) {
            for (int i = 0; i < bitString.length; i++) {
                if (!bitString[i]) {
                    rhos[j] = i;
                    break;
                }
            }
            j++;
        }
        return rhos;
    }

    // Helper method to calculate the arithmetic mean of an array
    private double arithmeticMean(double[] values) {
        double sum = 0;
        for (double value : values) sum += value;
        return sum / values.length;
    }

    public static void main(String[] args) throws FileNotFoundException {
        int size = 100000;
        int m = 64;
        boolean real = false;

        ProbabilisticCounting counter = new ProbabilisticCounting(m, 0.77351);

        StdOut.println("Size = " + counter.getSize());
        StdOut.println("Cardinality = " + counter.getEstimateOfCardinality());
        StdOut.print("\n");

        // Read in the file
        if (real) {
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
        } else {
            double random = 0;
            for (int i = 0; i < size; i++) counter.readSyntheticElement(random);

            StdOut.println("Size = " + counter.getSize());
            StdOut.println("Cardinality = " + counter.getEstimateOfCardinality());
            StdOut.println(
                    "Abs. Error = " + Math.abs(counter.getEstimateOfCardinality() - counter.getSize()));
            StdOut.println(
                    "Rel. Error = "
                            + (Math.abs(counter.getEstimateOfCardinality() - counter.getSize()) / counter.getSize()));
        }
    }
}
