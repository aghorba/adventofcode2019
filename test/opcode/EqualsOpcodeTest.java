package opcode;

import com.adventofcode.day.five.EqualsOpcode;
import com.adventofcode.day.five.ResultWithAddress;
import com.adventofcode.day.two.Opcode;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EqualsOpcodeTest {

    @Test
    void testEqualsIsTrue() {
        Opcode equals = EqualsOpcode.get();
        Optional<ResultWithAddress> result = equals.operation(99, 99, 1);
        assertTrue(result.isPresent());
        assertEquals(result.get(), ResultWithAddress.builder()
                .instructionPointerCount(4)
                .memoryAddress(1)
                .result(1L)
                .build());
    }

    @Test
    void testEqualsIsFalse() {
        Opcode equals = EqualsOpcode.get();
        Optional<ResultWithAddress> result = equals.operation(99, 30, 1);
        assertTrue(result.isPresent());
        assertEquals(result.get(), ResultWithAddress.builder()
                .instructionPointerCount(4)
                .memoryAddress(1)
                .result(0L)
                .build());
    }

    @Test
    void testEqualsOpcodeValue() {
        Opcode equals = EqualsOpcode.get();
        assertEquals(equals.value(), 8);
    }

}
