package main.java;

import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CardinalityCalculator {
  public static void main(String[] args) throws FileNotFoundException {
    String input = "src/datasets/f0";
    // Read in the file
    File inputFile = new File(input);
    Scanner fileReader = new Scanner(inputFile);
    StringBuilder sb = new StringBuilder();
    while (fileReader.hasNextLine()) sb.append(fileReader.nextLine());
    fileReader.close();

    String[] words = sb.toString().split(" ");

    Queue<String> q = new Queue<String>();

    boolean seen = false;
    String testWord = "";
    int cardinality = 0;
    for (String word : words) {
      for (int i = 0; i < cardinality; i++) {
        testWord = q.dequeue();
        if (word.equals(testWord)) seen = true;
        q.enqueue(testWord);
      }
      if (!seen) {
        q.enqueue(word);
        cardinality++;
      } else seen = false;
    }

    StdOut.println("Size of " + input + " = " + words.length);
    StdOut.println("Cardinality of " + input + " = " + cardinality);
  }
}
