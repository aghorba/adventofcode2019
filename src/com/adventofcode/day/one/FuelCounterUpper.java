package com.adventofcode.day.one;

import com.adventofcode.utils.PuzzleInputReader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class FuelCounterUpper extends PuzzleInputReader {

    public FuelCounterUpper(String inputDataPath) {
        super(inputDataPath);
    }

    public long totalFuelRequirement() throws Exception {

        long totalFuel = 0;

        try {
            List<String> fuelInput = Files.readAllLines(getInputData());

            totalFuel = fuelInput.stream()
                    .map(Long::parseLong)
                    .map(mass -> {
                        long extraFuelRequirements = 0;
                        long nextMass = mass;

                        while(nextMass > 0) {
                            nextMass = (nextMass / 3L) - 2;

                            if(nextMass < 0) {
                                continue;
                            }

                            extraFuelRequirements += nextMass;
                        }

                        return extraFuelRequirements;
                    })
                    .mapToLong(Long::longValue)
                    .sum();
        } catch (Exception e) {
            throw new Exception(String.format("Unable to read input data from %s", getInputData().toAbsolutePath()));
        }

        return totalFuel;
    }
}
