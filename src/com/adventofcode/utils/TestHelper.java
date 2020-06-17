package com.adventofcode.utils;

import org.junit.jupiter.api.AfterEach;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public abstract class TestHelper {

    private Set<Path> filesToRemove = new HashSet<>();
    protected Path currentTestFile;

    @AfterEach
    protected void removeTestFile() throws IOException {
        if(currentTestFile != null) {
            Files.deleteIfExists(currentTestFile);
        }

        for(Path fileToRemove : filesToRemove) {
            Files.deleteIfExists(fileToRemove);
        }
    }

    protected void addFileToRemove(Path fileToRemove) {
        filesToRemove.add(fileToRemove);
    }

    protected Path createTestFile(String name, List<String> input) throws IOException {
        Path testFile = Paths.get(String.join("_", name, UUID.randomUUID().toString()));

        Files.write(testFile, input);
        return testFile;
    }

}
