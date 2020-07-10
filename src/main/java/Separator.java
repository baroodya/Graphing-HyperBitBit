package main.java;

import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Separator {
    public static void separate(String fileName, String regex) throws IOException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        String[] words;
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            words = scanner.nextLine().split(regex);
            for (String word : words) sb.append(word).append("\n");
        }
        scanner.close();

        FileWriter writer = new FileWriter(fileName);
        StdOut.print(sb.toString());
        writer.write(sb.toString());
        writer.close();
    }

    public static void main(String[] args) throws IOException {
        Separator.separate("src/datasets/Curry2015-2016GameLogs.csv", ",");
    }
}
