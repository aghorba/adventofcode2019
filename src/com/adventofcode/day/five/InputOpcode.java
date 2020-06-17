package com.adventofcode.day.five;

import com.adventofcode.day.two.Opcode;
import com.adventofcode.utils.ConfigReader;

import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Takes a single integer as input and saves it to the position given by its only parameter.
 * For example, the instruction 3,50 would take an input value and store it at address 50
 */
public class InputOpcode implements Opcode {

    private final ConfigReader _config;
    private final BlockingQueue<Long> _queue;

    public InputOpcode(BlockingQueue<Long> queue, ConfigReader config) {
        _config = config;
        _queue = queue;
    }

    @Override
    public Optional<ResultWithAddress> operation(long firstParameter, long secondParameter, int address) {
        try {
            Long input = readInputValue();

            if(input == null) {
                throw new InterruptedException("Timed out waiting to get input. Timeout set to " + _config.inputQueueTimeout());
            }

            return Optional.of(ResultWithAddress.builder()
                    .result(input)
                    .memoryAddress(address)
                    .instructionPointerCount(instructionPointerIncrementCount())
                    .build());
        } catch(InterruptedException e) {
            throw new RuntimeException("Error receiving input from input source: " + e.getMessage());
        }
    }

    @Override
    public int value() {
        return 3;
    }

    @Override
    public int instructionPointerIncrementCount() {
        return 2;
    }


    private Long readInputValue() throws InterruptedException {
//        return _queue.take();
        return _queue.poll(_config.inputQueueTimeout().toMillis(), TimeUnit.MILLISECONDS);
    }

}
