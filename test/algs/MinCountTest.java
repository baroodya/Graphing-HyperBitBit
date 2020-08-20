package algs;

import main.HelperMethods;
import org.junit.Test;

import java.io.FileNotFoundException;

public class MinCountTest {
    private final MinCount algorithm = new MinCount(16);

    @Test
    public void readElement() {
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
    public void readSyntheticElement() {
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
    public void getSize() {
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
    public void resetAlgorithm() {
        algorithm.resetAlgorithm(16);
        assert algorithm.getSize() == 0;

        double random;
        for (int i = 0; i < 24589; i++) {
            random = Math.random();
            algorithm.readElement(Double.toString(random));
        }
        assert algorithm.getSize() == 24589;
        assert algorithm.m == 16;
        assert algorithm.size == 24589;
        assert algorithm.minSeen.length == 16;
        for (double value : algorithm.minSeen) assert value <= 1.0;

        algorithm.resetAlgorithm(64);
        assert algorithm.getSize() == 0;
        assert algorithm.m == 64;
        assert algorithm.size == 0;
        assert algorithm.minSeen.length == 64;
        for (double value : algorithm.minSeen) assert value == 1.0;
    }

    @Test
    public void count() {
        algorithm.resetAlgorithm(16);
        assert algorithm.getSize() == 0;
        for (double value : algorithm.minSeen) assert value == 1.0;

        algorithm.count(0);
        assert algorithm.minSeen[0] == 0;

        algorithm.count(0.5);
        assert algorithm.minSeen[8] == 0;

        algorithm.count(0.9);
        assert HelperMethods.withinFivePercent(algorithm.minSeen[14], 0.4);

        assert HelperMethods.withinFivePercent(algorithm.getEstimateOfCardinality(), 17.91044776119);

        double minSeenBefore = algorithm.minSeen[15];
        algorithm.count(1);
        assert algorithm.minSeen[15] == minSeenBefore;
    }

    @Test
    public void main() throws FileNotFoundException {
        String[] args = new String[1];
        MinCount.main(args);
        args[0] = "true";
        MinCount.main(args);
    }
}
