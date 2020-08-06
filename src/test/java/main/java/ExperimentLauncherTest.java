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

        values = new double[]{
                19367857,
                38987783,
                40700975,
                44386200,
                48878639,
                59055127,
                64613045,
                69424228,
                149676665,
                153323574,
                155927272,
                159689059,
                163775146,
                171987278,
                172183582,
                179331966,
                179586569,
                195709527,
                215663132,
                230356326,
                244319576,
                246522948,
                272030811,
                297336249,
                301971112,
                310044162,
                318505814,
                321884176,
                328687553,
                343683024,
                347798920,
                350800863,
                378750367,
                392725984,
                394895979,
                405825502,
                422771645,
                422984004,
                442742905,
                455696084,
                473603465,
                476777023,
                493559192,
                518432584,
                537342868,
                548457210,
                551325450,
                554276941,
                563557440,
                579866320,
                599337100,
                631275115,
                634045232,
                636866899,
                642367014,
                648825880,
                649861175,
                658560494,
                664057485,
                667896337,
                672022132,
                673798550,
                676013942,
                680610789,
                681208820,
                690407148,
                712732583,
                715168023,
                723103078,
                733405996,
                743643755,
                748376221,
                748894429,
                751639090,
                765237750,
                769193875,
                775641182,
                778049375,
                785624242,
                789576623,
                819059518,
                820652119,
                830951571,
                839388033,
                841332468,
                842150377,
                842426503,
                843637048,
                849679631,
                865428645,
                878248622,
                879517411,
                887347544,
                896702098,
                905256390,
                927753481,
                936820379,
                970206582,
                977035365,
                999022651
        };

        assert Math.round(launcher.getStdDev(values)) == 27576183.0;
        ;
    }

    @Test
    void arithmeticMean() {
    }
}
