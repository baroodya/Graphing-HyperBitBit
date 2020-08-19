package main;

import algs.HyperBitBit;
import helpers.Exact;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;

class ComparisonLauncherTest {
    private final HyperBitBit newAlg = new HyperBitBit(0.5, 16);
    private ComparisonLauncher launcher = new ComparisonLauncher(newAlg, 100000, 16, Exact.countArray("src/datasets/f0", 100000), 0.5, 100, "src/datasets/f0");

    private final double[][] fiveXTenRandomIntegers;
    private final double[] tenAvgOfFiveRandomIntegers;

    private final double[] oneHundredThousandZeros;
    private final double[][] oneHundredThousandXOneHundredZeros;
    private final double[] oneHundredThousandInfinities;
    private final double[][] oneHundredThousandXOneHundredInfinities;
    private final double[] sixteenZeros;
    private final double[] sixteenInfinities;
    private final double[][] sixteenXOneHundredZeros;
    private final double[][] sixteenXOneHundredInfinities;
    private final double[] oneThruOneHundredThousand;
    private final double[] oneThruSixteen;

    ComparisonLauncherTest() throws FileNotFoundException {
        fiveXTenRandomIntegers = new double[][]
                {
                        {
                                -959524526,
                                -787242262,
                                -712477369,
                                -449172658,
                                -160166097,
                                -118383787,
                                88685050,
                                619886815,
                                634187559,
                                671008365
                        }, {
                        -908551392,
                        -735609924,
                        -728011950,
                        -195316964,
                        8908870,
                        281809076,
                        355395304,
                        448936754,
                        576696226,
                        936549230
                }, {
                        -954122810,
                        -936767033,
                        -572090059,
                        -525111398,
                        -374290335,
                        15048795,
                        388573870,
                        637526699,
                        742982417,
                        846966504
                }, {
                        -868221599,
                        -867259577,
                        -754071576,
                        -164651758,
                        8076222,
                        310546807,
                        704029566,
                        734278940,
                        776570591,
                        860554644
                }, {
                        -818179623,
                        -777158949,
                        -726553991,
                        -308529212,
                        30561785,
                        414170294,
                        485308460,
                        516058892,
                        635745422,
                        806921857
                }};
        tenAvgOfFiveRandomIntegers = new double[]{
                -901719990,
                -820807549,
                -698640989,
                -328556398,
                -97381911,
                180638237,
                404398450,
                591337620,
                673236443,
                824400120
        };

        oneHundredThousandZeros = new double[100000];
        for (int i = 0; i < 100000; i++) oneHundredThousandZeros[i] = 0;

        oneHundredThousandXOneHundredZeros = new double[100][100000];
        for (int i = 0; i < 100; i++)
            for (int j = 0; j < 100000; j++) oneHundredThousandXOneHundredZeros[i][j] = 0;

        oneHundredThousandInfinities = new double[100000];
        for (int i = 0; i < 100000; i++)
            oneHundredThousandInfinities[i] = Double.POSITIVE_INFINITY;

        oneHundredThousandXOneHundredInfinities = new double[100][100000];
        for (int i = 0; i < 100; i++)
            for (int j = 0; j < 100000; j++)
                oneHundredThousandXOneHundredInfinities[i][j] = Double.POSITIVE_INFINITY;

        sixteenZeros = new double[16];
        for (int i = 0; i < 16; i++)
            sixteenZeros[i] = 0;

        sixteenInfinities = new double[16];
        for (int i = 0; i < 16; i++)
            sixteenInfinities[i] = Double.POSITIVE_INFINITY;

        sixteenXOneHundredZeros = new double[16][100];
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 100; j++)
                sixteenXOneHundredZeros[i][j] = 0;

        sixteenXOneHundredInfinities = new double[16][100];
        for (int i = 0; i < 16; i++)
            for (int j = 0; j < 100; j++)
                sixteenXOneHundredInfinities[i][j] = Double.POSITIVE_INFINITY;

        oneThruOneHundredThousand = new double[100000];
        for (int i = 0; i < 100000; i++) oneThruOneHundredThousand[i] = i + 1;

