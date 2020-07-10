package main.java;

import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Scanner;

public class TimingTracker {
    public static String add(String alg, String fileName, int substreams, int t, double time) throws IOException {
        File file = new File("src/timings.txt");
        Scanner sc = new Scanner(file);

        String id = alg + " " + fileName + " " + substreams + " " + t;
        String line = "";

        boolean seen = false;
        StringBuilder sb = new StringBuilder();
        String next = "";
        while (sc.hasNextLine()) {
            next = sc.nextLine();
            sb.append(next);
            if (next.contains(id)) {
                line = next;
                seen = true;
            }
        }
        sc.close();

        if (seen) {
            String[] parts = line.split(" ");
            double avg = Double.parseDouble(parts[2]);
            int m = Integer.parseInt(parts[3]);

            avg = ((avg * m) + time) / (++m);

            String replace = id + " " + avg + " " + m;

            FileWriter writer = new FileWriter("src/timings.txt");
            writer.write(sb.toString().replace(line, replace));
            writer.close();
        } else {
            line = id + " " + time + " " + 1;
            FileWriter writer = new FileWriter("src/timings.txt", true);
            writer.write("\n" + line);
            writer.close();
        }

        Duration duration = Duration.ofSeconds((long) Math.ceil(time));
        return duration.toHoursPart() + " hours, " + duration.toMinutesPart() + " minutes, and " + duration.toSecondsPart() + " seconds.";
    }

    public static String timing(String alg, String fileName, int m, int t) throws FileNotFoundException {
        File file = new File("src/timings.txt");
        Scanner sc = new Scanner(file);

        String id = alg + " " + fileName + " " + m + " " + t;
        String line = "";

        boolean seen = false;
        StringBuilder sb = new StringBuilder();
        String next = "";
        while (sc.hasNextLine()) {
            next = sc.nextLine();
            sb.append(next);
            if (next.contains(id)) {
                line = next;
                seen = true;
                break;
            }
        }
        sc.close();

        if (seen) {
            String[] parts = line.split(" ");
            double avg = Double.parseDouble(parts[2]);
            Duration duration = Duration.ofSeconds((long) Math.ceil(avg));
            return "This experiment will take about " + duration.toHoursPart() + " hours, " + duration.toMinutesPart() + " minutes, and " + duration.toSecondsPart() + " seconds.";
        } else
            return "This specific experiment has not been run before. I'm too lazy to give you an estimate.";
    }

    public static void main(String[] args) throws IOException {
        StdOut.println(timing("HBB", "test.txt", 4, 1));
        StdOut.println(add("HBB", "test.txt", 2, 4, 1));
        StdOut.println(timing("HBB", "test.txt", 4, 1));
    }
}
