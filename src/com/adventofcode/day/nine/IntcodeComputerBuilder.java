package com.adventofcode.day.nine;

import com.adventofcode.day.two.IntcodeComputer;
import com.adventofcode.utils.ConfigReader;

import java.util.concurrent.BlockingQueue;

public class IntcodeComputerBuilder {

    private ConfigReader configReader;
    private Integer extendedMemory;
    private String inputDataPath;
    private BlockingQueue<Long> inputQueue;
    private BlockingQueue<Long> outputQueue;

    public IntcodeComputer build() {
        if (inputDataPath == null) {
            throw new RuntimeException(String.format(
                    "Cannot build %s, %s parameter is missing.",
                    IntcodeComputer.class.getSimpleName(),
                    "circuit"));
        }

        if (configReader == null) {
            throw new RuntimeException(String.format(
                    "Cannot build %s, %s parameter is missing.",
                    IntcodeComputer.class.getSimpleName(),
                    "configReader"));
        }

        return new IntcodeComputer(this);
    }

    public IntcodeComputerBuilder configReader(ConfigReader configReader) {
        this.configReader = configReader;
        return this;
    }

    public ConfigReader configReader() {
        return configReader;
    }

    public Integer extendedMemorySize() {
        return extendedMemory;
    }

    public IntcodeComputerBuilder extendedMemorySize(Integer extendedMemorySize) {
        this.extendedMemory = extendedMemorySize;
        return this;
    }

    public IntcodeComputerBuilder inputDataPath(String inputDataPath) {
        this.inputDataPath = inputDataPath;
        return this;
    }

    public String inputDataPath() {
        return inputDataPath;
    }

    public IntcodeComputerBuilder inputQueue(BlockingQueue<Long> inputQueue) {
        this.inputQueue = inputQueue;
        return this;
    }

    public BlockingQueue<Long> inputQueue() {
        return inputQueue;
    }

    public IntcodeComputerBuilder outputQueue(BlockingQueue<Long> outputQueue) {
        this.outputQueue = outputQueue;
        return this;
    }

    public BlockingQueue<Long> outputQueue() {
        return outputQueue;
    }

}
