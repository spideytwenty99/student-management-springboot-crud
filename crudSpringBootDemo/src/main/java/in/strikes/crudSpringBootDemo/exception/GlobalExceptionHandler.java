package in.strikes.crudSpringBootDemo.exception;


import in.strikes.crudSpringBootDemo.dto.ExceptionResponseDto;
import in.strikes.crudSpringBootDemo.dto.ValidationExceptionResponseDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionResponseDto> handleResourceNotFoundException(RuntimeException ex, HttpServletRequest request) {
        ExceptionResponseDto exceptionReponse = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()


        );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exceptionReponse);

    }

    @ExceptionHandler(DuplicateRsourceException.class)
    public ResponseEntity<ExceptionResponseDto> handleDuplicateResourceNotFoundException(DuplicateRsourceException ex, HttpServletRequest request) {

        ExceptionResponseDto exceptionReponse = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                HttpStatus.CONFLICT.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()


        );

        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exceptionReponse);

    } @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationExceptionResponseDto> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex, HttpServletRequest request) {


        Map<String, String> fieldErrors= new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error ->
                        fieldErrors.put(error.getField(), error.getDefaultMessage()));

        ValidationExceptionResponseDto exceptionReponse = new ValidationExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                "Validation Failed",
                request.getRequestURI(),
                fieldErrors



        );

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(exceptionReponse);

    }



    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ExceptionResponseDto> handleRuntimeException(RuntimeException ex, HttpServletRequest request) {
        ExceptionResponseDto exceptionReponse = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()


        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionReponse);

    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDto> handleGenereicException(Exception ex, HttpServletRequest request) {
        ExceptionResponseDto exceptionReponse = new ExceptionResponseDto(
                LocalDateTime.now(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI()


        );

        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(exceptionReponse);

    }
}





