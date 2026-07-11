package com.prueba.backend.controller;

import com.prueba.backend.dto.RequestDTO.BenefitRequestDTO;
import com.prueba.backend.dto.ResponseDTO.BenefitResponseDTO;
import com.prueba.backend.service.BenefitService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para la gestión de beneficios laborales.
 *
 * <p>Expone los endpoints CRUD bajo la ruta base {@code /api/benefits}.
 * Delega toda la lógica de negocio a {@link BenefitService}.
 */
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/benefits")
public class BenefitController {

    private final BenefitService benefitService;

    /**
     * Construye el controlador con su dependencia de servicio.
     *
     * @param benefitService servicio de gestión de beneficios
     */
    public BenefitController(BenefitService benefitService){
        this.benefitService = benefitService;
    }

    /**
     * Obtiene todos los beneficios registrados en el sistema.
     *
     * @return 200 OK con la lista de {@link BenefitResponseDTO}
     */
    //Obtener todos los beneficios
    @GetMapping
    public ResponseEntity<List<BenefitResponseDTO>> getAllBenefit(){
        return ResponseEntity.ok(benefitService.findAll());
    }

    /**
     * Obtiene todos los beneficios de un empleado específico.
     *
     * @param id identificador del empleado
     * @return 200 OK con la lista de {@link BenefitResponseDTO} del empleado
     */
    //Obtener Beneficio por Empleado
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<BenefitResponseDTO>> getByIdEmployee(@PathVariable("employeeId") Long id){
        return ResponseEntity.ok(benefitService.findAllByIdEmployee(id));
    }

    /**
     * Crea un nuevo beneficio para un empleado.
     *
     * @param dto datos del beneficio validados con Jakarta Validation
     * @return 201 Created con el {@link BenefitResponseDTO} creado,
     *         o 404 si el empleado no existe
     */
    //Crear beneficio
    @PostMapping
    public ResponseEntity<BenefitResponseDTO> createBenefit(@Valid @RequestBody BenefitRequestDTO dto){
        BenefitResponseDTO benefit = benefitService.save(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(benefit);
    }
    /**
     * Actualiza un beneficio existente.
     *
     * @param id  identificador del beneficio
     * @param dto nuevos datos del beneficio
     * @return 200 OK con el {@link BenefitResponseDTO} actualizado,
     *         o 404 si el beneficio o el empleado no existen
     */
    //Actualizar Beneficio
    @PutMapping("/{id}")
    public ResponseEntity<BenefitResponseDTO> updateBenefit(
            @PathVariable Long id,
            @Valid @RequestBody BenefitRequestDTO dto){

        return ResponseEntity.ok(benefitService.update(id, dto));
    }
    /**
     * Elimina un beneficio por su identificador.
     *
     * @param id identificador del beneficio a eliminar
     * @return 204 No Content si se eliminó correctamente, o 404 si no existe
     */
    //Eliminar Beneficio
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBenefit(@PathVariable Long id){
        benefitService.delete(id);
        return ResponseEntity.noContent().build();
    }

}
