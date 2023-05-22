package ooop.analysis.concurrent;

import ooop.analysis.LetterFrequencyAnalysis;
import ooop.analysis.result.AnalysisResult;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

public class ConcurrentLetterFrequencyAnalysis implements LetterFrequencyAnalysis {

    private final ConcurrentAnalysisContainer container = new ConcurrentAnalysisContainer();

    @Override
    public void launch(String... args) {
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        for (String arg : args) {
            Path path = Path.of(arg);
            forkJoinPool.submit(new RecursivePathAction(path, container));
        }

        container.getResultByFile().forEach((p, r) -> System.out.println(p.toString() + ":\t" + r.toString()));
    }

    @Override
    public Map<Path, AnalysisResult> getResultByFile() {
        return container.getResultByFile();
    }
}
