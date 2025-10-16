package edu.education.birdmanagementapi.controller;

import edu.education.birdmanagementapi.domain.dto.request.HabitatRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.ApiResponseDTO;
import edu.education.birdmanagementapi.domain.dto.response.HabitatResponseDTO;
import edu.education.birdmanagementapi.service.interfaces.IHabitatService;
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
@RequestMapping("/habitats")
@RequiredArgsConstructor
public class HabitatController {

    private final IHabitatService habitatService;


    /**
     * Crea un nuevo hábitat en el sistema.
     *
     * @param habitatRequestDTO objeto con los datos del hábitat a registrar.
     * @param request            información del contexto HTTP para incluir la ruta en la respuesta.
     * @return una respuesta con el hábitat creado y estado {@code 201 Created}.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDTO<HabitatResponseDTO>> createHabitat(
            @Valid @RequestBody HabitatRequestDTO habitatRequestDTO,
            HttpServletRequest request) {

        HabitatResponseDTO createHabitat = habitatService.createHabitat(habitatRequestDTO);
        ApiResponseDTO<HabitatResponseDTO> response = ApiResponseDTO.<HabitatResponseDTO>builder()
                .success(true)
                .message("Habitat creado con éxito")
                .data(createHabitat)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Habitat creado: {} ", habitatRequestDTO.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza los datos de un hábitat existente identificado por su ID.
     *
     * @param id                 identificador único del hábitat a actualizar.
     * @param habitatRequestDTO  objeto con los nuevos datos del hábitat.
     * @param request            información del contexto HTTP.
     * @return una respuesta con el hábitat actualizado y estado {@code 200 OK}.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<HabitatResponseDTO>> updateHabitat(
            @PathVariable Long id,
            @Valid @RequestBody HabitatRequestDTO habitatRequestDTO,
            HttpServletRequest request) {
        HabitatResponseDTO updateHabitat = habitatService.updateHabitat(id, habitatRequestDTO);

        ApiResponseDTO<HabitatResponseDTO> response = ApiResponseDTO.<HabitatResponseDTO>builder()
                .success(true)
                .message("Habitat actualizado con éxito")
                .data(updateHabitat)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Habitat actualizado: {} ", habitatRequestDTO.name());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Recupera el listado completo de hábitats registrados en el sistema.
     *
     * @param request información del contexto HTTP.
     * @return una lista de todos los hábitats y estado {@code 200 OK}.
     */
    @GetMapping
    public ResponseEntity<ApiResponseDTO<List<HabitatResponseDTO>>> getAllHabitats(HttpServletRequest request) {
        List<HabitatResponseDTO> allHabitats = habitatService.findAllHabitats();

        ApiResponseDTO<List<HabitatResponseDTO>> response = ApiResponseDTO.<List<HabitatResponseDTO>>builder()
                .success(true)
                .message("Listado de Habitats recuperado con éxito.")
                .data(allHabitats)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Total de habitats recuperados: {} ", allHabitats.size());
        return ResponseEntity.ok(response);
    }

    /**
     * Busca un hábitat por su identificador único.
     *
     * @param id      ID del hábitat a consultar.
     * @param request información del contexto HTTP.
     * @return el hábitat encontrado y estado {@code 200 OK}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<HabitatResponseDTO>> getHabitatById(@PathVariable Long id,
                                                                             HttpServletRequest request) {

        HabitatResponseDTO habitatById = habitatService.findHabitatById(id);
        log.info("Consultando el habitat con Id: {}", id);

        ApiResponseDTO<HabitatResponseDTO> response = ApiResponseDTO.<HabitatResponseDTO>builder()
                .success(true)
                .message("Habitat encontrado con éxito.")
                .data(habitatById)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Habitat consultado con Id: {} ", id);
        return ResponseEntity.ok(response);
    }

    /**
     * Busca un hábitat por su nombre.
     *
     * @param name    nombre del hábitat a consultar.
     * @param request información del contexto HTTP.
     * @return el hábitat correspondiente al nombre indicado y estado {@code 200 OK}.
     */
    @GetMapping("/by-name")
    public ResponseEntity<ApiResponseDTO<HabitatResponseDTO>> getHabitatByName(@RequestParam String name,
                                                                               HttpServletRequest request) {
        HabitatResponseDTO habitatByName = habitatService.findHabitatByName(name);
        log.info("Cosultado el habitat con nombre: {}", name);

        ApiResponseDTO<HabitatResponseDTO> response = ApiResponseDTO.<HabitatResponseDTO>builder()
                .success(true)
                .message("Habitat encontrado con éxito")
                .data(habitatByName)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Habitat consultado con nombre: {} ", name);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina un hábitat existente identificado por su ID.
     *
     * @param id      ID del hábitat a eliminar.
     * @param request información del contexto HTTP.
     * @return una respuesta de confirmación y estado {@code 202 Accepted}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<HabitatResponseDTO>> deleteHabitatById(@PathVariable Long id,
                                                                                HttpServletRequest request) {
        habitatService.deleteHabitatById(id);

        ApiResponseDTO<HabitatResponseDTO> response = ApiResponseDTO.<HabitatResponseDTO>builder()
                .success(true)
                .message("Habitat eliminado con éxito")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Habitat eliminado con Id: {} ", id);

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

}
