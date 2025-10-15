package edu.education.birdmanagementapi.service.interfaces;

import edu.education.birdmanagementapi.domain.dto.request.BirdRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.BirdResponseDTO;

import java.util.List;

public interface IBirdService {

    BirdResponseDTO createBird(BirdRequestDTO birdRequestDTO);

    BirdResponseDTO updateBird(Long id, BirdRequestDTO birdRequestDTO);

    BirdResponseDTO findBirdById(Long id);

    List<BirdResponseDTO> findAllBirds();

    void deleteBirdById(Long id);

    List<BirdResponseDTO> searchByName(String name);

}
