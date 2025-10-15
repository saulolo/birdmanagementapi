package edu.education.birdmanagementapi.repository;

import edu.education.birdmanagementapi.domain.entity.Habitat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabitatRepository extends JpaRepository<Habitat, Long> {

    /**
     * Busca un hábitat por su nombre.
     *
     * @param name nombre del hábitat
     * @return resultado opcional
     */
    Optional<Habitat> findByName(String name);

    /**
     * Verifica si existe un hábitat con el nombre dado.
     *
     * @param name nombre del hábitat
     * @return true si existe
     */
    boolean existsByName(String name);
}
