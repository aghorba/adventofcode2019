package com.adventofcode.day.five;

import com.adventofcode.day.two.Opcode;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;

/**
 * Outputs the value of its only parameter.
 * For example, the instruction {@code 4,50} would output the value at address 50.
 */
public class OutputOpcode implements Opcode {

    private final BlockingQueue<Long> _queue;

    public OutputOpcode(BlockingQueue<Long> queue) {
        _queue = queue;
    }

    @Override
    public Optional<ResultWithAddress> operation(long firstParameter, long secondParameter, int address) {
        System.out.println("OUTPUT: " + firstParameter);

        try {
            _queue.put(firstParameter);
        } catch (InterruptedException e) {
            throw new RuntimeException("Error adding output to output source: " + e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public int instructionPointerIncrementCount() {
        return 2;
    }

    @Override
    public int value() {
        return 4;
    }

}
