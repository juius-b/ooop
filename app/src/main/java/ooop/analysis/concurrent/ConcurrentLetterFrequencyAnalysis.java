package ooop.analysis.concurrent;

import ooop.analysis.LetterFrequencyAnalysis;
import ooop.analysis.result.AnalysisResult;
import ooop.analysis.sequential.PathAction;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ConcurrentLetterFrequencyAnalysis implements LetterFrequencyAnalysis {
    private final Set<Path> seenFiles = new ConcurrentSkipListSet<>();
    private final Set<Future<AnalysisResult>> futureResults = new ConcurrentSkipListSet<>();
    private final Map<Path, AnalysisResult> resultByFile = new ConcurrentHashMap<>();

    @Override
    public void launch(String... args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        for (String arg : args) {
            Path path = Path.of(arg);
        }

        resultByFile.forEach((p, r) -> System.out.println(p.toString() + ":\t" + r.toString()));
    }

    public Map<Path, AnalysisResult> getResultByFile() {
        return resultByFile;
    }

    public Set<Path> getSeenFiles() {
        return seenFiles;
    }

    public Set<Future<AnalysisResult>> getFutureResults() {
        return futureResults;
    }
}
