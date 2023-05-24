package ooop.analysis.concurrent;

import ooop.analysis.result.ExceptionAnalysisResult;
import ooop.analysis.util.Analysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.RecursiveAction;
import java.util.stream.Stream;

public class RecursivePathAction extends RecursiveAction {
    private final Path path;
    private final ConcurrentAnalysisContainer container;

    public RecursivePathAction(Path path, ConcurrentAnalysisContainer container) {
        this.path = path;
        this.container = container;
    }

    @Override
    public void compute() {
        if (!container.getSeenPaths().add(path)) {
            return;
        }
        if (Files.isRegularFile(path)) {
            container.getResultByFile().putIfAbsent(path, Analysis.computeLetterFrequency(path));
        } else if (Files.isDirectory(path)) {
            try (Stream<Path> entries = Files.list(path)) {
                entries.forEach(entry -> container.getForkJoinTasks().add(new RecursivePathAction(entry, container).fork()));
            } catch (IOException e) {
                container.getResultByFile().putIfAbsent(path, new ExceptionAnalysisResult(e));
            }
        }
    }
}
