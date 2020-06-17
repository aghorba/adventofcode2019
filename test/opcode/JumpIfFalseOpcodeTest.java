package opcode;

import com.adventofcode.day.five.JumpIfFalseOpcode;
import com.adventofcode.day.two.Opcode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class JumpIfFalseOpcodeTest {

    @Test
    void testJumpWhenFalse() {
        JumpIfFalseOpcode jumpIfFalse = (JumpIfFalseOpcode) JumpIfFalseOpcode.get();
        jumpIfFalse.operation(0, 25, 0);

        assertNotNull(jumpIfFalse.jumpToInstruction());
        assertEquals(jumpIfFalse.jumpToInstruction(), 25);
    }

    @Test
    void testDoNotJump() {
        JumpIfFalseOpcode jumpIfFalse = (JumpIfFalseOpcode) JumpIfFalseOpcode.get();
        jumpIfFalse.operation(99, 25, 0);

        assertNull(jumpIfFalse.jumpToInstruction());
    }

    @Test
    void testJumpIfFalseOpcodeValue() {
        Opcode jumpIfFalse = JumpIfFalseOpcode.get();
        assertEquals(jumpIfFalse.value(), 6);
    }

}
