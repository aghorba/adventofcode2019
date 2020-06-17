package opcode;

import com.adventofcode.day.five.EqualsOpcode;
import com.adventofcode.day.five.LessThanOpcode;
import com.adventofcode.day.five.ResultWithAddress;
import com.adventofcode.day.two.Opcode;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LessThanOpcodeTest {

    @Test
    void testLessThanIsTrue() {
        Opcode lessThan = LessThanOpcode.get();
        Optional<ResultWithAddress> result = lessThan.operation(30, 99, 1);
        assertTrue(result.isPresent());
        assertEquals(result.get(), ResultWithAddress.builder()
                .instructionPointerCount(4)
                .memoryAddress(1)
                .result(1L)
                .build());
    }

    @Test
    void testLessThanIsFalse() {
        Opcode lessThan = LessThanOpcode.get();
        Optional<ResultWithAddress> result = lessThan.operation(99, 30, 1);
        assertTrue(result.isPresent());
        assertEquals(result.get(), ResultWithAddress.builder()
                .instructionPointerCount(4)
                .memoryAddress(1)
                .result(0L)
                .build());
    }

    @Test
    void testLessThanIsFalseWhenParametersEqual() {
        Opcode lessThan = LessThanOpcode.get();
        Optional<ResultWithAddress> result = lessThan.operation(30, 30, 1);
        assertTrue(result.isPresent());
        assertEquals(result.get(), ResultWithAddress.builder()
                .instructionPointerCount(4)
                .memoryAddress(1)
                .result(0L)
                .build());
    }

    @Test
    void testLessThanOpcodeValue() {
        Opcode lessThan = LessThanOpcode.get();
        assertEquals(lessThan.value(), 7);
    }

}
