package tech.xserver.adminserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.xserver.adminserver.entity.MovieEntity;
import tech.xserver.adminserver.entity.UserEntity;
import tech.xserver.adminserver.entity.VotesEntity;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Repository
public interface VotesRepo extends JpaRepository<VotesEntity, Long> {
    Optional<VotesEntity> findByUserIdAndMovieId(Long userId, Long movieId);

    Long countByMovieId(Long movieId);

    void deleteByMovieId(Long id);

    void deleteByUserId(Long id);
}
