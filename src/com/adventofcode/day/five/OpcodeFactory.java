package com.adventofcode.day.five;

import com.adventofcode.day.nine.RelativeBaseOffsetOpcode;
import com.adventofcode.day.two.AdditionOpcode;
import com.adventofcode.day.two.HaltOpcode;
import com.adventofcode.day.two.MultiplicationOpcode;
import com.adventofcode.day.two.Opcode;
import com.adventofcode.utils.ConfigReader;

import java.util.concurrent.BlockingQueue;

public class OpcodeFactory {

    private OpcodeFactory() {

    }

    public static Opcode getInstance(
            Operation operation,
            ConfigReader config,
            BlockingQueue<Long> inputQueue,
            BlockingQueue<Long> outputQueue) {
        switch(operation) {
            case ADD:
                return AdditionOpcode.get();
            case MULTIPLY:
                return MultiplicationOpcode.get();
            case INPUT:
                return new InputOpcode(inputQueue, config);
            case OUTPUT:
                return new OutputOpcode(outputQueue);
            case JUMP_IF_TRUE:
                return JumpIfTrueOpcode.get();
            case JUMP_IF_FALSE:
                return JumpIfFalseOpcode.get();
            case LESS_THAN:
                return LessThanOpcode.get();
            case EQUALS:
                return EqualsOpcode.get();
            case RELATIVE_BASE_OFFSET:
                return RelativeBaseOffsetOpcode.get();
            case HALT:
                return HaltOpcode.get();
            default:
                throw new UnsupportedOperationException("Unsupported operation: " + operation);
        }
    }

}
