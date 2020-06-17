package com.adventofcode;

import com.adventofcode.day.eight.SpaceImageReader;
import com.adventofcode.day.four.FuelDepotPasswordGenerator;
import com.adventofcode.day.one.FuelCounterUpper;
import com.adventofcode.day.seven.AmplificationCircuit;
import com.adventofcode.day.six.UniversalOrbitMap;
import com.adventofcode.day.three.WireGrid;
import com.adventofcode.day.two.IntcodeComputer;
import com.adventofcode.utils.ConfigReader;

import java.io.IOException;
import java.util.concurrent.LinkedBlockingQueue;

public class Main {

    public static void main(String[] args) throws IOException, InterruptedException {
        ConfigReader config = new ConfigReader("resources\\config.properties");
//        dayOne();
//        dayTwo(config);
//        dayThree();
//        dayFour();
//        dayFive(config);
//        daySix();
//        daySeven();
//        dayEight();
        dayNine();
    }

    public static void dayOne() {
        try {
            System.out.println(new FuelCounterUpper("resources\\puzzle1_input.txt").totalFuelRequirement());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void dayTwo(ConfigReader config) {
        IntcodeComputer computer = null;
        try {
            computer = IntcodeComputer.builder()
                    .inputDataPath("resources\\puzzle2_input.txt")
                    .configReader(config)
                    .build();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (computer != null) {
            int valueToSeek = 19690720;
            int overrideTwo = 1;

            computer.initiateComputer();

            for (int overrideOne = 1; overrideOne < computer.getMemory().size(); overrideOne++) {
                while (overrideTwo < computer.getMemory().size()) {
                    if (Integer.valueOf(computer.getMemory().get(0)) == valueToSeek) {
                        System.out.println(100 * Integer.valueOf(computer.getMemory().get(1)) + Integer.valueOf(computer.getMemory().get(2)));
                        break;
                    }

                    computer.resetMemory();
                    computer.setOverrides(new int[]{overrideOne, overrideTwo});
                    computer.initiateComputer();
                    overrideTwo++;
                }

                overrideTwo = 1;
            }

        }
    }

    public static void dayThree() {
        WireGrid grid = new WireGrid("resources\\puzzle3_input.txt");
        System.out.println(grid.getManhattanDistance());
        System.out.println(grid.getLowestStepDistance());
    }

    public static void dayFour() {
        FuelDepotPasswordGenerator passwordGenerator = new FuelDepotPasswordGenerator(
                "resources\\puzzle4_input.txt");

        System.out.println(passwordGenerator.checkPasswordCriteria().size());
    }

    public static void dayFive(ConfigReader config) {
        IntcodeComputer computer = IntcodeComputer.builder()
                .inputDataPath("resources\\puzzle5_input.txt")
                .configReader(config)
                .build();
        computer.initiateComputer();
    }

    public static void daySix() {
        UniversalOrbitMap map = new UniversalOrbitMap("resources\\puzzle6_input.txt");
        System.out.println("ORBIT COUNT: " + map.orbitsCount());
        System.out.println("MIN DISTANCE TO SAN FROM YOU: " + map.minimumOrbitalTransfers("YOU", "SAN"));

        // debug
        System.out.println("=== DEPTH FIRST ===");
        map.travelToObjectDepthFirst("YOU", "SAN");
        System.out.println("=== BREADTH FIRST ===");
        map.travelToObjectBreadthFirst("YOU", "SAN");
    }

    public static void daySeven() throws IOException, InterruptedException {
        AmplificationCircuit circuit = new AmplificationCircuit("resources\\puzzle7_input.txt", true);

        System.out.println("MAX THRUSTER SIGNAL: " + circuit.getMaxThrusterSignal());
    }

    public static void dayEight() throws IOException {
        SpaceImageReader reader = new SpaceImageReader("resources\\puzzle8_input.txt");
        System.out.println("Image verification data: " + reader.verifyImage(25, 6));
        System.out.println("Image decode: " + reader.imageDecode(25, 6));
    }

    public static void dayNine() throws IOException {
        int extendedMemorySize = 2048;
        LinkedBlockingQueue<Long> queue = new LinkedBlockingQueue<>();
        queue.add(2L);
        IntcodeComputer computer = IntcodeComputer.builder()
                .configReader(new ConfigReader("resources\\config.properties"))
                .inputDataPath("resources\\puzzle9_input.txt")
                .inputQueue(queue)
                .outputQueue(queue)
                .extendedMemorySize(extendedMemorySize)
                .build();
        computer.initiateComputer();
    }
}
