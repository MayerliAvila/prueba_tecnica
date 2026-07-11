package com.prueba.backend.service;

import com.prueba.backend.dto.RequestDTO.BenefitRequestDTO;
import com.prueba.backend.dto.ResponseDTO.BenefitResponseDTO;
import com.prueba.backend.entity.Benefit;

import java.util.List;

/**
 * Contrato de servicios para la gestión de beneficios laborales.
 *
 * <p>Define las operaciones CRUD disponibles para la entidad {@code Benefit}.
 * La implementación concreta se encuentra en {@link com.prueba.backend.service.impl.BenefitServiceImpl}.
 */
public interface BenefitService {

    /**
     * Obtiene todos los beneficios registrados en el sistema.
     *
     * @return lista de {@link BenefitResponseDTO} con todos los beneficios
     */
    List<BenefitResponseDTO> findAll();

    /**
     * Obtiene todos los beneficios asociados a un empleado específico.
     *
     * @param employeeId identificador del empleado
     * @return lista de {@link BenefitResponseDTO} de ese empleado (puede estar vacía)
     */
    List<BenefitResponseDTO> findAllByIdEmployee(Long employeeId);

    /**
     * Crea y persiste un nuevo beneficio para un empleado.
     *
     * @param dto datos de entrada del beneficio
     * @return {@link BenefitResponseDTO} con los datos del beneficio creado
     * @throws com.prueba.backend.exception.ResourceNotFoundException si el empleado indicado no existe
     */
    BenefitResponseDTO save(BenefitRequestDTO dto);

    /**
     * Actualiza los datos de un beneficio existente.
     * Permite cambiar también el empleado al que está asignado.
     *
     * @param id  identificador del beneficio a actualizar
     * @param dto nuevos datos del beneficio
     * @return {@link BenefitResponseDTO} con los datos actualizados
     * @throws com.prueba.backend.exception.ResourceNotFoundException si el beneficio o el empleado no existen
     */
    BenefitResponseDTO update(Long id, BenefitRequestDTO dto);

    /**
     * Elimina un beneficio por su identificador.
     *
     * @param id identificador del beneficio a eliminar
     * @throws com.prueba.backend.exception.ResourceNotFoundException si el beneficio no existe
     */
    void delete(Long id);
}
