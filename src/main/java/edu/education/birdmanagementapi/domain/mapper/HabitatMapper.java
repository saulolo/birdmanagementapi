package edu.education.birdmanagementapi.domain.mapper;

import edu.education.birdmanagementapi.domain.dto.request.HabitatRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.HabitatResponseDTO;
import edu.education.birdmanagementapi.domain.entity.Habitat;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Convierte entre entidaddes {@link Habitat} y sus respectivos DTO's.
 */
@Component
public class HabitatMapper {


    /**
     * Convierte una entidad {@link Habitat} en un {@link HabitatResponseDTO}.
     *
     * @param habitat la entidad de hábitat a convertir.
     * @return un DTO con los datos del hábitat o {@code null} si la entidad es nula.
     */
    public HabitatResponseDTO toHabitatResponseDTO(Habitat habitat) {
        if (habitat == null) {
            return null;
        }
        return HabitatResponseDTO.builder()
                .idHabitat(habitat.getIdHabitat())
                .name(habitat.getName())
                .description(habitat.getDescription())
                .build();
    }


    /**
     * Convierte un {@link HabitatRequestDTO} en una entidad {@link Habitat}.
     *
     * @param habitatRequestDTO DTO con los datos del hábitat a crear o actualizar.
     * @return una instancia de {@link Habitat} con los valores del DTO o {@code null} si el DTO es nulo.
     */
    public Habitat toHabitatEntity(HabitatRequestDTO habitatRequestDTO) {
        if (habitatRequestDTO == null) {
            return null;
        }
        return Habitat.builder()
                .name(habitatRequestDTO.name())
                .description(habitatRequestDTO.description())
                .build();
    }

    /**
     * Convierte una lista de entidades {@link Habitat} en una lista de {@link HabitatResponseDTO}.
     *
     * @param habitats lista de entidades a convertir.
     * @return lista de DTOs de respuesta o vacía si no existen hábitats.
     */
    public List<HabitatResponseDTO> toHabitatResponseList(List<Habitat> habitats) {
        if (habitats == null || habitats.isEmpty()) {
            return Collections.emptyList();
        }
        return habitats.stream()
                .map(this::toHabitatResponseDTO)
                .toList();
    }
}
