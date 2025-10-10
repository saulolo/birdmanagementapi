package edu.education.birdmanagementapi.domain.service.interfaces;

import edu.education.birdmanagementapi.domain.dto.request.FamilyRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.FamilyResponseDTO;

import java.util.List;

public interface IFamilyService {

    FamilyResponseDTO createFamily(FamilyRequestDTO familyRequestDTO);

    FamilyResponseDTO updateFamily(Long id, FamilyRequestDTO familyRequestDTO);

    List<FamilyResponseDTO> findAllFamilies();

    FamilyResponseDTO findFamilyById(Long id);

    FamilyResponseDTO findFamilyByName(String name);

    void deleteFamilyById(Long id);

}
