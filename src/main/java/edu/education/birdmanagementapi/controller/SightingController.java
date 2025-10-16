package edu.education.birdmanagementapi.controller;

import edu.education.birdmanagementapi.domain.dto.request.SightingRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.ApiResponseDTO;
import edu.education.birdmanagementapi.domain.dto.response.SightingResponseDTO;
import edu.education.birdmanagementapi.service.interfaces.ISightingService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/sightings")
@RequiredArgsConstructor
public class SightingController {

    private final ISightingService sightingService;

    /**
     * Crea un nuevo avistamiento.
     *
     * @param sightingRequestDTO datos del avistamiento a registrar
     * @return respuesta con el avistamiento creado
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<SightingResponseDTO>> createSighting(
            @Valid @RequestBody SightingRequestDTO sightingRequestDTO, HttpServletRequest request) {

        SightingResponseDTO createSighting = sightingService.createSighting(sightingRequestDTO);
        ApiResponseDTO<SightingResponseDTO> response = ApiResponseDTO.<SightingResponseDTO>builder()
                .success(true)
                .message("Avistamiento creado con éxito.")
                .data(createSighting)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza un avistamiento existente.
     *
     * @param id                 id del avistamiento a actualizar
     * @param sightingRequestDTO datos actualizados
     * @param request            HttpServletRequest para path
     * @return ApiResponseDTO con el avistamiento actualizado
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<SightingResponseDTO>> updateSighting(
            @PathVariable Long id,
            @Valid @RequestBody SightingRequestDTO sightingRequestDTO,
            HttpServletRequest request) {

        SightingResponseDTO updated = sightingService.updateSighting(id, sightingRequestDTO);

        ApiResponseDTO<SightingResponseDTO> response = ApiResponseDTO.<SightingResponseDTO>builder()
                .success(true)
                .message("Avistamiento actualizado con éxito.")
                .data(updated)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Avistamiento actualizado: id={}", id);
        return ResponseEntity.ok(response);
    }

    /**
     * Recupera todos los avistamientos.
     *
     * @param request HttpServletRequest para path
     * @return ApiResponseDTO con la lista de avistamientos
     */
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<SightingResponseDTO>>> getAllSightings(
            HttpServletRequest request) {

        List<SightingResponseDTO> list = sightingService.findAllSightings();

        ApiResponseDTO<List<SightingResponseDTO>> response = ApiResponseDTO.<List<SightingResponseDTO>>builder()
                .success(true)
                .message("Listado de avistamientos recuperado con éxito.")
                .data(list)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Total de avistamientos recuperados: {}", list.size());
        return ResponseEntity.ok(response);
    }

    /**
     * Recupera un avistamiento por su ID.
     *
     * @param id      id del avistamiento
     * @param request HttpServletRequest para path
     * @return ApiResponseDTO con el avistamiento encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<SightingResponseDTO>> getSightingById(
            @PathVariable Long id,
            HttpServletRequest request) {

        SightingResponseDTO sighting = sightingService.findSightingById(id);

        ApiResponseDTO<SightingResponseDTO> response = ApiResponseDTO.<SightingResponseDTO>builder()
                .success(true)
                .message("Avistamiento encontrado con éxito.")
                .data(sighting)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Avistamiento consultado con ID: {}", id);
        return ResponseEntity.ok(response);
    }

    /**
     * Busca avistamientos por ID del ave.
     *
     * @param idBird  id del ave
     * @param request HttpServletRequest para path
     * @return ApiResponseDTO con la lista de avistamientos
     */
    @GetMapping("/birds/{idBird}/sighting")
    public ResponseEntity<ApiResponseDTO<List<SightingResponseDTO>>> getByBirdId(
            @PathVariable Long idBird,
            HttpServletRequest request) {

        List<SightingResponseDTO> results = sightingService.findByBirdId(idBird);

        ApiResponseDTO<List<SightingResponseDTO>> response = ApiResponseDTO.<List<SightingResponseDTO>>builder()
                .success(true)
                .message(results.isEmpty() ? "No se encontaron aviatamientos." : "Avistamientos recuperados con éxito.")
                .data(results)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Búsqueda de avistamientos por aveId={}, resultados={}", idBird, results.size());
        return ResponseEntity.ok(response);
    }

