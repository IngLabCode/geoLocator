package az.naa.geoLocator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.security.ProviderException;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(InvalidIpException.class)
    public ResponseEntity<String> handleInvalidIp(InvalidIpException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ProviderException.class)
    public ResponseEntity<String> handleProviderError(ProviderException ex) {
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ex.getMessage());
    }
}
