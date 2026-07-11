package com.prueba.backend.entity;

/**
 * Enumeración que representa los posibles estados laborales de un empleado.
 *
 * <p>Se almacena como {@code STRING} en la base de datos.
 */
public enum EmployeeStatus {
    /** El empleado está activo y trabajando normalmente. */
    ACTIVO,
    /** El empleado ha sido dado de baja o desactivado en el sistema. */
    INACTIVO,
    /** El empleado se encuentra en periodo de vacaciones. */
    VACACIONES,
    /** El empleado se encuentra con licencia (médica, maternidad, etc.). */
    LICENCIA
}