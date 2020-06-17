import com.adventofcode.day.seven.AmplificationCircuit;
import com.adventofcode.utils.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AmplificationCircuitTest extends TestHelper {

    @Test
    void testPhaseSequenceOne() throws IOException, InterruptedException {
        Path testFile = createTestFile(
                "test_amplification_circuit",
                List.of("3,15,3,16,1002,16,10,16,1,16,15,15,4,15,99,0,0"));
        currentTestFile = testFile;

        AmplificationCircuit circuit = new AmplificationCircuit(
                testFile.toAbsolutePath().toString(),
                List.of(4, 3, 2, 1, 0),
                false);

        assertEquals(circuit.getMaxThrusterSignal(), 43210);
    }

    @Test
    void testPhaseSequenceTwo() throws IOException, InterruptedException {
        Path testFile = createTestFile(
                "test_amplification_circuit",
                List.of("3,23,3,24,1002,24,10,24,1002,23,-1,23,101,5,23,23,1,24,23,23,4,23,99,0,0"));
        currentTestFile = testFile;

        AmplificationCircuit circuit = new AmplificationCircuit(
                testFile.toAbsolutePath().toString(),
                List.of(0, 1, 2, 3, 4)
                , false);

        assertEquals(circuit.getMaxThrusterSignal(), 54321);
    }

    @Test
    void testPhaseSequenceThree() throws IOException, InterruptedException {
        Path testFile = createTestFile(
                "test_amplification_circuit",
                List.of("3,31,3,32,1002,32,10,32,1001,31,-2,31,1007,31,0,33," +
                        "1002,33,7,33,1,33,31,31,1,32,31,31,4,31,99,0,0,0"));
        currentTestFile = testFile;

        AmplificationCircuit circuit = new AmplificationCircuit(
                testFile.toAbsolutePath().toString(),
                List.of(1, 0, 4, 3, 2),
                false);

        assertEquals(circuit.getMaxThrusterSignal(), 65210);
    }

    @Test
    void testPhaseSequenceDay7Part1() throws IOException, InterruptedException {
        Path testFile = createTestFile(
                "test_amplification_circuit",
                List.of("3,8,1001,8,10,8,105,1,0,0,21,34,51,64,73,98,179,260,341,422,99999," +
                        "3,9,102,4,9,9,1001,9,4,9,4,9,99,3,9,1001,9,4,9,1002,9,3,9," +
                        "1001,9,5,9,4,9,99,3,9,101,5,9,9,102,5,9,9,4,9,99,3,9,101,5,9,9,4,9,99,3,9," +
                        "1002,9,5,9,1001,9,3,9,102,2,9,9,101,5,9,9,1002,9,2,9,4,9,99,3,9," +
                        "1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9," +
                        "1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9," +
                        "101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9," +
                        "101,2,9,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9," +
                        "102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9," +
                        "101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9," +
                        "1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,101,2,9,9,4,9,3,9," +
                        "102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9," +
                        "1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9," +
                        "102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,99,3,9," +
                        "1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9," +
                        "1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9," +
                        "102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9," +
                        "1001,9,2,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9," +
                        "1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9," +
                        "101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9," +
                        "1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,99"));
        currentTestFile = testFile;

        AmplificationCircuit circuit = new AmplificationCircuit(
                testFile.toAbsolutePath().toString(),
                List.of(3, 1, 2, 0, 4),
                false);

        assertEquals(circuit.getMaxThrusterSignal(), 14902);
    }

    @Test
    void TestPhaseSequenceFeedbackModeOne() throws IOException, InterruptedException {
        Path testFile = createTestFile(
                "test_amplification_circuit",
                List.of("3,26,1001,26,-4,26,3,27,1002,27,2,27,1,27,26," +
                        "27,4,27,1001,28,-1,28,1005,28,6,99,0,0,5"));
        currentTestFile = testFile;

        AmplificationCircuit circuit = new AmplificationCircuit(
                testFile.toAbsolutePath().toString(),
                List.of(9, 8, 7, 6, 5),
                true);

        assertEquals(circuit.getMaxThrusterSignal(), 139629729);
    }

    @Test
    void TestPhaseSequenceFeedbackModeTwo() throws IOException, InterruptedException {
        Path testFile = createTestFile(
                "test_amplification_circuit",
                List.of("3,52,1001,52,-5,52,3,53,1,52,56,54,1007,54,5,55,1005,55,26,1001,54," +
                        "-5,54,1105,1,12,1,53,54,53,1008,54,0,55,1001,55,1,55,2,53,55,53,4," +
                        "53,1001,56,-1,56,1005,56,6,99,0,0,0,0,10"));
        currentTestFile = testFile;

        AmplificationCircuit circuit = new AmplificationCircuit(
                testFile.toAbsolutePath().toString(),
                List.of(9, 7, 8, 5, 6)
                , true);

        assertEquals(circuit.getMaxThrusterSignal(), 18216);
    }

    @Test
    void TestPhaseSequenceFeedbackModeDay7PartTwo() throws IOException, InterruptedException {
        Path testFile = createTestFile(
                "test_amplification_circuit",
                List.of("3,8,1001,8,10,8,105,1,0,0,21,34,51,64,73,98,179,260,341,422,99999," +
                        "3,9,102,4,9,9,1001,9,4,9,4,9,99,3,9,1001,9,4,9,1002,9,3,9," +
                        "1001,9,5,9,4,9,99,3,9,101,5,9,9,102,5,9,9,4,9,99,3,9,101,5,9,9,4,9,99,3,9," +
                        "1002,9,5,9,1001,9,3,9,102,2,9,9,101,5,9,9,1002,9,2,9,4,9,99,3,9," +
                        "1001,9,1,9,4,9,3,9,1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9," +
                        "1001,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9," +
                        "101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9," +
                        "101,2,9,9,4,9,99,3,9,101,1,9,9,4,9,3,9,1001,9,1,9,4,9,3,9," +
                        "102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9," +
                        "101,1,9,9,4,9,3,9,101,1,9,9,4,9,3,9,1002,9,2,9,4,9,3,9," +
                        "1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,99,3,9,101,2,9,9,4,9,3,9," +
                        "102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9," +
                        "1001,9,2,9,4,9,3,9,101,2,9,9,4,9,3,9,1001,9,1,9,4,9,3,9," +
                        "102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,99,3,9," +
                        "1002,9,2,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9," +
                        "1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1002,9,2,9,4,9,3,9," +
                        "102,2,9,9,4,9,3,9,102,2,9,9,4,9,3,9,101,2,9,9,4,9,3,9," +
                        "1001,9,2,9,4,9,99,3,9,1001,9,1,9,4,9,3,9,101,1,9,9,4,9,3,9," +
                        "1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,3,9,1001,9,1,9,4,9,3,9," +
                        "101,1,9,9,4,9,3,9,102,2,9,9,4,9,3,9,1001,9,2,9,4,9,3,9," +
                        "1002,9,2,9,4,9,3,9,1001,9,2,9,4,9,99"));
        addFileToRemove(testFile);

        AmplificationCircuit circuit = new AmplificationCircuit(
                testFile.toAbsolutePath().toString(),
                List.of(9, 6, 7, 5, 8)
                , true);

        assertEquals(circuit.getMaxThrusterSignal(), 6489132);
    }

}
