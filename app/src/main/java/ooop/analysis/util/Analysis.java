package ooop.analysis.util;

import ooop.analysis.result.AnalysisResult;
import ooop.analysis.result.DistributionAnalysisResult;
import ooop.analysis.result.ExceptionAnalysisResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static ooop.analysis.util.Latin.*;

public class Analysis {
    public static AnalysisResult computeLetterFrequency(Path file) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(file)) {
            int[] distribution = new int[ALPHABET_LENGTH];
            int codePoint;
            while ((codePoint = bufferedReader.read()) != -1) {
                if (isLetter(codePoint)) {
                    distribution[(codePoint & ALPHABET_POSITION_MASK) - 1]++;
                }
            }
            return new DistributionAnalysisResult(distribution);
        } catch (IOException e) {
            return new ExceptionAnalysisResult(e);
        }
    }
}
