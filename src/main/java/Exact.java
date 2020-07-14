package main.java;

import edu.princeton.cs.algs4.StdOut;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;

public class Exact {
    // Returns the exact cardinality of stream
    public static int count(StringStream stream) {
        HashSet<String> hset = new HashSet<>();
        for (String x : stream) hset.add(x);
        return hset.size();
    }

    // Returns the exact cardinality of the first maxRead lines of file fileName
    public static int count(String fileName, int maxRead) throws FileNotFoundException {
        StringStream stream = new StringStream(fileName, maxRead);
        return count(stream);
    }

    // Returns the cardinality after every iteration of stream
    public static int[] countArray(StringStream stream) throws FileNotFoundException {
        int[] counts = new int[total(stream)];
        stream.resetStream();
        HashSet<String> hset = new HashSet<>();
        int i = 0;
        for (String x : stream) {
            hset.add(x);
            counts[i] = hset.size();
            i++;
        }
        return counts;
    }

    // Returns the cardinality after each line if fileName, up to maxRead lines
    public static int[] countArray(String fileName, int maxRead) throws FileNotFoundException {
        StringStream stream = new StringStream(fileName, maxRead);
        return countArray(stream);
    }

    // Returns the total number of Strings in stream
    public static int total(StringStream stream) {
        int N = 0;
        for (String ignored : stream) N++;
        return N;
    }

    // Returns the total number of lines in fileName, up to maxRead
    public static int total(String fileName, int maxRead) throws FileNotFoundException {
        StringStream stream = new StringStream(fileName, maxRead);
        return total(stream);
    }

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "src/datasets/f0";
        int N = 1000000;
        StringStream stream = new StringStream(fileName, N);
        long total = total(stream);
        stream.resetStream();
        StdOut.println(fileName + " has " + total + " total lines and " + count(stream) + " of them are distinct.");
        stream.resetStream();
        StdOut.println("The array is below: " + Arrays.toString(countArray(stream)));
    }
}
