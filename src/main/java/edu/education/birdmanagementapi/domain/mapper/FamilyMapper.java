package edu.education.birdmanagementapi.domain.mapper;

import edu.education.birdmanagementapi.domain.dto.request.FamilyRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.FamilyResponseDTO;
import edu.education.birdmanagementapi.domain.entity.Family;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * Mapper encargado de convertir entre entidades Family y sus DTOs.
 */
@Component
public class FamilyMapper {


    /**
     * Convierte una entidad {@link Family} a un {@link FamilyResponseDTO}.
     *
     * @param family entidad familia
     * @return DTO de respuesta
     */
    public FamilyResponseDTO toFamilyResponseDTO(Family family) {
        if (family == null) {
            return null;
        }
        return FamilyResponseDTO.builder()
                .idFamily(family.getIdFamily())
                .name(family.getName())
                .description(family.getDescription())
                .createdAt(family.getCreatedAt())
                .updatedAt(family.getUpdatedAt())
                .build();
    }

    /**
     * Convierte un {@link FamilyRequestDTO} en una entidad {@link Family}.
     *
     * @param familyRequestDTO DTO de solicitud
     * @return entidad Family
     */
    public Family toFamilyEntity(FamilyRequestDTO familyRequestDTO) {
        if (familyRequestDTO == null) {
            return null;
        }
        return Family.builder()
                .name(familyRequestDTO.name())
                .description(familyRequestDTO.description())
                .build();
    }

    /**
     * Convierte una lista de entidades {@link Family} en una lista de {@link FamilyResponseDTO}.
     *
     * @param families lista de entidades
     * @return lista de DTOs
     */
    public List<FamilyResponseDTO> toFamilyResponseList(List<Family> families) {
        if (families == null || families.isEmpty()) {
            return Collections.emptyList();
        }
        return families.stream()
                .map(this::toFamilyResponseDTO)
                .toList();
    }
}
