package edu.education.birdmanagementapi.repository;

import edu.education.birdmanagementapi.domain.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

    Optional<Family> findByName(String name);

    boolean existsByName(String name);
}
