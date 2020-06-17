package com.adventofcode.day.two;

import com.adventofcode.day.five.ResultWithAddress;

import java.util.Optional;

public class HaltOpcode implements Opcode {

    private static final Opcode instance = new HaltOpcode();

    private HaltOpcode() {

    }

    @Override
    public Optional<ResultWithAddress> operation(long firstParameter, long secondParameter, int address) {
        return Optional.empty();
    }

    public static Opcode get() {
        return instance;
    }

    @Override
    public int value() {
        return 99;
    }

    @Override
    public int instructionPointerIncrementCount() {
        return 1;
    }


}
