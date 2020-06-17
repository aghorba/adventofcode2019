package com.adventofcode.day.five;

import com.adventofcode.day.two.Opcode;
import com.adventofcode.day.five.ResultWithAddress.ResultWithAddressBuilder;

import java.util.Optional;

/**
 * If the first parameter is less than the second parameter,
 * it stores 1 in the position given by the third parameter.
 * Otherwise, it stores 0.
 */
public class LessThanOpcode implements Opcode, ComparisonOpcode {

    private static final Opcode instance = new LessThanOpcode();

    @Override
    public Optional<ResultWithAddress> operation(long firstParameter, long secondParameter, int address) {
        ResultWithAddressBuilder builder = ResultWithAddress.builder()
                .memoryAddress(address)
                .instructionPointerCount(instructionPointerIncrementCount());

        if (firstParameter < secondParameter) {
            builder.result(1L);
        }
        else {
            builder.result(0L);
        }

        return Optional.of(builder.build());
    }

    @Override
    public int instructionPointerIncrementCount() {
        return 4;
    }

    public static Opcode get() {
        return instance;
    }

    @Override
    public int value() {
        return 7;
    }

}
