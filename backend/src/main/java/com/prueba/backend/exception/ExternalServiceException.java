package com.prueba.backend.exception;

/**
 * Excepción lanzada cuando una llamada a un servicio externo falla
 * (ej. error de red al consultar la API de Nominatim).
 * Se traduce a una respuesta HTTP 503 Service Unavailable por el {@link GlobalExceptionHandler}.
 */
public class ExternalServiceException extends RuntimeException {

    /**
     * @param message mensaje descriptivo del error en el servicio externo
     */
    public ExternalServiceException(String message) {
        super(message);
    }

    /**
     * @param message mensaje descriptivo del error en el servicio externo
     * @param cause   causa original de la excepción
     */
    public ExternalServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}