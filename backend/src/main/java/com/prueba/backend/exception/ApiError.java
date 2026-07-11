package com.prueba.backend.exception;

import lombok.*;

import java.time.LocalDateTime;

/**
 * DTO que representa la estructura uniforme de un error en la API REST.
 *
 * <p>Todas las respuestas de error de la API retornan este objeto en el cuerpo,
 * facilitando que el cliente identifique el momento, código y mensaje del error.
 */
@Data
@Builder
@AllArgsConstructor
public class ApiError {

    /** Fecha y hora en que ocurrió el error. */
    private LocalDateTime timestamp;
    /** Código de estado HTTP numérico (ej. 404, 409, 500). */
    private int status;
    /** Descripción estándar del código HTTP (ej. "Not Found", "Conflict"). */
    private String error;
    /** Mensaje legible que explica la causa del error. */
    private String message;

}
