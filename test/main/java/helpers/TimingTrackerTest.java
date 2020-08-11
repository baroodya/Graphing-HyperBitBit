package main.java.helpers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

class TimingTrackerTest {
    private File file;
    private String someAlg = "HBB";
    private String someFile = "src/testTimings.txt";
    private int someM = 64;
    private int someT = 100;
    private double someTime = 3000.0;

    @BeforeEach
    void setUp() throws IOException {
        file = new File("src/testTimings.txt");
        assert file.createNewFile();
    }

    @AfterEach
    void tearDown() {
        assert file.delete();
    }

    @Test
    void add() throws IOException {
        someAlg = "HBB";
        someFile = "src/testTimings.txt";
        someM = 64;
        someT = 100;
        someTime = 3000.0;

        String output = TimingTracker.add(someAlg, "'" + someFile + "'", someM, someT, someTime, someFile);
        String properOutput = "0" + " hours, " + "50" + " minutes, and " + "0" + " seconds.";
        assert output.equals(properOutput);

        Scanner sc = new Scanner(file);
        String fileLine = sc.nextLine();
        while (fileLine.equals("")) fileLine = sc.nextLine();
        String[] words = fileLine.split(" ");

        assert words[0].equals(someAlg + "'" + someFile + "'" + someM + "|" + someT);
        assert words[1].equals(Double.toString(someTime));
        assert words[2].equals(Integer.toString(1));

        someTime = 0.0;

        output = TimingTracker.add(someAlg, "'" + someFile + "'", someM, someT, someTime, someFile);
        properOutput = "0" + " hours, " + "0" + " minutes, and " + "0" + " seconds.";
        assert output.equals(properOutput);

        sc = new Scanner(file);
        fileLine = sc.nextLine();
        while (fileLine.equals("")) fileLine = sc.nextLine();
        words = fileLine.split(" ");

        assert words[0].equals(someAlg + "'" + someFile + "'" + someM + "|" + someT);
        assert words[1].equals(Double.toString(1500));
        assert words[2].equals(Integer.toString(2));
    }

    @Test
    void timing() throws IOException {
        String output = TimingTracker.timing(someAlg, "'" + someFile + "'", someM, someT, someFile);
        assert output.equals("This specific experiment has not been run before. I'm too lazy to give you an estimate.");

        TimingTracker.add(someAlg, "'" + someFile + "'", someM, someT, someTime, someFile);
        output = TimingTracker.timing(someAlg, "'" + someFile + "'", someM, someT, someFile);
        assert output.equals("This experiment will take about " + "0" + " hours, " + "50" + " minutes, and " + "0" + " seconds.");

        someTime = 0.0;

        TimingTracker.add(someAlg, "'" + someFile + "'", someM, someT, someTime, someFile);
        output = TimingTracker.timing(someAlg, "'" + someFile + "'", someM, someT, someFile);
        assert output.equals("This experiment will take about " + "0" + " hours, " + "25" + " minutes, and " + "0" + " seconds.");
    }
}
