package main.java;

import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;

public class TimingTracker {
    // Static method to add a time to the file
    public static String add(String alg, String fileName, int substreams, int t, double time) throws IOException {
        // Initialize the scanner
        File file = new File("src/timings.txt");
        Scanner sc = new Scanner(file);

        // Create an id for the experiment
        String id = alg + fileName + substreams + "|" + t;
        String line = null;

        // Go through timings.txt and determine whether this experiment has been run before
        boolean seen = false;
        StringBuilder sb = new StringBuilder();
        String next;
        while (sc.hasNextLine()) {
            next = sc.nextLine();
            sb.append(next);
            if (next.contains(id)) {
                line = next;
                seen = true;
            }
        }
        sc.close();

        // if it has, update the average in the file
        if (seen) {
            String[] parts = line.split(" ");
            double avg = Double.parseDouble(parts[2]);
            int m = Integer.parseInt(parts[3]);

            avg = ((avg * m) + time) / (++m);

            String replace = id + " " + avg + " " + m;

            FileWriter writer = new FileWriter("src/timings.txt");
            writer.write(sb.toString().replace(line, "\n" + replace));
            writer.close();
        } // If it hasn't add it to the file
        else {
            line = id + " " + time + " " + "1";
            FileWriter writer = new FileWriter("src/timings.txt", true);
            writer.write("\n" + line);
            writer.close();
        }

        // Return the duration as a three part time (HH:MM:SS)
        Duration duration = Duration.ofSeconds((long) Math.ceil(time));
        return duration.toHoursPart() + " hours, " + duration.toMinutesPart() + " minutes, and " + duration.toSecondsPart() + " seconds.";
    }

    // Static Method to fetch a timing from the file
    public static String timing(String alg, String fileName, int m, int t) throws FileNotFoundException {
        // Initialize the scanner
        File file = new File("src/timings.txt");
        Scanner sc = new Scanner(file);

        // Create an id for the experiment
        String id = alg + fileName + m + "|" + t;
        String line = "";

        // Search for the id in the file
        boolean seen = false;
        String next;
        while (sc.hasNextLine()) {
            next = sc.nextLine();
            if (next.contains(id)) {
                line = next;
                seen = true;
                break;
            }
        }
        sc.close();

        // if you find it, return the appropriate estimate for timing
        if (seen) {
            String[] parts = line.split(" ");
            double avg = Double.parseDouble(parts[2]);
            Duration duration = Duration.ofSeconds((long) Math.ceil(avg));
            return "This experiment will take about " + duration.toHoursPart() + " hours, " + duration.toMinutesPart() + " minutes, and " + duration.toSecondsPart() + " seconds.";
        } // if you don't, return an error statement
        else
            return "This specific experiment has not been run before. I'm too lazy to give you an estimate.";
    }

    public static void main(String[] args) throws IOException {
        StdOut.println(timing("HBB", "test.txt", 4, 1));
        StdOut.println(add("HBB", "test.txt", 2, 4, 1));
        StdOut.println(timing("HBB", "test.txt", 4, 1));
    }
}
