package com.adventofcode.day.seven;

import com.adventofcode.day.two.IntcodeComputer;
import com.adventofcode.utils.ConfigReader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;

final class Amplifier {

    private final AmplificationCircuit circuit;
    private final IntcodeComputer computer;
    private Integer inputSignal;
    private final BlockingQueue<Long> inputQueue;
    private final BlockingQueue<Long> outputQueue;
    private final int phaseSetting;
    private final String programDataPath;

    private Amplifier(AmplifierBuilder builder) throws IOException, InterruptedException {
        circuit = builder.circuit;
        inputQueue = builder.inputQueue;
        outputQueue = builder.outputQueue;
        inputSignal = builder.inputSignal;
        phaseSetting = builder.phaseSetting;
        programDataPath = builder.programDataPath;

        inputQueue.put((long) phaseSetting);
        if(inputSignal != null) {
            inputQueue.put((long) inputSignal);
        }
        // Do not re-use the same file from programDataPath, create a copy and use that
        String dataToRun = writeProgramDataCopy();

        ConfigReader config = new ConfigReader("resources\\config.properties");
        computer = IntcodeComputer.builder()
                .inputDataPath(dataToRun)
                .configReader(config)
                .inputQueue(inputQueue)
                .outputQueue(outputQueue)
                .build();
    }

    public void enable() {
        computer.initiateComputer();
    }

    private String writeProgramDataCopy() throws IOException {
        String programDataCopy = String.join("_",
                this.getClass().getSimpleName().toLowerCase(),
                "program_data_copy",
                UUID.randomUUID().toString());
        Path programDataCopyPath = Paths.get(programDataCopy);

        Files.copy(Paths.get(programDataPath), programDataCopyPath);
        addToCleanUp(programDataCopyPath);

        return programDataCopy;
    }

    private void addToCleanUp(Path... filesToRemove) {
        circuit.addAmplifierDataFile(filesToRemove);
    }

    public String programDataPath() {
        return programDataPath;
    }

    public static AmplifierBuilder builder() {
        return new AmplifierBuilder();
    }

    public static class AmplifierBuilder {
        private AmplificationCircuit circuit;
        private Integer inputSignal;
        private Integer phaseSetting;
        private String programDataPath;
        private BlockingQueue<Long> inputQueue;
        private BlockingQueue<Long> outputQueue;

        private AmplifierBuilder() {

        }

        public AmplifierBuilder circuit(AmplificationCircuit circuit) {
            this.circuit = circuit;
            return this;
        }

        public AmplifierBuilder inputSignal(Integer inputSignal) {
            this.inputSignal = inputSignal;
            return this;
        }

        public AmplifierBuilder inputQueue(BlockingQueue queue) {
            inputQueue = queue;
            return this;
        }

        public AmplifierBuilder outputQueue(BlockingQueue queue) {
            outputQueue = queue;
            return this;
        }

        public AmplifierBuilder phaseSetting(int phaseSetting) {
            this.phaseSetting = phaseSetting;
            return this;
        }

        public AmplifierBuilder programDataPath(String programDataPath) {
            this.programDataPath = programDataPath;
            return this;
        }

        public Amplifier build() throws IOException, InterruptedException {
            if (circuit == null) {
                throw new RuntimeException(String.format(
                        "Cannot build %s, %s parameter is missing.",
                        Amplifier.class.getSimpleName(),
                        "circuit"));
            }

            if (phaseSetting == null) {
                throw new RuntimeException(String.format(
                        "Cannot build %s, %s parameter is missing.",
                        Amplifier.class.getSimpleName(),
                        "phaseSetting"));
            }

            if (programDataPath == null) {
                throw new RuntimeException(String.format(
                        "Cannot build %s, %s parameter is missing.",
                        Amplifier.class.getSimpleName(),
                        "programDataPath"));
            }

            if (inputQueue == null) {
                throw new RuntimeException(String.format(
                        "Cannot build %s, %s parameter is missing.",
                        Amplifier.class.getSimpleName(),
                        "inputQueue"));
            }

            if (outputQueue == null) {
                throw new RuntimeException(String.format(
                        "Cannot build %s, %s parameter is missing.",
                        Amplifier.class.getSimpleName(),
                        "outputQueue"));
            }

            return new Amplifier(this);
        }
    }

}
