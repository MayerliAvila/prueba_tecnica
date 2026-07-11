package com.prueba.backend.mapper;

import com.prueba.backend.dto.RequestDTO.BenefitRequestDTO;
import com.prueba.backend.dto.ResponseDTO.BenefitResponseDTO;
import com.prueba.backend.entity.Benefit;
import org.springframework.stereotype.Component;

/**
 * Componente de Spring que convierte entre la entidad {@link Benefit}
 * y sus DTOs de entrada/salida.
 */
@Component
public class BenefitMapper {
    /**
     * Convierte un {@link BenefitRequestDTO} en una nueva entidad {@link Benefit}.
     * El campo {@code employee} NO se asigna aquí; debe establecerse en el servicio.
     * Si el DTO es {@code null}, retorna {@code null}.
     *
     * @param dto datos de entrada
     * @return entidad {@link Benefit} parcialmente construida, o {@code null}
     */
    public Benefit toEntity(BenefitRequestDTO dto){
        if (dto == null){
            return null;
        }
        return Benefit.builder()
                .benefitName(dto.getBenefitName())
                .amount(dto.getAmount())
                .build();
    }
    /**
     * Convierte una entidad {@link Benefit} en un {@link BenefitResponseDTO}.
     * Si el beneficio tiene un empleado asociado, incluye su ID en la respuesta.
     * Si la entidad es {@code null}, retorna {@code null}.
     *
     * @param benefit entidad a convertir
     * @return DTO de respuesta, o {@code null}
     */
    public BenefitResponseDTO toResponse(Benefit benefit){
        if(benefit == null){
            return null;
        }
        BenefitResponseDTO dto = new BenefitResponseDTO();

        dto.setId(benefit.getId());
        dto.setBenefitName(benefit.getBenefitName());
        dto.setAmount(benefit.getAmount());

        if(benefit.getEmployee() != null){
            dto.setEmployeeId(benefit.getEmployee().getId());
        }
        return dto;
    }
}
