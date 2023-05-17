package ooop.analysis.results;

public final class ExceptionAnalysisResult extends AnalysisResult {
    private final Exception e;

    public ExceptionAnalysisResult(Exception e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return e.toString();
    }
}
