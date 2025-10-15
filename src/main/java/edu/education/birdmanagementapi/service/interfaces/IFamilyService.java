package edu.education.birdmanagementapi.service.interfaces;

import edu.education.birdmanagementapi.domain.dto.request.FamilyRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.FamilyResponseDTO;

import java.util.List;

public interface IFamilyService {

    /**
     * Crea una nueva familia.
     *
     * @param familyRequestDTO datos de entrada
     * @return familia creada
     */
    FamilyResponseDTO createFamily(FamilyRequestDTO familyRequestDTO);

    /**
     * Actualiza una familia existente.
     *
     * @param id identificador de la familia
     * @param familyRequestDTO datos actualizados
     * @return familia actualizada
     */
    FamilyResponseDTO updateFamily(Long id, FamilyRequestDTO familyRequestDTO);

    /**
     * Obtiene todas las familias registradas.
     *
     * @return lista de familias
     */
    List<FamilyResponseDTO> findAllFamilies();

    /**
     * Busca una familia por su ID.
     *
     * @param id identificador de la familia
     * @return familia encontrada
     */
    FamilyResponseDTO findFamilyById(Long id);

    /**
     * Busca una familia por su nombre.
     *
     * @param name nombre de la familia
     * @return familia encontrada
     */
    FamilyResponseDTO findFamilyByName(String name);

    /**
     * Elimina una familia por su ID.
     *
     * @param id identificador de la familia
     */
    void deleteFamilyById(Long id);

}
