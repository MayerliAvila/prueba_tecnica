package com.prueba.backend.service.impl;

import com.prueba.backend.dto.RequestDTO.BenefitRequestDTO;
import com.prueba.backend.dto.ResponseDTO.BenefitResponseDTO;
import com.prueba.backend.entity.Benefit;
import com.prueba.backend.entity.Employee;
import com.prueba.backend.exception.ResourceNotFoundException;
import com.prueba.backend.mapper.BenefitMapper;
import com.prueba.backend.repository.BenefitRepository;
import com.prueba.backend.repository.EmployeeRepository;
import com.prueba.backend.service.BenefitService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Implementación de {@link BenefitService} que gestiona la lógica de negocio
 * para los beneficios laborales de los empleados.
 *
 * <p>Coordina operaciones de persistencia con {@link BenefitRepository} y
 * {@link EmployeeRepository}, y la conversión de datos con {@link BenefitMapper}.
 */
@Service
public class BenefitServiceImpl implements BenefitService {

    private final BenefitRepository benefitRepository;
    private final EmployeeRepository employeeRepository;
    private final BenefitMapper benefitMapper;

    /**
     * Construye el servicio inyectando sus dependencias.
     *
     * @param benefitRepository  repositorio JPA de beneficios
     * @param employeeRepository repositorio JPA de empleados (para validar existencia)
     * @param benefitMapper      conversor entre entidades y DTOs
     */
    public BenefitServiceImpl(BenefitRepository benefitRepository,
                              EmployeeRepository employeeRepository,
                              BenefitMapper benefitMapper) {
        this.benefitRepository = benefitRepository;
        this.employeeRepository = employeeRepository;
        this.benefitMapper = benefitMapper;
    }

    /** {@inheritDoc} */
    @Override
    public List<BenefitResponseDTO> findAll() {

        return benefitRepository.findAll()
                .stream()
                .map(benefitMapper::toResponse)
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    public List<BenefitResponseDTO> findAllByIdEmployee(Long employeeId) {

        return benefitRepository.findByEmployeeId(employeeId)
                .stream()
                .map(benefitMapper::toResponse)
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    public BenefitResponseDTO save(BenefitRequestDTO dto) {

        Employee employee = employeeRepository.findById(dto.getEmployeeId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Empleado no encontrado"));

        Benefit benefit = benefitMapper.toEntity(dto);

        benefit.setEmployee(employee);

        Benefit savedBenefit = benefitRepository.save(benefit);

        return benefitMapper.toResponse(savedBenefit);
    }

    /** {@inheritDoc} */
    @Override
    public BenefitResponseDTO update(Long id, BenefitRequestDTO dto) {

        Benefit current = benefitRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Beneficio no encontrado"));

        current.setBenefitName(dto.getBenefitName());
        current.setAmount(dto.getAmount());

        if (!current.getEmployee().getId().equals(dto.getEmployeeId())) {

            Employee employee = employeeRepository.findById(dto.getEmployeeId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Empleado no encontrado"));

            current.setEmployee(employee);
        }

        Benefit updatedBenefit = benefitRepository.save(current);

        return benefitMapper.toResponse(updatedBenefit);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(Long id) {

        if (!benefitRepository.existsById(id)) {
            throw new ResourceNotFoundException("Beneficio no encontrado");
        }

        benefitRepository.deleteById(id);
    }
}