        oneThruSixteen = new double[16];
        for (int i = 0; i < 16; i++) oneThruSixteen[i] = i + 1;
    }

    @Test
    void readElement() throws FileNotFoundException {
        launcher.syntheticData = false;

        assert launcher.MinCount.getSize() == 0;
        assert launcher.ProbabilisticCounting.getSize() == 0;
        assert launcher.HyperBitBit.getSize() == 0;
        assert launcher.newAlgorithm.getSize() == 0;

        launcher.readElement("");
        assert launcher.MinCount.getSize() == 1;
        assert launcher.ProbabilisticCounting.getSize() == 1;
        assert launcher.HyperBitBit.getSize() == 1;
        assert launcher.newAlgorithm.getSize() == 1;

        launcher.readElement("a");
        assert launcher.MinCount.getSize() == 2;
        assert launcher.ProbabilisticCounting.getSize() == 2;
        assert launcher.HyperBitBit.getSize() == 2;
        assert launcher.newAlgorithm.getSize() == 2;

        launcher.readElement("bb");
        assert launcher.MinCount.getSize() == 3;
        assert launcher.ProbabilisticCounting.getSize() == 3;
        assert launcher.HyperBitBit.getSize() == 3;
        assert launcher.newAlgorithm.getSize() == 3;

        launcher.readElement("ccc4");
        assert launcher.MinCount.getSize() == 4;
        assert launcher.ProbabilisticCounting.getSize() == 4;
        assert launcher.HyperBitBit.getSize() == 4;
        assert launcher.newAlgorithm.getSize() == 4;

        launcher.readElement("kaffeeklatsches");
        assert launcher.MinCount.getSize() == 5;
        assert launcher.ProbabilisticCounting.getSize() == 5;
        assert launcher.HyperBitBit.getSize() == 5;
        assert launcher.newAlgorithm.getSize() == 5;

        launcher.readElement(" ");
        assert launcher.MinCount.getSize() == 6;
        assert launcher.ProbabilisticCounting.getSize() == 6;
        assert launcher.HyperBitBit.getSize() == 6;
        assert launcher.newAlgorithm.getSize() == 6;

        launcher = new ComparisonLauncher(newAlg, 100000, 16, Exact.countArray("src/datasets/f0", 100000), 0.5, 100, "synthetic");

        assert launcher.MinCount.getSize() == 0;
        assert launcher.ProbabilisticCounting.getSize() == 0;
        assert launcher.HyperBitBit.getSize() == 0;
        assert launcher.newAlgorithm.getSize() == 0;

        launcher.readElement("");
        assert launcher.MinCount.getSize() == 1;
        assert launcher.ProbabilisticCounting.getSize() == 1;
        assert launcher.HyperBitBit.getSize() == 1;
        assert launcher.newAlgorithm.getSize() == 1;

        launcher.readElement("a");
        assert launcher.MinCount.getSize() == 2;
        assert launcher.ProbabilisticCounting.getSize() == 2;
        assert launcher.HyperBitBit.getSize() == 2;
        assert launcher.newAlgorithm.getSize() == 2;

        launcher.readElement("bb");
        assert launcher.MinCount.getSize() == 3;
        assert launcher.ProbabilisticCounting.getSize() == 3;
        assert launcher.HyperBitBit.getSize() == 3;
        assert launcher.newAlgorithm.getSize() == 3;

        launcher.readElement("ccc4");
        assert launcher.MinCount.getSize() == 4;
        assert launcher.ProbabilisticCounting.getSize() == 4;
        assert launcher.HyperBitBit.getSize() == 4;
        assert launcher.newAlgorithm.getSize() == 4;

        launcher.readElement("kaffeeklatsches");
        assert launcher.MinCount.getSize() == 5;
        assert launcher.ProbabilisticCounting.getSize() == 5;
        assert launcher.HyperBitBit.getSize() == 5;
        assert launcher.newAlgorithm.getSize() == 5;

        launcher.readElement(" ");
        assert launcher.MinCount.getSize() == 6;
        assert launcher.ProbabilisticCounting.getSize() == 6;
        assert launcher.HyperBitBit.getSize() == 6;
        assert launcher.newAlgorithm.getSize() == 6;
    }

    @Test
    void runExperiments() throws FileNotFoundException {
        launcher.runExperiments();

        assert Arrays.equals(oneThruOneHundredThousand, launcher.sizes);
        assert Arrays.equals(oneThruSixteen, launcher.varyMs);

        assert !Arrays.deepEquals(oneHundredThousandXOneHundredZeros, launcher.MCEstimates);
        assert !Arrays.deepEquals(oneHundredThousandXOneHundredInfinities, launcher.MCEstimates);
        assert !Arrays.deepEquals(oneHundredThousandXOneHundredZeros, launcher.PCEstimates);
        assert !Arrays.deepEquals(oneHundredThousandXOneHundredInfinities, launcher.PCEstimates);
        assert !Arrays.deepEquals(oneHundredThousandXOneHundredZeros, launcher.HBBEstimates);
        assert !Arrays.deepEquals(oneHundredThousandXOneHundredInfinities, launcher.HBBEstimates);
        assert !Arrays.deepEquals(oneHundredThousandXOneHundredZeros, launcher.newAlgEstimates);
        assert !Arrays.deepEquals(oneHundredThousandXOneHundredInfinities, launcher.newAlgEstimates);

        assert !Arrays.deepEquals(sixteenXOneHundredZeros, launcher.varyMMCEstimates);
        assert !Arrays.deepEquals(sixteenXOneHundredInfinities, launcher.varyMMCEstimates);
        assert !Arrays.deepEquals(sixteenXOneHundredZeros, launcher.varyMPCEstimates);
        assert !Arrays.deepEquals(sixteenXOneHundredInfinities, launcher.varyMPCEstimates);
        assert !Arrays.deepEquals(sixteenXOneHundredZeros, launcher.varyMHBBEstimates);
        assert !Arrays.deepEquals(sixteenXOneHundredInfinities, launcher.varyMHBBEstimates);
        assert !Arrays.deepEquals(sixteenXOneHundredZeros, launcher.varyMNewAlgEstimates);
        assert !Arrays.deepEquals(sixteenXOneHundredInfinities, launcher.varyMNewAlgEstimates);


        // Run the rest of the tests from here to save time
        getAllMCRelativeErrors();
        getAvgMCRelativeErrors();
        getAllPCRelativeErrors();
        getAvgPCRelativeErrors();
        getAllHBBRelativeErrors();
        getAvgHBBRelativeErrors();
        getAllNewAlgRelativeErrors();
        getAvgNewAlgRelativeErrors();
        getAvgMCEstimatesVaryM();
        getAvgPCEstimatesVaryM();
        getAvgHBBEstimatesVaryM();
        getAvgNewAlgEstimatesVaryM();
    }

    void getAllMCRelativeErrors() {
        assert !Arrays.equals(oneHundredThousandXOneHundredZeros, launcher.getAllMCRelativeErrors());
        assert !Arrays.equals(oneHundredThousandXOneHundredInfinities, launcher.getAllMCRelativeErrors());
    }

    void getAvgMCRelativeErrors() {
        assert !Arrays.equals(oneHundredThousandZeros, launcher.getAvgMCRelativeErrors());
        assert !Arrays.equals(oneHundredThousandInfinities, launcher.getAvgMCRelativeErrors());
    }

    void getAllPCRelativeErrors() {
        assert !Arrays.equals(oneHundredThousandXOneHundredZeros, launcher.getAllPCRelativeErrors());
        assert !Arrays.equals(oneHundredThousandXOneHundredInfinities, launcher.getAllPCRelativeErrors());
    }

    void getAvgPCRelativeErrors() {
        assert !Arrays.equals(oneHundredThousandZeros, launcher.getAvgPCRelativeErrors());
        assert !Arrays.equals(oneHundredThousandInfinities, launcher.getAvgPCRelativeErrors());
    }

    void getAllHBBRelativeErrors() {
        assert !Arrays.equals(oneHundredThousandXOneHundredZeros, launcher.getAllHBBRelativeErrors());
        assert !Arrays.equals(oneHundredThousandXOneHundredInfinities, launcher.getAllHBBRelativeErrors());
    }

    void getAvgHBBRelativeErrors() {
        assert !Arrays.equals(oneHundredThousandZeros, launcher.getAvgHBBRelativeErrors());
        assert !Arrays.equals(oneHundredThousandInfinities, launcher.getAvgHBBRelativeErrors());
    }

    void getAllNewAlgRelativeErrors() {
        assert !Arrays.equals(oneHundredThousandXOneHundredZeros, launcher.getAllHBBRelativeErrors());
        assert !Arrays.equals(oneHundredThousandXOneHundredInfinities, launcher.getAllHBBRelativeErrors());
    }

    void getAvgNewAlgRelativeErrors() {
        assert !Arrays.equals(oneHundredThousandZeros, launcher.getAvgNewAlgRelativeErrors());
        assert !Arrays.equals(oneHundredThousandInfinities, launcher.getAvgNewAlgRelativeErrors());
    }

    void getAvgMCEstimatesVaryM() {
        assert !Arrays.equals(sixteenZeros, launcher.getAvgMCEstimatesVaryM());
        assert !Arrays.equals(sixteenInfinities, launcher.getAvgMCEstimatesVaryM());
    }

    void getAvgPCEstimatesVaryM() {
        assert !Arrays.equals(sixteenZeros, launcher.getAvgPCEstimatesVaryM());
        assert !Arrays.equals(sixteenInfinities, launcher.getAvgPCEstimatesVaryM());
    }

    void getAvgHBBEstimatesVaryM() {
        assert !Arrays.equals(sixteenZeros, launcher.getAvgHBBEstimatesVaryM());
        assert !Arrays.equals(sixteenInfinities, launcher.getAvgHBBEstimatesVaryM());
    }

    void getAvgNewAlgEstimatesVaryM() {
        assert !Arrays.equals(sixteenZeros, launcher.getAvgNewAlgEstimatesVaryM());
        assert !Arrays.equals(sixteenInfinities, launcher.getAvgNewAlgRelativeErrors());
    }

    @Test
    void averageOverTrials() {
        assert Arrays.equals(oneHundredThousandZeros, launcher.averageOverTrials(oneHundredThousandXOneHundredZeros));
        assert Arrays.equals(oneHundredThousandInfinities, launcher.averageOverTrials(oneHundredThousandXOneHundredInfinities));
        assert Arrays.equals(tenAvgOfFiveRandomIntegers, launcher.averageOverTrials(fiveXTenRandomIntegers));
    }

//    @Test
//    void main() throws FileNotFoundException {
//        String[] args = new String[]{"false"};
//        ComparisonLauncher.main(args);
//        args[0] = "true";
//        ComparisonLauncher.main(args);
//    }
}
