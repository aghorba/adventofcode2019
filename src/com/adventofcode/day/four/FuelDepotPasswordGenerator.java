package com.adventofcode.day.four;

import com.adventofcode.utils.PuzzleInputReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class FuelDepotPasswordGenerator extends PuzzleInputReader {

    private long startOfRange;
    private long endOfRange;

    public FuelDepotPasswordGenerator(String inputDataPath) {
        super(inputDataPath);
        readPasswordRange();
    }

    private void readPasswordRange() {
        try {
            List<Long> ranges = Files.readAllLines(getInputData(), StandardCharsets.UTF_8)
                    .stream()
                    .map(line -> line.split("-"))
                    .flatMap(Arrays::stream)
                    .map(Long::parseLong)
                    .collect(Collectors.toList());

            startOfRange = ranges.get(0);
            endOfRange = ranges.get(1);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Set<Long> checkPasswordCriteria() {
        Set<Long> validPasswords = new HashSet<>();

        for (long i = startOfRange; i <= endOfRange; i++) {
            if (passwordCriteriaMatches(String.valueOf(i))) {
                validPasswords.add(i);
            }
        }

        return validPasswords;
    }

    private boolean passwordCriteriaMatches(String password) {

        Integer previousDigit = null;
        boolean foundPair = false;
        boolean result = true;

        for (int i = 0; i < password.length(); i++) {
            int currentDigit = Integer.parseInt(String.valueOf(password.charAt(i)));

            if (previousDigit == null) {
                previousDigit = currentDigit;
                continue;
            }

            if (currentDigit == previousDigit) {
                if (i + 1 < password.length()) {
                    int repetitionAmount = findRepeatingDigit(password.substring(i + 1), currentDigit);

                    if (repetitionAmount != 0) {
                        i += repetitionAmount;
                        previousDigit = Integer.parseInt(String.valueOf(password.charAt(i)));
                        continue;
                    }
                }

                foundPair = true;
            } else if (currentDigit < previousDigit) {
                result = false;
                break;
            }

            previousDigit = currentDigit;
        }

        return foundPair && result;
    }

    private int findRepeatingDigit(String passwordSubstring, int previousDigit) {

        Character storedDigit = Character.forDigit(previousDigit, 10);
        int repetitionAmount = 0;

        for (Character passwordDigit : passwordSubstring.toCharArray()) {
            if (passwordDigit == storedDigit) {
                repetitionAmount++;
            } else {
                break;
            }
        }

        return repetitionAmount;
    }

}
