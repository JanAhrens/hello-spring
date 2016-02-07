package io.github.janahrens;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    ResponseEntity handleNoHandlerFound(final NoHandlerFoundException exception, final NativeWebRequest request) {
        return ResponseEntity.status(404).contentType(MediaType.APPLICATION_JSON).body("{\"error\":\"NOT_FOUND\"}");
    }
}
