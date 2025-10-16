package edu.education.birdmanagementapi.service.interfaces;

import edu.education.birdmanagementapi.domain.dto.request.SightingRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.SightingResponseDTO;

import java.time.LocalDateTime;
import java.util.List;

public interface ISightingService {

    /**
     * Crea un nuevo avistamiento.
     *
     * @param sightingRequestDTO datos del avistamiento a registrar
     * @return el avistamiento creado
     */
    SightingResponseDTO createSighting(SightingRequestDTO sightingRequestDTO);

    /**
     * Actualiza un avistamiento existente.
     *
     * @param id                 identificador del avistamiento
     * @param sightingRequestDTO datos actualizados
     * @return el avistamiento actualizado
     */
    SightingResponseDTO updateSighting(Long id, SightingRequestDTO sightingRequestDTO);

    /**
     * Lista todos los avistamientos registrados.
     *
     * @return lista de avistamientos
     */
    List<SightingResponseDTO> findAllSightings();

    /**
     * Obtiene un avistamiento por su ID.
     *
     * @param id identificador del avistamiento
     * @return el avistamiento encontrado
     */
    SightingResponseDTO findSightingById(Long id);


    /**
     * Busca avistamientos por el ID del ave.
     *
     * @param idBird identificador del ave
     * @return lista de avistamientos asociados
     */
    List<SightingResponseDTO> findByBirdId(Long idBird);

    /**
     * Busca avistamientos por el ID del usuario.
     *
     * @param idUser identificador del usuario
     * @return lista de avistamientos asociados
     */
    List<SightingResponseDTO> findByUserId(Long idUser);

    /**
     * Busca avistamientos por el ID del país.
     *
     * @param idCountry identificador del país
     * @return lista de avistamientos asociados
     */
    List<SightingResponseDTO> findByCountryId(Long idCountry);

    /**
     * Busca avistamientos por el ID del hábitat.
     *
     * @param idHabitat identificador del hábitat
     * @return lista de avistamientos asociados
     */
    List<SightingResponseDTO> findByHabitatId(Long idHabitat);

    /**
     * Busca avistamientos dentro de un rango de fechas.
     *
     * @param from fecha/hora de inicio
     * @param to   fecha/hora de fin
     * @return lista de avistamientos en el rango especificado
     */
    List<SightingResponseDTO> findByDateRange(LocalDateTime from, LocalDateTime to);

    /**
     * Elimina un avistamiento por su ID.
     *
     * @param id identificador del avistamiento a eliminar
     */
    void deleteSightingById(Long id);

}
