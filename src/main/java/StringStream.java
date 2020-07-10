package main.java;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class StringStream implements Iterable<String> {
    private final int N;

    private final File file;
    private Scanner scanner;

    public StringStream(String fileName, int N) throws FileNotFoundException {
        this.N = N;
        file = new File(fileName);
        scanner = new Scanner(file);
    }

    public Iterator<String> iterator() {
        return new Reader();
    }

    private class Reader implements Iterator<String> {
        int i = 0;

        public boolean hasNext() {
            return scanner.hasNextLine() && i < N;
        }

        public String next() {
            i++;
            return scanner.nextLine();
        }

        public void remove() {
        }
    }

    public void resetStream() throws FileNotFoundException {
        scanner = new Scanner(file);
    }

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = args[0];
        int N = Integer.parseInt(args[1]);
        StringStream stream = new StringStream(fileName, N);
        for (String x : stream) System.out.println(x);
        stream.resetStream();
        for (String x : stream) System.out.println(x);
    }
}
