package tech.xserver.adminserver.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "views", indexes = {
        @Index(name = "views_user_id_index", columnList = "userId", unique = false),
        @Index(name = "views_movie_id_index", columnList = "movieId", unique = false)
})
public class ViewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "history_id_seq")
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
    private String type;

}
