package edu.education.birdmanagementapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonPropertyOrder({"latitude", "longitude", "sightingDateTime", "notes", "idCountry", "idBird", "idHabitat", "idUser"})
public record SightingRequestDTO(

        Double latitude,
        Double longitude,
        @NotNull(message = "La fecha/hora del avistamiento son obligatorias.")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime sightingDateTime,
        @Size(message = "Las notas no pueden exceder los 255 caracteres.")
        String notes,
        @NotNull(message = "El id del pais es obligatorio.")
        Long idCountry,
        @NotNull(message = "El id de la ave es obligatoria.")
        Long idBird,
        @NotNull(message = "El id del habitat es obligatoria.")
        Long idHabitat,
        @NotNull(message = "El id del usuario es obligatoria.")
        Long idUser) {
}
