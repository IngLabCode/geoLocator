package az.naa.geoLocator.exception;

public class RateLimitException extends RuntimeException {
    public RateLimitException(String message,Throwable cause) {
        super(message);
    }
}