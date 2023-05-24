package ooop.analysis.sequential;

import ooop.analysis.LetterFrequencyAnalysis;
import ooop.analysis.result.AnalysisResult;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class SequentialLetterFrequencyAnalysis implements LetterFrequencyAnalysis {
    private final Map<Path, AnalysisResult> resultByFile = new HashMap<>();

    public Map<Path, AnalysisResult> getResultByFile() {
        return resultByFile;
    }

    public void launch(String... args) {
        for (String arg : args) {
            Path path = Path.of(arg);
            PathAction.launch(this, path);
        }

        resultByFile.forEach((p, r) -> System.out.println(p.toString() + ":\t" + r.toString()));
    }

}
