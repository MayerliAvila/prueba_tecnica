package com.prueba.backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;



/**
 * Entidad JPA que representa un beneficio laboral asociado a un empleado.
 *
 * <p>Mapea la tabla {@code benefits} en la base de datos. Un beneficio pertenece
 * a un único empleado (relación muchos-a-uno). El monto del beneficio debe ser
 * un valor positivo mayor que cero.
 */
@Entity
@Table(name = "benefits")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Benefit {

    /** Identificador único autogenerado del beneficio. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre descriptivo del beneficio (ej. "Auxilio de Transporte"). Campo obligatorio. */
    @NotBlank(message = "El nombre del beneficio es obligatorio")
    @Column(nullable = false)
    private String benefitName;

    /** Monto económico del beneficio. Debe ser mayor a cero. */
    @DecimalMin(value = "0.0", inclusive = false, message = "El monto debe ser mayor a 0")
    @Column(nullable = false)
    private BigDecimal amount;

    /**
     * Empleado al que pertenece este beneficio.
     * Clave foránea {@code employee_id} en la tabla {@code benefits}.
     */
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

}
