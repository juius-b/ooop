package ooop.analysis.concurrent;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.concurrent.RecursiveAction;

public class RecursivePathAction extends RecursiveAction {
    private final ConcurrentLetterFrequencyAnalysis concurrentLetterFrequencyAnalysis;
    private final Path path;

    public RecursivePathAction(ConcurrentLetterFrequencyAnalysis concurrentLetterFrequencyAnalysis, Path path) {
        this.concurrentLetterFrequencyAnalysis = concurrentLetterFrequencyAnalysis;
        this.path = path;
    }

    @Override
    protected void compute() {
        Set<Path> seenFiles = concurrentLetterFrequencyAnalysis.getSeenFiles();
        if (Files.isRegularFile(path)) {
            seenFiles.add(path);
        }
    }
}
