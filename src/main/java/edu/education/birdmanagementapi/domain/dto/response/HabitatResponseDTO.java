package edu.education.birdmanagementapi.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@Builder
@JsonPropertyOrder({"idHabitat", "name", "description"})
public record HabitatResponseDTO(
        Long idHabitat,
        String name,
        String description) {
}
