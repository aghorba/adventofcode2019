package opcode;

import com.adventofcode.day.five.ResultWithAddress;
import com.adventofcode.day.two.AdditionOpcode;
import com.adventofcode.day.two.Opcode;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AdditionOpcodeTest {

    @Test
    void testAddition() {
        Opcode addition = AdditionOpcode.get();
        Optional<ResultWithAddress> result = addition.operation(2, 4, 10);

        assertTrue(result.isPresent());
        assertEquals(result.get(), ResultWithAddress.builder()
                .instructionPointerCount(4)
                .memoryAddress(10)
                .result(6L)
                .build());
    }

    @Test
    void testAdditionOpcodeValue() {
        Opcode addition = AdditionOpcode.get();
        assertEquals(addition.value(), 1);
    }

}
