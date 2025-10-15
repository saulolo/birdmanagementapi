package edu.education.birdmanagementapi.controller;

import edu.education.birdmanagementapi.domain.dto.request.BirdRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.ApiResponseDTO;
import edu.education.birdmanagementapi.domain.dto.response.BirdResponseDTO;
import edu.education.birdmanagementapi.service.interfaces.IBirdService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/birds")
@RequiredArgsConstructor
public class BirdController {

    private final IBirdService birdService;

    @PostMapping
    public ResponseEntity<ApiResponseDTO<BirdResponseDTO>> createBird(
            @Valid @RequestBody BirdRequestDTO birdRequestDTO,
            HttpServletRequest request) {

        BirdResponseDTO createdBird = birdService.createBird(birdRequestDTO);

        ApiResponseDTO<BirdResponseDTO> response = ApiResponseDTO.<BirdResponseDTO>builder()
                .success(true)
                .message("Ave creada con éxito.")
                .data(createdBird)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Ave creada exitosamente: {}", createdBird.commonName());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<BirdResponseDTO>> updateBird(
            @PathVariable Long id,
            @Valid @RequestBody BirdRequestDTO birdRequestDTO,
            HttpServletRequest request) {

        BirdResponseDTO updatedBird = birdService.updateBird(id, birdRequestDTO);

        ApiResponseDTO<BirdResponseDTO> response = ApiResponseDTO.<BirdResponseDTO>builder()
                .success(true)
                .message("Ave actualizada con éxito.")
                .data(updatedBird)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Ave actualizada con ID: {}", id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<BirdResponseDTO>>> getAllBirds(HttpServletRequest request) {
        List<BirdResponseDTO> birds = birdService.findAllBirds();

        ApiResponseDTO<List<BirdResponseDTO>> response = ApiResponseDTO.<List<BirdResponseDTO>>builder()
                .success(true)
                .message("Listado de aves recuperado con éxito.")
                .data(birds)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Total de aves recuperadas: {}", birds.size());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<BirdResponseDTO>> getBirdById(
            @PathVariable Long id,
            HttpServletRequest request) {

        BirdResponseDTO bird = birdService.findBirdById(id);

        ApiResponseDTO<BirdResponseDTO> response = ApiResponseDTO.<BirdResponseDTO>builder()
                .success(true)
                .message("Ave encontrada con éxito.")
                .data(bird)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Ave consultada con ID: {}", id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/by-name")
    public ResponseEntity<ApiResponseDTO<List<BirdResponseDTO>>> getBirdsByName(
            @RequestParam String name,
            HttpServletRequest request) {

        List<BirdResponseDTO> results = birdService.searchByName(name);

        ApiResponseDTO<List<BirdResponseDTO>> response = ApiResponseDTO.<List<BirdResponseDTO>>builder()
                .success(true)
                .message("Resultados de búsqueda por nombre recuperados con éxito.")
                .data(results)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Búsqueda de aves por nombre: '{}', total resultados: {}", name, results.size());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteBirdById(
            @PathVariable Long id,
            HttpServletRequest request) {

        birdService.deleteBirdById(id);

        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .success(true)
                .message("Ave eliminada con éxito.")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.warn("Ave eliminada con ID: {}", id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}