package tech.xserver.adminserver.service;

import org.springframework.data.domain.Pageable;
import tech.xserver.adminserver.DTO.PaginatedResponse;
import tech.xserver.adminserver.entity.MovieEntity;

import java.util.List;
import java.util.Optional;

public interface MovieService {
    MovieEntity createMovie(MovieEntity movieEntity);
    Optional<MovieEntity> getMovie(Long id, boolean filterAdult);
    List<MovieEntity> getMovies(Integer page, Integer pageSize, boolean filterAdult);
    Optional<MovieEntity> updateMovie(Long id, MovieEntity movieEntity);
    void deleteMovie(Long id);
}
