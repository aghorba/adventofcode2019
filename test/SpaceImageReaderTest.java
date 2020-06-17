import com.adventofcode.day.eight.SpaceImageReader;
import com.adventofcode.utils.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SpaceImageReaderTest extends TestHelper {

    @Test
    void test3x2Image() throws IOException {
        String imageData = "123456789012";
        Path testFile = createTestFile("test_space_image_reader", List.of(imageData));
        addFileToRemove(testFile);

        SpaceImageReader reader = new SpaceImageReader(testFile.toAbsolutePath().toString());
        assertEquals(reader.verifyImage(3, 2), 1);
    }

    @Test
    void test25x6ImageDay8Part1() throws IOException {
        SpaceImageReader reader = new SpaceImageReader("resources\\puzzle8_input.txt");
        assertEquals(reader.verifyImage(25, 6), 1584);
    }

    @Test
    void testImageDecode3x2Image() throws IOException {
        String imageData = "0222112222120000";
        Path testFile = createTestFile("test_space_image_reader", List.of(imageData));
        addFileToRemove(testFile);

        SpaceImageReader reader = new SpaceImageReader(testFile.toAbsolutePath().toString());
        assertEquals(reader.imageDecode(2, 2), "01\n10");
    }

    @Test
    void testImageDecode25x6ImageDay8Part2() throws IOException {
        SpaceImageReader reader = new SpaceImageReader("resources\\puzzle8_input.txt");
        assertEquals(reader.imageDecode(25, 6),
                "1001001100011001111001100\n" +
                "1010010010100101000010010\n" +
                "1100010000100001110010000\n" +
                "1010010000101101000010000\n" +
                "1010010010100101000010010\n" +
                "1001001100011101111001100");
    }

}
