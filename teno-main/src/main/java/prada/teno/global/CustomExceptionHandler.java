package prada.teno.global;

import io.jsonwebtoken.ExpiredJwtException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import prada.teno.global.exception.DataNotFoundException;
import prada.teno.global.exception.InvalidOldPasswordException;

import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomExceptionHandler {

    // 200(OK)
    @ExceptionHandler({
            InvalidOldPasswordException.class
    })
    public ResponseEntity<ResponseMessage> Ok(final Exception e) {
        return ResponseEntity.ok(
                ResponseMessage.builder()
                        .message(e.getMessage())
                        .build()
        );
    }

    // 401(Unauthorized)
    @ExceptionHandler({
            UsernameNotFoundException.class
            , BadCredentialsException.class
            , ExpiredJwtException.class
    })
    public ResponseEntity<ResponseMessage> Unauthorized(final Exception e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        ResponseMessage.builder()
                                .message(e.getMessage())
                                .build()
                );
    }

    // 404(Not Found)
    @ExceptionHandler({
            IllegalArgumentException.class
            , NoSuchElementException.class
            , DataNotFoundException.class
    })
    public ResponseEntity<ResponseMessage> BadRequestException(final Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ResponseMessage.builder()
                                .message(e.getMessage())
                                .build()
                );
    }

    // 500(Internal Server Error)
    @ExceptionHandler({
            Exception.class
    })
    public ResponseEntity InternalServerError(final Exception e) {
        return ResponseEntity.internalServerError()
                .body(
                        ResponseMessage.builder()
                                .message(e.getMessage())
                                .build()
                );
    }
}
