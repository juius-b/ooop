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

}
