package edu.education.birdmanagementapi.service.interfaces;

import edu.education.birdmanagementapi.domain.dto.request.HabitatRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.HabitatResponseDTO;

import java.util.List;

public interface IHabitatService {


    /**
     * Crea un nuevo hábitat en el sistema.
     *
     * @param habitatRequestDTO datos del hábitat a registrar.
     * @return el hábitat creado.
     */
    HabitatResponseDTO createHabitat(HabitatRequestDTO habitatRequestDTO);

    /**
     * Actualiza un hábitat existente.
     *
     * @param id                identificador del hábitat a actualizar.
     * @param habitatRequestDTO datos actualizados del hábitat.
     * @return el hábitat actualizado.
     */
    HabitatResponseDTO updateHabitat(Long id, HabitatRequestDTO habitatRequestDTO);

    /**
     * Recupera todos los hábitats registrados.
     *
     * @return lista de hábitats.
     */
    List<HabitatResponseDTO> findAllHabitats();

    /**
     * Busca un hábitat por su ID.
     *
     * @param id identificador único del hábitat.
     * @return el hábitat encontrado.
     */
    HabitatResponseDTO findHabitatById(Long id);

    /**
     * Busca un hábitat por su nombre.
     *
     * @param name nombre del hábitat a buscar.
     * @return el hábitat correspondiente.
     */
    HabitatResponseDTO findHabitatByName(String name);

    /**
     * Elimina un hábitat según su ID.
     *
     * @param id identificador del hábitat a eliminar.
     */
    void deleteHabitatById(Long id);

}
