package com.prueba.backend.service;

import com.prueba.backend.dto.RequestDTO.EmployeeRequestDTO;
import com.prueba.backend.dto.ResponseDTO.EmployeeResponseDTO;

import java.util.List;

/**
 * Contrato de servicios para la gestión de empleados.
 *
 * <p>Define las operaciones CRUD disponibles para la entidad {@code Employee}.
 * La implementación concreta se encuentra en {@link com.prueba.backend.service.impl.EmployeeServiceImpl}.
 */
public interface EmployeeService {

    /**
     * Obtiene todos los empleados registrados en el sistema.
     *
     * @return lista de {@link EmployeeResponseDTO} con todos los empleados
     */
    List<EmployeeResponseDTO> findAll();

    /**
     * Busca un empleado por su identificador único.
     *
     * @param id identificador del empleado
     * @return {@link EmployeeResponseDTO} con los datos del empleado encontrado
     * @throws com.prueba.backend.exception.ResourceNotFoundException si el empleado no existe
     */
    EmployeeResponseDTO findById(Long id);

    /**
     * Crea y persiste un nuevo empleado. También obtiene las coordenadas
     * geográficas de la ciudad indicada mediante la API de Nominatim.
     *
     * @param dto datos de entrada del nuevo empleado
     * @return {@link EmployeeResponseDTO} con los datos del empleado creado
     * @throws com.prueba.backend.exception.DuplicateResourceException si el correo ya está registrado
     */
    EmployeeResponseDTO save(EmployeeRequestDTO dto);

    /**
     * Actualiza los datos de un empleado existente.
     * Si la ciudad cambia, se consultan nuevamente las coordenadas geográficas.
     *
     * @param id  identificador del empleado a actualizar
     * @param dto nuevos datos del empleado
     * @return {@link EmployeeResponseDTO} con los datos actualizados
     * @throws com.prueba.backend.exception.ResourceNotFoundException  si el empleado no existe
     * @throws com.prueba.backend.exception.DuplicateResourceException si el nuevo correo ya está en uso
     */
    EmployeeResponseDTO update(Long id, EmployeeRequestDTO dto);

    /**
     * Elimina un empleado y todos sus beneficios asociados (cascada).
     *
     * @param id identificador del empleado a eliminar
     * @throws com.prueba.backend.exception.ResourceNotFoundException si el empleado no existe
     */
    void deleteById(Long id);
}