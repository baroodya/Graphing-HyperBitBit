package main;

import helpers.Exact;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;

public class ExperimentLauncherTest {
  private ExperimentLauncher launcher =
      new ExperimentLauncher(
          "MC",
          100000,
          16,
          Exact.countArray("src/datasets/f0", 100000),
          0.5,
          0.77351,
          100,
          "src/datasets/f0");
  private final double[] oneHundredEqualValues;
  private final double[] tenThousandEqualValues;
  private final double[] stdDevEqualsOne;
  private final double[] tenRandomIntegers;
  private final double[] oneHundredRandomIntegers;
  private final double[][] fiveXTenRandomIntegers;
  private final double[] tenAvgOfFiveRandomIntegers;
  private final double[] fiveAvgOfTenRandomIntegers;

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

  public ExperimentLauncherTest() throws FileNotFoundException {
    oneHundredEqualValues = new double[100];
    for (int i = 0; i < 100; i++) oneHundredEqualValues[i] = 4;

    tenThousandEqualValues = new double[10000];
    for (int i = 0; i < 10000; i++) tenThousandEqualValues[i] = 99574.0;

    stdDevEqualsOne = new double[] {1, 1, 1, 1, 3, 3, 3, 3};

    tenRandomIntegers =
        new double[] {
          775453126, 462925776, 253157421, 682002360, 275778635, 11848667, 772756520, 939071191,
          351051786, 941821128
        };

    oneHundredRandomIntegers =
        new double[] {
          19367857, 38987783, 40700975, 44386200, 48878639, 59055127, 64613045, 69424228, 149676665,
          153323574, 155927272, 159689059, 163775146, 171987278, 172183582, 179331966, 179586569,
          195709527, 215663132, 230356326, 244319576, 246522948, 272030811, 297336249, 301971112,
          310044162, 318505814, 321884176, 328687553, 343683024, 347798920, 350800863, 378750367,
          392725984, 394895979, 405825502, 422771645, 422984004, 442742905, 455696084, 473603465,
          476777023, 493559192, 518432584, 537342868, 548457210, 551325450, 554276941, 563557440,
          579866320, 599337100, 631275115, 634045232, 636866899, 642367014, 648825880, 649861175,
          658560494, 664057485, 667896337, 672022132, 673798550, 676013942, 680610789, 681208820,
          690407148, 712732583, 715168023, 723103078, 733405996, 743643755, 748376221, 748894429,
          751639090, 765237750, 769193875, 775641182, 778049375, 785624242, 789576623, 819059518,
          820652119, 830951571, 839388033, 841332468, 842150377, 842426503, 843637048, 849679631,
          865428645, 878248622, 879517411, 887347544, 896702098, 905256390, 927753481, 936820379,
          970206582, 977035365, 999022651
        };

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

    fiveAvgOfTenRandomIntegers =
        new double[] {-117319891, 4080523, -73128335, 73985226, 25834493.5};

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

    launcher.runExperiments();
  }

