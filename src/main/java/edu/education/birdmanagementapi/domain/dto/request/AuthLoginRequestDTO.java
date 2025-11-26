package edu.education.birdmanagementapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@JsonPropertyOrder({"username", "password"})
public record AuthLoginRequestDTO(
        @NotBlank(message = "El username es obligatorio.")
        @Size(max = 50, message = "El username no puede exceder los 50 caracteres.")
        String username,
        @NotBlank(message = "El password es obligatorio.")
        @Size(max = 50, message = "El password no puede exceder los 50 caracteres.")
        String password) {
}
