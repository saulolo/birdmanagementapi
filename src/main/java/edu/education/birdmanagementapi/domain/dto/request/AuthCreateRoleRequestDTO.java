package edu.education.birdmanagementapi.domain.dto.request;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Builder
@Validated
@JsonPropertyOrder({"roleListName"})
public record AuthCreateRoleRequestDTO(
        @Size(max = 3, message = "El usuario no puede tener mas de 3 roles.")
        List<String> roleListName) {
}
