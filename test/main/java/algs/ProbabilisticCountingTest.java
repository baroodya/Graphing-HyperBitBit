package main.java.algs;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;

class ProbabilisticCountingTest {
    private final ProbabilisticCounting algorithm = new ProbabilisticCounting(16, 0.77351);

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

        algorithm.readSyntheticElement(1);
        assert algorithm.getSize() == 2;

        algorithm.readSyntheticElement(2);
        assert algorithm.getSize() == 3;

        algorithm.readSyntheticElement(3);
        assert algorithm.getSize() == 4;

        algorithm.readSyntheticElement(4);
        assert algorithm.getSize() == 5;

        algorithm.readSyntheticElement(0.1);
        assert algorithm.getSize() == 6;

        algorithm.readSyntheticElement(0.2);
        algorithm.readSyntheticElement(0.3);
        assert algorithm.getSize() == 8;

        algorithm.readSyntheticElement(0.4);
        assert algorithm.getSize() == 9;

        algorithm.readSyntheticElement(-0.1);
        assert algorithm.getSize() == 10;

        algorithm.readSyntheticElement(10000000000000000000000200000000.000);
        assert algorithm.getSize() == 11;

        algorithm.readSyntheticElement(0.0000000000000001);
        assert algorithm.getSize() == 12;

        algorithm.readSyntheticElement(10e3);
        assert algorithm.getSize() == 13;

        algorithm.readSyntheticElement(1654);
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

        algorithm.readElement("\naasw");
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
        assert algorithm.getSize() == 0;

        double random;
        for (int i = 0; i < 24536789; i++) {
            random = StdRandom.uniform();
            algorithm.readElement(Double.toString(random));
        }
        assert algorithm.getSize() == 24536789;
        assert algorithm.m == 16;
        assert algorithm.lgM == 4;
        assert algorithm.size == 24536789;
        assert algorithm.bitmaps.length == 16;
        assert algorithm.bitmaps[0].length == 124;

