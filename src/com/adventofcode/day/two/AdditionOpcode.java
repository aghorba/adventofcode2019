package com.adventofcode.day.two;

import com.adventofcode.day.five.ResultWithAddress;

import java.util.Optional;

public class AdditionOpcode implements Opcode {

    private static final Opcode instance = new AdditionOpcode();

    private AdditionOpcode () {

    }

    @Override
    public Optional<ResultWithAddress> operation(long firstParameter, long secondParameter, int address) {
        return Optional.of(ResultWithAddress.builder()
                .result(firstParameter + secondParameter)
                .memoryAddress(address)
                .instructionPointerCount(instructionPointerIncrementCount())
                .build());
    }

    public static Opcode get() {
        return instance;
    }

    @Override
    public int value() {
        return 1;
    }

    @Override
    public int instructionPointerIncrementCount() {
        return 4;
    }

}
