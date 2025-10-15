package edu.education.birdmanagementapi.service.interfaces;

import edu.education.birdmanagementapi.domain.dto.request.BirdRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.BirdResponseDTO;

import java.util.List;

public interface IBirdService {

    /**
     * Crea una nueva ave.
     *
     * @param birdRequestDTO datos de entrada
     * @return ave creada
     */
    BirdResponseDTO createBird(BirdRequestDTO birdRequestDTO);

    /**
     * Actualiza una ave existente.
     *
     * @param id             identificador del ave
     * @param birdRequestDTO datos actualizados
     * @return ave actualizada
     */
    BirdResponseDTO updateBird(Long id, BirdRequestDTO birdRequestDTO);

    /**
     * Busca un ave por su ID.
     *
     * @param id identificador del ave
     * @return DTO del ave encontrada
     */
    BirdResponseDTO findBirdById(Long id);

    /**
     * Obtiene todas las aves registradas.
     *
     * @return lista de aves
     */
    List<BirdResponseDTO> findAllBirds();

    /**
     * Elimina un ave por su ID.
     *
     * @param id identificador del ave
     */
    void deleteBirdById(Long id);

    /**
     * Busca aves cuyo nombre contenga el texto indicado.
     *
     * @param name fragmento del nombre
     * @return lista de resultados
     */
    List<BirdResponseDTO> searchByName(String name);

}
