package main.java;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import randomhash.RandomHashes;

public class Bits {
    protected double a;
    protected double b;
    protected randomhash.RandomHashes hasher;

    public Bits() {
        a = StdRandom.uniform();
        b = StdRandom.uniform();
        hasher = new RandomHashes();
    }

    public long hash(String s) {
        long x = (long) ((a * hasher.hash(s)) + b % (Integer.MAX_VALUE - 1));
        return (x & 0xFFFFFFFFL) | (x << 32);
    }

    public void randomizeHash() {
        a = StdRandom.uniform();
        b = StdRandom.uniform();
    }

    public int hash2(String s, int M) {
        long x = hasher.hash(s);
        return (int) ((x >> 22) & 0x3FFL) % M;
    }

    public static long R(long x)
    //      {  return 1 + ((x ^ (x+1)) / 2);  }
    {
        return ~x & (x + 1);
    }

    public static int r(long x) {
        int t = 0;
        if (x == -1) return 64;
        while ((x & 1) == 1) {
            x = x >> 1;
            t++;
        }
        return t;
    }

    public static int p(long x) {
        int t = 0;
        while (x != 0) {
            if (x < 0) t++;
            x = x << 1;
        }
        return t;
    }

    public static String toString(long x, int w) {
        String s = "";
        for (int i = 0; i < w; i++) {
            if ((x & 1) == 0) s = '0' + s;
            else s = '1' + s;
            x = x >> 1;
        }
        return s;
    }

    public static void main(String[] args) {
        for (int t = 0; t < 32; t++)
            StdOut.println(t + " " + r(t) + " " + R(t) + " " + p(t));
        StdOut.println(toString(65, 16));
        StdOut.println(toString(100000000, 32));
        StdOut.println(toString(10000000000000000L, 64));
        long t = 10000000000000000L;
        StdOut.println(t + " " + r(t) + " " + R(t) + " " + p(t));
    }
}
