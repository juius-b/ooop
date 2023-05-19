package ooop.analysis.result;

import java.util.Arrays;

public final class DistributionAnalysisResult implements AnalysisResult {
    private final int[] distribution;

    public DistributionAnalysisResult(int[] distribution) {
        this.distribution = distribution;
    }

    @Override
    public String toString() {
        return Arrays.stream(distribution).summaryStatistics().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DistributionAnalysisResult that = (DistributionAnalysisResult) o;
        return Arrays.equals(distribution, that.distribution);
    }
}
