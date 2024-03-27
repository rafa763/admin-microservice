package tech.xserver.adminserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class MovieEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "movie_id_seq")
    private Long id;
    private String title;
    private String original_language;
    private String original_title;
    private String overview;
    private String poster_path;
    private String media_type;
    private double popularity;
    private String release_date;
    private boolean video;
    private double vote_average;
    private int vote_count;
    private boolean adult;
    private String backdrop_path;
    private String type;

//    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<ViewsEntity> views;
//
//    @OneToMany(mappedBy = "movie", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JsonIgnore
//    private List<VotesEntity> votes;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "certification_id", referencedColumnName = "id")
//    private CertificationEntity certification;
}

