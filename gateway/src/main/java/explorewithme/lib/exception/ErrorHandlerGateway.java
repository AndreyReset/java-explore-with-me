package explorewithme.lib.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class ErrorHandlerGateway {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<Object> validationErrorResponse(MethodArgumentNotValidException e) {
        ErrorResponse errorResponse = errorResponse(e, HttpStatus.BAD_REQUEST);
        log.info(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, defaultHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public ResponseEntity<Object> validationErrorResponse(IllegalArgumentException e) {
        ErrorResponse errorResponse = errorResponse(e, HttpStatus.BAD_REQUEST);
        log.info(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, defaultHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<Object> validationErrorResponse(ConstraintViolationException e) {
        ErrorResponse errorResponse = errorResponse(e, HttpStatus.BAD_REQUEST);
        log.info(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, defaultHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ObjNotFoundException.class)
    @ResponseBody
    public ResponseEntity<Object> objNotFound(ObjNotFoundException e) {
        ErrorResponse errorResponse = errorResponse(e, HttpStatus.NOT_FOUND);
        log.info(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, defaultHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<Object> badRequest(BadRequestException e) {
        ErrorResponse errorResponse = errorResponse(e, HttpStatus.BAD_REQUEST);
        log.info(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, defaultHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmptyResultDataAccessException.class)
    @ResponseBody
    public ResponseEntity<Object> objNotFound(EmptyResultDataAccessException e) {
        ErrorResponse errorResponse = errorResponse(e, HttpStatus.NOT_FOUND);
        log.info(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, defaultHeaders(), HttpStatus.NOT_FOUND);
    }

    private HttpHeaders defaultHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        headers.set("X-Error", "true");
        return headers;
    }

    private ErrorResponse errorResponse(Exception e, HttpStatus status) {
        return ErrorResponse.builder()
                //.errors()
                .message(e.getMessage())
                .status(status.getReasonPhrase())
                .reason(e.getLocalizedMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }
}
