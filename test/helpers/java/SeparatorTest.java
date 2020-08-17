package helpers.java;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class SeparatorTest {

    @Test
    void separate() throws IOException {
        String fileName = "src/testFile.txt";
        String someText = "hi. i come from a planet called alex's planet. it has lots of golf and cheeseburgers. dad plays fortnite alot and listens to billie eyelash. isn't that weird? i am going swimming tomorrow and trying to surprise sophie and julia but julia is being annoying and wants to surprise sophie tomorrow too. isn't that annoying?";
        String someSeparatedText = "hi.\n" + "i\n" + "come\n" + "from\n" + "a\n" + "planet\n" + "called\n" + "alex's\n" + "planet.\n" + "it\n" + "has\n" + "lots\n" + "of\n" + "golf\n" + "and\n" + "cheeseburgers.\n" + "dad\n" + "plays\n" + "fortnite\n" + "alot\n" + "and\n" + "listens\n" + "to\n" + "billie\n" + "eyelash.\n" + "isn't\n" + "that\n" + "weird?\n" + "i\n" + "am\n" + "going\n" + "swimming\n" + "tomorrow\n" + "and\n" + "trying\n" + "to\n" + "surprise\n" + "sophie\n" + "and\n" + "julia\n" + "but\n" + "julia\n" + "is\n" + "being\n" + "annoying\n" + "and\n" + "wants\n" + "to\n" + "surprise\n" + "sophie\n" + "tomorrow\n" + "too.\n" + "isn't\n" + "that\n" + "annoying?\n";
        File file = new File(fileName);
        assert file.createNewFile();

        FileWriter writer = new FileWriter(fileName);
        writer.write(someText);
        writer.close();

        Separator.separate(fileName, " ");

        Scanner sc = new Scanner(file);
        StringBuilder sb = new StringBuilder();
        while (sc.hasNextLine()) sb.append(sc.nextLine()).append("\n");
        sc.close();
        assert file.delete();

        String sbString = sb.toString();
        assert sbString.equals(someSeparatedText);
    }
}
