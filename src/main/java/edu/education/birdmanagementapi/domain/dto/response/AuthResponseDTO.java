package edu.education.birdmanagementapi.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"username", "message", "jwt", "status"})
public record AuthResponseDTO(
        String username,
        String message,
        String jwt,
        boolean status) {
}
