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

  public static void main(String[] args) throws FileNotFoundException {
    String fileName = args[0];
    int N = Integer.parseInt(args[1]);
    StringStream stream = new StringStream(fileName, N);
    StdOut.println(count(stream));
  }
}
