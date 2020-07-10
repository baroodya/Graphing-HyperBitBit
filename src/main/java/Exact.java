package main.java;

import edu.princeton.cs.algs4.StdOut;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashSet;

public class Exact {
    public static int count(StringStream stream) {
        HashSet<String> hset = new HashSet<String>();
        for (String x : stream) hset.add(x);
        return hset.size();
    }

    public static int count(String fileName, int maxRead) throws FileNotFoundException {
        StringStream stream = new StringStream(fileName, maxRead);
        return count(stream);
    }

    public static int[] countArray(StringStream stream) throws FileNotFoundException {
        int[] counts = new int[total(stream)];
        stream.resetStream();
        HashSet<String> hset = new HashSet<String>();
        int i = 0;
        for (String x : stream) {
            hset.add(x);
            counts[i] = hset.size();
            i++;
        }
        return counts;
    }

    public static int[] countArray(String fileName, int maxRead) throws FileNotFoundException {
        StringStream stream = new StringStream(fileName, maxRead);
        return countArray(stream);
    }

    public static int total(StringStream stream) {
        int N = 0;
        for (String x : stream) N++;
        return N;
    }

    public static int total(String fileName, int maxRead) throws FileNotFoundException {
        StringStream stream = new StringStream(fileName, maxRead);
        return total(stream);
    }

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = "src/datasets/f0";
        int N = 1000000; //Integer.parseInt(args[1]);
        StringStream stream = new StringStream(fileName, N);
        long total = total(stream);
        stream.resetStream();
        StdOut.println(fileName + " has " + total + " total lines and " + count(stream) + " of them are distinct.");
        stream.resetStream();
        StdOut.println("The array is below: " + Arrays.toString(countArray(stream)));
    }
}
