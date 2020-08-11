package main.java.randomhash;

import edu.princeton.cs.algs4.StdOut;
import main.java.helpers.Bits;
import main.java.randomhash.main.RandomHashFamily;
import org.junit.jupiter.api.Test;

class RandomHashFamilyTest {
    private RandomHashFamily hasher = new RandomHashFamily();

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
