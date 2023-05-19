package ooop.analysis.sequential;

import ooop.analysis.LetterFrequencyAnalysis;
import ooop.analysis.result.AnalysisResult;
import ooop.analysis.result.DistributionAnalysisResult;
import ooop.analysis.result.ExceptionAnalysisResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class SequentialLetterFrequencyAnalysis implements LetterFrequencyAnalysis {
    public static final int ALPHABET_LENGTH = 'Z' - 'A' + 1;
    private static final int ALPHABET_POSITION_MASK = 0b11111;
    private final Map<Path, AnalysisResult> resultByFile = new HashMap<>();

    public Map<Path, AnalysisResult> getResultByFile() {
        return resultByFile;
    }

    public void launch(String... args) {
        for (String arg : args) {
            Path path = Path.of(arg);
            PathAction.launch(this, path);
        }

        resultByFile.forEach((p, r) -> System.out.println(p.toString() + ":\t" + r.toString()));
    }

    public static AnalysisResult ofFile(Path file) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(file)) {
            int[] distribution = new int[ALPHABET_LENGTH];
            int charValue;
            while ((charValue = bufferedReader.read()) != -1) {
                if (isLetter(charValue)) {
                    distribution[(charValue & ALPHABET_POSITION_MASK) - 1]++;
                }
            }
            return new DistributionAnalysisResult(distribution);
        } catch (IOException e) {
            return new ExceptionAnalysisResult(e);
        }
    }

    private static boolean isLetter(int charValue) {
        return isLowercaseLetter(charValue) || isUppercaseLetter(charValue);
    }

    private static boolean isLowercaseLetter(int charValue) {
        return charValue >= 'a' && charValue <= 'z';
    }
    private static boolean isUppercaseLetter(int charValue) {
        return charValue >= 'A' && charValue <= 'Z';
    }

}
