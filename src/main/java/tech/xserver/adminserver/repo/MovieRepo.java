package tech.xserver.adminserver.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tech.xserver.adminserver.entity.MovieEntity;

@Repository
public interface MovieRepo extends JpaRepository<MovieEntity, Long> {
}
