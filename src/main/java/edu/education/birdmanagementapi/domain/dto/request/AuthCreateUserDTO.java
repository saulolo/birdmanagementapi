package edu.education.birdmanagementapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;

@Builder
@JsonPropertyOrder({"name", "username", "email", "password", "roleRequestDTO"})
public record AuthCreateUserDTO(
        @NotBlank(message = "El nombre es obligatorio.")
        @Size(max = 30, message = "El nombre no puede exceder los 30 caracteres.")
        String name,
        @NotBlank(message = "El username es obligatorio.")
        @Email(message = "El formato del email no es válido.")
        @Size(max = 30, message = "El username no puede exceder los 30 caracteres.")
        String username,
        @NotBlank(message = "El email es obligatorio.")
        @Size(max = 100, message = "El email no puede exceder los 100 caracteres.")
        String email,
        @NotBlank(message = "El password es obligatorio.")
        String password,
        @Valid
        AuthCreateRoleRequestDTO roleRequestDTO) {
}
