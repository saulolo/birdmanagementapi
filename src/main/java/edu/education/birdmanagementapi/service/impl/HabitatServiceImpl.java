package edu.education.birdmanagementapi.service.impl;

import edu.education.birdmanagementapi.domain.dto.request.HabitatRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.HabitatResponseDTO;
import edu.education.birdmanagementapi.domain.entity.Habitat;
import edu.education.birdmanagementapi.domain.mapper.HabitatMapper;
import edu.education.birdmanagementapi.exception.ConflictException;
import edu.education.birdmanagementapi.exception.ResourceNotFoundException;
import edu.education.birdmanagementapi.repository.HabitatRepository;
import edu.education.birdmanagementapi.service.interfaces.IHabitatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class HabitatServiceImpl implements IHabitatService {

    private final HabitatRepository habitatRepository;
    private final HabitatMapper habitatMapper;


    /**
     * Crea un nuevo hábitat validando que no exista previamente uno con el mismo nombre.
     *
     * @param habitatRequestDTO DTO con los datos del hábitat a crear.
     * @return el hábitat creado.
     * @throws ConflictException si el nombre ya está registrado.
     */
    @Override
    @Transactional
    public HabitatResponseDTO createHabitat(HabitatRequestDTO habitatRequestDTO) {
        boolean existHabitat = habitatRepository.existsByName(habitatRequestDTO.name());
        if (existHabitat) {
            log.warn("Intento de creación de habitat duplicada: {}", habitatRequestDTO.name());
            throw new ConflictException("El habitat: " + habitatRequestDTO.name() + " ya se ecuentra registrada.");
        }
        Habitat newHabitat = habitatMapper.toHabitatEntity(habitatRequestDTO);
        Habitat createdHabitat = habitatRepository.save(newHabitat);
        log.info("Habitat creada con éxito.");
        return habitatMapper.toHabitatResponseDTO(createdHabitat);
    }

    /**
     * Actualiza los datos de un hábitat existente.
     *
     * @param id                ID del hábitat a actualizar.
     * @param habitatRequestDTO DTO con los nuevos datos.
     * @return el hábitat actualizado.
     * @throws ResourceNotFoundException si el hábitat no existe.
     */
    @Override
    @Transactional
    public HabitatResponseDTO updateHabitat(Long id, HabitatRequestDTO habitatRequestDTO) {
        Habitat existHabitat = habitatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el habitat con ID: " + id));

        existHabitat.setName(habitatRequestDTO.name());
        existHabitat.setDescription(habitatRequestDTO.description());

        Habitat updatedHabitat = habitatRepository.save(existHabitat);
        log.info("Habitat con ID: {} actualizada con éxito.", id);
        return habitatMapper.toHabitatResponseDTO(updatedHabitat);
    }

    /**
     * Obtiene todos los hábitats registrados.
     *
     * @return lista de hábitats existentes.
     */
    @Override
    @Transactional(readOnly = true)
    public List<HabitatResponseDTO> findAllHabitats() {
        log.debug("Consultando habitats registrados.");
        List<Habitat> habitats = habitatRepository.findAll();
        return habitatMapper.toHabitatResponseList(habitats);
    }

    /**
     * Busca un hábitat por su identificador único.
     *
     * @param id ID del hábitat.
     * @return el hábitat correspondiente.
     * @throws ResourceNotFoundException si no existe el hábitat.
     */
    @Override
    @Transactional(readOnly = true)
    public HabitatResponseDTO findHabitatById(Long id) {
        Habitat existHabitat = habitatRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el habitta con ID: " + id));
        log.debug("Consultando un habitat por su ID: {} ", id);
        return habitatMapper.toHabitatResponseDTO(existHabitat);
    }

    /**
     * Busca un hábitat por su nombre.
     *
     * @param name nombre del hábitat.
     * @return el hábitat correspondiente.
     * @throws ResourceNotFoundException si no se encuentra un hábitat con ese nombre.
     */
    @Override
    @Transactional(readOnly = true)
    public HabitatResponseDTO findHabitatByName(String name) {
        Habitat existHabitat = habitatRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontro el habitta con nombre: " + name));
        log.debug("Consultando un habitat por su nombre: {} ", name);
        return habitatMapper.toHabitatResponseDTO(existHabitat);
    }

    /**
     * Elimina un hábitat por su ID si existe en el sistema.
     *
     * @param id identificador del hábitat a eliminar.
     * @throws ResourceNotFoundException si el hábitat no existe.
     */
    @Override
    @Transactional
    public void deleteHabitatById(Long id) {
        if (!habitatRepository.existsById(id)) {
            log.warn("Intentando eliminar Habitat inexistente con ID: {} ", id);
            throw new ResourceNotFoundException("No se encuentra el habitat con ID: " + id);
        }
        habitatRepository.deleteById(id);
        log.info("Eliminado el registro del habitat con ID: {} con éxito.", id);
    }

}
