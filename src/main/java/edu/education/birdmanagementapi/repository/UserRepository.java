package edu.education.birdmanagementapi.repository;

import edu.education.birdmanagementapi.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Busca un usuario por su nombre de usuario (username).
     *
     * @param username El nombre de usuario a buscar.
     * @return Un Optional que contiene el objeto User si se encuentra, o un Optional vaco.
     */
    Optional<User> findByUsername(String username);

}
