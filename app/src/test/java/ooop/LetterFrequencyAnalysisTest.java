package ooop;

import ooop.analysis.LetterFrequencyAnalysis;
import ooop.analysis.concurrent.ConcurrentLetterFrequencyAnalysis;
import ooop.analysis.result.AnalysisResult;
import ooop.analysis.result.DistributionAnalysisResult;
import ooop.analysis.sequential.SequentialLetterFrequencyAnalysis;
import ooop.analysis.util.Latin;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LetterFrequencyAnalysisTest {
    private final ClassLoader classLoader = getClass().getClassLoader();
    private Path emptyDirPath;
    private static int[] distributionOfTestFile() {
        int[] distribution = new int[Latin.ALPHABET_LENGTH];
        Arrays.fill(distribution, 1);
        for (char c : Set.of('R', 'U', 'H', 'T')) {
            distribution[indexOfLetter(c)] = 2;
        }
        distribution[indexOfLetter('E')] = 3;
        distribution[indexOfLetter('O')] = 4;
        return distribution;
    }

    private static int indexOfLetter(char ch) {
        return ch - 'A';
    }

    public static Stream<Arguments> letterFrequencyAnalysisProvider() {
        return Stream.of(new SequentialLetterFrequencyAnalysis(), new ConcurrentLetterFrequencyAnalysis()).map(Arguments::of);
    }

    @BeforeEach
    void createEmptyDir() throws IOException, URISyntaxException {
        URI emptyDirParentURI = classLoader.getResource("data").toURI();
        Path emptyDirParentPath = Path.of(emptyDirParentURI);
        emptyDirPath = Files.createTempDirectory(emptyDirParentPath, "empty");
    }

    @AfterEach
    void deleteEmptyDir() throws IOException {
        Files.delete(emptyDirPath);
    }
    @ParameterizedTest
    @MethodSource("letterFrequencyAnalysisProvider")
    void testEmptyDirectoryLetterFrequencyAnalysisYieldsNoResults(LetterFrequencyAnalysis analysis) {
        Map<Path, AnalysisResult> expectedResultMap = Collections.emptyMap();

        analysis.launch(emptyDirPath.toString());

        assertEquals(expectedResultMap, analysis.getResultByFile());
    }

    @ParameterizedTest
    @MethodSource("letterFrequencyAnalysisProvider")
    void testEmptyFileLetterFrequencyAnalysisYieldsEmptyDistributionResult(LetterFrequencyAnalysis analysis) throws URISyntaxException {
        URI emptyFileURI = classLoader.getResource("data/empty.txt").toURI();
        Path emptyFilePath = Path.of(emptyFileURI);
        Map<Path, AnalysisResult> expectedResultMap = Map.of(
                emptyFilePath, new DistributionAnalysisResult(new int[Latin.ALPHABET_LENGTH])
        );

        analysis.launch(emptyFilePath.toString());

        assertEquals(expectedResultMap, analysis.getResultByFile());
    }

    @ParameterizedTest
    @MethodSource("letterFrequencyAnalysisProvider")
    void testFileLetterFrequencyAnalysisYieldsNonEmptyDistributionResult(LetterFrequencyAnalysis analysis) throws URISyntaxException {
        URI testFileURI = classLoader.getResource("data/test.txt").toURI();
        Path testFilePath = Path.of(testFileURI);
        Map<Path, AnalysisResult> expectedResultMap = Map.of(
                testFilePath, new DistributionAnalysisResult(distributionOfTestFile())
        );

        analysis.launch(testFilePath.toString());

        assertEquals(expectedResultMap, analysis.getResultByFile());
    }

    @ParameterizedTest
    @MethodSource("letterFrequencyAnalysisProvider")
    void testLetterFrequencyAnalysis(LetterFrequencyAnalysis analysis) throws URISyntaxException {
        URI emptyFileURI = classLoader.getResource("data/empty.txt").toURI();
        URI testFileURI = classLoader.getResource("data/test.txt").toURI();
        Map<Path, AnalysisResult> expectedResultMap = Map.of(
                Path.of(emptyFileURI), new DistributionAnalysisResult(new int[Latin.ALPHABET_LENGTH]),
                Path.of(testFileURI), new DistributionAnalysisResult(distributionOfTestFile()));

        URI testDirectoryURI = classLoader.getResource("data").toURI();
        Path testDirectoryPath = Path.of(testDirectoryURI);
        analysis.launch(testDirectoryPath.toString());

        assertEquals(expectedResultMap, analysis.getResultByFile());
    }
}