        algorithm.resetAlgorithm(64);
        assert algorithm.getSize() == 0;
        assert algorithm.m == 64;
        assert algorithm.lgM == 6;
        assert algorithm.size == 0;
        assert algorithm.bitmaps.length == 64;
        assert algorithm.bitmaps[0].length == 122;
    }

    @Test
    void count() {
        algorithm.resetAlgorithm(16);

        boolean[][] zeros = new boolean[16][124];
        assert Arrays.deepEquals(algorithm.bitmaps, zeros);

        zeros[0][1] = true;
        assert !Arrays.deepEquals(algorithm.bitmaps, zeros);

        String sb = "00001" +
                "0".repeat(119);
        algorithm.count(sb);
        assert Arrays.deepEquals(algorithm.bitmaps, zeros);
    }

    @Test
    void rho1() {
        assert algorithm.rho("0000", 0) == 0;
        assert algorithm.rho("0001", 0) == 0;
        assert algorithm.rho("0010", 0) == 0;
        assert algorithm.rho("0011", 0) == 0;
        assert algorithm.rho("0100", 0) == 0;
        assert algorithm.rho("0101", 0) == 0;
        assert algorithm.rho("0110", 0) == 0;
        assert algorithm.rho("0111", 0) == 0;
        assert algorithm.rho("1000", 0) == 1;
        assert algorithm.rho("1001", 0) == 1;
        assert algorithm.rho("1010", 0) == 1;
        assert algorithm.rho("1011", 0) == 1;
        assert algorithm.rho("1100", 0) == 2;
        assert algorithm.rho("1101", 0) == 2;
        assert algorithm.rho("1110", 0) == 3;
        assert algorithm.rho("1111", 0) == 3;
        assert algorithm.rho("00100111100011010", 0) == 0;
        assert algorithm.rho("1001001010101101110111000", 0) == 1;
        assert algorithm.rho("111111111111111110", 0) == 17;
        assert algorithm.rho("10100011011110101110101011", 0) == 1;
        assert algorithm.rho("1111111100000011010111", 0) == 8;
        assert algorithm.rho("01111111111111111111111", 0) == 0;
        assert algorithm.rho("0000", 1) == 0;
        assert algorithm.rho("0001", 1) == 0;
        assert algorithm.rho("0010", 1) == 0;
        assert algorithm.rho("0011", 1) == 0;
        assert algorithm.rho("0100", 1) == 1;
        assert algorithm.rho("0101", 1) == 1;
        assert algorithm.rho("0110", 1) == 2;
        assert algorithm.rho("0111", 1) == 3;
        assert algorithm.rho("1000", 1) == 0;
        assert algorithm.rho("1001", 1) == 0;
        assert algorithm.rho("1010", 1) == 0;
        assert algorithm.rho("1011", 1) == 0;
        assert algorithm.rho("1100", 1) == 1;
        assert algorithm.rho("1101", 1) == 1;
        assert algorithm.rho("1110", 1) == 2;
        assert algorithm.rho("1111", 1) == 3;
        assert algorithm.rho("00100111100011010", 4) == 0;
        assert algorithm.rho("1001001010101101110111000", 8) == 1;
        assert algorithm.rho("111111111111111110", 16) == 1;
        assert algorithm.rho("10100011011110101110101011", 6) == 2;
        assert algorithm.rho("1111111100000011010111", 3) == 5;
        assert algorithm.rho("01111111111111111111111", 1) == 22;

    }

    @Test
    void rho2() {
        algorithm.resetAlgorithm(16);

        int[] zeros = new int[16];
        assert Arrays.equals(algorithm.rho(algorithm.bitmaps), zeros);

        boolean[][] fauxBitmaps = new boolean[16][124];
        fauxBitmaps[0][0] = true;
        zeros[0] = 1;
        assert Arrays.equals(algorithm.rho(fauxBitmaps), zeros);

        zeros[0] = 0;
        assert !Arrays.equals(algorithm.rho(fauxBitmaps), zeros);
    }

    @Test
    void arithmeticMean() {
        double[] values = new double[300];
        assert algorithm.arithmeticMean(values) == 0;

        values = new double[1];
        assert algorithm.arithmeticMean(values) == 0;

        values = new double[100];
        assert algorithm.arithmeticMean(values) == 0;

        for (int i = 0; i < 100; i++) values[i] = 1;
        assert algorithm.arithmeticMean(values) == 1;

        values = new double[]{162652721,
                939323954,
                -109274849,
                -148697044,
                989021868,
                -144773756,
                -774236292,
                -146882666,
                337620967,
                216921304,
                -340227180,
                170883883,
                -101560729,
                732870652,
                720281264,
                658709202,
                157978671,
                126747242,
                -847551811,
                250196656,
                -704327486,
                -934919202,
                -562936227,
                -762391751,
                -217309242,
                445568078,
                979513098,
                435851230,
                84301518,
                42708326,
                -791944052,
                75364764,
                -675296887,
                549355023,
                950331016,
                -575091479,
                658355434,
                148216911,
                -828214466,
                -846244213,
                805987543,
                -415990172,
                -600597807,
                38325787,
                238315171,
                -542084385,
                924832704,
                -573221127,
                674023392,
                996472641,
                -990342148,
                -888498705,
                148424958,
                -111543184,
                272552407,
                -853640127,
                -587218720,
                -320241477,
                710667731,
                713423746,
                -918968990,
                -559243989,
                -183196078,
                235264660,
                948536636,
                -499092155,
                -247019055,
                84368902,
                -597599751,
                -430163834,
                -782125759,
                -389456794,
                -179065804,
                -283818296,
                -868005464,
                -419800876,
                -586885369,
                -463628483,
                -26288427,
                -677571384,
                -417798937,
                749386680,
                -843720075,
                595845104,
                -101566421,
                983430633,
                -767547770,
                -867186288,
                707686417,
                767610617,
                -855214344,
                -438399254,
                -119380701,
                908565664,
                747552814,
                -635489382,
                -906806771,
                356404143,
                168586837,
                131454160};

        assert algorithm.arithmeticMean(values) == -67198045.06;
    }

    @Test
    void main() throws FileNotFoundException {
        String[] args = new String[1];
        ProbabilisticCounting.main(args);
    }
}
