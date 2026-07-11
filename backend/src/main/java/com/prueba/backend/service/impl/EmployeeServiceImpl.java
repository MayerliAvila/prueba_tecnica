package com.prueba.backend.service.impl;

import com.prueba.backend.client.NominatimClient;
import com.prueba.backend.client.dto.NominatimResponseDTO;
import com.prueba.backend.dto.RequestDTO.EmployeeRequestDTO;
import com.prueba.backend.dto.ResponseDTO.EmployeeResponseDTO;
import com.prueba.backend.entity.Employee;
import com.prueba.backend.exception.DuplicateResourceException;
import com.prueba.backend.exception.ResourceNotFoundException;
import com.prueba.backend.mapper.EmployeeMapper;
import com.prueba.backend.repository.EmployeeRepository;
import com.prueba.backend.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * Implementación de {@link EmployeeService} que gestiona la lógica de negocio
 * para empleados.
 *
 * <p>Coordina las operaciones de persistencia a través de {@link EmployeeRepository},
 * la conversión de datos mediante {@link EmployeeMapper}, y la obtención de
 * coordenadas geográficas mediante {@link NominatimClient}.
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final NominatimClient nominatimClient;

    /**
     * Construye el servicio inyectando sus dependencias.
     *
     * @param employeeRepository repositorio JPA de empleados
     * @param employeeMapper     conversor entre entidades y DTOs
     * @param nominatimClient    cliente para geocodificación via Nominatim
     */
    public EmployeeServiceImpl(EmployeeRepository employeeRepository,
                               EmployeeMapper employeeMapper,
                               NominatimClient nominatimClient) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.nominatimClient = nominatimClient;
    }

    /** {@inheritDoc} */
    @Override
    public List<EmployeeResponseDTO> findAll() {
        return employeeRepository.findAll()
                .stream()
                .map(employeeMapper::toResponse)
                .toList();
    }

    /** {@inheritDoc} */
    @Override
    public EmployeeResponseDTO findById(Long id) {
        Employee employee = findEntityOrThrow(id);
        return employeeMapper.toResponse(employee);
    }

    /** {@inheritDoc} */
    @Override
    public EmployeeResponseDTO save(EmployeeRequestDTO dto) {

        if (employeeRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("El correo ya está registrado");
        }

        Employee employee = employeeMapper.toEntity(dto);
        applyCoordinates(employee);

        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toResponse(savedEmployee);
    }

    /** {@inheritDoc} */
    @Override
    public EmployeeResponseDTO update(Long id, EmployeeRequestDTO dto) {

        Employee employee = findEntityOrThrow(id);

        if (!Objects.equals(employee.getEmail(), dto.getEmail())
                && employeeRepository.existsByEmail(dto.getEmail())) {
            throw new DuplicateResourceException("El correo ya está registrado");
        }

        String previousCity = employee.getCity();

        employeeMapper.updateEntity(employee, dto);

        if (!Objects.equals(previousCity, employee.getCity())) {
            applyCoordinates(employee);
        }

        Employee savedEmployee = employeeRepository.save(employee);
        return employeeMapper.toResponse(savedEmployee);
    }

    /** {@inheritDoc} */
    @Override
    public void deleteById(Long id) {
        Employee employee = findEntityOrThrow(id);
        employeeRepository.delete(employee);
    }

    /**
     * Consulta las coordenadas geográficas de la ciudad del empleado
     * usando la API de Nominatim (OpenStreetMap) y las asigna al objeto {@link Employee}.
     *
     * <p>Si la ciudad no es encontrada o las coordenadas son inválidas,
     * se asigna {@code null} tanto a latitud como a longitud, y se registra
     * una advertencia en el log.
     *
     * @param employee entidad a la que se le asignarán las coordenadas
     */
    private void applyCoordinates(Employee employee) {
        NominatimResponseDTO location = nominatimClient.searchCity(employee.getCity());

        if (location == null) {
            log.warn("No se encontraron coordenadas para la ciudad '{}'", employee.getCity());
            employee.setLatitude(null);
            employee.setLongitude(null);
            return;
        }

        try {
            employee.setLatitude(Double.parseDouble(location.getLatitude()));
            employee.setLongitude(Double.parseDouble(location.getLongitude()));
        } catch (NumberFormatException ex) {
            log.warn("Coordenadas inválidas de Nominatim para '{}': lat={}, lon={}",
                    employee.getCity(), location.getLatitude(), location.getLongitude());
            employee.setLatitude(null);
            employee.setLongitude(null);
        }
    }

    /**
     * Busca un empleado por ID o lanza una excepción si no existe.
     *
     * @param id identificador del empleado
     * @return la entidad {@link Employee} encontrada
     * @throws com.prueba.backend.exception.ResourceNotFoundException si no se encuentra
     */
    private Employee findEntityOrThrow(Long id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Empleado no encontrado"));
    }
}