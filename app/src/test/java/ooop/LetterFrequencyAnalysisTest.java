package ooop;

import ooop.analysis.LetterFrequencyAnalysis;
import ooop.analysis.concurrent.ConcurrentLetterFrequencyAnalysis;
import ooop.analysis.result.AnalysisResult;
import ooop.analysis.result.DistributionAnalysisResult;
import ooop.analysis.sequential.SequentialLetterFrequencyAnalysis;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LetterFrequencyAnalysisTest {
    private final ClassLoader classLoader = getClass().getClassLoader();

    private static int[] distributionOfTestFile() {
        int[] distribution = new int[SequentialLetterFrequencyAnalysis.ALPHABET_LENGTH];
        Arrays.fill(distribution, 1);
        for (char c : Set.of('R', 'U', 'H', 'T')) {
            distribution[c - 'A'] = 2;
        }
        distribution['E' - 'A'] = 3;
        distribution['O' - 'A'] = 4;
        return distribution;
    }

    public static Stream<Arguments> letterFrequencyAnalysisProvider() {
        return Stream.of(new SequentialLetterFrequencyAnalysis(), new ConcurrentLetterFrequencyAnalysis()).map(Arguments::of);
    }

    @ParameterizedTest
    @MethodSource("letterFrequencyAnalysisProvider")
    void testEmptyDirectoryLetterFrequencyAnalysisYieldsNoResults(LetterFrequencyAnalysis analysis) {
        Map<Path, AnalysisResult> expectedResultMap = Collections.emptyMap();
        String emptyDirPath = classLoader.getResource("data/empty").getPath();

        analysis.launch(emptyDirPath);

        assertEquals(expectedResultMap, analysis.getResultByFile());
    }

    @ParameterizedTest
    @MethodSource("letterFrequencyAnalysisProvider")
    void testEmptyFileLetterFrequencyAnalysisYieldsEmptyDistributionResult(LetterFrequencyAnalysis analysis) {
        String emptyFilePath = classLoader.getResource("data/empty.txt").getPath();
        Map<Path, AnalysisResult> expectedResultMap = Map.of(
                Path.of(emptyFilePath), new DistributionAnalysisResult(new int[SequentialLetterFrequencyAnalysis.ALPHABET_LENGTH])
        );

        analysis.launch(emptyFilePath);

        assertEquals(expectedResultMap, analysis.getResultByFile());
    }

    @ParameterizedTest
    @MethodSource("letterFrequencyAnalysisProvider")
    void testNonEmptyFileLetterFrequencyAnalysisYieldsNonEmptyDistributionResult(LetterFrequencyAnalysis analysis) {
        String testFilePath = classLoader.getResource("data/test.txt").getPath();
        Map<Path, AnalysisResult> expectedResultMap = Map.of(
                Path.of(testFilePath), new DistributionAnalysisResult(distributionOfTestFile())
        );

        analysis.launch(testFilePath);

        assertEquals(expectedResultMap, analysis.getResultByFile());
    }

    @ParameterizedTest
    @MethodSource("letterFrequencyAnalysisProvider")
    void testLetterFrequencyAnalysis(LetterFrequencyAnalysis analysis) {
        String emptyFilePath = classLoader.getResource("data/empty.txt").getPath();
        String testFilePath = classLoader.getResource("data/test.txt").getPath();
        Map<Path, AnalysisResult> expectedResultMap = Map.of(
                Path.of(emptyFilePath), new DistributionAnalysisResult(new int[SequentialLetterFrequencyAnalysis.ALPHABET_LENGTH]),
                Path.of(testFilePath), new DistributionAnalysisResult(distributionOfTestFile()));

        analysis.launch(classLoader.getResource("data").getPath());

        assertEquals(expectedResultMap, analysis.getResultByFile());
    }
}
