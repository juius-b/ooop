package ooop.analysis.concurrent;

import ooop.analysis.result.AnalysisResult;
import ooop.analysis.util.Analysis;

import java.nio.file.Path;
import java.util.concurrent.RecursiveTask;

public class FileTask extends RecursiveTask<AnalysisResult> {
    private final Path file;

    public FileTask(Path file) {
        this.file = file;
    }

    @Override
    protected AnalysisResult compute() {
        return Analysis.computeLetterFrequency(file);
    }
}
