package com.adventofcode.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ConfigReader implements Config {

    private final Path inputData;
    private final Properties _config;

    public ConfigReader(String inputDataPath) throws IOException {
        inputData = Paths.get(inputDataPath);
        _config = new Properties();

        try(FileInputStream configFileStream = new FileInputStream(inputData.toFile())) {
            _config.load(configFileStream);
        }
    }

    public ConfigReader(Map<String, String> configProperties) {
        inputData = null;
        _config = new Properties();
        _config.putAll(configProperties);
    }

    @Override
    public Duration inputQueueTimeout() {
        Duration duration;

        try {
            duration = Duration.parse(_config.getProperty("computer.input.queue.timeout"));
        } catch (NumberFormatException e) {
            // Default
            duration = Duration.of(1000, ChronoUnit.MILLIS);
        }

        return duration;
    }

}
