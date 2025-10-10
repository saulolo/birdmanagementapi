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
@Table(name = "Sightings")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Sighting implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sighting")
    Long idSighting;

    @Column(name = "latitude", nullable = false)
    Double latitude;

    @Column(name = "longitude", nullable = false)
    Double longitude;

    @Column(name = "sighting_date_time", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime sightingDateTime;

    @Column(name = "notes")
    String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_bird", nullable = false)
    Bird bird;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_user", nullable = false)
    User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_habitat", nullable = false)
    Habitat habitat;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_country", nullable = false)
    Country country;

    @Column(name = "created_date", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime createdDate;

    @PrePersist
    private void beforePersisting() {
        this.createdDate = LocalDateTime.now();
    }


}
