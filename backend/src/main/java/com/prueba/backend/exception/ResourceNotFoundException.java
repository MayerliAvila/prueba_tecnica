package com.prueba.backend.exception;

/**
 * Excepción lanzada cuando se intenta acceder a un recurso que no existe en la base de datos.
 * Se traduce a una respuesta HTTP 404 Not Found por el {@link GlobalExceptionHandler}.
 */
public class ResourceNotFoundException extends RuntimeException {
    /**
     * @param message mensaje descriptivo del recurso no encontrado
     */
    public ResourceNotFoundException(String message){
        super(message);
    }
}
