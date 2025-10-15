package edu.education.birdmanagementapi.controller;

import edu.education.birdmanagementapi.domain.dto.request.FamilyRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.ApiResponseDTO;
import edu.education.birdmanagementapi.domain.dto.response.FamilyResponseDTO;
import edu.education.birdmanagementapi.service.interfaces.IFamilyService;
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
@RequestMapping("/families")
@RequiredArgsConstructor
public class FamilyController {

    private final IFamilyService familyService;


    /**
     * Crea una nueva familia.
     *
     * @param familyRequestDTO datos de la familia
     * @param request          información HTTP
     * @return familia creada
     */
    @PostMapping()
    public ResponseEntity<ApiResponseDTO<FamilyResponseDTO>> createFamily(@Valid @RequestBody FamilyRequestDTO familyRequestDTO,
                                                                          HttpServletRequest request) {
        FamilyResponseDTO createdFamily = familyService.createFamily(familyRequestDTO);
        ApiResponseDTO<FamilyResponseDTO> response = ApiResponseDTO.<FamilyResponseDTO>builder()
                .success(true)
                .message("Familia creada con éxito.")
                .data(createdFamily)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Familia creada: {}", createdFamily.name());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Actualiza una familia existente.
     *
     * @param id               id de la familia
     * @param familyRequestDTO nuevos datos
     * @param request          información HTTP
     * @return familia actualizada
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<FamilyResponseDTO>> updateFamily(@PathVariable Long id,
                                                                          @Valid @RequestBody FamilyRequestDTO familyRequestDTO,
                                                                          HttpServletRequest request) {

        FamilyResponseDTO updatedFamily = familyService.updateFamily(id, familyRequestDTO);
        ApiResponseDTO<FamilyResponseDTO> response = ApiResponseDTO.<FamilyResponseDTO>builder()
                .success(true)
                .message("Familia actualizada con éxito.")
                .data(updatedFamily)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Familia actualizada: {}", updatedFamily.name());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    /**
     * Obtiene todas las familias.
     *
     * @param request información HTTP
     * @return lista de familias
     */
    @GetMapping()
    public ResponseEntity<ApiResponseDTO<List<FamilyResponseDTO>>> getAllFamilies(HttpServletRequest request) {
        List<FamilyResponseDTO> allFamilies = familyService.findAllFamilies();

        ApiResponseDTO<List<FamilyResponseDTO>> response = ApiResponseDTO.<List<FamilyResponseDTO>>builder()
                .success(true)
                .message("Listado de familias recuperado con éxito.")
                .data(allFamilies)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Total de familias recuperadas: {}", allFamilies.size());
        return ResponseEntity.ok(response);
    }

    /**
     * Obtiene una familia por su ID.
     *
     * @param id      id de la familia
     * @param request información HTTP
     * @return familia encontrada
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<FamilyResponseDTO>> getFamilyById(@PathVariable Long id,
                                                                           HttpServletRequest request) {
        FamilyResponseDTO familyById = familyService.findFamilyById(id);
        log.debug("Consultando familia con ID: {}", id);
        ApiResponseDTO<FamilyResponseDTO> response = ApiResponseDTO.<FamilyResponseDTO>builder()
                .success(true)
                .message("Familia encontrada con éxito.")
                .data(familyById)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Familia consultada con ID: {}", id);
        return ResponseEntity.ok(response);
    }

    /**
     * Busca una familia por nombre.
     *
     * @param name    nombre de la familia
     * @param request información HTTP
     * @return familia encontrada
     */
    @GetMapping("/by-name")
    public ResponseEntity<ApiResponseDTO<FamilyResponseDTO>> getFamilyByName(@RequestParam String name,
                                                                             HttpServletRequest request) {
        FamilyResponseDTO familyByName = familyService.findFamilyByName(name);
        log.debug("Consultando familia con nombre: {}", name);

        ApiResponseDTO<FamilyResponseDTO> response = ApiResponseDTO.<FamilyResponseDTO>builder()
                .success(true)
                .message("Familia encontrada con éxito.")
                .data(familyByName)
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Familia consultada con nombre: {}", name);
        return ResponseEntity.ok(response);
    }

    /**
     * Elimina una familia por su ID.
     *
     * @param id      id de la familia
     * @param request información HTTP
     * @return confirmación de eliminación
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDTO<Void>> deleteFamilyById(@PathVariable Long id, HttpServletRequest request) {

        familyService.deleteFamilyById(id);

        ApiResponseDTO<Void> response = ApiResponseDTO.<Void>builder()
                .success(true)
                .message("Familia eliminada con éxito.")
                .timestamp(LocalDateTime.now())
                .path(request.getRequestURI())
                .build();

        log.info("Familia eliminada con ID: {}", id);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }
}
