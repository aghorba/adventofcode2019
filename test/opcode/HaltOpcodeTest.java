package opcode;

import com.adventofcode.day.two.HaltOpcode;
import com.adventofcode.day.two.Opcode;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HaltOpcodeTest {

    @Test
    void testHalt() {
        Opcode halt = HaltOpcode.get();
        assertEquals(halt.operation(0, 0, 0), Optional.empty());
    }

    @Test
    void testHaltOpcodeValue() {
        Opcode halt = HaltOpcode.get();
        assertEquals(halt.value(), 99);
    }

}
