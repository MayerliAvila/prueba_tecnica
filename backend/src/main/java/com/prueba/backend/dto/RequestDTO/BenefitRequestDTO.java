package com.prueba.backend.dto.RequestDTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO de entrada para crear o actualizar un beneficio laboral.
 *
 * <p>Todos los campos son validados con anotaciones de Jakarta Validation
 * antes de llegar a la capa de servicio.
 */
@Data
public class BenefitRequestDTO {

    @NotBlank(message = "El nombre del beneficio es obligatorio")
    private String benefitName;

    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal amount;

    @NotNull(message = "El empleado es obligatorio")
    private Long employeeId;
}
