package com.adventofcode.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

public abstract class PuzzleInputReader {

    private final Path inputData;

    public PuzzleInputReader(String inputDataPath) {
        inputData = Paths.get(inputDataPath);
    }

    protected Path getInputData() {
        return inputData;
    }

    protected InputStream getInputDataInputStream() throws FileNotFoundException {
        return new FileInputStream(inputData.toFile());
    }

}
