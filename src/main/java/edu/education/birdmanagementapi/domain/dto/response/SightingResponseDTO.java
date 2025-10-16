package edu.education.birdmanagementapi.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonPropertyOrder({"idSighting", "latitude", "longitude", "sightingDateTime", "notes", "countryName", "birdName",
        "habitatName", "userName", "createdAt"})
public record SightingResponseDTO(
        Long idSighting,
        Double latitude,
        Double longitude,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime sightingDateTime,
        String notes,
        String countryName,
        String birdName,
        String habitatName,
        String userName,
        @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
        LocalDateTime createdAt) {
}
