package edu.education.birdmanagementapi.domain.mapper;

import edu.education.birdmanagementapi.domain.dto.request.SightingRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.SightingResponseDTO;
import edu.education.birdmanagementapi.domain.entity.*;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Mapper encargado de convertir entre entidades Sighting y sus DTOs asociados.
 */
@Component
public class SightingMapper {



    public SightingResponseDTO toSightingResponseDTO(Sighting sighting) {
        if (sighting == null) {
            return null;
        }
        return SightingResponseDTO.builder()
                .idSighting(sighting.getIdSighting())
                .latitude(sighting.getLatitude())
                .longitude(sighting.getLongitude())
                .sightingDateTime(sighting.getSightingDateTime())
                .notes(sighting.getNotes())
                .countryName(sighting.getCountry() != null ? sighting.getCountry().getName() : null)
                .birdName(sighting.getBird() != null ? sighting.getBird().getCommonName() : null)
                .habitatName(sighting.getHabitat() != null ? sighting.getHabitat().getName() : null)
                .userName(sighting.getUser() != null ? sighting.getUser().getName() : null)
                .createdAt(sighting.getCreatedDate())
                .build();
    }


    public Sighting toSightingEntity(SightingRequestDTO sightingRequestDTO, Country country, Bird bird, Habitat habitat,
                                     User user) {
        if (sightingRequestDTO == null) {
            return null;
        }
        return Sighting.builder()
                .latitude(sightingRequestDTO.latitude())
                .longitude(sightingRequestDTO.longitude())
                .sightingDateTime(sightingRequestDTO.sightingDateTime())
                .notes(sightingRequestDTO.notes())
                .country(country)
                .bird(bird)
                .habitat(habitat)
                .user(user)
                .build();
    }


    public List<SightingResponseDTO> toSightingResponseList(List<Sighting> sightings) {
        if (sightings == null || sightings.isEmpty()) {
            return Collections.emptyList();
        }
        return sightings.stream()
                .map(this::toSightingResponseDTO)
                .toList();
    }
}
