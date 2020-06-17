package opcode;

import com.adventofcode.day.five.ResultWithAddress;
import com.adventofcode.day.two.MultiplicationOpcode;
import com.adventofcode.day.two.Opcode;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MultiplicationOpcodeTest {

    @Test
    void testMultiply() {
        Opcode multiply = MultiplicationOpcode.get();
        Optional<ResultWithAddress> result = multiply.operation(2, 4, 10);

        assertTrue(true);
        assertEquals(result.get(), ResultWithAddress.builder()
                .instructionPointerCount(4)
                .memoryAddress(10)
                .result(8L)
                .build());
    }

    @Test
    void testMultiplyOpcodeValue() {
        Opcode multiply = MultiplicationOpcode.get();
        assertEquals(multiply.value(), 2);
    }

}