    /**
     * Busca avistamientos por ID del usuario.
     *
     * @param idUser  id del usuario
     * @param request HttpServletRequest para path
     * @return ApiResponseDTO con la lista de avistamientos
     */
    @GetMapping("/users/{idUser}/sighting")
    public ResponseEntity<ApiResponseDTO<List<SightingResponseDTO>>> getByUserId(
            @PathVariable Long idUser,
            HttpServletRequest request) {

        List<SightingResponseDTO> results = sightingService.findByUserId(idUser);

        ApiResponseDTO<List<SightingResponseDTO>> response = ApiResponseDTO.<List<SightingResponseDTO>>builder()
                .success(true)
                .message("Avistamientos por usuario recuperados con éxito.")
                .data(results)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Búsqueda de avistamientos por userId={}, resultados={}", idUser, results.size());
        return ResponseEntity.ok(response);
    }

    /**
     * Busca avistamientos por ID del país.
     *
     * @param idCountry id del país
     * @param request   HttpServletRequest para path
     * @return ApiResponseDTO con la lista de avistamientos
     */
    @GetMapping("/countries/{idCountry}/sighting")
    public ResponseEntity<ApiResponseDTO<List<SightingResponseDTO>>> getByCountryId(
            @PathVariable Long idCountry,
            HttpServletRequest request) {

        List<SightingResponseDTO> results = sightingService.findByCountryId(idCountry);

        ApiResponseDTO<List<SightingResponseDTO>> response = ApiResponseDTO.<List<SightingResponseDTO>>builder()
                .success(true)
                .message("Avistamientos por país recuperados con éxito.")
                .data(results)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Búsqueda de avistamientos por countryId={}, resultados={}", idCountry, results.size());
        return ResponseEntity.ok(response);
    }

    /**
     * Busca avistamientos por ID del hábitat.
     *
     * @param idHabitat id del hábitat
     * @param request   HttpServletRequest para path
     * @return ApiResponseDTO con la lista de avistamientos
     */
    @GetMapping("/habitats/{idHabitat}/sighting")
    public ResponseEntity<ApiResponseDTO<List<SightingResponseDTO>>> getByHabitatId(
            @PathVariable Long idHabitat,
            HttpServletRequest request) {

        List<SightingResponseDTO> results = sightingService.findByHabitatId(idHabitat);

        ApiResponseDTO<List<SightingResponseDTO>> response = ApiResponseDTO.<List<SightingResponseDTO>>builder()
                .success(true)
                .message("Avistamientos por hábitat recuperados con éxito.")
                .data(results)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Búsqueda de avistamientos por habitatId={}, resultados={}", idHabitat, results.size());
        return ResponseEntity.ok(response);
    }

    /**
     * Busca avistamientos dentro de un rango de fechas.
     *
     * @param from    fecha/hora de inicio (ISO-8601)
     * @param to      fecha/hora de fin (ISO-8601)
     * @param request HttpServletRequest para path
     * @return ApiResponseDTO con la lista de avistamientos dentro del rango
     */
    @GetMapping("/date-range")
    public ResponseEntity<ApiResponseDTO<List<SightingResponseDTO>>> getByDateRange(
            @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime from,
            @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime to,
            HttpServletRequest request) {

        List<SightingResponseDTO> results = sightingService.findByDateRange(from, to);

        ApiResponseDTO<List<SightingResponseDTO>> response = ApiResponseDTO.<List<SightingResponseDTO>>builder()
                .success(true)
                .message("Avistamientos en el rango de fechas recuperados con éxito.")
                .data(results)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Búsqueda de avistamientos por rango from={}, to={}, resultados={}", from, to, results.size());
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un avistamiento por su ID.
     *
     * @param id      id del avistamiento a eliminar
     * @param request HttpServletRequest para path
     * @return ApiResponseDTO con confirmación de eliminación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteSighting(
            @PathVariable Long id,
            HttpServletRequest request) {

        sightingService.deleteSightingById(id);

        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .success(true)
                .message("Avistamiento eliminado con éxito.")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.warn("Avistamiento eliminado con ID: {}", id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);

    }

}