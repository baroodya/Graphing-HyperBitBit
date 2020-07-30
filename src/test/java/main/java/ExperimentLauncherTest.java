package main.java;

import main.java.helpers.Exact;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

class ExperimentLauncherTest {
    private ExperimentLauncher launcher;

    ExperimentLauncherTest() throws FileNotFoundException {
        launcher = new ExperimentLauncher("MC", 100000, 16, Exact.countArray("src/datasets/f0", 100000), 0.5, 0.77351, 100);
    }

    @Test
    void getSizes() {
    }

    @Test
    void getAvgEstimates() {
    }

    @Test
    void getAllAbsoluteErrors() {
    }

    @Test
    void getAvgAbsoluteErrors() {
    }

    @Test
    void getAllRelativeErrors() {
    }

    @Test
    void getAvgRelativeErrors() {
    }

    @Test
    void getAvgNormalizedEstimates() {
    }

    @Test
    void getAllNormalizedEstimates() {
    }

    @Test
    void getAvgEstimatesVaryM() {
    }

    @Test
    void getAllAbsoluteErrorsVaryM() {
    }

    @Test
    void getAvgAbsoluteErrorsVaryM() {
    }

    @Test
    void getAllMRelativeErrorsVaryM() {
    }

    @Test
    void getAvgRelativeErrorsVaryM() {
    }

    @Test
    void getAvgNormalizedEstimatesVaryM() {
    }

    @Test
    void getAllNormalizedEstimatesVaryM() {
    }

    @Test
    void getStdDevOfAllTrials() {
    }

    @Test
    void averageOverTrials() {
    }

    @Test
    void getStdDev() {
        double[] values = new double[100];
        for (int i = 0; i < 100; i++) values[i] = 4;
        assert launcher.getStdDev(values) == 0;

        values = new double[10000];
        for (int i = 0; i < 10000; i++) values[i] = 99574.0;
        assert launcher.getStdDev(values) == 0;

        values = new double[]{1, 1, 1, 1, 3, 3, 3, 3};
        assert launcher.getStdDev(values) == 1;

        values = new double[]{
                775453126,
                462925776,
                253157421,
                682002360,
                275778635,
                11848667,
                772756520,
                939071191,
                351051786,
                941821128
        };

        assert Math.round(launcher.getStdDev(values)) == 303766345.0;
    }

    @Test
    void arithmeticMean() {
    }
}
