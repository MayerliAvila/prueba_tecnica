package com.prueba.backend.dto.ResponseDTO;

import com.prueba.backend.entity.EmployeeStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

/**
 * DTO de salida que representa un empleado en las respuestas de la API.
 *
 * <p>Incluye todos los datos del empleado, sus coordenadas geográficas
 * (obtenidas de Nominatim) y la lista de beneficios asociados.
 */
@Data
public class EmployeeResponseDTO {

    private Long id;

    private String name;

    private String email;

    private String city;

    private String position;

    private String phone;

    private String department;

    private LocalDate hireDate;

    private BigDecimal baseSalary;

    private EmployeeStatus status;

    private Double latitude;

    private Double longitude;

    private List<BenefitResponseDTO> benefits;
}