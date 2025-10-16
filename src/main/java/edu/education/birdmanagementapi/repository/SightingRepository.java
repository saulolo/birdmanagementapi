package edu.education.birdmanagementapi.repository;

import edu.education.birdmanagementapi.domain.entity.Sighting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SightingRepository extends JpaRepository<Sighting, Long> {


    List<Sighting> findByBirdIdBird(Long IdBird);

    List<Sighting> findByUserIdUser(Long IdUser);

    List<Sighting> findByCountryIdCountry(Long IdCountry);

    List<Sighting> findByHabitatIdHabitat(Long idHabitat);

    List<Sighting> findBySightingDateTimeBetween(LocalDateTime from, LocalDateTime to);
}
