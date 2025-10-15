package edu.education.birdmanagementapi.domain.mapper;

import edu.education.birdmanagementapi.domain.dto.request.BirdRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.BirdResponseDTO;
import edu.education.birdmanagementapi.domain.entity.Bird;
import edu.education.birdmanagementapi.domain.entity.Family;
import edu.education.birdmanagementapi.domain.entity.Habitat;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class BirdMapper {


    public BirdResponseDTO toBirdResponseDTO(Bird bird) {
        if (bird == null) {
            return null;
        }
        return BirdResponseDTO.builder()
                .idBird(bird.getIdBird())
                .commonName(bird.getCommonName())
                .scientificName(bird.getScientificName())
                .conservationModel(bird.getConservationModel())
                .notes(bird.getNotes())
                .familyName(bird.getFamily() != null ? bird.getFamily().getName() : null)
                .habitatNames(bird.getHabitats() != null ? bird.getHabitats().stream()
                        .map(Habitat::getName)
                        .collect(Collectors.toSet()) : Collections.emptySet())
                .createdAt(bird.getCreatedAt())
                .updatedAt(bird.getUpdatedAt())
                .build();
    }

    public Bird toBirdEntity(BirdRequestDTO birdRequestDTO, Family family, Set<Habitat> habitats) {
        if (birdRequestDTO == null) {
            return null;
        }
        return Bird.builder()
                .commonName(birdRequestDTO.commonName())
                .scientificName(birdRequestDTO.scientificName())
                .conservationModel(birdRequestDTO.conservationModel())
                .notes(birdRequestDTO.notes())
                .family(family)
                .habitats(habitats == null ? Collections.emptySet() : habitats)
                .build();
    }

    public List<BirdResponseDTO> toBirdResponseList(List<Bird> birds) {
        if (birds == null || birds.isEmpty()) {
            return Collections.emptyList();
        }
        return birds.stream()
                .map(this::toBirdResponseDTO)
                .toList();
    }
}
