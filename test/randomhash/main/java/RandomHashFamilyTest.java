package randomhash.main.java;

import edu.princeton.cs.algs4.StdOut;
import helpers.java.Bits;
import org.junit.jupiter.api.Test;

class RandomHashFamilyTest {
    private final RandomHashFamily hasher = new RandomHashFamily();

    @Test
    void hash2() {
        String testString = "tehehsdhe";
        int m = 16;

        int hashedBits = Bits.hash2(testString, m);
        int hashedNew = hasher.hash2(testString, m);
        StdOut.println("Bits: " + hashedBits);
        StdOut.println("New: " + hashedNew);
    }
}
