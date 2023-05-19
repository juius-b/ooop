package ooop.analysis.result;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static ooop.analysis.util.Latin.*;

public interface AnalysisResult {
    static AnalysisResult of(Path file) {
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
