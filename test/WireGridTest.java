import com.adventofcode.day.three.WireGrid;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WireGridTest {

    @Test
    void testGetDistanceOne() throws IOException {
        Path testFile = Paths.get("test_wire_grid_" + UUID.randomUUID());
        Files.write(testFile, List.of(
                "R8,U5,L5,D3",
                "U7,R6,D4,L4"
        ));

        int distance = new WireGrid(testFile.toAbsolutePath().toString()).getManhattanDistance();
        assertEquals(distance, 6);

        Files.delete(testFile);
    }

    @Test
    void testGetDistanceTwo() throws IOException {
        Path testFile = Paths.get("test_wire_grid_" + UUID.randomUUID());
        Files.write(testFile, List.of(
                "R75,D30,R83,U83,L12,D49,R71,U7,L72",
                "U62,R66,U55,R34,D71,R55,D58,R83"
        ));

        int distance = new WireGrid(testFile.toAbsolutePath().toString()).getManhattanDistance();
        assertEquals(distance, 159);

        Files.delete(testFile);
    }

    @Test
    void testGetDistanceThree() throws IOException {
        Path testFile = Paths.get("test_wire_grid_" + UUID.randomUUID());
        Files.write(testFile, List.of(
                "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51",
                "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
        ));

        int distance = new WireGrid(testFile.toAbsolutePath().toString()).getManhattanDistance();
        assertEquals(distance, 135);

        Files.delete(testFile);
    }

    @Test
    void testGetStepCountOne() throws IOException {
        Path testFile = Paths.get("test_wire_grid_" + UUID.randomUUID());
        Files.write(testFile, List.of(
                "R75,D30,R83,U83,L12,D49,R71,U7,L72",
                "U62,R66,U55,R34,D71,R55,D58,R83"
        ));

        int stepCount = new WireGrid(testFile.toAbsolutePath().toString()).getLowestStepDistance();
        assertEquals(stepCount, 610);

        Files.delete(testFile);
    }

    @Test
    void testGetStepCountTwo() throws IOException {
        Path testFile = Paths.get("test_wire_grid_" + UUID.randomUUID());
        Files.write(testFile, List.of(
                "R98,U47,R26,D63,R33,U87,L62,D20,R33,U53,R51",
                "U98,R91,D20,R16,D67,R40,U7,R15,U6,R7"
        ));

        int stepCount = new WireGrid(testFile.toAbsolutePath().toString()).getLowestStepDistance();
        assertEquals(stepCount, 410);

        Files.delete(testFile);
    }
}
