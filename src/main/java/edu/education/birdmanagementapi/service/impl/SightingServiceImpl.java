package edu.education.birdmanagementapi.service.impl;

import edu.education.birdmanagementapi.domain.dto.request.SightingRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.SightingResponseDTO;
import edu.education.birdmanagementapi.domain.entity.*;
import edu.education.birdmanagementapi.domain.mapper.SightingMapper;
import edu.education.birdmanagementapi.exception.ResourceNotFoundException;
import edu.education.birdmanagementapi.repository.*;
import edu.education.birdmanagementapi.service.interfaces.ISightingService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SightingServiceImpl implements ISightingService {

    private final CountryRepository countryRepository;
    private final BirdRepository birdRepository;
    private final HabitatRepository habitatRepository;
    private final UserRepository userRepository;
    private final SightingMapper sightingMapper;
    private final SightingRepository sightingRepository;


    /**
     * Crea un nuevo avistamiento.
     *
     * @param sightingRequestDTO datos del avistamiento a registrar
     * @return el avistamiento creado
     */
    @Override
    @Transactional
    public SightingResponseDTO createSighting(SightingRequestDTO sightingRequestDTO) {

        Country existsCountry = countryRepository.findById(sightingRequestDTO.idCountry())
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el pais con Id: " +
                        sightingRequestDTO.idCountry()));

        Bird existsBird = birdRepository.findById(sightingRequestDTO.idBird())
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el ave con Id: " +
                        sightingRequestDTO.idBird()));

        Habitat existsHabitat = habitatRepository.findById(sightingRequestDTO.idHabitat())
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el habitat con Id: " +
                        sightingRequestDTO.idHabitat()));

        User existsUser = userRepository.findById(sightingRequestDTO.idUser())
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el usuario con Id: " +
                        sightingRequestDTO.idUser()));

        Sighting newSighting = sightingMapper.toSightingEntity(sightingRequestDTO, existsCountry, existsBird,
                existsHabitat, existsUser);

        Sighting createSighting = sightingRepository.save(newSighting);
        log.info("Avistamiento creado con éxito: {}", createSighting.getIdSighting());

        return sightingMapper.toSightingResponseDTO(createSighting);
    }

    /**
     * Actualiza un avistamiento existente.
     *
     * @param id                 identificador del avistamiento
     * @param sightingRequestDTO datos actualizados
     * @return el avistamiento actualizado
     */
    @Override
    @Transactional
    public SightingResponseDTO updateSighting(Long id, SightingRequestDTO sightingRequestDTO) {

        Sighting existsSighting = sightingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el avistamiento con ID: " + id));

        Country existsCountry = countryRepository.findById(sightingRequestDTO.idCountry())
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el pais con Id: " +
                        sightingRequestDTO.idCountry()));

        Bird existsBird = birdRepository.findById(sightingRequestDTO.idBird())
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el ave con Id: " +
                        sightingRequestDTO.idBird()));

        Habitat existsHabitat = habitatRepository.findById(sightingRequestDTO.idHabitat())
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el habitat con Id: " +
                        sightingRequestDTO.idHabitat()));

        User existsUser = userRepository.findById(sightingRequestDTO.idUser())
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el usuario con Id: " +
                        sightingRequestDTO.idUser()));

        existsSighting.setLatitude(sightingRequestDTO.latitude());
        existsSighting.setLongitude(sightingRequestDTO.longitude());
        existsSighting.setSightingDateTime(sightingRequestDTO.sightingDateTime());
        existsSighting.setNotes(sightingRequestDTO.notes());
        existsSighting.setCountry(existsCountry);
        existsSighting.setBird(existsBird);
        existsSighting.setHabitat(existsHabitat);
        existsSighting.setUser(existsUser);

        Sighting updateSighting = sightingRepository.save(existsSighting);
        log.info("Avistamiento actualizado ID: {}", id);
        return sightingMapper.toSightingResponseDTO(updateSighting);
    }

    /**
     * Lista todos los avistamientos registrados.
     *
     * @return lista de avistamientos
     */
    @Override
    @Transactional(readOnly = true)
    public List<SightingResponseDTO> findAllSightings() {
        return sightingRepository.findAll().stream()
                .map(sightingMapper::toSightingResponseDTO)
                .toList();
    }

    /**
     * Obtiene un avistamiento por su ID.
     *
     * @param id identificador del avistamiento
     * @return el avistamiento encontrado
     */
    @Override
    @Transactional(readOnly = true)
    public SightingResponseDTO findSightingById(Long id) {
        Sighting sightingById = sightingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el avistamiento con ID: " + id));
        return sightingMapper.toSightingResponseDTO(sightingById);
    }

    /**
     * Busca avistamientos por el ID del ave.
     *
     * @param idBird identificador del ave
     * @return lista de avistamientos asociados
     */
    @Override
    @Transactional(readOnly = true)
    public List<SightingResponseDTO> findByBirdId(Long idBird) {
        return sightingRepository.findByBirdIdBird(idBird).stream()
                .map(sightingMapper::toSightingResponseDTO)
                .toList();
    }

    /**
     * Busca avistamientos por el ID del usuario.
     *
     * @param idUser identificador del usuario
     * @return lista de avistamientos asociados
     */
    @Override
    @Transactional(readOnly = true)
    public List<SightingResponseDTO> findByUserId(Long idUser) {
        return sightingRepository.findByUserIdUser(idUser).stream()
                .map(sightingMapper::toSightingResponseDTO)
                .toList();
    }

    /**
     * Busca avistamientos por el ID del país.
     *
     * @param idCountry identificador del país
     * @return lista de avistamientos asociados
     */
    @Override
    @Transactional(readOnly = true)
    public List<SightingResponseDTO> findByCountryId(Long idCountry) {
        return sightingRepository.findByCountryIdCountry(idCountry).stream()
                .map(sightingMapper::toSightingResponseDTO)
                .toList();
    }

    /**
     * Busca avistamientos por el ID del hábitat.
     *
     * @param idHabitat identificador del hábitat
     * @return lista de avistamientos asociados
     */
    @Override
    @Transactional(readOnly = true)
    public List<SightingResponseDTO> findByHabitatId(Long idHabitat) {
        return sightingRepository.findByHabitatIdHabitat(idHabitat).stream()
                .map(sightingMapper::toSightingResponseDTO)
                .toList();
    }

    /**
     * Busca avistamientos dentro de un rango de fechas.
     *
     * @param from fecha/hora de inicio
     * @param to   fecha/hora de fin
     * @return lista de avistamientos en el rango especificado
     */
    @Override
    @Transactional(readOnly = true)
    public List<SightingResponseDTO> findByDateRange(LocalDateTime from, LocalDateTime to) {
        return sightingRepository.findBySightingDateTimeBetween(from, to).stream()
                .map(sightingMapper::toSightingResponseDTO)
                .toList();
    }

    /**
     * Elimina un avistamiento por su ID.
     *
     * @param id identificador del avistamiento a eliminar
     */
    @Override
    @Transactional
    public void deleteSightingById(Long id) {
        if (!sightingRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encuentra el avistamiento con ID: " + id);
        }
        sightingRepository.deleteById(id);
        log.info("Avistamiento eliminado ID: {}", id);
    }
}
