package com.adventofcode.day.two;

import com.adventofcode.day.five.*;
import com.adventofcode.day.nine.IntcodeComputerBuilder;
import com.adventofcode.day.nine.RelativeBaseOffsetOpcode;
import com.adventofcode.utils.ConfigReader;
import com.adventofcode.utils.PuzzleInputReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.stream.Collectors;

public class IntcodeComputer extends PuzzleInputReader {

    private final ConfigReader _config;
    private final Integer _extendedMemorySize;
    private final BlockingQueue<Long> _inputQueue;
    private final BlockingQueue<Long> _outputQueue;
    private final List<String> startingState;
    private int _parameterModeSize = 3;
    private List<String> computerInput = Collections.emptyList();
    private Opcode currentOp;
    private int[] currentParameterModes;
    private List<Opcode> existingOperations;
    private int[] overrides;
    private int relativeBase = 0;
    private Map<Integer, Opcode> validOpcodes = new HashMap<>();

    /*
        states:
        opcode 1 - addition
        opcode 2 - multiply
        opcode 3 - input
        opcode 4 - output
        opcode 5 - jump-if-true
        opcode 6 - jump-if-false
        opcode 7 - less than
        opcode 8 - equals
        opcode 9 - adjust relative base
        opcode 99 - halt
        Others - error
     */

    public IntcodeComputer(IntcodeComputerBuilder intcodeComputerBuilder) {
        super(intcodeComputerBuilder.inputDataPath());

        _config = intcodeComputerBuilder.configReader();
        _extendedMemorySize = intcodeComputerBuilder.extendedMemorySize();
        _inputQueue = intcodeComputerBuilder.inputQueue() == null
                ? new LinkedBlockingQueue<>(2)
                : intcodeComputerBuilder.inputQueue();
        _outputQueue = intcodeComputerBuilder.outputQueue() == null
                ? new LinkedBlockingQueue<>(2)
                : intcodeComputerBuilder.outputQueue();

        getAvailableOperations();
        initializeOperations(_parameterModeSize);
        readComputerInput();

        if (_extendedMemorySize != null) {
            addExtendedMemory();
        }

        startingState = List.copyOf(computerInput);
    }

    public static IntcodeComputerBuilder builder() {
        return new IntcodeComputerBuilder();
    }

    private void addExtendedMemory() {
        List<String> extraMemory = new ArrayList<>(_extendedMemorySize);
        extraMemory.addAll(computerInput);

        for (int i = computerInput.size(); i < _extendedMemorySize; i++) {
            extraMemory.add("0");
        }

        computerInput = extraMemory;
    }

    private long computeInputValue(int inputPosition, int parameterModeBit, List<String> computerInput) {
        switch (currentParameterModes[parameterModeBit]) {
            case 0:
                return getPositionValue(inputPosition, computerInput);
            case 1:
                return getImmediateValue(inputPosition, computerInput);
            case 2:
                return getRelativeValue(inputPosition, computerInput);
            default:
                throw new RuntimeException("Invalid parameter bit set: " + parameterModeBit);
        }
    }

    private void getAvailableOperations() {
        existingOperations = Arrays.stream(Operation.values())
                .map(op -> OpcodeFactory.getInstance(op, _config, _inputQueue, _outputQueue))
                .collect(Collectors.toList());
    }

    private long getImmediateValue(int inputPosition, List<String> computerInput) {
        return Long.parseLong(computerInput.get(inputPosition));
    }

    public List<String> getMemory() {
        return computerInput;
    }

    private long getPositionValue(int inputPosition, List<String> computerInput) {
        int address = Integer.parseInt(computerInput.get(inputPosition));

        return Long.parseLong(computerInput.get(address));
    }

    private long getRelativeValue(int inputPosition, List<String> computerInput) {
        int address = Integer.parseInt(computerInput.get(inputPosition));
        int calculatedAddress = address + relativeBase;

        return Long.parseLong(computerInput.get(calculatedAddress));
    }

    private void initializeOperations(int parameterModeSize) {
        _parameterModeSize = parameterModeSize;
        currentParameterModes = new int[parameterModeSize];
        validOpcodes = existingOperations.stream()
                .collect(Collectors.toMap(
                        (key) -> key.value(),
                        (value) -> value));
        currentOp = validOpcodes.get(
                OpcodeFactory.getInstance(Operation.HALT, _config, _inputQueue, _outputQueue).value());
    }

    public void initiateComputer() {
        startComputer(memoryOverride());
    }

    private List<String> memoryOverride() {
        if (overrides != null) {
            List<String> overwrittenCodes = new ArrayList<>(computerInput);
            overwrittenCodes.set(1, String.valueOf(overrides[0]));
            overwrittenCodes.set(2, String.valueOf(overrides[1]));

            return overwrittenCodes;
        }

        return computerInput;
    }

