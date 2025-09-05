package az.naa.geoLocator.exception;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProviderException extends RuntimeException {
    public ProviderException(String message, Throwable cause) {
        super(message, cause);
    }


    public ProviderException(String s) {

    }
}
