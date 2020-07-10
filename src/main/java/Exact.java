package main.java;

import edu.princeton.cs.algs4.StdOut;

import java.io.FileNotFoundException;
import java.util.HashSet;

public class Exact {
    public static long count(Iterable<String> stream) {
        HashSet<String> hset = new HashSet<String>();
        for (String x : stream) hset.add(x);
        return hset.size();
    }

    public static long count(String fileName, int maxRead) throws FileNotFoundException {
        StringStream stream = new StringStream(fileName, maxRead);
        return count(stream);
    }

    public static long total(Iterable<String> stream) {
        long N = 0;
        for (String x : stream) N++;
        return N;
    }

    public static long total(String fileName, int maxRead) throws FileNotFoundException {
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
    }
}
