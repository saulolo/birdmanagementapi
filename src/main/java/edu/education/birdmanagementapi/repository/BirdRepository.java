package edu.education.birdmanagementapi.repository;

import edu.education.birdmanagementapi.domain.entity.Bird;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BirdRepository extends JpaRepository<Bird, Long> {

    /**
     * Verifica si existe un ave con el mismo nombre común o científico.
     *
     * @param commonName nombre común
     * @param scientificName nombre científico
     * @return true si existe
     */
    boolean existsByCommonNameOrScientificName(String commonName, String scientificName);
}
