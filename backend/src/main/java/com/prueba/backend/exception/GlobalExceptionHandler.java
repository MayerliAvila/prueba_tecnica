package com.prueba.backend.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Manejador global de excepciones para toda la API REST.
 *
 * <p>Intercepta las excepciones lanzadas por los controladores y las convierte
 * en respuestas HTTP estructuradas usando {@link ApiError}. Registrado como
 * {@code @RestControllerAdvice} para que aplique a todos los controladores.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    /**
     * Maneja recursos no encontrados: responde con 404 Not Found.
     *
     * @param ex excepción capturada
     * @return {@link ApiError} con estado 404
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiError> handleNotFound(ResourceNotFoundException ex) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error(HttpStatus.NOT_FOUND.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    /**
     * Maneja recursos duplicados: responde con 409 Conflict.
     *
     * @param ex excepción capturada
     * @return {@link ApiError} con estado 409
     */
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ApiError> handleDuplicate(DuplicateResourceException ex) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.CONFLICT.value())
                .error(HttpStatus.CONFLICT.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
    }

    /**
     * Maneja errores de validación de campos (Jakarta Validation): responde con 400 Bad Request.
     * Extrae el mensaje del primer campo con error.
     *
     * @param ex excepción de validación capturada
     * @return {@link ApiError} con estado 400 y el mensaje del primer error
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidation(MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult()
                .getFieldError()
                .getDefaultMessage();

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(message)
                .build();

        return ResponseEntity.badRequest().body(error);
    }

    /**
     * Manejador genérico de último recurso: responde con 500 Internal Server Error.
     *
     * @param ex cualquier excepción no capturada por otros handlers
     * @return {@link ApiError} con estado 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneral(Exception ex) {

        ApiError error = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .message(ex.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
    /**
     * Maneja fallos en servicios externos (ej. Nominatim): responde con 503 Service Unavailable.
     *
     * @param ex excepción del servicio externo
     * @return {@link ApiError} con estado 503
     */
    @ExceptionHandler(ExternalServiceException.class)
    public ResponseEntity<ApiError> handleExternalServiceException(ExternalServiceException ex) {
        ApiError apiError = ApiError.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.SERVICE_UNAVAILABLE.value())
                .error("Service Unavailable")
                .message(ex.getMessage())
                .build();

        return new ResponseEntity<>(apiError, HttpStatus.SERVICE_UNAVAILABLE);
    }

}