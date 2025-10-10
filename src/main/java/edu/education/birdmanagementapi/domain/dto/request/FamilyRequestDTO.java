package edu.education.birdmanagementapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@JsonPropertyOrder({"name", "description"})
public record FamilyRequestDTO(
        @NotBlank(message = "El nombre es obligatorio.")
        @Size(max = 50, message = "El nombre no puede exceder los 50 caracteres.")
        String name,
        @Size(max = 255, message = "La descripción no puede exceder los 255 carateres.")
        String description) {
}
