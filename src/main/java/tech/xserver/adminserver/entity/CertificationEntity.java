package tech.xserver.adminserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "certifications")
public class CertificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "certification_id_seq")
    private Long id;
    private String certification;
    private String meaning;
    private int level;

//    @OneToMany(mappedBy = "certification", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    private List<MovieEntity> movies;
}
