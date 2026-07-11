package com.prueba.backend.client;

import com.prueba.backend.client.dto.NominatimResponseDTO;
import com.prueba.backend.exception.ExternalServiceException;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

/**
 * Cliente HTTP que consulta la API gratuita de Nominatim (OpenStreetMap)
 * para obtener las coordenadas geográficas de una ciudad.
 *
 * <p>Usa {@link RestClient} de Spring para realizar peticiones GET al endpoint
 * {@code /search} de {@code https://nominatim.openstreetmap.org}.
 * Se devuelve solo el primer resultado de búsqueda.
 */
@Component
public class NominatimClient {

    private final RestClient restClient;

    /**
     * Crea el cliente configurando la URL base y el encabezado {@code User-Agent}
     * requerido por la política de uso de Nominatim.
     */
    public NominatimClient() {

        this.restClient = RestClient.builder()
                .baseUrl("https://nominatim.openstreetmap.org")
                .defaultHeader("User-Agent", "PruebaTecnicaSpringBoot/1.0")
                .build();
    }

    /**
     * Busca las coordenadas de una ciudad usando la API de Nominatim.
     *
     * <p>Envía una petición GET a {@code /search?q={city}&format=json&limit=1}
     * y retorna el primer resultado encontrado.
     *
     * @param city nombre de la ciudad a buscar (ej. "Bogotá")
     * @return el primer {@link NominatimResponseDTO} con latitud y longitud,
     *         o {@code null} si la ciudad no fue encontrada
     * @throws ExternalServiceException si ocurre un error de red o comunicación
     */
    public NominatimResponseDTO searchCity(String city) {

        try {
            List<NominatimResponseDTO> response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/search")
                            .queryParam("q", city)
                            .queryParam("format", "json")
                            .queryParam("limit", 1)
                            .build())
                    .retrieve()
                    .body(new ParameterizedTypeReference<List<NominatimResponseDTO>>() {
                    });

            if (response == null || response.isEmpty()) {
                return null;
            }

            return response.get(0);

        }catch (Exception ex){
            throw new ExternalServiceException(
                    "Error consultando OpenStreetMap", ex);
        }
    }

}