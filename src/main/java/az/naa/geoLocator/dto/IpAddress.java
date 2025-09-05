package az.naa.geoLocator.dto;

import az.naa.geoLocator.exception.InvalidIpException;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.InetAddress;

@Data
@AllArgsConstructor
public class IpAddress {
    private final String value;

    public static IpAddress of(String value) {
        try {
            InetAddress.getByName(value);
            return new IpAddress(value);
        } catch (Exception e) {
            throw new InvalidIpException("Invalid ip: " + value);
        }
    }
}
