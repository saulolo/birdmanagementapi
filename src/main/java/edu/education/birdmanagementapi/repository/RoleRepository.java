package edu.education.birdmanagementapi.repository;

import edu.education.birdmanagementapi.domain.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    /**
     * Busca roles cuyo nombre esté en la lista proporcionada.
     *
     * @param roleNames Nombres de roles a buscar.
     * @return Lista de Role que coinciden.
     */
    List<Role> findByRoleEnumIn(List<String> roleNames);

}
