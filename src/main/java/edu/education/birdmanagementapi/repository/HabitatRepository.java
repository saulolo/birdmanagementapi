package edu.education.birdmanagementapi.repository;

import edu.education.birdmanagementapi.domain.entity.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabitatRepository extends JpaRepository<Habitat, Long> {

    Optional<Habitat> findByName(String name);

    boolean existsByName(String name);
}
