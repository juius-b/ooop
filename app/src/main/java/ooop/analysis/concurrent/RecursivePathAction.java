package ooop.analysis.concurrent;

import ooop.analysis.result.AnalysisResult;
import ooop.analysis.result.ExceptionAnalysisResult;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.Future;
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
    protected void compute() {
        if (Files.isRegularFile(path)) {
            if (!container.getSeenFiles().add(path)) {
                container.getFutureResults().add(new FileTask(path));
            }
        } else if (Files.isDirectory(path)) {
            try (Stream<Path> entries = Files.list(path)) {
                entries.forEach(entry -> new RecursivePathAction(path, container));
            } catch (IOException e) {
                container.getResultByFile().putIfAbsent(path, new ExceptionAnalysisResult(e));
            }
        }
    }
}
