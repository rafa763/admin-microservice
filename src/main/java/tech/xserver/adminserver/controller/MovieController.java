package tech.xserver.adminserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.xserver.adminserver.DTO.MovieDto;
import tech.xserver.adminserver.DTO.PaginatedResponse;
import tech.xserver.adminserver.entity.MovieEntity;
import tech.xserver.adminserver.filter.Claims;
import tech.xserver.adminserver.mappers.MovieMapper;
import tech.xserver.adminserver.service.MovieService;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(path = "movies")
public class MovieController {
    private final MovieService movieService;
    private final MovieMapper movieMapper;
    private final Claims claims;


    public MovieController(MovieService movieService, MovieMapper movieMapper, Claims claims) {
        this.movieService = movieService;
        this.movieMapper = movieMapper;
        this.claims = claims;
    }

    @GetMapping()
    public List<MovieEntity> listMovies(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "20") Integer size,
            @RequestAttribute("accessToken") String token
    ) {
        log.info("page" + page);
        boolean filterAdult = false;
        String r = claims.getRoles(token).get(0);
        if (r.equalsIgnoreCase("CHILD")) {
            filterAdult = true;
        }
        List<MovieEntity> movies = movieService.getMovies(page - 1, size, filterAdult);
//        log.info("movies: " + movies);
        return movies;
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<MovieDto> listMovie(@PathVariable Long id, @RequestAttribute("accessToken") String token){
        boolean filterAdult = false;
        String r = claims.getRoles(token).get(0);
        if (r.equalsIgnoreCase("CHILD")) {
            filterAdult = true;
        }
        Optional<MovieEntity> foundMovie = movieService.getMovie(id, filterAdult);
        return foundMovie.map(movieEntity -> {
            MovieDto movieDto = movieMapper.mapToMovieDto(movieEntity);
            return new ResponseEntity<>(movieDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping()
    public ResponseEntity<MovieDto> createMovie(@RequestBody MovieDto movieDto) {
        log.info("movieDto: " + movieDto);
        MovieEntity movieEntity = movieMapper.mapToMovie(movieDto);
        log.info("movieEntity: " + movieEntity);
        movieEntity.setType("Internal");
        MovieEntity savedMovieEntity = movieService.createMovie(movieEntity);
        return new ResponseEntity<>(movieMapper.mapToMovieDto(savedMovieEntity), HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}")
    public ResponseEntity<MovieDto> updateMovie(@PathVariable Long id, @RequestBody MovieDto movieDto) {
        Optional<MovieEntity> updatedMovie = movieService.updateMovie(id, movieMapper.mapToMovie(movieDto));
        return updatedMovie.map(movieEntity -> {
            MovieDto updatedMovieDto = movieMapper.mapToMovieDto(movieEntity);
            return new ResponseEntity<>(updatedMovieDto, HttpStatus.OK);
        }).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<MovieDto> deleteMovie(@PathVariable Long id) {
        movieService.deleteMovie(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
