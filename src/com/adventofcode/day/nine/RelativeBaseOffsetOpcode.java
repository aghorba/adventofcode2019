package com.adventofcode.day.nine;

import com.adventofcode.day.five.ResultWithAddress;
import com.adventofcode.day.two.Opcode;

import java.util.Optional;

/**
 * Adjusts the relative base by the value of its only parameter.
 * The relative base increases (or decreases, if the value is negative) by the value of the parameter.
 */
public class RelativeBaseOffsetOpcode implements Opcode {

    private static final Opcode instance = new RelativeBaseOffsetOpcode();

    private RelativeBaseOffsetOpcode() {

    }

    @Override
    public Optional<ResultWithAddress> operation(long firstParameter, long secondParameter, int address) {
        return Optional.of(ResultWithAddress.builder()
                .instructionPointerCount(instructionPointerIncrementCount())
                .memoryAddress(address)
                .result(secondParameter + firstParameter)
                .build());
    }

    public static Opcode get() {
        return instance;
    }

    @Override
    public int instructionPointerIncrementCount() {
        return 2;
    }

    @Override
    public int value() {
        return 9;
    }

}
