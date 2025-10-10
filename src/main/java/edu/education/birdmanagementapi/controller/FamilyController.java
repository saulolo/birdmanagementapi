package edu.education.birdmanagementapi.controller;

import edu.education.birdmanagementapi.domain.dto.request.FamilyRequestDTO;
import edu.education.birdmanagementapi.domain.dto.response.FamilyResponseDTO;
import edu.education.birdmanagementapi.service.interfaces.IFamilyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/families")
@RequiredArgsConstructor
public class FamilyController {

    private final IFamilyService familyService;


    @PostMapping("/create")
    public ResponseEntity<FamilyResponseDTO> createFamily(@Valid @RequestBody FamilyRequestDTO familyRequestDTO) {
        try {
            FamilyResponseDTO familyResponseDTO = familyService.createFamily(familyRequestDTO);
            return new ResponseEntity<>(familyResponseDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<FamilyResponseDTO> updateFamily(@PathVariable Long id, @Valid @RequestBody FamilyRequestDTO familyRequestDTO) {
        FamilyResponseDTO updated = familyService.updateFamily(id, familyRequestDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<FamilyResponseDTO>> findAllFamilies() {
        List<FamilyResponseDTO> familyResponseDTOList = familyService.findAllFamilies();
        return ResponseEntity.ok(familyResponseDTOList);
    }

    @GetMapping("/findById/{id}")
    public ResponseEntity<FamilyResponseDTO> findFamilyById(@PathVariable Long id) {
        FamilyResponseDTO familyResponseDTO = familyService.findFamilyById(id);
        return ResponseEntity.ok(familyResponseDTO);
    }

    @GetMapping("/findByName")
    public ResponseEntity<FamilyResponseDTO> findFamilyByName(@RequestParam String name) {
        FamilyResponseDTO familyResponseDTO = familyService.findFamilyByName(name);
        return ResponseEntity.ok(familyResponseDTO);

    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<Void> deleteFamilyById(@PathVariable Long id) {
        familyService.deleteFamilyById(id);
        return ResponseEntity.noContent().build();
    }

}
