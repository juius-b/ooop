package ooop.analysis.result;

public final class ExceptionAnalysisResult implements AnalysisResult {
    private final Exception e;

    public ExceptionAnalysisResult(Exception e) {
        this.e = e;
    }

    @Override
    public String toString() {
        return e.toString();
    }
}
