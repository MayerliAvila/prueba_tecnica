package com.prueba.backend.mapper;

import com.prueba.backend.dto.RequestDTO.EmployeeRequestDTO;
import com.prueba.backend.dto.ResponseDTO.BenefitResponseDTO;
import com.prueba.backend.dto.ResponseDTO.EmployeeResponseDTO;
import com.prueba.backend.entity.Employee;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Componente de Spring que convierte entre la entidad {@link Employee}
 * y sus DTOs de entrada/salida.
 *
 * <p>Centraliza todas las transformaciones de datos para evitar acoplar
 * la capa de servicio con la estructura interna de la entidad.
 */
@Component
public class EmployeeMapper {

    /**
     * Convierte un {@link EmployeeRequestDTO} en una nueva entidad {@link Employee}.
     * Si el DTO es {@code null}, retorna {@code null}.
     * Si el DTO no incluye estado, el builder usará el valor por defecto ({@code ACTIVO}).
     *
     * @param dto datos de entrada
     * @return entidad {@link Employee} lista para persistir, o {@code null}
     */
    public Employee toEntity(EmployeeRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Employee.EmployeeBuilder builder = Employee.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .city(dto.getCity())
                .position(dto.getPosition())
                .phone(dto.getPhone())
                .department(dto.getDepartment())
                .hireDate(dto.getHireDate())
                .baseSalary(dto.getBaseSalary());

        if (dto.getStatus() != null) {
            builder.status(dto.getStatus());
        }

        return builder.build();
    }

    /**
     * Actualiza los campos de un {@link Employee} existente con los datos
     * del {@link EmployeeRequestDTO} provisto. No actualiza el ID ni los beneficios.
     * Si el DTO es {@code null}, no realiza ninguna operación.
     *
     * @param employee entidad a actualizar (modificada in-place)
     * @param dto      nuevos datos del empleado
     */
    public void updateEntity(Employee employee, EmployeeRequestDTO dto) {
        if (dto == null) {
            return;
        }
        employee.setName(dto.getName());
        employee.setEmail(dto.getEmail());
        employee.setCity(dto.getCity());
        employee.setPosition(dto.getPosition());
        employee.setPhone(dto.getPhone());
        employee.setDepartment(dto.getDepartment());
        employee.setHireDate(dto.getHireDate());
        employee.setBaseSalary(dto.getBaseSalary());
        if (dto.getStatus() != null) {
            employee.setStatus(dto.getStatus());
        }
    }

    /**
     * Convierte una entidad {@link Employee} en un {@link EmployeeResponseDTO}
     * que incluye la lista de beneficios del empleado.
     * Si la entidad es {@code null}, retorna {@code null}.
     *
     * @param employee entidad a convertir
     * @return DTO de respuesta con todos los datos del empleado, o {@code null}
     */
    public EmployeeResponseDTO toResponse(Employee employee) {
        if (employee == null) {
            return null;
        }

        EmployeeResponseDTO dto = new EmployeeResponseDTO();

        dto.setId(employee.getId());
        dto.setName(employee.getName());
        dto.setEmail(employee.getEmail());
        dto.setCity(employee.getCity());
        dto.setPosition(employee.getPosition());
        dto.setPhone(employee.getPhone());
        dto.setDepartment(employee.getDepartment());
        dto.setHireDate(employee.getHireDate());
        dto.setBaseSalary(employee.getBaseSalary());
        dto.setStatus(employee.getStatus());
        dto.setLatitude(employee.getLatitude());
        dto.setLongitude(employee.getLongitude());
        dto.setBenefits(toBenefitResponses(employee));

        return dto;
    }

    /**
     * Mapea la lista de beneficios de un empleado a una lista de {@link BenefitResponseDTO}.
     * Si la lista de beneficios es {@code null}, retorna una lista vacía.
     *
     * @param employee entidad cuya lista de beneficios se va a convertir
     * @return lista de {@link BenefitResponseDTO}
     */
    private List<BenefitResponseDTO> toBenefitResponses(Employee employee) {
        if (employee.getBenefits() == null) {
            return Collections.emptyList();
        }
        return employee.getBenefits().stream()
                .map(b -> {
                    BenefitResponseDTO b1 = new BenefitResponseDTO();
                    b1.setId(b.getId());
                    b1.setBenefitName(b.getBenefitName());
                    b1.setEmployeeId(employee.getId());
                    b1.setAmount(b.getAmount());
                    return b1;
                })
                .toList();
    }
}