package edu.education.birdmanagementapi.repository;

import edu.education.birdmanagementapi.domain.entity.Bird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;

@Repository
public interface BirdRepository extends JpaRepository<Bird, Long> {

    Set<Bird> findByCommonNameContainingIgnoreCaseOrScientificNameContainingIgnoreCase(String name1, String name2);

    Optional<Bird> findByCommonNameIgnoreCase(String commonName);

    Optional<Bird> findByScientificNameIgnoreCase(String scientificName);

    boolean existsByCommonNameOrScientificName(String commonName, String scientificName);
}
