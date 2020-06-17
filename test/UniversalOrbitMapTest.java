import com.adventofcode.day.six.UniversalOrbitMap;
import com.adventofcode.utils.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniversalOrbitMapTest extends TestHelper {

    @Test
    void testGetOrbitsCount() throws IOException {
        Path testFile = createTestFile("test_universalorbitmap", List.of(
                "COM)B","B)C","C)D","D)E","E)F","B)G","G)H","D)I","E)J","J)K","K)L"));
        currentTestFile = testFile;

        UniversalOrbitMap map = new UniversalOrbitMap(testFile.toAbsolutePath().toString());
        assertEquals(map.orbitsCount(), 42);
    }

    @Test
    void testGetMinimumTransfers() throws IOException {
        Path testFile = createTestFile("test_universalorbitmap", List.of(
                "COM)B","B)C","C)D","D)E","E)F","B)G","G)H","D)I","E)J","J)K","K)L","K)YOU","I)SAN"));
        currentTestFile = testFile;

        UniversalOrbitMap map = new UniversalOrbitMap(testFile.toAbsolutePath().toString());
        assertEquals(map.minimumOrbitalTransfers("YOU", "SAN"), 4);
    }

}
