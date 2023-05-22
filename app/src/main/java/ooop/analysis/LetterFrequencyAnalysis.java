package ooop.analysis;

import ooop.analysis.result.AnalysisResult;

import java.nio.file.Path;
import java.util.Map;

public interface LetterFrequencyAnalysis {
    void launch(String... args);
    Map<Path, AnalysisResult> getResultByFile();
}