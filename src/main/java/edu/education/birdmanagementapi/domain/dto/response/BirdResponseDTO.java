package edu.education.birdmanagementapi.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.Set;

@Builder
@JsonPropertyOrder({"idBird", "commonName", "scientificName", "conservationModel", "notes", "familyName", "habitatNames",
        "createdAt", "updatedAt"})
public record BirdResponseDTO(
        Long idBird,
        String commonName,
        String scientificName,
        String conservationModel,
        String notes,
        String familyName,
        Set<String> habitatNames,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime updatedAt) {
}
