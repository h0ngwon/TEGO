package prada.teno.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DataNotFoundException extends RuntimeException {

    private final String message;
}
