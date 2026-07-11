package com.prueba.backend.exception;

/**
 * Excepción lanzada cuando se intenta crear un recurso que ya existe
 * (ej. correo electrónico duplicado).
 * Se traduce a una respuesta HTTP 409 Conflict por el {@link GlobalExceptionHandler}.
 */
public class DuplicateResourceException extends RuntimeException{
    /**
     * @param message mensaje descriptivo del conflicto detectado
     */
    public DuplicateResourceException(String message){
        super(message);
    }
}
