import com.adventofcode.day.two.IntcodeComputer;
import com.adventofcode.utils.ConfigReader;
import com.adventofcode.utils.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntcodeComputerTest extends TestHelper {

    private BlockingQueue<Long> computerInputOpOverride(String inputValue) throws InterruptedException {
        BlockingQueue<Long> inputQueue = new LinkedBlockingQueue<>(2);
        inputQueue.put(Long.valueOf(inputValue));

        return inputQueue;
    }

    private IntcodeComputer initiateComputer(List<String> puzzleInput) throws IOException {
        return initiateComputer(puzzleInput, null);
    }

    private IntcodeComputer initiateComputer(List<String> puzzleInput, Integer extendedMemory) throws IOException {
        Path testFile = Paths.get("test_computer_" + UUID.randomUUID());
        currentTestFile = testFile;

        Files.write(testFile, puzzleInput);

        IntcodeComputer computer = IntcodeComputer.builder()
                .configReader(new ConfigReader("resources\\config.properties"))
                .extendedMemorySize(extendedMemory)
                .inputDataPath(testFile.toAbsolutePath().toString())
                .build();
        computer.initiateComputer();

        return computer;
    }

    private IntcodeComputer initiateComputerWithConfig(
            List<String> puzzleInput,
            ConfigReader config,
            BlockingQueue<Long> inputQueue,
            BlockingQueue<Long> outputQueue) throws IOException {
        return initiateComputerWithConfigWithExtendedMemory(
                puzzleInput,
                config,
                inputQueue,
                outputQueue,
                null);
    }

    private IntcodeComputer initiateComputerWithConfigOverrides(
            List<String> puzzleInput,
            Map<String, String> configOverrides,
            BlockingQueue<Long> inputQueue,
            BlockingQueue<Long> outputQueue) throws IOException {
        return initiateComputerWithConfigOverridesWithExtendedMemory(
                puzzleInput,
                configOverrides,
                inputQueue,
                outputQueue,
                null);
    }

    private IntcodeComputer initiateComputerWithConfigWithExtendedMemory(
            List<String> puzzleInput,
            ConfigReader config,
            BlockingQueue<Long> inputQueue,
            BlockingQueue<Long> outputQueue,
            Integer extendedMemory) throws IOException {

        Path testFile = Paths.get("test_computer_" + UUID.randomUUID());
        currentTestFile = testFile;

        Files.write(testFile, puzzleInput);

        IntcodeComputer computer = IntcodeComputer.builder()
                .inputDataPath(testFile.toAbsolutePath().toString())
                .configReader(config)
                .extendedMemorySize(extendedMemory)
                .inputQueue(inputQueue)
                .outputQueue(outputQueue)
                .build();
        computer.initiateComputer();

        return computer;
    }

    private IntcodeComputer initiateComputerWithConfigOverridesWithExtendedMemory(
            List<String> puzzleInput,
            Map<String, String> configOverrides,
            BlockingQueue<Long> inputQueue,
            BlockingQueue<Long> outputQueue,
            Integer extendedMemory) throws IOException {

        Path testFile = Paths.get("test_computer_" + UUID.randomUUID());
        currentTestFile = testFile;

        Files.write(testFile, puzzleInput);

        IntcodeComputer computer = IntcodeComputer.builder()
                .inputDataPath(testFile.toAbsolutePath().toString())
                .configReader(new ConfigReader(configOverrides))
                .extendedMemorySize(extendedMemory)
                .inputQueue(inputQueue)
                .outputQueue(outputQueue)
                .build();
        computer.initiateComputer();

        return computer;
    }

    private String initiateComputerWithRedirectOutput(List<String> puzzleInput) throws IOException {
        return initiateComputerWithConfigRedirectOutput(puzzleInput,
                new ConfigReader("resources\\config.properties"),
                new LinkedBlockingQueue<>(),
                new LinkedBlockingQueue<>());
    }

    private String initiateComputerWithRedirectOutput(List<String> puzzleInput, Integer extendedMemory) throws IOException {
        return initiateComputerWithConfigRedirectOutputWithExtendedMemory(
                puzzleInput,
                new ConfigReader("resources\\config.properties"),
                new LinkedBlockingQueue<>(),
                new LinkedBlockingQueue<>(),
                extendedMemory);
    }

    private String initiateComputerWithConfigRedirectOutput(
            List<String> puzzleInput,
            ConfigReader config,
            BlockingQueue<Long> inputQueue,
            BlockingQueue<Long> outputQueue) throws IOException {
        return initiateComputerWithConfigRedirectOutputWithExtendedMemory(
                puzzleInput,
                config,
                inputQueue,
                outputQueue,
                null);
    }

    private String initiateComputerWithConfigRedirectOutputWithExtendedMemory(
            List<String> puzzleInput,
            ConfigReader config,
            BlockingQueue<Long> inputQueue,
            BlockingQueue<Long> outputQueue,
            Integer extendedMemory) throws IOException {

        Path testFile = Paths.get("test_computer_" + UUID.randomUUID());
        currentTestFile = testFile;

        Files.write(testFile, puzzleInput);

        IntcodeComputer computer = IntcodeComputer.builder()
                .inputDataPath(testFile.toAbsolutePath().toString())
                .configReader(config)
                .extendedMemorySize(extendedMemory)
                .inputQueue(inputQueue)
                .outputQueue(outputQueue)
                .build();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;

        try {
            System.setOut(ps);
            computer.initiateComputer();
        } finally {
            System.out.flush();
            System.setOut(old);
        }

        return baos.toString();
    }

    private String initiateComputerWithConfigOverridesRedirectOutput(
            List<String> puzzleInput,
            Map<String, String> configOverrides,
            BlockingQueue<Long> inputQueue,
            BlockingQueue<Long> outputQueue) throws IOException {

        Path testFile = Paths.get("test_computer_" + UUID.randomUUID());
        currentTestFile = testFile;

        Files.write(testFile, puzzleInput);

        IntcodeComputer computer = IntcodeComputer.builder()
                .inputDataPath(testFile.toAbsolutePath().toString())
                .configReader(new ConfigReader(configOverrides))
                .inputQueue(inputQueue)
                .outputQueue(outputQueue)
                .build();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(baos);
        PrintStream old = System.out;

        try {
            System.setOut(ps);
            computer.initiateComputer();
        } finally {
            System.out.flush();
            System.setOut(old);
        }

        return baos.toString();
    }

    @Test
    void testComputer1() throws IOException {
        IntcodeComputer computer = initiateComputer(List.of("1", "0", "0", "0", "99"));
        assertEquals(computer.getMemory(), List.of("2", "0", "0", "0", "99"));
    }

    @Test
    void testComputer2() throws IOException {
        IntcodeComputer computer = initiateComputer(List.of("2", "3", "0", "3", "99"));
        assertEquals(computer.getMemory(), List.of("2", "3", "0", "6", "99"));
    }

    @Test
    void testComputer3() throws IOException {
        IntcodeComputer computer = initiateComputer(List.of("2", "4", "4", "5", "99", "0"));
        assertEquals(computer.getMemory(), List.of("2", "4", "4", "5", "99", "9801"));
    }

    @Test
    void testComputer4() throws IOException {
        IntcodeComputer computer = initiateComputer(List.of("1", "1", "1", "4", "99", "5", "6", "0", "99"));
        assertEquals(computer.getMemory(), List.of("30", "1", "1", "4", "2", "5", "6", "0", "99"));
    }

    @Test
    void testComputerOverride() throws IOException {
        IntcodeComputer computer = IntcodeComputer.builder()
                .inputDataPath("resources\\puzzle2_input.txt")
                .configReader(new ConfigReader("resources\\config.properties"))
                .build();
        computer.initiateComputer();

        assertEquals(computer.getMemory().get(0), "1028301");

        computer.resetMemory();
        computer.setOverrides(new int[]{12, 2});
        computer.initiateComputer();

        assertEquals(computer.getMemory().get(0), "6627023");
    }

    @Test
    void testInputCode() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("1");
        IntcodeComputer computer = initiateComputerWithConfig(
                List.of("3,0,99"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(computer.getMemory(), List.of("1", "0", "99"));
    }

    @Test
    void testOutputCode() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("5");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("4,2,99"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 99\r\n");
    }

    @Test
    void testInputOutputCodes() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("1");
        IntcodeComputer computer = initiateComputerWithConfig(
                List.of("3,0,4,0,99"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(computer.getMemory(), List.of("1", "0", "4", "0", "99"));
    }

    @Test
    void testParameterModeMultiply() throws IOException {
        IntcodeComputer computer = initiateComputer(List.of("1002,4,3,4,33"));
        assertEquals(computer.getMemory(), List.of("1002", "4", "3", "4", "99"));
    }

    @Test
    void testParameterModeAdd() throws IOException {
        IntcodeComputer computer = initiateComputer(List.of("1101,100,-1,4,0"));
        assertEquals(computer.getMemory(), List.of("1101", "100", "-1", "4", "99"));
    }

    @Test
    void testPositionModeEqualsOpOne() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("8");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,9,8,9,10,9,4,9,99,-1,8"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 1\r\n");
    }

    @Test
    void testPositionModeEqualsOpTwo() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("999");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,9,8,9,10,9,4,9,99,-1,8"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 0\r\n");
    }

    @Test
    void testImmediateModeEqualsOpOne() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("8");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,3,1108,-1,8,3,4,3,99"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 1\r\n");
    }

    @Test
    void testImmediateModeEqualsOpTwo() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("999");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,3,1108,-1,8,3,4,3,99"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 0\r\n");
    }

    @Test
    void testPositionModeLessThanOpOne() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("1");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,9,7,9,10,9,4,9,99,-1,8"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 1\r\n");
    }

    @Test
    void testPositionModeLessThanOpTwo() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("999");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,9,7,9,10,9,4,9,99,-1,8"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 0\r\n");
    }

    @Test
    void testImmediateModeLessThanOpOne() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("1");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,3,1107,-1,8,3,4,3,99"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 1\r\n");
    }

    @Test
    void testImmediateModeLessThanOpTwo() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("999");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,3,1107,-1,8,3,4,3,99"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 0\r\n");
    }

    @Test
    void testPositionModeJumpOpOne() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("0");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 0\r\n");
    }

    @Test
    void testPositionModeJumpOpTwo() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("999");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,12,6,12,15,1,13,14,13,4,13,99,-1,0,1,9"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 1\r\n");
    }


    @Test
    void testImmediateModeJumpOpOne() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("0");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,3,1105,-1,9,1101,0,0,12,4,12,99,1"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 0\r\n");
    }

    @Test
    void testImmediateModeJumpOpTwo() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("999");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,3,1105,-1,9,1101,0,0,12,4,12,99,1"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 1\r\n");
    }

    @Test
    void testDayFiveIntegrationTestOne() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("1");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                        "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999," +
                        "1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 999\r\n");
    }

    @Test
    void testDayFiveIntegrationTestTwo() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("8");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                        "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999," +
                        "1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 1000\r\n");
    }

    @Test
    void testDayFiveIntegrationTestThree() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("999");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,21,1008,21,8,20,1005,20,22,107,8,21,20,1006,20,31," +
                        "1106,0,36,98,0,0,1002,21,125,20,4,20,1105,1,46,104,999," +
                        "1105,1,46,1101,1000,1,20,4,20,1105,1,46,98,99"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 1001\r\n");
    }

    @Test
    void testDay5Part2() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("5");
        String output = initiateComputerWithConfigRedirectOutput(
                List.of("3,225,1,225,6,6,1100,1,238,225,104,0,1,192,154,224,101,-161,224,224," +
                        "4,224,102,8,223,223,101,5,224,224,1,223,224,223," +
                        "1001,157,48,224,1001,224,-61,224,4,224,102,8,223,223," +
                        "101,2,224,224,1,223,224,223,1102,15,28,225," +
                        "1002,162,75,224,1001,224,-600,224,4,224," +
                        "1002,223,8,223,1001,224,1,224,1,224,223,223,102,32,57,224," +
                        "1001,224,-480,224,4,224,102,8,223,223,101,1,224,224,1,224,223,223," +
                        "1101,6,23,225,1102,15,70,224,1001,224,-1050,224,4,224," +
                        "1002,223,8,223,101,5,224,224,1,224,223,223,101,53,196,224," +
                        "1001,224,-63,224,4,224,102,8,223,223,1001,224,3,224,1,224,223,223," +
                        "1101,64,94,225,1102,13,23,225,1101,41,8,225,2,105,187,224," +
                        "1001,224,-60,224,4,224,1002,223,8,223,101,6,224,224,1,224,223,223," +
                        "1101,10,23,225,1101,16,67,225,1101,58,10,225,1101,25,34,224," +
                        "1001,224,-59,224,4,224,1002,223,8,223,1001,224,3,224,1,223,224,223," +
                        "4,223,99,0,0,0,677,0,0,0,0,0,0,0,0,0,0,0,1105,0,99999,1105,227,247," +
                        "1105,1,99999,1005,227,99999,1005,0,256,1105,1,99999,1106,227,99999," +
                        "1106,0,265,1105,1,99999,1006,0,99999,1006,227,274,1105,1,99999," +
                        "1105,1,280,1105,1,99999,1,225,225,225,1101,294,0,0,105,1,0," +
                        "1105,1,99999,1106,0,300,1105,1,99999,1,225,225,225,1101,314,0,0,106,0,0," +
                        "1105,1,99999,1108,226,226,224,102,2,223,223,1005,224,329,101,1,223,223," +
                        "107,226,226,224,1002,223,2,223,1005,224,344,1001,223,1,223," +
                        "107,677,226,224,102,2,223,223,1005,224,359,101,1,223,223,7,677,226,224," +
                        "102,2,223,223,1005,224,374,101,1,223,223,108,226,226,224,102,2,223,223," +
                        "1006,224,389,101,1,223,223,1007,677,677,224,102,2,223,223,1005,224,404," +
                        "101,1,223,223,7,226,677,224,102,2,223,223,1006,224,419,101,1,223,223," +
                        "1107,226,677,224,1002,223,2,223,1005,224,434,1001,223,1,223," +
                        "1108,226,677,224,102,2,223,223,1005,224,449,101,1,223,223," +
                        "108,226,677,224,102,2,223,223,1005,224,464,1001,223,1,223,8,226,677,224," +
                        "1002,223,2,223,1005,224,479,1001,223,1,223,1007,226,226,224,102,2,223,223," +
                        "1006,224,494,101,1,223,223,1008,226,677,224,102,2,223,223,1006,224,509," +
                        "101,1,223,223,1107,677,226,224,1002,223,2,223,1006,224,524,1001,223,1,223," +
                        "108,677,677,224,1002,223,2,223,1005,224,539,1001,223,1,223,1107,226,226,224," +
                        "1002,223,2,223,1006,224,554,1001,223,1,223,7,226,226,224,1002,223,2,223," +
                        "1006,224,569,1001,223,1,223,8,677,226,224,102,2,223,223,1006,224,584," +
                        "101,1,223,223,1008,677,677,224,102,2,223,223,1005,224,599,101,1,223,223," +
                        "1007,226,677,224,1002,223,2,223,1006,224,614,1001,223,1,223,8,677,677,224," +
                        "1002,223,2,223,1005,224,629,101,1,223,223,107,677,677,224,102,2,223,223," +
                        "1005,224,644,101,1,223,223,1108,677,226,224,102,2,223,223,1005,224,659," +
                        "101,1,223,223,1008,226,226,224,102,2,223,223,1006,224,674,1001,223,1,223,4,223,99,226"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue);
        assertEquals(output, "OUTPUT: 2140710\r\n");
    }

    @Test
    void testRelativeModeOne() throws IOException {
        int extendedMemorySize = 128;
        String output = initiateComputerWithConfigRedirectOutputWithExtendedMemory(
                List.of("109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99"),
                new ConfigReader("resources\\config.properties"),
                new LinkedBlockingQueue<>(),
                new LinkedBlockingQueue<>(),
                extendedMemorySize);

        String result = String.join(",", Arrays.stream(output.split("OUTPUT: "))
                .filter(splitResult -> !splitResult.equals(""))
                .map(outputResult -> outputResult.trim())
                .collect(Collectors.toList()));
        assertEquals(result, "109,1,204,-1,1001,100,1,100,1008,100,16,101,1006,101,0,99");
    }

    @Test
    void testRelativeModeTwo() throws IOException {
        String output = initiateComputerWithRedirectOutput(List.of("1102,34915192,34915192,7,4,7,99,0"));
        assertEquals(output, "OUTPUT: 1219070632396864\r\n");
    }

    @Test
    void testRelativeModeThree() throws IOException {
        String output = initiateComputerWithRedirectOutput(List.of("104,1125899906842624,99"));
        assertEquals(output, "OUTPUT: 1125899906842624\r\n");
    }

    @Test
    void testComputerWithExtraMemory() throws IOException {
        IntcodeComputer computer = initiateComputer(List.of("2,3,0,3,99"), 512);
        assertEquals(computer.getMemory().size(), 512);
    }

    @Test
    void testComputerNoExtraMemory() throws IOException {
        IntcodeComputer computer = initiateComputer(List.of("2,3,0,3,99"));
        assertEquals(computer.getMemory().size(), 5);
    }

    @Test
    void testRelativeModeFour() throws IOException {
        String output = initiateComputerWithRedirectOutput(List.of("109,-1,4,1,99"));
        assertEquals(output, "OUTPUT: -1\r\n");
    }

    @Test
    void testRelativeModeFive() throws IOException {
        String output = initiateComputerWithRedirectOutput(List.of("109,-1,104,1,99"));
        assertEquals(output, "OUTPUT: 1\r\n");
    }

    @Test
    void testRelativeModeSix() throws IOException {
        String output = initiateComputerWithRedirectOutput(List.of("109,-1,204,1,99"));
        assertEquals(output, "OUTPUT: 109\r\n");
    }

    @Test
    void testRelativeModeSeven() throws IOException {
        String output = initiateComputerWithRedirectOutput(List.of("109,1,9,2,204,-6,99"), 32);
        assertEquals(output, "OUTPUT: 204\r\n");
    }

    @Test
    void testRelativeModeEight() throws IOException {
        String output = initiateComputerWithRedirectOutput(List.of("109,1,109,9,204,-6,99"), 32);
        assertEquals(output, "OUTPUT: 204\r\n");
    }

    @Test
    void testRelativeModeNine() throws IOException {
        String output = initiateComputerWithRedirectOutput(List.of("109,1,209,-1,204,-106,99"), 128);
        assertEquals(output, "OUTPUT: 204\r\n");
    }

    @Test
    void testRelativeModeTen() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("1");
        String output = initiateComputerWithConfigRedirectOutputWithExtendedMemory(
                List.of("109,1,3,3,204,2,99"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue,
                32);
        assertEquals(output, "OUTPUT: 1\r\n");
    }

    @Test
    void testRelativeModeEleven() throws IOException, InterruptedException {
        BlockingQueue<Long> queue = computerInputOpOverride("1");
        String output = initiateComputerWithConfigRedirectOutputWithExtendedMemory(
                List.of("109,1,203,2,204,2,99"),
                new ConfigReader("resources\\config.properties"),
                queue,
                queue,
                32);
        assertEquals(output, "OUTPUT: 1\r\n");
    }

}
