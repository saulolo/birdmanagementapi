package edu.education.birdmanagementapi.domain.mapper;

import edu.education.birdmanagementapi.domain.dto.request.FamilyRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.FamilyResponseDTO;
import edu.education.birdmanagementapi.domain.entity.Family;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class FamilyMapper {


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

    public Family toFamilyEntity(FamilyRequestDTO familyRequestDTO) {
        if (familyRequestDTO == null) {
            return null;
        }
        return Family.builder()
                .name(familyRequestDTO.name())
                .description(familyRequestDTO.description())
                .build();
    }

    public List<FamilyResponseDTO> toFamilyResponseList(List<Family> families) {
        if (families == null || families.isEmpty()) {
            return Collections.emptyList();
        }
        return families.stream()
                .map(this::toFamilyResponseDTO)
                .toList();
    }
}
