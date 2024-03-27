package tech.xserver.adminserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "votes", indexes = {
        @Index(name = "votes_user_id_index", columnList = "userId", unique = false),
        @Index(name = "votes_movie_id_index", columnList = "movieId", unique = false)
})
public class VotesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "votes_id_seq")
    private Long id;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "userId")
//    private UserEntity user;
    private Long userId;

//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "movieId")
//    private MovieEntity movie;
    private Long movieId;

    private String created_at;
}