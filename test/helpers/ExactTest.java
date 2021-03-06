package helpers;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.util.Arrays;

public class ExactTest {
    private final String fileName = "src/datasets/test.txt";
    private final int maxRead = 100000;
    private final StringStream someStream = new StringStream(fileName, maxRead);
    private final int streamSize = 15;
    private final int streamCard = 13;
    private final int[] streamCardArray = {1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 10, 10, 11, 12, 13};

    public ExactTest() throws FileNotFoundException {
    }

    @Test
    public void count() throws FileNotFoundException {
        assert Exact.count(someStream) == streamCard;
        assert Exact.count(fileName, maxRead) == streamCard;

        assert Exact.count(someStream) != streamSize;
        assert Exact.count(fileName, maxRead) != streamSize;
    }

    @Test
    public void countArray() throws FileNotFoundException {
        assert Arrays.equals(Exact.countArray(someStream), streamCardArray);
        assert Arrays.equals(Exact.countArray(fileName, maxRead), streamCardArray);
    }

    @Test
    public void total() throws FileNotFoundException {
        assert Exact.total(someStream) == streamSize;
        assert Exact.total(fileName, maxRead) == streamSize;

        assert Exact.total(someStream) != streamCard;
        assert Exact.total(fileName, maxRead) != streamCard;
    }
}
