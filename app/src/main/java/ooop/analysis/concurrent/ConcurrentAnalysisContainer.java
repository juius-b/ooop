package ooop.analysis.concurrent;

import ooop.analysis.result.AnalysisResult;

import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class ConcurrentAnalysisContainer {
    private final Set<Future<AnalysisResult>> futureResults = ConcurrentHashMap.newKeySet();
    private final Set<Path> seenFiles = ConcurrentHashMap.newKeySet();
    private final Map<Path, AnalysisResult> resultByFile = new ConcurrentHashMap<>();

    public Set<Future<AnalysisResult>> getFutureResults() {
        return futureResults;
    }

    public Set<Path> getSeenFiles() {
        return seenFiles;
    }

    public Map<Path, AnalysisResult> getResultByFile() {
        return resultByFile;
    }
}
