package tech.xserver.adminserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.xserver.adminserver.entity.MovieEntity;
import tech.xserver.adminserver.entity.UserEntity;
import tech.xserver.adminserver.entity.ViewsEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface ViewsRepo extends JpaRepository<ViewsEntity, Long>{
    Long countByMovieId(Long movieId);
    List<ViewsEntity> findByUserId(Long userId);
    Optional<ViewsEntity> findByUserIdAndMovieId(Long userId, Long movieId);

    void deleteByMovieId(Long id);

    void deleteByUserId(Long id);
}
