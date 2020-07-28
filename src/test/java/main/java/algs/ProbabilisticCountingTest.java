//package main.java.algs;
//
//import org.junit.jupiter.api.Test;
//
//class ProbabilisticCountingTest {
//    protected ProbabilisticCounting algorithm = new ProbabilisticCounting(16, 0.77351);
//
//    @Test
//    void readElement() {
//    }
//
//    @Test
//    void readSyntheticElement() {
//    }
//
//    @Test
//    void getSize() {
//    }
//
//    @Test
//    void getEstimateOfCardinality() {
//    }
//
//    @Test
//    void resetAlgorithm() {
//    }
//
//    @Test
//    void count() {
//    }
//
//    @Test
//    void rho() {
//        assert algorithm.rho("0000", 0) == 0;
//        assert algorithm.rho("0001", 0) == 0;
//        assert algorithm.rho("0010", 0) == 0;
//        assert algorithm.rho("0011", 0) == 0;
//        assert algorithm.rho("0100", 0) == 0;
//        assert algorithm.rho("0101", 0) == 0;
//        assert algorithm.rho("0110", 0) == 0;
//        assert algorithm.rho("0111", 0) == 0;
//        assert algorithm.rho("1000", 0) == 1;
//        assert algorithm.rho("1001", 0) == 1;
//        assert algorithm.rho("1010", 0) == 1;
//        assert algorithm.rho("1011", 0) == 1;
//        assert algorithm.rho("1100", 0) == 2;
//        assert algorithm.rho("1101", 0) == 2;
//        assert algorithm.rho("1110", 0) == 3;
//        assert algorithm.rho("1111", 0) == 3;
//        assert algorithm.rho("00100111100011010", 0) == 0;
//        assert algorithm.rho("1001001010101101110111000", 0) == 1;
//        assert algorithm.rho("111111111111111110", 0) == 17;
//        assert algorithm.rho("10100011011110101110101011", 0) == 1;
//        assert algorithm.rho("1111111100000011010111", 0) == 8;
//        assert algorithm.rho("01111111111111111111111", 0) == 0;
//        assert algorithm.rho("0000", 1) == 0;
//        assert algorithm.rho("0001", 1) == 0;
//        assert algorithm.rho("0010", 1) == 0;
//        assert algorithm.rho("0011", 1) == 0;
//        assert algorithm.rho("0100", 1) == 1;
//        assert algorithm.rho("0101", 1) == 1;
//        assert algorithm.rho("0110", 1) == 2;
//        assert algorithm.rho("0111", 1) == 3;
//        assert algorithm.rho("1000", 1) == 0;
//        assert algorithm.rho("1001", 1) == 0;
//        assert algorithm.rho("1010", 1) == 0;
//        assert algorithm.rho("1011", 1) == 0;
//        assert algorithm.rho("1100", 1) == 1;
//        assert algorithm.rho("1101", 1) == 2;
//        assert algorithm.rho("1110", 1) == 3;
//        assert algorithm.rho("1111", 1) == 3;
//        assert algorithm.rho("00100111100011010", 4) == 0;
//        assert algorithm.rho("1001001010101101110111000", 8) == 1;
//        assert algorithm.rho("111111111111111110", 16) == 1;
//        assert algorithm.rho("10100011011110101110101011", 6) == 2;
//        assert algorithm.rho("1111111100000011010111", 3) == 5;
//        assert algorithm.rho("01111111111111111111111", 1) == 21;
//
//    }
//
//    @Test
//    void testRho() {
//    }
//
//    @Test
//    void arithmeticMean() {
//    }
//}
