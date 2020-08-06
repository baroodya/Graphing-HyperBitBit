package main.java.algs;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import main.java.helpers.Exact;
import main.java.helpers.StringStream;
import main.java.randomhash.main.RandomHashFamily;

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
    protected RandomHashFamily hasher;


    // Constructor initializes constants and sets all minSeen entries to INFINITY
    public MinCount(int cardinality) {
        resetAlgorithm(cardinality);
    }

    // Reads in a real element, hashes it, and calculates a new estimate
    public void readElement(String element) {
        // Increment the size of the experiment (N)
        size++;

        double hashed = ((double) hasher.hash(element)) / (Math.pow(2, 32) - 1);

        // Calculate a new estimate for the cardinality of the stream
        count(hashed);
    }

    // Reads in a random synthetic element and calculates a new estimate
    public void readSyntheticElement(double element) {
        // Increment the size of the experiment (N)
        size++;

        // Calculate a new estimate for the cardinality of the stream
        count(element);
    }


    public int getSize() { // exact number of calls to readElement()
        return size;
    }

    public double getEstimateOfCardinality() { // get estimate of n *right now*
        double sum = 0;
        for (int k = 0; k < m; k++)
            sum += minSeen[k];
        return (((m * (m - 1)) / sum));

    }

    // Reset the algorithm for a new trial
    public void resetAlgorithm(int newM) {
        m = newM;
        size = 0;
        estimate = 0;
        minSeen = new double[m];
        for (int i = 0; i < m; i++) minSeen[i] = 1;
        hasher = new RandomHashFamily(1);
    }

    // Calculate a new estimate based on the addition of a new random double
    protected void count(double continuousRandom) {
        double random = m * continuousRandom;
        int j = (int) random;

        double sum = 0;
        double cardinality = estimate;
        if (minSeen[j] > (random - j)) {
            minSeen[j] = random - j;
        }
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
        }
    }
}
