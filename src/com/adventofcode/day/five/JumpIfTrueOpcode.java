package com.adventofcode.day.five;

import com.adventofcode.day.two.Opcode;

import java.util.Optional;

/**
 * If the first parameter is non-zero, it sets the instruction pointer to the value from the second parameter.
 * Otherwise, it does nothing.
 */

public class JumpIfTrueOpcode extends JumpOpcode implements Opcode {

    private static final Opcode instance = new JumpIfTrueOpcode();

    private JumpIfTrueOpcode() {

    }

    @Override
    public Optional<ResultWithAddress> operation(long firstParameter, long secondParameter, int address) {
        if (firstParameter != 0) {
            setInstructionToJumpTo((int) secondParameter);
        }
        else {
            setInstructionToJumpTo(null);
        }

        return Optional.empty();
    }

    public static Opcode get() {
        return instance;
    }

    @Override
    public int instructionPointerIncrementCount() {
        return 3;
    }

    @Override
    public int value() {
        return 5;
    }
}
