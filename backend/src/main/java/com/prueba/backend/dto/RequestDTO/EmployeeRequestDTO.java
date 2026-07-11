package com.prueba.backend.dto.RequestDTO;

import com.prueba.backend.entity.EmployeeStatus;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO de entrada para crear o actualizar un empleado.
 *
 * <p>Todos los campos son validados con anotaciones de Jakarta Validation
 * antes de llegar a la capa de servicio. Los datos son enviados en el cuerpo
 * de las peticiones POST y PUT a {@code /api/employees}.
 */
@Data
public class EmployeeRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String name;

    @Email(message = "Correo invalido")
    @NotBlank(message = "El correo es obligatorio")
    private String email;

    @NotBlank(message = "La ciudad es obligadoria")
    private String city;

    @NotBlank(message = "El cargo es obligatorio")
    private String position;

    @NotBlank(message = "El telefono es obligatorio")
    private String phone;

    @NotBlank(message = "El departamento es obligatorio")
    private String department;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @PastOrPresent(message = "La fecha de ingreso no puede ser futura")
    private LocalDate hireDate;

    @NotNull(message = "El salario es obligatorio")
    @Positive(message = "El salario debe ser mayor a cero")
    private BigDecimal baseSalary;

    private EmployeeStatus status;

}