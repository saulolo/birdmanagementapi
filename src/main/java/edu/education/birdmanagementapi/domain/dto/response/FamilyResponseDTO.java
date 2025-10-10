package edu.education.birdmanagementapi.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonPropertyOrder({"idFamily", "name", "description", "createdAt", "updatedAt"})
public record FamilyResponseDTO(
        Long idFamily,
        String name,
        String description,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime updatedAt) {
}
