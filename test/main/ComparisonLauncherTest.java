package main;

import algs.HyperBitBit;
import helpers.Exact;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class ComparisonLauncherTest {
  private final HyperBitBit newAlg = new HyperBitBit(0.5, 16);
  private ComparisonLauncher launcher =
      new ComparisonLauncher(
          newAlg,
          100000,
          16,
          Exact.countArray("src/datasets/f0", 100000),
          0.5,
          100,
          "src/datasets/f0");

  private final double[][] fiveXTenRandomIntegers;
  private final double[] tenAvgOfFiveRandomIntegers;

  private final double[] oneThousandZeros;
  private final double[][] oneThousandXOneHundredZeros;
  private final double[] oneThousandInfinities;
  private final double[][] oneThousandXOneHundredInfinities;
  private final double[] sixteenZeros;
  private final double[] sixteenInfinities;
  private final double[][] sixteenXOneHundredZeros;
  private final double[][] sixteenXOneHundredInfinities;
  private final double[] oneThruOneThousand;
  private final double[] oneThruSixteen;

  public ComparisonLauncherTest() throws FileNotFoundException {
    fiveXTenRandomIntegers =
        new double[][] {
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
          },
          {
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
          },
          {
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
          },
          {
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
          },
          {
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
          }
        };
    tenAvgOfFiveRandomIntegers =
        new double[] {
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

    oneThousandZeros = new double[1000];
    for (int i = 0; i < 1000; i++) oneThousandZeros[i] = 0;

    oneThousandXOneHundredZeros = new double[100][1000];
    for (int i = 0; i < 100; i++)
      for (int j = 0; j < 1000; j++) oneThousandXOneHundredZeros[i][j] = 0;

    oneThousandInfinities = new double[1000];
    for (int i = 0; i < 1000; i++) oneThousandInfinities[i] = Double.POSITIVE_INFINITY;

    oneThousandXOneHundredInfinities = new double[100][1000];
    for (int i = 0; i < 100; i++)
      for (int j = 0; j < 1000; j++)
        oneThousandXOneHundredInfinities[i][j] = Double.POSITIVE_INFINITY;

    sixteenZeros = new double[16];
    for (int i = 0; i < 16; i++) sixteenZeros[i] = 0;

    sixteenInfinities = new double[16];
    for (int i = 0; i < 16; i++) sixteenInfinities[i] = Double.POSITIVE_INFINITY;

    sixteenXOneHundredZeros = new double[16][100];
    for (int i = 0; i < 16; i++) for (int j = 0; j < 100; j++) sixteenXOneHundredZeros[i][j] = 0;

    sixteenXOneHundredInfinities = new double[16][100];
    for (int i = 0; i < 16; i++)
      for (int j = 0; j < 100; j++) sixteenXOneHundredInfinities[i][j] = Double.POSITIVE_INFINITY;

    oneThruOneThousand = new double[1000];
    for (int i = 0; i < 1000; i++) oneThruOneThousand[i] = i + 1;

    oneThruSixteen = new double[16];
    for (int i = 0; i < 16; i++) oneThruSixteen[i] = i + 1;
  }

  @Test
  public void readElement() throws FileNotFoundException {
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

    launcher =
        new ComparisonLauncher(
            newAlg, 1000, 16, Exact.countArray("src/datasets/f0", 1000), 0.5, 100, "synthetic");

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
  public void runExperiments() throws FileNotFoundException {
    launcher.runExperiments();

    assert Arrays.equals(oneThruOneThousand, launcher.sizes);
    assert Arrays.equals(oneThruSixteen, launcher.varyMs);

    assert !Arrays.deepEquals(oneThousandXOneHundredZeros, launcher.MCEstimates);
    assert !Arrays.deepEquals(oneThousandXOneHundredInfinities, launcher.MCEstimates);
    assert !Arrays.deepEquals(oneThousandXOneHundredZeros, launcher.PCEstimates);
    assert !Arrays.deepEquals(oneThousandXOneHundredInfinities, launcher.PCEstimates);
    assert !Arrays.deepEquals(oneThousandXOneHundredZeros, launcher.HBBEstimates);
    assert !Arrays.deepEquals(oneThousandXOneHundredInfinities, launcher.HBBEstimates);
    assert !Arrays.deepEquals(oneThousandXOneHundredZeros, launcher.newAlgEstimates);
    assert !Arrays.deepEquals(oneThousandXOneHundredInfinities, launcher.newAlgEstimates);

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

  public void getAllMCRelativeErrors() {
    assert !Arrays.equals(oneThousandXOneHundredZeros, launcher.getAllMCRelativeErrors());
    assert !Arrays.equals(oneThousandXOneHundredInfinities, launcher.getAllMCRelativeErrors());
  }

  public void getAvgMCRelativeErrors() {
    assert !Arrays.equals(oneThousandZeros, launcher.getAvgMCRelativeErrors());
    assert !Arrays.equals(oneThousandInfinities, launcher.getAvgMCRelativeErrors());
  }

  public void getAllPCRelativeErrors() {
    assert !Arrays.equals(oneThousandXOneHundredZeros, launcher.getAllPCRelativeErrors());
    assert !Arrays.equals(oneThousandXOneHundredInfinities, launcher.getAllPCRelativeErrors());
  }

  public void getAvgPCRelativeErrors() {
    assert !Arrays.equals(oneThousandZeros, launcher.getAvgPCRelativeErrors());
    assert !Arrays.equals(oneThousandInfinities, launcher.getAvgPCRelativeErrors());
  }

  public void getAllHBBRelativeErrors() {
    assert !Arrays.equals(oneThousandXOneHundredZeros, launcher.getAllHBBRelativeErrors());
    assert !Arrays.equals(oneThousandXOneHundredInfinities, launcher.getAllHBBRelativeErrors());
  }

  public void getAvgHBBRelativeErrors() {
    assert !Arrays.equals(oneThousandZeros, launcher.getAvgHBBRelativeErrors());
    assert !Arrays.equals(oneThousandInfinities, launcher.getAvgHBBRelativeErrors());
  }

  public void getAllNewAlgRelativeErrors() {
    assert !Arrays.equals(oneThousandXOneHundredZeros, launcher.getAllHBBRelativeErrors());
    assert !Arrays.equals(oneThousandXOneHundredInfinities, launcher.getAllHBBRelativeErrors());
  }

  public void getAvgNewAlgRelativeErrors() {
    assert !Arrays.equals(oneThousandZeros, launcher.getAvgNewAlgRelativeErrors());
    assert !Arrays.equals(oneThousandInfinities, launcher.getAvgNewAlgRelativeErrors());
  }

  public void getAvgMCEstimatesVaryM() {
    assert !Arrays.equals(sixteenZeros, launcher.getAvgMCEstimatesVaryM());
    assert !Arrays.equals(sixteenInfinities, launcher.getAvgMCEstimatesVaryM());
  }

  public void getAvgPCEstimatesVaryM() {
    assert !Arrays.equals(sixteenZeros, launcher.getAvgPCEstimatesVaryM());
    assert !Arrays.equals(sixteenInfinities, launcher.getAvgPCEstimatesVaryM());
  }

  public void getAvgHBBEstimatesVaryM() {
    assert !Arrays.equals(sixteenZeros, launcher.getAvgHBBEstimatesVaryM());
    assert !Arrays.equals(sixteenInfinities, launcher.getAvgHBBEstimatesVaryM());
  }

  public void getAvgNewAlgEstimatesVaryM() {
    assert !Arrays.equals(sixteenZeros, launcher.getAvgNewAlgEstimatesVaryM());
    assert !Arrays.equals(sixteenInfinities, launcher.getAvgNewAlgRelativeErrors());
  }

  @Test
  public void averageOverTrials() {
    assert Arrays.equals(oneThousandZeros, launcher.averageOverTrials(oneThousandXOneHundredZeros));
    assert Arrays.equals(
        oneThousandInfinities, launcher.averageOverTrials(oneThousandXOneHundredInfinities));
    assert Arrays.equals(
        tenAvgOfFiveRandomIntegers, launcher.averageOverTrials(fiveXTenRandomIntegers));
  }

  @Test
  public void main() throws FileNotFoundException {
    String[] args = new String[] {"false", "true"};
    ComparisonLauncher.main(args);
    args[0] = "true";
    args[1] = "true";
    ComparisonLauncher.main(args);
  }
}
