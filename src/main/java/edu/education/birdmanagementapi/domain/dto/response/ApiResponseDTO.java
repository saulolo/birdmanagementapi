package edu.education.birdmanagementapi.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"success", "message", "data", "timestamp", "path"})
public record ApiResponseDTO<T>(
        boolean success,
        String message,
        T data,
        LocalDateTime timestamp,
        String path) {
}
