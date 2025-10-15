package edu.education.birdmanagementapi.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@ToString(exclude = {"family", "habitats"})
@Table(name = "birds")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Bird implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_bird")
    Long idBird;

    @Column(name = "common_name", nullable = false, length = 50)
    String commonName;

    @Column(name = "scientific_name", nullable = false, length = 50, unique = true)
    String scientificName;

    @Column(name = "conservation_model", nullable = false)
    String conservationModel;

    @Column(name = "notes")
    String notes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_family")
    @JsonIgnoreProperties({"birds"})
    Family family;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "birds_habitats", joinColumns = @JoinColumn(name = "id_bird"), inverseJoinColumns = @JoinColumn(name = "id_habitat"))
    @Builder.Default
    @JsonIgnoreProperties({"birds"})
    Set<Habitat> habitats = new HashSet<>();

    @Column(name = "created_at", nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    LocalDateTime updatedAt;

    @PrePersist
    private void beforePersisting() {
        this.createdAt = LocalDateTime.now();
    }

    @PreUpdate
    private void modificationDate() {
        this.updatedAt = LocalDateTime.now();
    }

}
