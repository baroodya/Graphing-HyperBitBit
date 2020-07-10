package main.java;

import edu.princeton.cs.algs4.StdOut;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class IOPractice {
  public static void main(String[] args) {
    try {
      File myObj = new File("src/datasets/Curry2015-2016GameLogs.csv");
      Scanner myReader = new Scanner(myObj);
      String[] words;
      while (myReader.hasNextLine()) {
        String data = myReader.nextLine();
        words = data.split(",");
        for (String word : words) StdOut.println(word);
      }
      myReader.close();
    } catch (FileNotFoundException e) {
      StdOut.println("An error occurred.");
      e.printStackTrace();
    }
  }
}
