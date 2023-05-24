package ooop.analysis.concurrent;

import ooop.analysis.LetterFrequencyAnalysis;
import ooop.analysis.result.AnalysisResult;

import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

public class ConcurrentLetterFrequencyAnalysis implements LetterFrequencyAnalysis {

    private final ConcurrentAnalysisContainer container = new ConcurrentAnalysisContainer();

    @Override
    public void launch(String... args) {
        ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        for (String arg : args) {
            Path path = Path.of(arg);
            container.getForkJoinTasks().add(forkJoinPool.submit(new RecursivePathAction(path, container)));
        }

        for (ForkJoinTask<?> forkJoinTask : container.getForkJoinTasks()) {
            forkJoinTask.join();
        }

        container.getResultByFile().forEach((p, r) -> System.out.println(p.toString() + ":\t" + r.toString()));
    }

    @Override
    public Map<Path, AnalysisResult> getResultByFile() {
        return container.getResultByFile();
    }
}
