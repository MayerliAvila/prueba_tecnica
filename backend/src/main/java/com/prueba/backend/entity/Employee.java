package com.prueba.backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad JPA que representa un empleado de la empresa.
 *
 * <p>Mapea la tabla {@code employees} en la base de datos. Utiliza Lombok para
 * reducir el código boilerplate (getters, setters, constructores y builder).
 * Mantiene una relación uno-a-muchos con la entidad {@link Benefit}.
 */
@Entity
@Table(name = "employees")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    /** Identificador único autogenerado del empleado. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Nombre completo del empleado. Campo obligatorio. */
    @NotBlank(message = "El nombre es obligatorio")
    @Column(nullable = false)
    private String name;

    /** Correo electrónico único del empleado. Campo obligatorio. */
    @NotBlank(message = "El email es obligatorio")
    @Column(nullable = false, unique = true)
    private String email;

    /** Ciudad de residencia o trabajo del empleado. Campo obligatorio. */
    @NotBlank(message = "La ciudad es obligatoria")
    @Column(nullable = false)
    private String city;

    /** Cargo o posición del empleado dentro de la empresa. Campo obligatorio. */
    @NotBlank(message = "El cargo es obligatorio")
    @Column(nullable = false)
    private String position;

    /** Número de teléfono de contacto del empleado. */
    @Column(name = "phone")
    private String phone;

    /** Departamento o área al que pertenece el empleado. */
    @Column(name = "department")
    private String department;

    /** Fecha en que el empleado inició en la empresa. */
    @Column(name = "hire_date")
    private LocalDate hireDate;

    /** Salario base mensual del empleado, con hasta 12 dígitos y 2 decimales. */
    @Column(name = "base_salary", precision = 12, scale = 2)
    private BigDecimal baseSalary;

    /**
     * Estado laboral actual del empleado.
     * Por defecto es {@link EmployeeStatus#ACTIVO}.
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private EmployeeStatus status = EmployeeStatus.ACTIVO;


    /**
     * Latitud geográfica de la ciudad del empleado.
     * Obtenida automáticamente a través de la API de Nominatim.
     */
    @Column(name = "latitude")
    private Double latitude;

    /**
     * Longitud geográfica de la ciudad del empleado.
     * Obtenida automáticamente a través de la API de Nominatim.
     */
    @Column(name = "longitude")
    private Double longitude;

    /**
     * Lista de beneficios asociados a este empleado.
     * La relación es uno-a-muchos con eliminación en cascada (orphanRemoval).
     */
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @Builder.Default
    private List<Benefit> benefits = new ArrayList<>();
}