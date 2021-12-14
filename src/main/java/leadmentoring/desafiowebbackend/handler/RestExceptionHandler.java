package leadmentoring.desafiowebbackend.handler;

import leadmentoring.desafiowebbackend.exception.*;
import leadmentoring.desafiowebbackend.exception.badRequest.BadRequestException;
import leadmentoring.desafiowebbackend.exception.badRequest.BadRequestExceptionDetails;
import leadmentoring.desafiowebbackend.exception.notFound.NotFoundException;
import leadmentoring.desafiowebbackend.exception.notFound.NotFoundExceptionDetails;
import leadmentoring.desafiowebbackend.exception.forbidden.ForbiddenException;
import leadmentoring.desafiowebbackend.exception.forbidden.ForbiddenExceptionDetails;
import leadmentoring.desafiowebbackend.exception.unauthorized.UnauthorizedException;
import leadmentoring.desafiowebbackend.exception.unauthorized.UnauthorizedExceptionDetails;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<NotFoundExceptionDetails> handlerNotFoundException(NotFoundException notFoundException){
        return new ResponseEntity<>(
                NotFoundExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .message(notFoundException.getMessage())
                        .error("Not Found")
                        .build(),HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<BadRequestExceptionDetails> handlerBadRequestException(BadRequestException badRequestException){
        return new ResponseEntity<>(
                BadRequestExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.NOT_FOUND.value())
                        .message(badRequestException.getMessage())
                        .error("Not Found")
                        .build(),HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(ForbiddenException.class)
    public ResponseEntity<ForbiddenExceptionDetails> handlerForbiddenException(ForbiddenException forbiddenException){
        return new ResponseEntity<>(
                ForbiddenExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.FORBIDDEN.value())
                        .message(forbiddenException.getMessage())
                        .error("O acesso à página ou recurso que você estava tentando acessar é proibido.")
                        .build(),HttpStatus.FORBIDDEN
        );
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<UnauthorizedExceptionDetails> handlerUnauthorizedException(UnauthorizedException unauthorizedException){
        return new ResponseEntity<>(
                UnauthorizedExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.UNAUTHORIZED.value())
                        .message(unauthorizedException.getMessage())
                        .error("Solicitação não foi aplicada porque não possui credenciais de autenticação válidas")
                        .build(),HttpStatus.UNAUTHORIZED
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionDetails> handlerMethodArgumentNotValidException
            (MethodArgumentNotValidException methodArgumentNotValidException){

        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        String fields = fieldErrors.stream().map(FieldError::getField).collect(Collectors.joining(", "));
        String messages = fieldErrors.stream().map(FieldError::getDefaultMessage).collect(Collectors.joining(", "));

        return new ResponseEntity<>(
                ValidationExceptionDetails.builder()
                        .timestamp(LocalDateTime.now())
                        .statusCode(HttpStatus.BAD_REQUEST.value())
                        .message("Simplificando methodArgumentNotValidException.getMessage()")
                        .error("Method Argument Not Valid")
                        .fields(fields)
                        .fieldsMessage(messages)
                        .build(),HttpStatus.BAD_REQUEST
        );
    }

}
