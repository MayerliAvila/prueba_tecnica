package com.prueba.backend.dto.ResponseDTO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO de salida que representa un beneficio en las respuestas de la API.
 * Incluye el ID del empleado al que pertenece el beneficio.
 */
@Data
public class BenefitResponseDTO {

    private Long id;

    private String benefitName;

    private BigDecimal amount;

    private Long employeeId;
}
