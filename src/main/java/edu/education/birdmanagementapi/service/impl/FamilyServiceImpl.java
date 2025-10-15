package edu.education.birdmanagementapi.service.impl;

import edu.education.birdmanagementapi.domain.dto.request.FamilyRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.FamilyResponseDTO;
import edu.education.birdmanagementapi.domain.entity.Family;
import edu.education.birdmanagementapi.domain.mapper.FamilyMapper;
import edu.education.birdmanagementapi.exception.ConflictException;
import edu.education.birdmanagementapi.exception.ResourceNotFoundException;
import edu.education.birdmanagementapi.repository.FamilyRepository;
import edu.education.birdmanagementapi.service.interfaces.IFamilyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FamilyServiceImpl implements IFamilyService {

    private final FamilyRepository familyRepository;
    private final FamilyMapper familyMapper;

    /**
     * Crea una nueva familia validando duplicados.
     */
    @Override
    @Transactional
    public FamilyResponseDTO createFamily(FamilyRequestDTO familyRequestDTO) {
        boolean isExistingFamily = familyRepository.existsByName(familyRequestDTO.name());
        if (isExistingFamily) {
            log.warn("Intento de creación de familia duplicada: {}", familyRequestDTO.name());
            throw new ConflictException("La familia: " + familyRequestDTO.name() + " ya se ecuentra registrada.");
        }
        Family newFamily = familyMapper.toFamilyEntity(familyRequestDTO);
        Family createdFamily = familyRepository.save(newFamily);
        log.info("Familia creada con éxito.");
        return familyMapper.toFamilyResponseDTO(createdFamily);
    }

    /**
     * Actualiza una familia existente.
     */
    @Override
    @Transactional
    public FamilyResponseDTO updateFamily(Long id, FamilyRequestDTO familyRequestDTO) {
        Family existingFamily = familyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encontró la familia con Id: " + id));

        existingFamily.setName(familyRequestDTO.name());
        existingFamily.setDescription(familyRequestDTO.description());

        Family updatedFamily = familyRepository.save(existingFamily);
        log.info("Familia con ID: {} actualizada con éxito.", id);
        return familyMapper.toFamilyResponseDTO(updatedFamily);
    }

    /**
     * Lista todas las familias registradas.
     */
    @Override
    @Transactional(readOnly = true)
    public List<FamilyResponseDTO> findAllFamilies() {
        log.debug("Consultando familias registradas.");
        List<Family> familyList = familyRepository.findAll();
        return familyMapper.toFamilyResponseList(familyList);
    }

    /**
     * Busca una familia por ID.
     */
    @Override
    @Transactional(readOnly = true)
    public FamilyResponseDTO findFamilyById(Long id) {
        Family existingFamily = familyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra la familia con Id: " + id));

        log.debug("Consultando familia por Id: {}", id);
        return familyMapper.toFamilyResponseDTO(existingFamily);
    }

    /**
     * Busca una familia por nombre.
     */
    @Override
    @Transactional(readOnly = true)
    public FamilyResponseDTO findFamilyByName(String name) {
        Family existingFamily = familyRepository.findByName(name)
                .orElseThrow(() -> new ResourceNotFoundException("No se encuentra la familia con nombre: " + name));
        log.debug("Consultando familia por nombre: {}", name);
        return familyMapper.toFamilyResponseDTO(existingFamily);
    }

    /**
     * Elimina una familia por su ID.
     */
    @Override
    @Transactional
    public void deleteFamilyById(Long id) {
        if (!familyRepository.existsById(id)) {
            log.warn("Intentando eliminar familia inesxistente con Id: {}", id);
            throw new ResourceNotFoundException("No se encuentra la familia con Id: " + id);
        }

        familyRepository.deleteById(id);
        log.info("Eliminado registro de familia con Id: {} con éxito", id);
    }
}
