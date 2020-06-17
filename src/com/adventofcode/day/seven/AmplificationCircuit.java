package com.adventofcode.day.seven;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public class AmplificationCircuit {

    private ByteArrayOutputStream ampOutput = new ByteArrayOutputStream();
    private Set<Path> amplifierDataFiles = new HashSet<>();
    private ExecutorService executorService;
    private boolean _feedbackMode;
    private List<Integer> initialPhaseSequence = List.of(0, 1, 2, 3, 4);
    private Set<List<Integer>> phaseSequences = new HashSet<>();
    // For permutations of phase sequences
    private Map<Integer, List<Integer>> adjacencyLists = new HashMap<>();
    private List<Amplifier> circuit = new ArrayList<>();
    private String _programInputData;

    public AmplificationCircuit(String programInputData, boolean feedbackMode) {
        _programInputData = programInputData;
        _feedbackMode = feedbackMode;

        if (feedbackMode) {
            initialPhaseSequence = List.of(5, 6, 7, 8, 9);
        }

        generatePhaseSequences();
    }

    public AmplificationCircuit(String programInputData, List<Integer> phaseSequence, boolean feedbackMode) {
        _feedbackMode = feedbackMode;
        _programInputData = programInputData;
        phaseSequences.add(phaseSequence);
    }

    public int getMaxThrusterSignal() throws IOException, InterruptedException {
        executorService = Executors.newFixedThreadPool(initialPhaseSequence.size());

        for (List<Integer> sequence : phaseSequences) {
            CountDownLatch completedComputers = new CountDownLatch(sequence.size());
            BlockingQueue<Integer> nextInputQueue = null;
            BlockingQueue<Integer> lastOutputQueue = new LinkedBlockingQueue<>(2);
            boolean setAmplifierA = false;
            int index = 0;

            for (int phaseNum : sequence) {
                Amplifier.AmplifierBuilder amplifierBuilder = Amplifier.builder()
                        .phaseSetting(phaseNum)
                        .programDataPath(_programInputData)
                        .circuit(this);

                if (!setAmplifierA) {
                    BlockingQueue outputQueue = new LinkedBlockingQueue<>(2);

                    if(_feedbackMode) {
                        amplifierBuilder.inputQueue(lastOutputQueue);
                    }
                    else {
                        amplifierBuilder.inputQueue(new LinkedBlockingQueue(2));
                    }

                    amplifierBuilder
                            .inputSignal(0)
                            .outputQueue(outputQueue);
                    setAmplifierA = true;
                    nextInputQueue = outputQueue;

                    Amplifier amplifierA = amplifierBuilder.build();
                    circuit.add(amplifierA);
                    enableAmplifier(amplifierA, completedComputers);
                    index++;
                    continue;
                }

                amplifierBuilder.inputQueue(nextInputQueue);

                if(index == sequence.size() - 1) {
                    amplifierBuilder.outputQueue(lastOutputQueue);
                }
                else {
                    BlockingQueue<Integer> outputQueue = new LinkedBlockingQueue<>(2);
                    amplifierBuilder.outputQueue(outputQueue);
                    nextInputQueue = outputQueue;
                }

                Amplifier amplifier = amplifierBuilder.build();
                circuit.add(amplifier);
                enableAmplifier(amplifier, completedComputers);

                index++;
            }

            try {
                completedComputers.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            disassembleCircuit();
            removeAmplifierFiles();
        }

        shutdownAmplificationCircuit();

        return parseMaxThrusterSignal();
    }

    private int parseMaxThrusterSignal() {
        List<Integer> signals = Arrays.stream(ampOutput.toString().split("\r\n"))
                .map(outputLine -> outputLine.split(" "))
                .map(outputSplit -> outputSplit[1])
                .map(Integer::valueOf)
                .sorted()
                .collect(Collectors.toList());

        return signals.get(signals.size() - 1);
    }

    private void disassembleCircuit() {
        circuit.clear();
    }

    private void enableAmplifier(Amplifier amplifier, CountDownLatch completedComputers) {
        PrintStream old = new PrintStream(System.out);
        PrintStream redirect = new PrintStream(ampOutput);

        executorService.submit(() -> {
            try {
                System.setOut(redirect);
                amplifier.enable();
            } finally {
                completedComputers.countDown();
                System.out.flush();
                System.setOut(old);
            }
        });
    }

    private void shutdownAmplificationCircuit() {
            executorService.shutdown();
    }

    void addAmplifierDataFile(Path... amplifierFiles) {
        amplifierDataFiles.addAll(Arrays.asList(amplifierFiles));
    }

    private void removeAmplifierFiles() {
        Set<Path> allFiles = new HashSet<>();
        allFiles.addAll(amplifierDataFiles);

        for (Path amplifierFile : allFiles) {
            try {
                Files.deleteIfExists(amplifierFile);
            } catch (IOException e) {
                System.out.println(String.format(
                        "Unable to delete amplifier file '%s': %s",
                        amplifierFile.toAbsolutePath().toString(),
                        e.getMessage()));
            }
        }

        amplifierDataFiles.clear();
    }

    private void generatePhaseSequences() {
        for (int phaseNum : initialPhaseSequence) {
            adjacencyLists.put(phaseNum, initialPhaseSequence.stream()
                    .filter(elem -> phaseNum != elem)
                    .collect(Collectors.toList()));
        }

        for (int phaseNum : initialPhaseSequence) {
            generatePhaseSequence(new ArrayList<>(), phaseNum, new HashSet<>(), initialPhaseSequence.size());
        }
    }

    private void generatePhaseSequence(
            List<Integer> currentSequence,
            int currentPhaseNum,
            Set<Integer> visited,
            int maxSize) {

        currentSequence.add(currentPhaseNum);
        visited.add(currentPhaseNum);

        if (currentSequence.size() == maxSize) {
            phaseSequences.add(new ArrayList<>(currentSequence));
            return;
        }

        for (int nextPhaseNum : adjacencyLists.get(currentPhaseNum)) {
            if (!visited.contains(nextPhaseNum)) {
                generatePhaseSequence(currentSequence, nextPhaseNum, visited, maxSize);
                currentSequence.remove(Integer.valueOf(nextPhaseNum));
                visited.remove(Integer.valueOf(nextPhaseNum));
            }
        }
    }
}
