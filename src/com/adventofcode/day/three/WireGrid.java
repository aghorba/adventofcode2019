package com.adventofcode.day.three;

import com.adventofcode.utils.PuzzleInputReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class WireGrid extends PuzzleInputReader {

    private Set<WirePoint> wireOne;
    private Set<WirePoint> wireTwo;
    private List<WirePoint> wireIntersections;

    public WireGrid(String inputDataPath) {
        super(inputDataPath);
        determineAllWireCoordinatePoints();
    }

    public int getManhattanDistance() {
        findWireIntersections();
        return getClosestIntersectionDistance();
    }

    public int getLowestStepDistance() {
        findWireIntersections();
        List<WirePoint> intersectionsCopy = new ArrayList<>(wireIntersections);

        intersectionsCopy.sort((pointOne, pointTwo) -> {
            if (pointOne.stepCount() > pointTwo.stepCount()) {
                return 1;
            } else if (pointOne.stepCount() < pointTwo.stepCount()) {
                return -1;
            }

            return 0;
        });

        return intersectionsCopy.get(0).stepCount();
    }

    private int getClosestIntersectionDistance() {
        List<Integer> manhattanDistances = wireIntersections.stream()
                .map(point -> Integer.sum(Math.abs(point.x()), Math.abs(point.y())))
                .collect(Collectors.toList());
        Collections.sort(manhattanDistances);

        return manhattanDistances.get(0);
    }

    private void determineAllWireCoordinatePoints() {
        try {
            List<String> gridPoints = Files.readAllLines(getInputData(), StandardCharsets.UTF_8);

            wireOne = getWireCoordinatePoints(gridPoints.get(0));
            wireTwo = getWireCoordinatePoints(gridPoints.get(1));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void findWireIntersections() {
        Set<WirePoint> wireOneIntersections = wireOne.stream()
                .filter(wireTwo::contains)
                .collect(Collectors.toSet());

        wireIntersections = wireTwo.stream()
                .filter(wireOneIntersections::contains)
                .map(point -> {
                    Optional<WirePoint> wireOnePoint = wireOneIntersections.stream().filter(point::equals).findFirst();

                    return wireOnePoint
                            .map(intersection -> WirePoint.builder()
                                    .x(point.x())
                                    .y(point.y())
                                    .stepCount(Integer.sum(point.stepCount(), intersection.stepCount()))
                                    .build())
                            .orElse(point);
                })
                .collect(Collectors.toList());
    }

    private Set<WirePoint> getWireCoordinatePoints(String inputDataLine) {
        int x = 0;
        int y = 0;
        int stepCount = 0;
        Set<WirePoint> coordinatePoints = new HashSet<>();

        for (String direction : inputDataLine.split(",")) {
            char axis = direction.charAt(0);
            String coordinate = direction.substring(1);

            int coordinateValue = Integer.parseInt(coordinate);
            switch (axis) {
                case 'R':
                    stepCount = recordPoints(
                            coordinatePoints,
                            stepCount,
                            x,
                            x += coordinateValue,
                            y,
                            (a, b) -> a + b,
                            true);
                    break;
                case 'L':
                    stepCount = recordPoints(
                            coordinatePoints,
                            stepCount,
                            x,
                            x -= coordinateValue,
                            y,
                            (a, b) -> a - b,
                            true);
                    break;
                case 'U':
                    stepCount = recordPoints(
                            coordinatePoints,
                            stepCount,
                            y,
                            y += coordinateValue,
                            x,
                            (a, b) -> a + b,
                            false);
                    break;
                case 'D':
                    stepCount = recordPoints(
                            coordinatePoints,
                            stepCount,
                            y,
                            y -= coordinateValue,
                            x,
                            (a, b) -> a - b,
                            false);
                    break;
                default:
                    throw new UnsupportedOperationException(String.format("Unknown direction in input data: %s", axis));
            }
        }

        return coordinatePoints;
    }

    private int recordPoints(
            Set<WirePoint> coordinatePoints,
            int stepCount,
            int previousCoordinate,
            int nextCoordinate,
            int unchangedCoordinate,
            BiFunction<Integer, Integer, Integer> operation,
            boolean setXCoordinates) {

        int distance = Math.abs(nextCoordinate - previousCoordinate);

        for (int i = 1; i <= distance; i++) {
            stepCount++;
            coordinatePoints.add(WirePoint.builder()
                    .x(setXCoordinates ? operation.apply(previousCoordinate, i) : unchangedCoordinate)
                    .y(setXCoordinates ? unchangedCoordinate : operation.apply(previousCoordinate, i))
                    .stepCount(stepCount)
                    .build());
        }

        return stepCount;
    }

}
