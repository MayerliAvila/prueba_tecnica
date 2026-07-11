package com.prueba.backend.repository;

import com.prueba.backend.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio JPA para la entidad {@link Employee}.
 *
 * <p>Extiende {@link JpaRepository} para heredar las operaciones CRUD estándar.
 * Agrega consultas personalizadas para validar unicidad de correo electrónico.
 */
@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    /**
     * Busca un empleado por su correo electrónico.
     *
     * @param email correo electrónico a buscar
     * @return un {@link Optional} con el empleado si existe, o vacío si no
     */
    //Buscar correo si existe en la base de datos
    Optional<Employee> findByEmail(String email);

    /**
     * Verifica si ya existe un empleado con el correo electrónico dado.
     * Se usa para prevenir correos duplicados antes de crear o actualizar un empleado.
     *
     * @param email correo electrónico a verificar
     * @return {@code true} si ya existe al menos un empleado con ese correo
     */
    //Nos permitirá validar que no existan correos duplicados antes de guardar un empleado.
    boolean existsByEmail(String email);


}
