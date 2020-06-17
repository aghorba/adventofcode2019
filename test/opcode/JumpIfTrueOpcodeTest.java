package opcode;

import com.adventofcode.day.five.JumpIfFalseOpcode;
import com.adventofcode.day.five.JumpIfTrueOpcode;
import com.adventofcode.day.two.Opcode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class JumpIfTrueOpcodeTest {

    @Test
    void testJumpWhenTrue() {
        JumpIfTrueOpcode jumpIfTrue = (JumpIfTrueOpcode) JumpIfTrueOpcode.get();
        jumpIfTrue.operation(30, 25, 0);

        assertNotNull(jumpIfTrue.jumpToInstruction());
        assertEquals(jumpIfTrue.jumpToInstruction(), 25);
    }

    @Test
    void testDoNotJump() {
        JumpIfTrueOpcode jumpIfTrue = (JumpIfTrueOpcode) JumpIfTrueOpcode.get();
        jumpIfTrue.operation(0, 25, 0);

        assertNull(jumpIfTrue.jumpToInstruction());
    }

    @Test
    void testJumpIfTrueOpcodeValue() {
        Opcode jumpIfTrue = JumpIfTrueOpcode.get();
        assertEquals(jumpIfTrue.value(), 5);
    }

}
