package com.carlog.backend.error;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Hidden
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String KEY_MESSAGE = "message";
    private static final String KEY_STATUS = "status";
    private static final String KEY_TIMESTAMP = "timestamp";

    private ResponseEntity<Map<String, Object>> buildResponse(String message, HttpStatus status) {
        Map<String, Object> response = new HashMap<>();
        response.put(KEY_MESSAGE, message);
        response.put(KEY_STATUS, status.value());
        response.put(KEY_TIMESTAMP, System.currentTimeMillis());
        return ResponseEntity.status(status).body(response);
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleVehicleNotFound(VehicleNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(UserNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WorkOrderNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleWorkOrderNotFound(WorkOrderNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WorkshopNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleWorkshopNotFound(WorkshopNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(WorkOrderLineNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleWorkOrderLineNotFound(WorkOrderLineNotFoundException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            WorkshopNotAssignedException.class,
            VehicleNotInWorkshopException.class,
            ClosedWorkOrderException.class,
            WorkOrderLineMismatchException.class,
            MechanicNotInWorkshopException.class,
            NoPendingRequestException.class,
            InvalidSearchTypeException.class,
            InvalidRegistrationException.class,
            NoPendingInvitationException.class
    })
    public ResponseEntity<Map<String, Object>> handleBusinessRulesExceptions(RuntimeException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnauthorizedActionException.class)
    public ResponseEntity<Map<String, Object>> handleUnauthorizedAction(UnauthorizedActionException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler({
            VehicleOcuppiedException.class,
            VehicleAlreadyExistsException.class,
            UserAlreadyExistsException.class,
            UserAlreadyInWorkshopException.class,
            WorkshopAlreadyExistsException.class,
            UserAlreadyHasWorkshopException.class
    })
    public ResponseEntity<Map<String, Object>> handleConflictExceptions(RuntimeException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentialsException(BadCredentialsException ex) {
        return buildResponse("Bad credentials", HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(RateLimitExceededException.class)
    public ResponseEntity<Map<String, Object>> handleRateLimitExceededException(RateLimitExceededException ex) {
        return buildResponse(ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        log.error("Error interno no controlado: ", ex);

        return buildResponse("Error interno del servidor. Por favor, contacte con soporte técnico.",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}