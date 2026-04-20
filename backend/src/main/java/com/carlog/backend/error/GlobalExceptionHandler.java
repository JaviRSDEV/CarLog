package com.carlog.backend.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status){
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", status.value());
        response.put("timestamp", System.currentTimeMillis());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleVehicleNotFound(VehicleNotFoundException ex){
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex){
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(VehicleOcuppiedException.class)
    public ResponseEntity<Map<String, Object>> handleVehicleOccupied(VehicleOcuppiedException ex){
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(WorkOrderNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleWorkOrderNotFound(WorkOrderNotFoundException ex){
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException ex){
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of(
                       "message", "Bad credentials",
                       "status", 401,
                       "timestamp", System.currentTimeMillis()
                )
        );
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<Object> handleRateLimitExceededException(RateLimitExceededException ex){
        return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(
                Map.of(
                        "message", ex.getMessage(),
                        "status", 429,
                        "timestamp", System.currentTimeMillis()
                )
        );
    }

    @ExceptionHandler(WorkshopNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleWorkshopNotFound(WorkshopNotFoundException ex){
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleAllErrors(Exception ex){
        return buildResponse("Error interno: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
