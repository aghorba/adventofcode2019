package opcode;

import com.adventofcode.day.five.OutputOpcode;
import com.adventofcode.day.two.Opcode;
import com.adventofcode.utils.ConfigReader;
import com.adventofcode.utils.TestHelper;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputOpcodeTest extends TestHelper {

    @Test
    void testOutputValue() {
        Opcode output = new OutputOpcode(new LinkedBlockingQueue<>(2));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream current = new PrintStream(byteArrayOutputStream);
        PrintStream old = System.out;

        try {
            System.setOut(current);
            output.operation(100, 0, 0);
        } finally {
            System.out.flush();
            System.setOut(old);
        }

        assertEquals(byteArrayOutputStream.toString(), "OUTPUT: 100\r\n");
    }

    @Test
    void testOutputOpcodeValue() {
        Opcode output = new OutputOpcode(new LinkedBlockingQueue<>(2));
        assertEquals(output.value(), 4);
    }

}
