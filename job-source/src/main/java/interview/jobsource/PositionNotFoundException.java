package interview.jobsource;

public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
