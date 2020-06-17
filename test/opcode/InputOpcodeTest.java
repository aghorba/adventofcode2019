package opcode;

import com.adventofcode.day.five.InputOpcode;
import com.adventofcode.day.five.ResultWithAddress;
import com.adventofcode.day.two.Opcode;
import com.adventofcode.utils.ConfigReader;
import com.adventofcode.utils.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.*;

public class InputOpcodeTest extends TestHelper {

    private BlockingQueue<Long> initializeInputQueue(String inputValue) throws InterruptedException {
        BlockingQueue<Long> inputQueue = new LinkedBlockingQueue<>(1);
        inputQueue.put(Long.valueOf(inputValue));

        return inputQueue;
    }

    private ConfigReader getConfigReader() throws IOException {
        return new ConfigReader("resources\\config.properties");
    }

    @Test
    void testGetInputValue() throws InterruptedException, IOException {
        BlockingQueue<Long> inputQueue = initializeInputQueue("1");
        InputOpcode input = new InputOpcode(inputQueue, getConfigReader());
        Optional<ResultWithAddress> result = input.operation(0, 0, 0);

        assertTrue(result.isPresent());
        assertEquals(result.get(), ResultWithAddress.builder()
                .instructionPointerCount(2)
                .memoryAddress(0)
                .result(1L)
                .build());
    }

    @Test
    void testGetInputValueTimeOut() throws IOException {
        try {
            InputOpcode input = new InputOpcode(new LinkedBlockingQueue<>(1), getConfigReader());
            input.operation(0, 0, 0);
        } catch(RuntimeException e) {
            assertEquals(e.getMessage(), "Error receiving input from input source: Timed out waiting to get input. Timeout set to PT1S");
        }
    }

    @Test
    void testInputOpcodeValue() throws InterruptedException, IOException {
        BlockingQueue<Long> inputQueue = initializeInputQueue("1");
        Opcode input = new InputOpcode(inputQueue, getConfigReader());
        assertEquals(input.value(), 3);
    }

}
