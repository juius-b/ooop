package ooop;

import ooop.analysis.LetterFrequencyAnalysis;
import ooop.analysis.results.AnalysisResult;
import ooop.analysis.results.DistributionAnalysisResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URISyntaxException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LetterFrequencyAnalysisTest {
    private ClassLoader classLoader = getClass().getClassLoader();
    private LetterFrequencyAnalysis letterFrequencyAnalysis;

    private static int[] distributionOfTestFile() {
        int[] distribution = new int[LetterFrequencyAnalysis.ALPHABET_LENGTH];
        Arrays.fill(distribution, 1);
        for (char c : Set.of('R', 'U', 'H', 'T')) {
            distribution[c - 'A'] = 2;
        }
        distribution['E' - 'A'] = 3;
        distribution['O' - 'A'] = 4;
        return distribution;
    }

    @BeforeEach void beforeEach() throws URISyntaxException {
        letterFrequencyAnalysis = new LetterFrequencyAnalysis();
    }
    @Test void testEmptyDirectoryLetterFrequencyAnalysisYieldsNoResults() {
        Map<Path, AnalysisResult> expectedResultMap = Collections.emptyMap();
        String emptyDirPath = classLoader.getResource("data/empty").getPath();

        letterFrequencyAnalysis.launch(emptyDirPath);

        assertEquals(expectedResultMap, letterFrequencyAnalysis.getResultByFile());
    }

    @Test void testEmptyFileLetterFrequencyAnalysisYieldsEmptyDistributionResult() {
        String emptyFilePath = classLoader.getResource("data/empty.txt").getPath();
        Map<Path, AnalysisResult> expectedResultMap = Map.of(
                Path.of(emptyFilePath), new DistributionAnalysisResult(new int[LetterFrequencyAnalysis.ALPHABET_LENGTH])
        );

        letterFrequencyAnalysis.launch(emptyFilePath);

        assertEquals(expectedResultMap, letterFrequencyAnalysis.getResultByFile());
    }

    @Test void testNonEmptyFileLetterFrequencyAnalysisYieldsNonEmptyDistributionResult() {
        String testFilePath = classLoader.getResource("data/test.txt").getPath();
        Map<Path, AnalysisResult> expectedResultMap = Map.of(
                Path.of(testFilePath), new DistributionAnalysisResult(distributionOfTestFile())
        );

        letterFrequencyAnalysis.launch(testFilePath);

        assertEquals(expectedResultMap, letterFrequencyAnalysis.getResultByFile());
    }

    @Test void testLetterFrequencyAnalysis() {
        String emptyFilePath = classLoader.getResource("data/empty.txt").getPath();
        String testFilePath = classLoader.getResource("data/test.txt").getPath();
        Map<Path, AnalysisResult> expectedResultMap = Map.of(
                Path.of(emptyFilePath), new DistributionAnalysisResult(new int[LetterFrequencyAnalysis.ALPHABET_LENGTH]),
                Path.of(testFilePath), new DistributionAnalysisResult(distributionOfTestFile()));

        letterFrequencyAnalysis.launch(classLoader.getResource("data").getPath());

        assertEquals(expectedResultMap, letterFrequencyAnalysis.getResultByFile());
    }
}
