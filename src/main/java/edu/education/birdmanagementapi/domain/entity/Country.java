package edu.education.birdmanagementapi.domain.entity;

import edu.education.birdmanagementapi.domain.enums.RoleEnum;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "countries")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Country implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_country")
    Long idCountry;

    @Column(name = "name", nullable = false, length = 50)
    String name;

    @Column(name = "iso_code", nullable = false, length = 3, unique = true)
    String isoCode;

    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    Set<Sighting> sightings = new HashSet<>();

}
