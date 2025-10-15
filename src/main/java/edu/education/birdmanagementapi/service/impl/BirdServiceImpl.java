package edu.education.birdmanagementapi.service.impl;

import edu.education.birdmanagementapi.domain.dto.request.BirdRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.BirdResponseDTO;
import edu.education.birdmanagementapi.domain.entity.Bird;
import edu.education.birdmanagementapi.domain.entity.Family;
import edu.education.birdmanagementapi.domain.entity.Habitat;
import edu.education.birdmanagementapi.domain.mapper.BirdMapper;
import edu.education.birdmanagementapi.exception.ConflictException;
import edu.education.birdmanagementapi.exception.ResourceNotFoundException;
import edu.education.birdmanagementapi.repository.BirdRepository;
import edu.education.birdmanagementapi.repository.FamilyRepository;
import edu.education.birdmanagementapi.repository.HabitatRepository;
import edu.education.birdmanagementapi.service.interfaces.IBirdService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BirdServiceImpl implements IBirdService {

    private final BirdRepository birdRepository;
    private final FamilyRepository familyRepository;
    private final HabitatRepository habitatRepository;
    private final BirdMapper birdMapper;


    @Override
    @Transactional
    public BirdResponseDTO createBird(BirdRequestDTO birdRequestDTO) {
        boolean existsBird = birdRepository.existsByCommonNameOrScientificName(
                birdRequestDTO.commonName(),
                birdRequestDTO.scientificName()
        );

        if (existsBird) {
            log.warn("Intento de creación duplicada: {}", birdRequestDTO.scientificName());
            throw new ConflictException("Ya existe un ave con el mismo nombre común o científico.");
        }

        Family family = familyRepository.findById(birdRequestDTO.idFamily())
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra la familia con ID: " + birdRequestDTO.idFamily()));

        Set<Habitat> habitats = new HashSet<>();
        if (birdRequestDTO.idHabitats() != null && !birdRequestDTO.idHabitats().isEmpty()) {
            habitats = birdRequestDTO.idHabitats().stream()
                    .map(id -> habitatRepository.findById(id)
                            .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el hábitat con ID: " + id)))
                    .collect(Collectors.toSet());
        }

        Bird newBird = birdMapper.toBirdEntity(birdRequestDTO, family, habitats);
        Bird createdBird = birdRepository.save(newBird);

        log.info("Ave creada con éxito: {}", createdBird.getScientificName());
        return birdMapper.toBirdResponseDTO(createdBird);
    }

    @Override
    @Transactional
    public BirdResponseDTO updateBird(Long id, BirdRequestDTO birdRequestDTO) {
        Bird existingBird = birdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el ave con ID: " + id));

        existingBird.setCommonName(birdRequestDTO.commonName());
        existingBird.setScientificName(birdRequestDTO.scientificName());
        existingBird.setConservationModel(birdRequestDTO.conservationModel());
        existingBird.setNotes(birdRequestDTO.notes());

        Family family = familyRepository.findById(birdRequestDTO.idFamily())
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra la familia con ID: " + birdRequestDTO.idFamily()));
        existingBird.setFamily(family);

        if (birdRequestDTO.idHabitats() != null) {
            Set<Habitat> habitats = birdRequestDTO.idHabitats().stream()
                    .map(idHabitat -> habitatRepository.findById(idHabitat)
                            .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el hábitat con ID: " + idHabitat)))
                    .collect(Collectors.toSet());
            existingBird.setHabitats(habitats);
        }

        Bird updatedBird = birdRepository.save(existingBird);
        log.info("Ave actualizada con ID: {}", id);

        return birdMapper.toBirdResponseDTO(updatedBird);
    }

    @Override
    @Transactional(readOnly = true)
    public BirdResponseDTO findBirdById(Long id) {
        Bird bird = birdRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra el ave con ID: " + id));
        return birdMapper.toBirdResponseDTO(bird);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BirdResponseDTO> findAllBirds() {
        List<Bird> birds = birdRepository.findAll();
        return birdMapper.toBirdResponseList(birds);
    }

    @Override
    @Transactional
    public void deleteBirdById(Long id) {
        if (!birdRepository.existsById(id)) {
            throw new ResourceNotFoundException("No se encuentra el ave con ID: " + id);
        }
        birdRepository.deleteById(id);
        log.info("Ave eliminada con ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<BirdResponseDTO> searchByName(String name) {
        List<Bird> birds = birdRepository.findAll().stream()
                .filter(bird -> bird.getCommonName().toLowerCase().contains(name.toLowerCase()) ||
                        bird.getScientificName().toLowerCase().contains(name.toLowerCase()))
                .toList();

        if (birds.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron aves con el nombre: " + name);
        }

        return birdMapper.toBirdResponseList(birds);
    }
}
