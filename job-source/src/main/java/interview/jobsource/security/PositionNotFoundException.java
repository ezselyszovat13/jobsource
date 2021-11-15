package interview.jobsource.security;

public class PositionNotFoundException extends RuntimeException {
    public PositionNotFoundException(String errorMessage) {
        super(errorMessage);
    }
}
