import com.adventofcode.day.four.FuelDepotPasswordGenerator;
import com.adventofcode.utils.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuelDepotPasswordGeneratorTest extends TestHelper {

    @Test
    void testPasswordCriteriaCheckOne() throws IOException {
        Path testFile = Paths.get("test_password_gen_" + UUID.randomUUID());
        currentTestFile = testFile;

        Files.write(testFile, List.of("111111-111111"));

        FuelDepotPasswordGenerator passwordGenerator = new FuelDepotPasswordGenerator(
                testFile.toAbsolutePath().toString());

        assertEquals(passwordGenerator.checkPasswordCriteria(), Collections.emptySet());
    }

    @Test
    void testPasswordCriteriaCheckTwo() throws IOException {
        Path testFile = Paths.get("test_password_gen_" + UUID.randomUUID());
        currentTestFile = testFile;

        Files.write(testFile, List.of("223450-223450"));

        FuelDepotPasswordGenerator passwordGenerator = new FuelDepotPasswordGenerator(
                testFile.toAbsolutePath().toString());

        assertEquals(passwordGenerator.checkPasswordCriteria(), Collections.emptySet());
    }

    @Test
    void testPasswordCriteriaCheckThree() throws IOException {
        Path testFile = Paths.get("test_password_gen_" + UUID.randomUUID());
        currentTestFile = testFile;

        Files.write(testFile, List.of("123789-123789"));

        FuelDepotPasswordGenerator passwordGenerator = new FuelDepotPasswordGenerator(
                testFile.toAbsolutePath().toString());

        assertEquals(passwordGenerator.checkPasswordCriteria(), Collections.emptySet());
    }

    @Test
    void testPasswordCriteriaCheckFour() throws IOException {
        Path testFile = Paths.get("test_password_gen_" + UUID.randomUUID());
        currentTestFile = testFile;

        Files.write(testFile, List.of("112233-112233"));

        FuelDepotPasswordGenerator passwordGenerator = new FuelDepotPasswordGenerator(
                testFile.toAbsolutePath().toString());

        assertEquals(passwordGenerator.checkPasswordCriteria().size(), 1);
    }

    @Test
    void testPasswordCriteriaCheckFive() throws IOException {
        Path testFile = Paths.get("test_password_gen_" + UUID.randomUUID());
        currentTestFile = testFile;

        Files.write(testFile, List.of("123444-123444"));

        FuelDepotPasswordGenerator passwordGenerator = new FuelDepotPasswordGenerator(
                testFile.toAbsolutePath().toString());

        assertEquals(passwordGenerator.checkPasswordCriteria(), Collections.emptySet());
    }

    @Test
    void testPasswordCriteriaCheckSix() throws IOException {
        Path testFile = Paths.get("test_password_gen_" + UUID.randomUUID());
        currentTestFile = testFile;

        Files.write(testFile, List.of("111122-111122"));

        FuelDepotPasswordGenerator passwordGenerator = new FuelDepotPasswordGenerator(
                testFile.toAbsolutePath().toString());

        assertEquals(passwordGenerator.checkPasswordCriteria().size(), 1);
    }

}
