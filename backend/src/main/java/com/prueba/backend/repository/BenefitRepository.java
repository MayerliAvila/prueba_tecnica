package com.prueba.backend.repository;

import com.prueba.backend.entity.Benefit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad {@link Benefit}.
 *
 * <p>Extiende {@link JpaRepository} para heredar las operaciones CRUD estándar.
 * Agrega una consulta personalizada para recuperar los beneficios de un empleado.
 */
@Repository
public interface BenefitRepository extends JpaRepository<Benefit, Long> {

    /**
     * Obtiene todos los beneficios cuyo empleado tiene el ID indicado.
     *
     * @param employeeID identificador del empleado
     * @return lista de {@link Benefit} del empleado (puede ser vacía)
     */
    //Buscar todos los beneficios cuyo employee.id sea igual al valor recibido
    List<Benefit> findByEmployeeId(Long employeeID);
}
