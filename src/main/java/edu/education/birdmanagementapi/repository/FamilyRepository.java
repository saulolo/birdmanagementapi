package edu.education.birdmanagementapi.repository;

import edu.education.birdmanagementapi.domain.entity.Family;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FamilyRepository extends JpaRepository<Family, Long> {

    /**
     * Busca una familia por su nombre.
     *
     * @param name nombre de la familia
     * @return resultado opcional
     */
    Optional<Family> findByName(String name);

    /**
     * Verifica si existe una familia con el nombre dado.
     *
     * @param name nombre de la familia
     * @return true si existe
     */
    boolean existsByName(String name);
}
