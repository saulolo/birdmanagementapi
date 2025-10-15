package edu.education.birdmanagementapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

import java.util.Set;

@Builder
@JsonPropertyOrder({"commonName", "scientificName", "conservationModel", "notes", "idFamily", "idHabitats"})
public record BirdRequestDTO(
        @NotBlank(message = "El nombre común es obligatorio.")
        @Size(max = 50, message = "El nombre común no puede exceder los 50 caracteres.")
        String commonName,
        @NotBlank(message = "El nombre científico es obligatorio.")
        @Size(max = 50, message = "El nombre científico no puede exceder los 50 caracteres.")
        String scientificName,
        @NotBlank(message = "El modo de conservación es obligatorio.")
        @Size(message = "El modo de conservación no puede exceder los 255 caracteres.")
        String conservationModel,
        @Size(message = "Las notas no pueden exceder los 255 caracteres.")
        String notes,
        Long idFamily,
        Set<Long> idHabitats) {
}
