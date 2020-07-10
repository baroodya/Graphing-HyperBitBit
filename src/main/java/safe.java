package main.java; // Last checked by RS on June 29, 2020

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.io.FileNotFoundException;
import java.util.HashSet;

public class safe {
  public static int estimate(long sketch, int avg, double alpha, int M) {
    double beta = 1.0 - 1.0 * Bits.p(sketch) / M;
    double bias = 2 * Math.log(1.0 / alpha) / 3.0 - 2 * Math.log(beta);
    return (int) (Math.pow(2, avg) * M * bias);
  }

  public static int count(Iterable<String> stream, double alpha, int M, int N) {
    long sketch = 0, sketch2 = 0;
    int avg = 0;
    HashSet<String> hset = new HashSet<String>();
    for (String s : stream) {
      hset.add(s);
      if (Bits.r(Bits.hash(s)) > avg) sketch = sketch | (1L << Bits.hash2(s, M));
      if (Bits.r(Bits.hash(s)) > avg + 1) sketch2 = sketch2 | (1L << Bits.hash2(s, M));
      if (Bits.p(sketch) >= alpha * M) {
        sketch = sketch2;
        avg++;
        sketch2 = 0;
      }
    }
    StdOut.println("Exact:    " + hset.size());
    StdOut.println("Estimate: " + estimate(sketch, avg, alpha, M));
    return estimate(sketch, avg, alpha, M);
  }

  public static void main(String[] args) throws FileNotFoundException {
    String fileName = "src/datasets/test.txt";
    int N = 100000;
    int M = 100000;
    double alpha = 0.5;
    StringStream stream = new StringStream(fileName, N);

    Queue<String> q = new Queue<String>();
    StringBuilder randoms;
    double bit;
    for (int i = 0; i < N; i++) {
      randoms = new StringBuilder();
      for (int j = 0; j < 64; j++) {
        bit = StdRandom.uniform();
        randoms.append(bit);
      }
      q.enqueue("0");
    }

    count(q, alpha, M, N);
  }
}
