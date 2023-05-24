package ooop.analysis.concurrent;

import ooop.analysis.result.AnalysisResult;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ForkJoinTask;

public class ConcurrentAnalysisContainer {
    private final Set<ForkJoinTask<?>> forkJoinTasks = ConcurrentHashMap.newKeySet();
    private final Set<Path> seenPaths = ConcurrentHashMap.newKeySet();
    private final Map<Path, AnalysisResult> resultByFile = new ConcurrentHashMap<>();

    public Set<ForkJoinTask<?>> getForkJoinTasks() {
        return forkJoinTasks;
    }

    public Set<Path> getSeenPaths() {
        return seenPaths;
    }

    public Map<Path, AnalysisResult> getResultByFile() {
        return resultByFile;
    }
}
