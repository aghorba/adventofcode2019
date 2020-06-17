package com.adventofcode.day.two;

import com.adventofcode.day.five.ResultWithAddress;

import java.util.Optional;

public interface Opcode {

    Optional<ResultWithAddress> operation(long firstParameter, long secondParameter, int address);

    default int instructionPointerIncrementCount() {
        return 0;
    }

    int value();

}
