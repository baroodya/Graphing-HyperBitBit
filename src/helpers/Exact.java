package helpers;

import java.io.FileNotFoundException;
import java.util.HashSet;

public class Exact {
    // Returns the exact cardinality of stream
    public static int count(helpers.StringStream stream) {
        HashSet<String> hset = new HashSet<>();
        for (String x : stream) hset.add(x);
        return hset.size();
    }

    // Returns the exact cardinality of the first maxRead lines of file fileName
    public static int count(String fileName, int maxRead) throws FileNotFoundException {
        helpers.StringStream stream = new helpers.StringStream(fileName, maxRead);
        return count(stream);
    }

    // Returns the cardinality after every iteration of stream
    public static int[] countArray(helpers.StringStream stream) throws FileNotFoundException {
        int[] counts = new int[total(stream)];
        stream.resetStream();
        HashSet<String> hset = new HashSet<>();
        int t = 0;
        for (String x : stream) {
            hset.add(x);
            counts[t] = hset.size();
            t++;
        }
        return counts;
    }

    // Returns the cardinality after each line if fileName, up to maxRead lines
    public static int[] countArray(String fileName, int maxRead) throws FileNotFoundException {
        helpers.StringStream stream = new helpers.StringStream(fileName, maxRead);
        return countArray(stream);
    }

    // Returns the total number of Strings in stream
    public static int total(helpers.StringStream stream) {
        int N = 0;
        for (String ignored : stream) N++;
        return N;
    }

    // Returns the total number of lines in fileName, up to maxRead
    public static int total(String fileName, int maxRead) throws FileNotFoundException {
        helpers.StringStream stream = new helpers.StringStream(fileName, maxRead);
        return total(stream);
    }
}
