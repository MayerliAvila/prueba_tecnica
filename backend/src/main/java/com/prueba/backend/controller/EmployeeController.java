package com.prueba.backend.controller;

import com.prueba.backend.dto.RequestDTO.EmployeeRequestDTO;
import com.prueba.backend.dto.ResponseDTO.EmployeeResponseDTO;
import com.prueba.backend.service.EmployeeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de empleados.
 *
 * <p>Expone los endpoints CRUD bajo la ruta base {@code /api/employees}.
 * Delega toda la lógica de negocio a {@link EmployeeService}.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    /**
     * Construye el controlador con su dependencia de servicio.
     *
     * @param employeeService servicio de gestión de empleados
     */
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    /**
     * Obtiene la lista completa de todos los empleados.
     *
     * @return 200 OK con la lista de {@link EmployeeResponseDTO}
     */
    // Obtener todos los empleados
    @GetMapping
    public ResponseEntity<List<EmployeeResponseDTO>> getAllEmployees() {
        return ResponseEntity.ok(employeeService.findAll());
    }

    /**
     * Obtiene los datos de un empleado específico por su ID.
     *
     * @param id identificador del empleado
     * @return 200 OK con el {@link EmployeeResponseDTO} encontrado,
     *         o 404 si no existe
     */
    // Obtener por ID el empleado
    @GetMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> getByIdEmployee(@PathVariable Long id) {
        return ResponseEntity.ok(employeeService.findById(id));
    }

    /**
     * Crea un nuevo empleado.
     *
     * @param dto datos del empleado validados con Jakarta Validation
     * @return 201 Created con el {@link EmployeeResponseDTO} del empleado creado,
     *         o 409 si el correo ya existe, o 400 si los datos son inválidos
     */
    // Crear Empleado
    @PostMapping
    public ResponseEntity<EmployeeResponseDTO> createEmployee(@Valid @RequestBody EmployeeRequestDTO dto) {
        EmployeeResponseDTO employee = employeeService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(employee);
    }

    /**
     * Actualiza los datos de un empleado existente.
     *
     * @param id  identificador del empleado
     * @param dto nuevos datos del empleado, validados con Jakarta Validation
     * @return 200 OK con el {@link EmployeeResponseDTO} actualizado,
     *         o 404 si no existe, o 409 si el correo ya está en uso
     */
    // Actualizar Empleado
    @PutMapping("/{id}")
    public ResponseEntity<EmployeeResponseDTO> updateEmployee(
            @PathVariable Long id,
            @Valid @RequestBody EmployeeRequestDTO dto) {
        return ResponseEntity.ok(employeeService.update(id, dto));
    }

    /**
     * Elimina un empleado y sus beneficios en cascada.
     *
     * @param id identificador del empleado a eliminar
     * @return 204 No Content si se eliminó correctamente, o 404 si no existe
     */
    // Eliminar Empleado
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable Long id) {
        employeeService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}