  @Test(expected = IllegalArgumentException.class)
  public void realConstructor() throws FileNotFoundException {
    ExperimentLauncher newLauncher =
        new ExperimentLauncher(
            "AAA",
            1000,
            16,
            Exact.countArray("src/datasets/f0", 1000),
            0.5,
            0.77351,
            100,
            "src/datasets/f0");
    System.out.println(newLauncher.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void synthConstructor() throws FileNotFoundException {
    ExperimentLauncher newLauncher =
        new ExperimentLauncher(
            "AAA", 1000, 16, Exact.countArray("src/datasets/f0", 1000), 0.5, 0.77351, 100);
    System.out.println(newLauncher.toString());
  }

  @Test
  public void readElement() throws FileNotFoundException {
    launcher =
        new ExperimentLauncher(
            "MC",
            1000,
            16,
            Exact.countArray("src/datasets/f0", 1000),
            0.5,
            0.77351,
            100,
            "src/datasets/f0");
    launcher.syntheticData = false;

    assert launcher.algorithm.getSize() == 0;

    launcher.readElement("");
    assert launcher.algorithm.getSize() == 1;

    launcher.readElement("a");
    assert launcher.algorithm.getSize() == 2;

    launcher.readElement("bb");
    assert launcher.algorithm.getSize() == 3;

    launcher.readElement("ccc4");
    assert launcher.algorithm.getSize() == 4;

    launcher.readElement("kaffeeklatsches");
    assert launcher.algorithm.getSize() == 5;

    launcher.readElement(" ");
    assert launcher.algorithm.getSize() == 6;

    launcher =
        new ExperimentLauncher(
            "MC", 1000, 16, Exact.countArray("src/datasets/f0", 1000), 0.5, 0.77351, 100);

    assert launcher.algorithm.getSize() == 0;

    launcher.readElement("");
    assert launcher.algorithm.getSize() == 1;

    launcher.readElement("a");
    assert launcher.algorithm.getSize() == 2;

    launcher.readElement("bb");
    assert launcher.algorithm.getSize() == 3;

    launcher.readElement("ccc4");
    assert launcher.algorithm.getSize() == 4;

    launcher.readElement("kaffeeklatsches");
    assert launcher.algorithm.getSize() == 5;

    launcher.readElement(" ");
    assert launcher.algorithm.getSize() == 6;
  }

  @Test
  public void runExperiments() {
    assert Arrays.equals(oneThruOneThousand, launcher.getSizes());
    assert Arrays.equals(oneThruSixteen, launcher.varyMs);

    assert !Arrays.deepEquals(oneThousandXOneHundredZeros, launcher.estimates);
    assert !Arrays.deepEquals(oneThousandXOneHundredInfinities, launcher.estimates);

    assert !Arrays.deepEquals(sixteenXOneHundredZeros, launcher.varyMEstimates);
    assert !Arrays.deepEquals(sixteenXOneHundredInfinities, launcher.varyMEstimates);
  }

  @Test
  public void getAvgEstimates() {
    assert !Arrays.equals(oneThousandZeros, launcher.getAvgEstimates());
    assert !Arrays.equals(oneThousandInfinities, launcher.getAvgEstimates());
  }

  @Test
  public void getAvgAbsErrors() {
    assert !Arrays.equals(oneThousandZeros, launcher.getAvgAbsoluteErrors());
    assert !Arrays.equals(oneThousandInfinities, launcher.getAvgAbsoluteErrors());
  }

  @Test
  public void getAvgRelErrors() {
    assert !Arrays.equals(oneThousandZeros, launcher.getAvgRelativeErrors());
    assert !Arrays.equals(oneThousandInfinities, launcher.getAvgRelativeErrors());
  }

  @Test
  public void getAvgNormalizedEstimates() {
    assert !Arrays.equals(oneThousandZeros, launcher.getAvgNormalizedEstimates());
    assert !Arrays.equals(oneThousandInfinities, launcher.getAvgNormalizedEstimates());
  }

  @Test
  public void getAllAbsoluteErrors() {
    assert !Arrays.deepEquals(oneThousandXOneHundredZeros, launcher.getAllAbsoluteErrors());
    assert !Arrays.deepEquals(oneThousandXOneHundredInfinities, launcher.getAllAbsoluteErrors());
  }

  @Test
  public void getAllRelativeErrors() {
    assert !Arrays.deepEquals(oneThousandXOneHundredZeros, launcher.getAllRelativeErrors());
    assert !Arrays.deepEquals(oneThousandXOneHundredInfinities, launcher.getAllRelativeErrors());
  }

  @Test
  public void getAllNormalizedEstimates() {
    assert !Arrays.deepEquals(oneThousandXOneHundredZeros, launcher.getAllNormalizedEstimates());
    assert !Arrays.deepEquals(
        oneThousandXOneHundredInfinities, launcher.getAllNormalizedEstimates());
  }

  @Test
  public void getAvgEstimatesVaryM() {
    assert !Arrays.equals(sixteenZeros, launcher.getAvgEstimatesVaryM());
    assert !Arrays.equals(sixteenInfinities, launcher.getAvgEstimatesVaryM());
  }

  @Test
  public void getAvgAbsoluteErrorsVaryM() {
    assert !Arrays.equals(sixteenZeros, launcher.getAvgAbsoluteErrorsVaryM());
    assert !Arrays.equals(sixteenInfinities, launcher.getAvgAbsoluteErrorsVaryM());
  }

  @Test
  public void getAvgRelativeErrorsVaryM() {
    assert !Arrays.equals(sixteenZeros, launcher.getAvgRelativeErrorsVaryM());
    assert !Arrays.equals(sixteenInfinities, launcher.getAvgRelativeErrorsVaryM());
  }

  @Test
  public void getAvgNormalizedEstimatesVaryM() {
    assert !Arrays.equals(sixteenZeros, launcher.getAvgNormalizedEstimatesVaryM());
    assert !Arrays.equals(sixteenInfinities, launcher.getAvgNormalizedEstimatesVaryM());
  }

  @Test
  public void getAllAbsoluteErrorsVaryM() {
    assert !Arrays.deepEquals(sixteenXOneHundredZeros, launcher.getAllAbsoluteErrorsVaryM());
    assert !Arrays.deepEquals(sixteenXOneHundredInfinities, launcher.getAllAbsoluteErrorsVaryM());
  }

  @Test
  public void getAllRelativeErrorsVaryM() {
    assert !Arrays.deepEquals(sixteenXOneHundredZeros, launcher.getAllRelativeErrorsVaryM());
    assert !Arrays.deepEquals(sixteenXOneHundredInfinities, launcher.getAllRelativeErrorsVaryM());
  }

  @Test
  public void getAllNormalizedEstimatesVaryM() {
    assert !Arrays.deepEquals(sixteenXOneHundredZeros, launcher.getAllNormalizedEstimatesVaryM());
    assert !Arrays.deepEquals(
        sixteenXOneHundredInfinities, launcher.getAllNormalizedEstimatesVaryM());
  }

  @Test
  public void getStdDev() {
    assert launcher.getStdDev(oneHundredEqualValues) == 0;

    assert launcher.getStdDev(tenThousandEqualValues) == 0;

    assert launcher.getStdDev(stdDevEqualsOne) == 1;

    assert HelperMethods.withinFivePercent(launcher.getStdDev(tenRandomIntegers), 303766345.0);

    assert HelperMethods.withinFivePercent(
        launcher.getStdDev(oneHundredRandomIntegers), 275761833.0);
  }

  @Test
  public void getStdDevOfAllTrials() {
    double[] allTrials = launcher.getStdDevOfAllTrials();
    double[][] transverseEstimates =
        new double[launcher.varyMEstimates[0].length][launcher.varyMEstimates.length];

    for (int i = 0; i < transverseEstimates.length; i++) {
      for (int j = 0; j < transverseEstimates[0].length; j++) {
        transverseEstimates[i][j] = launcher.varyMEstimates[j][i];
      }
    }

    for (int i = 0; i < 16; i++) {
      assert launcher.getStdDev(transverseEstimates[i]) == allTrials[i];
    }
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
  public void arithmeticMean() {
    assert launcher.arithmeticMean(oneHundredEqualValues) == 4;

    assert launcher.arithmeticMean(tenThousandEqualValues) == 99574.0;

    assert launcher.arithmeticMean(stdDevEqualsOne) == 2;

    assert launcher.arithmeticMean(tenRandomIntegers) == 546586661;

    assert HelperMethods.withinFivePercent(
        launcher.arithmeticMean(oneHundredRandomIntegers), 535098568.91);

    for (int i = 0; i < 5; i++)
      assert launcher.arithmeticMean(fiveXTenRandomIntegers[i]) == fiveAvgOfTenRandomIntegers[i];
  }

  @Test
  public void main() throws IOException {
    String[] args = new String[1];
    args[0] = "true";
    ExperimentLauncher.main(args);
  }
}
