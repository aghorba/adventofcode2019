package com.adventofcode.day.eight;

import com.adventofcode.utils.PuzzleInputReader;
import org.w3c.dom.ls.LSOutput;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;

public class SpaceImageReader extends PuzzleInputReader {

    private final String imageData;

    public SpaceImageReader(String inputDataPath) throws IOException {
        super(inputDataPath);
        imageData = readImageData();
    }

    private String readImageData() throws IOException {
        return Files.readString(getInputData(), StandardCharsets.UTF_8);
    }

    public int verifyImage(int pixelWidth, int pixelHeight) {
        int layerLength = pixelWidth * pixelHeight;
        int layerIndexWithFewestZeroes = 0;
        int numberOfZeroes = Integer.MAX_VALUE;
        int currentLayerPoint = 0;
        int currentZeroCount = 0;
        int currentIndex = 0;

        for (Character pixel : imageData.toCharArray()) {
            if (pixel == '0') {
                currentZeroCount++;
            }

            if (currentLayerPoint == layerLength - 1) {
                currentLayerPoint = 0;

                if (currentZeroCount != 0 && numberOfZeroes > currentZeroCount) {
                    numberOfZeroes = currentZeroCount;
                    layerIndexWithFewestZeroes = currentIndex - (layerLength - 1);
                }

                currentZeroCount = 0;
                currentIndex++;
                continue;
            }

            currentIndex++;
            currentLayerPoint++;
        }

        String fewestZeroesLayer = imageData.substring(
                layerIndexWithFewestZeroes,
                layerIndexWithFewestZeroes + layerLength);
        Map<Character, Integer> imageDataCounts = fewestZeroesLayer.chars()
                .mapToObj(character -> (char) character)
                .filter(character -> character.equals('1') || character.equals('2'))
                .collect(Collectors.toMap(
                        (key) -> key,
                        (value) -> 1,
                        (oldValue, newValue) -> ++oldValue
                ));

        int imageResult = 1;

        for (int count : imageDataCounts.values()) {
            imageResult *= count;
        }

        return imageResult;
    }

    public String imageDecode(int pixelWidth, int pixelHeight) {
        int layerLength = pixelWidth * pixelHeight;
        int currentLayerPoint = 0;
        char[] defaultImage = new char[layerLength];
        Arrays.fill(defaultImage, '2');

        BitSet pixelsSet = new BitSet(layerLength);

        for (Character pixel : imageData.toCharArray()) {
            if (pixel == '0' || pixel == '1') {
                if (!pixelsSet.get(currentLayerPoint)) {
                    defaultImage[currentLayerPoint] = pixel;
                    pixelsSet.set(currentLayerPoint);
                }
            }

            if (currentLayerPoint == layerLength - 1) {
                currentLayerPoint = 0;
                continue;
            }

            currentLayerPoint++;
        }

        int layerLineIndex = 0;
        int breakPoint = pixelWidth;
        StringBuilder imageLayer = new StringBuilder();
        for (Character coloredPixel : defaultImage) {
            if (layerLineIndex == breakPoint) {
                System.out.println();
                layerLineIndex = 1;
                imageLayer.append("\n");
                imageLayer.append(coloredPixel);
                System.out.print(coloredPixel == '0'
                        ? " "
                        : "X");
                continue;
            } else {
                imageLayer.append(coloredPixel);
                System.out.print(coloredPixel == '0'
                        ? " "
                        : "X");
            }

            layerLineIndex++;
        }

        return imageLayer.toString();
    }

}