    private void readComputerInput() {
        try {
            computerInput = Files.readAllLines(getInputData(), StandardCharsets.UTF_8).stream()
                    .map(opcodeArray -> opcodeArray.split(","))
                    .flatMap(Arrays::stream)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean relativeBaseValueOverflow() {
        if (relativeBase >= computerInput.size()) {
            return true;
        }

        return false;
    }

    private boolean relativeModeIsSet(int parameterModeBit) {
        return currentParameterModes[parameterModeBit] == 2;
    }

    public void resetMemory() {
        computerInput = new ArrayList<>(startingState);
    }

    private void resetParameterModes() {
        Arrays.fill(currentParameterModes, 0);
    }

    private void setOpcode(String input) {
        if (input == null) {
            throw new IllegalArgumentException("Null opcode input given.");
        }

        Opcode operation = validOpcodes.get(Integer.parseInt(input));

        if (operation != null) {
            currentOp = operation;
        } else {
            throw new IllegalArgumentException(input + " is not a valid opcode.");
        }
    }

    private void setOperationAndParameterModes(String input) {
        int inputSize = input.length();

        if (inputSize == 1) {
            setOpcode(input);
            return;
        }

        // Handle presence of parameter modes

        // Last 2 digits represent the opcode
        String operation = input.substring(inputSize - 2, inputSize);
        setOpcode(operation);

        // The rest of the characters represent the parameter modes
        String parameterModes = input.substring(0, inputSize - 2);
        setParameterModes(parameterModes);
    }

    public void setOverrides(int[] overrides) {
        this.overrides = overrides;
    }

    private void setParameterModes(String parameterModes) {
        if (parameterModes == null) {
            throw new IllegalArgumentException("Null parameter modes given.");
        }

        // Reset parameter modes for each opcode
        resetParameterModes();

        // The parameter mode string will be setting the parameter modes in backwards order
        // For example, parameter mode string "10" will set the 2nd parameter to 1 (immediate mode)
        // and set the 1st parameter to 0 (position mode).
        int parameterModeStringIndex = 0;
        for (int i = parameterModes.length() - 1; i >= 0; i--) {
            char parameterModeBit = parameterModes.charAt(parameterModeStringIndex);
            if (parameterModeBit == '1' || parameterModeBit == '2') {
                currentParameterModes[i] = Integer.parseInt(String.valueOf(parameterModeBit));
            }

            parameterModeStringIndex++;
        }
    }

    private void startComputer(List<String> computerInput) {
        for (int i = 0; i < computerInput.size(); i++) {
            if (relativeBaseValueOverflow()) {
                break;
            }

            setOperationAndParameterModes(computerInput.get(i));

            long firstParameter = 0;
            long secondParameter = 0;
            int address = 0;
            int parameterModeBit = 0;

            if (currentOp instanceof AdditionOpcode
                    || currentOp instanceof MultiplicationOpcode
                    || currentOp instanceof JumpOpcode
                    || currentOp instanceof ComparisonOpcode) {
                firstParameter = computeInputValue(i + 1, parameterModeBit, computerInput);
                secondParameter = computeInputValue(i + 2, parameterModeBit + 1, computerInput);
                address = (int) getImmediateValue(i + 3, computerInput);

                if (relativeModeIsSet(parameterModeBit + 2)) {
                    address += relativeBase;
                }
            } else if (currentOp instanceof InputOpcode) {
                address = (int) getImmediateValue(i + 1, computerInput);
                if (relativeModeIsSet(parameterModeBit)) {
                    address += relativeBase;
                }
            } else if (currentOp instanceof OutputOpcode) {
                firstParameter = computeInputValue(i + 1, parameterModeBit, computerInput);
            } else if (currentOp instanceof RelativeBaseOffsetOpcode) {
                firstParameter = computeInputValue(i + 1, parameterModeBit, computerInput);
                secondParameter = relativeBase;
            } else if (currentOp instanceof HaltOpcode) {
                break;
            }

            Optional<ResultWithAddress> result = currentOp.operation(firstParameter, secondParameter, address);

            if (result.isPresent()) {
                ResultWithAddress resultWithAddress = result.get();

                if (currentOp instanceof RelativeBaseOffsetOpcode) {
                    relativeBase = Integer.parseInt(resultWithAddress.result());
                } else {
                    computerInput.set(resultWithAddress.memoryAddress(), resultWithAddress.result());
                }
            }

            // Reset parameter modes for next opcode
            resetParameterModes();

            if (currentOp instanceof JumpOpcode) {
                JumpOpcode jumpOpcode = (JumpOpcode) currentOp;

                // If jump operation succeeded, decrement 1 as 1 will be added on the next loop
                i = jumpOpcode.jumpToInstruction() != null
                        ? jumpOpcode.jumpToInstruction() - 1
                        : i + currentOp.instructionPointerIncrementCount() - 1;
            } else {
                // Decrement 1 as 1 will be added on the next loop
                i += currentOp.instructionPointerIncrementCount() - 1;
            }
        }

        this.computerInput = computerInput;
    }

}
