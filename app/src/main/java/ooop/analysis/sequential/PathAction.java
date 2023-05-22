package ooop.analysis.sequential;

import ooop.analysis.result.ExceptionAnalysisResult;
import ooop.analysis.util.Analysis;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class PathAction {

    public static void launch(SequentialLetterFrequencyAnalysis letterFrequencyAnalysis, Path path) {
        if (Files.isRegularFile(path)) {
            letterFrequencyAnalysis.getResultByFile().computeIfAbsent(path, Analysis::computeLetterFrequency);
        } else if (Files.isDirectory(path)) {
            try (Stream<Path> entries = Files.list(path)) {
                // Recursive call
                entries.forEach(entry -> launch(letterFrequencyAnalysis, entry));
            } catch (IOException e) {
                letterFrequencyAnalysis.getResultByFile().putIfAbsent(path, new ExceptionAnalysisResult(e));
            }
        }
    }
}
