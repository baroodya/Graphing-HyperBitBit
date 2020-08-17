package algs.java;

import edu.princeton.cs.algs4.StdRandom;
import main.java.HelperMethods;
import org.junit.jupiter.api.Test;
import randomhash.main.java.RandomHashFamily;

import java.io.FileNotFoundException;

class HyperBitBitTest {
    private final HyperBitBit algorithm = new HyperBitBit(0.5, 16);

    @Test
    void readElement() {
        algorithm.resetAlgorithm(16);

        assert algorithm.getSize() == 0;

        algorithm.readElement("");
        assert algorithm.getSize() == 1;

        algorithm.readElement("a");
        assert algorithm.getSize() == 2;

        algorithm.readElement("b");
        assert algorithm.getSize() == 3;

        algorithm.readElement("c");
        assert algorithm.getSize() == 4;

        algorithm.readElement("d");
        assert algorithm.getSize() == 5;

        algorithm.readElement("aa");
        assert algorithm.getSize() == 6;

        algorithm.readElement("geeerenfenntete");
        algorithm.readElement("cheesewiz");
        assert algorithm.getSize() == 8;

        algorithm.readElement("pasta");
        assert algorithm.getSize() == 9;

        algorithm.readElement(" ");
        assert algorithm.getSize() == 10;

        algorithm.readElement("0");
        assert algorithm.getSize() == 11;

        algorithm.readElement("00101101011011000");
        assert algorithm.getSize() == 12;

        algorithm.readElement("              ");
        assert algorithm.getSize() == 13;

        algorithm.readElement("\n ssssssssss");
        assert algorithm.getSize() == 14;
        assert algorithm.getSize() == 14;
    }

    @Test
    void readSyntheticElement() {
        algorithm.resetAlgorithm(16);

        assert algorithm.getSize() == 0;

        algorithm.readSyntheticElement(0);
        assert algorithm.getSize() == 1;

        algorithm.readSyntheticElement(0.5);
        assert algorithm.getSize() == 2;

        algorithm.readSyntheticElement(0.25);
        assert algorithm.getSize() == 3;

        algorithm.readSyntheticElement(0.375);
        assert algorithm.getSize() == 4;

        algorithm.readSyntheticElement(0.75);
        assert algorithm.getSize() == 5;

        algorithm.readSyntheticElement(0.1);
        assert algorithm.getSize() == 6;

        algorithm.readSyntheticElement(0.2);
        algorithm.readSyntheticElement(0.3);
        assert algorithm.getSize() == 8;

        algorithm.readSyntheticElement(0.4);
        assert algorithm.getSize() == 9;

        algorithm.readSyntheticElement(0.999);
        assert algorithm.getSize() == 10;

        algorithm.readSyntheticElement(0.00000000000000000000000000000001001);
        assert algorithm.getSize() == 11;

        algorithm.readSyntheticElement(0.0000000000000001);
        assert algorithm.getSize() == 12;

        algorithm.readSyntheticElement(10e-3);
        assert algorithm.getSize() == 13;

        algorithm.readSyntheticElement(0.1654);
        assert algorithm.getSize() == 14;
        assert algorithm.getSize() == 14;
    }

    @Test
    void getSize() {
        algorithm.resetAlgorithm(16);

        assert algorithm.getSize() == 0;

        algorithm.readElement("a");
        assert algorithm.getSize() == 1;

        algorithm.readSyntheticElement(0);
        assert algorithm.getSize() == 2;

        algorithm.readElement("a");
        assert algorithm.getSize() == 3;

        algorithm.readSyntheticElement(0.99);
        assert algorithm.getSize() == 4;

        algorithm.readElement("a");
        assert algorithm.getSize() == 5;

        algorithm.readSyntheticElement(0.5);
        assert algorithm.getSize() == 6;

        algorithm.readElement("\na's");
        assert algorithm.getSize() == 7;

        algorithm.readElement("1");
        assert algorithm.getSize() == 8;

        algorithm.readSyntheticElement(0);
        assert algorithm.getSize() == 9;

        assert algorithm.getSize() == 9;

        algorithm.resetAlgorithm(16);
        assert algorithm.getSize() == 0;
    }

    @Test
    void resetAlgorithm() {
        algorithm.resetAlgorithm(16);
        RandomHashFamily hasherBefore = algorithm.hasher;
        assert algorithm.getSize() == 0;

        double random;
        for (int i = 0; i < 24536789; i++) {
            random = StdRandom.uniform();
            algorithm.readElement(Double.toString(random));
        }
        assert algorithm.getSize() == 24536789;
        assert algorithm.m == 16;
        assert algorithm.cnt == 24536789;
        assert HelperMethods.withinFivePercent(algorithm.hset.size(), 24526789);
        assert algorithm.hasher == hasherBefore;

        algorithm.resetAlgorithm(64);
        assert algorithm.getSize() == 0;
        assert algorithm.m == 64;
        assert algorithm.cnt == 0;
        assert algorithm.sketch == 0;
        assert algorithm.sketch2 == 0;
        assert algorithm.avg == 0;
        assert algorithm.hset.size() == 0;
        assert algorithm.hasher != hasherBefore;
    }

    @Test
    void count() {
        algorithm.resetAlgorithm(16);
        assert algorithm.getSize() == 0;
        assert algorithm.hset.size() == 0;

        algorithm.readElement("");
        assert algorithm.getSize() == 1;
        assert algorithm.hset.size() == 1;

        algorithm.readElement(" ");
        assert algorithm.getSize() == 2;
        assert algorithm.hset.size() == 2;

        algorithm.readElement("a");
        assert algorithm.getSize() == 3;
        assert algorithm.hset.size() == 3;

        algorithm.readElement("abcdefghijklmnopqrstuvwxyz");
        assert algorithm.getSize() == 4;
        assert algorithm.hset.size() == 4;

        algorithm.resetAlgorithm(16);
        assert algorithm.getSize() == 0;
        assert algorithm.hset.size() == 0;

        algorithm.readElement("");
        assert algorithm.getSize() == 1;
        assert algorithm.hset.size() == 1;

        algorithm.readElement(" ");
        assert algorithm.getSize() == 2;
        assert algorithm.hset.size() == 2;

        algorithm.readElement("a");
        assert algorithm.getSize() == 3;
        assert algorithm.hset.size() == 3;

        algorithm.readElement("abcdefghijklmnopqrstuvwxyz");
        assert algorithm.getSize() == 4;
        assert algorithm.hset.size() == 4;

        algorithm.readElement("abcdefghijklmnopqrstuvwxyz");
        assert algorithm.getSize() == 5;
        assert algorithm.hset.size() == 4;

        algorithm.count("abcdefghijklmnopqrstuvwxyz");
        assert algorithm.getSize() == 5;
        assert algorithm.hset.size() == 4;
    }

    @Test
    void estimate() {
        algorithm.resetAlgorithm(16);

        assert HelperMethods.withinFivePercent(algorithm.estimate(), 7.393569926);

        algorithm.count(" ");

        assert algorithm.estimate() != 0;
    }

    @Test
    void getEstimateOfCardinality() {
        algorithm.resetAlgorithm(16);

        assert HelperMethods.withinFivePercent(algorithm.getEstimateOfCardinality(), 7.393569926);

        for (int i = 0; i < 10; i++)
            algorithm.count(Integer.toString(i));

        assert !HelperMethods.withinFivePercent(algorithm.getEstimateOfCardinality(), 7.393569926);
    }

    @Test
    void main() throws FileNotFoundException {
        String[] args = new String[1];
        HyperBitBit.main(args);
        args[0] = "true";
        HyperBitBit.main(args);
    }
}
