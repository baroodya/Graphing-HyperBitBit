package helpers;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.Scanner;

public class StringStream implements Iterable<String> {
    private final int N; // size of the stream

    // File and scanner
    private final File file;
    private Scanner scanner;

    // Constructor initializes variables
    public StringStream(String fileName, int N) throws FileNotFoundException {
        this.N = N;
        file = new File(fileName);
        scanner = new Scanner(file);
    }

    // Iterator
    @Nonnull
    public Iterator<String> iterator() {
        return new Reader();
    }

    // Iterable
    private class Reader implements Iterator<String> {
        int i = 0;

        // Returns true if the file has another line and we haven't reached our limit
        public boolean hasNext() {
            return scanner.hasNextLine() && i < N;
        }

        // returns the next line of the file
        public String next() {
            i++;
            return scanner.nextLine();
        }
    }

    // Resets the stream for another trial
    public void resetStream() throws FileNotFoundException {
        scanner = new Scanner(file);
    }
}
