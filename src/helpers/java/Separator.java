package helpers.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Separator {
    // Static method to take a file fileName and separate it into lines based on the regular expression regex
    public static void separate(String fileName, String regex) throws IOException {
        // initialize the file and scanner
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);

        // Build a string that is what we want the file to look like
        String[] words;
        StringBuilder sb = new StringBuilder();
        while (scanner.hasNextLine()) {
            words = scanner.nextLine().split(regex);
            for (String word : words) sb.append(word).append("\n");
        }
        scanner.close();

        // Write the string to the file, over writing what was already there
        FileWriter writer = new FileWriter(fileName);
        writer.write(sb.toString());
        writer.close();
    }
}
