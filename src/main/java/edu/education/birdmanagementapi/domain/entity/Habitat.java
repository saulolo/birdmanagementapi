package edu.education.birdmanagementapi.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "habitats")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Habitat implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_habitat")
    Long idHabitat;

    @Column(name = "name", nullable = false, length = 50)
    String name;

    @Column(name = "description")
    String description;

    @ManyToMany(mappedBy = "habitats", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    Set<Bird> birds = new HashSet<>();

    @OneToMany(mappedBy = "habitat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @Builder.Default
    Set<Sighting> sightings = new HashSet<>();

}
