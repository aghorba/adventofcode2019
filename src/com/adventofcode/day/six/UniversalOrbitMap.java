package com.adventofcode.day.six;

import com.adventofcode.utils.PuzzleInputReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class UniversalOrbitMap extends PuzzleInputReader {

    private Map<String, String> orbitMap = new HashMap<>();
    private Map<String, List<String>> orbitMapGraph = new HashMap<>();
    private Integer orbits = null;
    private Integer minimumTransfers = null;

    public UniversalOrbitMap(String inputDataPath) {
        super(inputDataPath);

        readOrbitMap();
    }

    private static class OrbitObject {

        private final int shortestDistanceFromSource;
        private final String viaThisVertex;

        private OrbitObject(OrbitObjectBuilder orbitObjectBuilder) {
            shortestDistanceFromSource = orbitObjectBuilder.shortestDistanceFromSource;
            viaThisVertex = orbitObjectBuilder.viaThisVertex;
        }

        public static OrbitObjectBuilder builder() {
            return new OrbitObjectBuilder();
        }

        public static class OrbitObjectBuilder {
            private int shortestDistanceFromSource;
            private String viaThisVertex;

            private OrbitObjectBuilder() {

            }

            public OrbitObjectBuilder shortestDistanceFromSource(int shortestDistanceFromSource) {
                this.shortestDistanceFromSource = shortestDistanceFromSource;
                return this;
            }

            public OrbitObjectBuilder viaThisVertex(String viaThisVertex) {
                this.viaThisVertex = viaThisVertex;
                return this;
            }

            public OrbitObject build() {
                return new OrbitObject(this);
            }
        }
    }

    private void readOrbitMap() {
        try {
            List<String> orbitData = Files.readAllLines(getInputData(), StandardCharsets.UTF_8);

            for (String data : orbitData) {
                String[] orbitObjects = data.split("\\)");
                String firstObject = orbitObjects[1];
                String secondObject = orbitObjects[0];

                orbitMap.put(firstObject, secondObject);
                // Add each object to each other's orbit (adjacency) list to create the graph
                List<String> firstObjectOrbits = orbitMapGraph.getOrDefault(firstObject, new LinkedList<>());
                firstObjectOrbits.add(secondObject);
                orbitMapGraph.put(firstObject, firstObjectOrbits);

                List<String> secondObjectOrbits = orbitMapGraph.getOrDefault(secondObject, new LinkedList<>());
                secondObjectOrbits.add(firstObject);
                orbitMapGraph.put(secondObject, secondObjectOrbits);
            }
        } catch (IOException e) {
            System.err.println(String.format("Error processing orbital map from %s: %s", getInputData(), e.getMessage()));
        }
    }

    private int traverseOrbitMap(Collection<String> objects) {
        int orbitCount = 0;

        for (String object : objects) {
            orbitCount += traverseOrbitMap(object);
        }

        return orbitCount;
    }

    private int traverseOrbitMap(String object) {
        int orbitCount = 0;
        String orbittingObject = orbitMap.get(object);

        if (orbittingObject != null) {
            orbitCount++;
            orbitCount += traverseOrbitMap(orbittingObject);
        }

        return orbitCount;
    }

    private void checkIfObjectIsInOrbitMap(String object) {
        if(orbitMap.get(object) == null) {
            throw new IllegalArgumentException(String.format("Object %s is not in the orbit map.", object));
        }
    }

    public void travelToObjectDepthFirst(String sourceObject, String destinationObject) {
        Objects.requireNonNull(sourceObject);
        Objects.requireNonNull(destinationObject);

        checkIfObjectIsInOrbitMap(sourceObject);
        checkIfObjectIsInOrbitMap(destinationObject);

        travelToObjectDepthFirst(sourceObject, destinationObject, new HashSet<>());
    }

    public void travelToObjectBreadthFirst(String sourceObject, String destinationObject) {
        Objects.requireNonNull(sourceObject);
        Objects.requireNonNull(destinationObject);

        checkIfObjectIsInOrbitMap(sourceObject);
        checkIfObjectIsInOrbitMap(destinationObject);

        if(sourceObject.equals(destinationObject)) {
            System.out.println("REACHED DESTINATION: " + destinationObject);
            return;
        }

        Set<String> visitedObjects = new HashSet<>();
        Deque<String> unvisitedObjects = new ArrayDeque<>();
        unvisitedObjects.addLast(sourceObject);

        for(String adjacentObject : orbitMapGraph.get(sourceObject)) {
            System.out.println("TRAVERSING: " + adjacentObject);
            visitedObjects.add(adjacentObject);
            unvisitedObjects.add(adjacentObject);
        }

        while(!unvisitedObjects.isEmpty()) {
            String currentObject = unvisitedObjects.removeFirst();

            for(String adjacentObject : orbitMapGraph.get(currentObject)) {
                if(adjacentObject.equals(destinationObject)) {
                    System.out.println("REACHED DESTINATION: " + destinationObject);
                    return;
                }

                if(!visitedObjects.contains(adjacentObject)) {
                    System.out.println("TRAVERSING: " + adjacentObject);
                    unvisitedObjects.addLast(adjacentObject);
                    visitedObjects.add(adjacentObject);
                }
            }
        }
    }

    private void travelToObjectDepthFirst(
            String sourceObject,
            String destinationObject,
            Set<String> visitedObjects) {

        System.out.println("TRAVERSING: " + sourceObject);
        visitedObjects.add(sourceObject);

        for(String adjacentObject : orbitMapGraph.get(sourceObject)) {
            if(sourceObject.equals(destinationObject)) {
                System.out.println("REACHED DESTINATION: " + sourceObject);
                return;
            }

            if(!visitedObjects.contains(adjacentObject)) {
                travelToObjectDepthFirst(adjacentObject, destinationObject, visitedObjects);
            }
        }
    }

    public int minimumOrbitalTransfers(String sourceObject, String destinationObject) {
        Objects.requireNonNull(sourceObject);
        Objects.requireNonNull(destinationObject);

        checkIfObjectIsInOrbitMap(sourceObject);
        checkIfObjectIsInOrbitMap(destinationObject);

        Set<String> visitedVertexes = new HashSet<>();
        Set<String> unvisitedVertexes = new HashSet<>(orbitMapGraph.keySet());
        Map<String, OrbitObject> shortestDistancesTable = orbitMapGraph.keySet().stream()
                .collect(Collectors.toMap(
                        (key) -> key,
                        (value) -> {
                            if (value.equals(sourceObject)) {
                                return OrbitObject.builder()
                                        .shortestDistanceFromSource(0)
                                        .viaThisVertex("")
                                        .build();
                            }

                            return OrbitObject.builder()
                                    .shortestDistanceFromSource(Integer.MAX_VALUE)
                                    .viaThisVertex("")
                                    .build();
                        }
                ));

        while (!unvisitedVertexes.isEmpty()) {
            // Find closest unvisited vertex
            int currentShortestDistance = Integer.MAX_VALUE;
            String currentClosestObject = null;

            for (Map.Entry<String, OrbitObject> entry : shortestDistancesTable.entrySet()) {
                String object = entry.getKey();
                OrbitObject orbitDistanceData = entry.getValue();

                if (!visitedVertexes.contains(object) && currentShortestDistance > orbitDistanceData.shortestDistanceFromSource) {
                    currentShortestDistance = orbitDistanceData.shortestDistanceFromSource;
                    currentClosestObject = object;
                }
            }

            for (String adjacentObject : orbitMapGraph.get(currentClosestObject)) {
                if(!visitedVertexes.contains(adjacentObject)) {
                    // Treat all objects as 1 distance away
                    int objectEdgeToEdgeDistance = 1;
                    int adjacentObjectDistanceFromStart = shortestDistancesTable.get(adjacentObject).shortestDistanceFromSource;
                    int calculatedDistanceFromStart = shortestDistancesTable.get(currentClosestObject).shortestDistanceFromSource
                            + objectEdgeToEdgeDistance;

                    if (calculatedDistanceFromStart < adjacentObjectDistanceFromStart) {
                        shortestDistancesTable.put(adjacentObject, OrbitObject.builder()
                                .shortestDistanceFromSource(calculatedDistanceFromStart)
                                .viaThisVertex(currentClosestObject)
                                .build());
                    }
                }
            }

            visitedVertexes.add(currentClosestObject);
            unvisitedVertexes.remove(currentClosestObject);
        }

        // Get minimum distance. Subtract 2 from the result to not include the source and destination
        Optional<OrbitObject> destination = shortestDistancesTable.entrySet().stream()
                .filter(entry -> entry.getKey().equals(destinationObject))
                .map(entry -> entry.getValue())
                .findFirst();
        return  destination.map(object -> object.shortestDistanceFromSource - 2)
                .orElse(-1);

        /*
        1. List of visited nodes
        2. List of unvisited nodes
        3. Create a hash table with the following:
           a. Each vertex
           b. The value of it's shortest distance to the start
           c. The vertex taken to get to that vertex

        while we have unvisited nodes:
        1. Get the current closest unvisited node to the start
        2. Get the nodes adjacent nodes
        3. Check if the adjacent node has been visited
        4. Compare the following distances to find the shorter distance:
           a. The current closest node's distance + the adjacent node's edge connecting the two nodes weight
           b. The adjacent node's currently set distance from the start
           c. If a.'s calculated distance is closer than b.'s existing value, update the adjacent node's shortest
           distance.
           d. Update the vertex that was used to access the adjacent node to the current
        5. After visiting each adjacent node to the current closest node, add the current node as visited
        6. Remove the current node from the unvisited list
         */
    }

    public int orbitsCount() {
        if (orbits == null) {
            orbits = traverseOrbitMap(orbitMap.keySet());
        }

        return orbits;
    }

}
