package leadmentoring.desafiowebbackend.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected int statusCode;
    protected String message;
    protected String error;
    protected LocalDateTime timestamp;
}
