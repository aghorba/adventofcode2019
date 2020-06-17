import com.adventofcode.day.one.FuelCounterUpper;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FuelCounterUpperTest {


    @Test
    void testFuelCount() throws Exception {
        Path testFile = Paths.get("test_fuel_count_" + UUID.randomUUID());

        Files.write(testFile, List.of("1969", "100756"), StandardCharsets.UTF_8);
        FuelCounterUpper fuelCounterUpper = new FuelCounterUpper(testFile.toAbsolutePath().toString());

        assertEquals(fuelCounterUpper.totalFuelRequirement(), 51_312);
        Files.delete(testFile);
    }
}
