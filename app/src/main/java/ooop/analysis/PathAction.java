package ooop.analysis;

import ooop.analysis.results.ExceptionAnalysisResult;

import java.io.IOException;
import java.nio.file.*;
import java.util.stream.Stream;

public class PathAction {

    public static void launch(LetterFrequencyAnalysis letterFrequencyAnalysis,Path path) {
        if (Files.isRegularFile(path)) {
            letterFrequencyAnalysis.getResultByFile().computeIfAbsent(path, LetterFrequencyAnalysis::ofFile);
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
