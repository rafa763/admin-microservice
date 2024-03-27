package tech.xserver.adminserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tech.xserver.adminserver.DTO.MovieDto;
import tech.xserver.adminserver.DTO.PaginatedResponse;
import tech.xserver.adminserver.DTO.WrapperDto;
import tech.xserver.adminserver.client.Client;
import tech.xserver.adminserver.entity.CertificationEntity;
import tech.xserver.adminserver.entity.MovieEntity;
import tech.xserver.adminserver.mappers.MovieMapper;
import tech.xserver.adminserver.mappers.WrapperMapper;
import tech.xserver.adminserver.repo.MovieRepo;
import tech.xserver.adminserver.repo.ViewsRepo;
import tech.xserver.adminserver.repo.VotesRepo;
import tech.xserver.adminserver.service.MovieService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepo movieRepository;
    private final Client client;
    private final WrapperMapper wrapperMapper;
    private final MovieMapper movieMapper;
    private final ViewsRepo viewsRepo;
    private final VotesRepo votesRepo;
    public MovieServiceImpl(MovieRepo movieRepository, Client client, WrapperMapper wrapperMapper, MovieMapper movieMapper, ViewsRepo viewsRepo, VotesRepo votesRepo) {
        this.movieRepository = movieRepository;
        this.client = client;
        this.wrapperMapper = wrapperMapper;
        this.movieMapper = movieMapper;
        this.viewsRepo = viewsRepo;
        this.votesRepo = votesRepo;
    }

    @Override
    public MovieEntity createMovie(MovieEntity movieEntity) {
        log.info("Creating movie"+ movieEntity.toString());
        return movieRepository.save(movieEntity);
    }

    @Override
    public Optional<MovieEntity> getMovie(Long id, boolean filterAdult) {
        if (id > 90000000000L) {
            MovieDto movieDto = (MovieDto) client.getMovie(id - 90000000000L).getBody();
            if (movieDto != null) {
                movieDto.setType("External");
            }
            if (filterAdult && movieDto != null && movieDto.isAdult()) {
                return Optional.empty();
            }
            return Optional.of(movieMapper.mapToMovie(movieDto));
        }
        if (filterAdult) {
            return movieRepository.findById(id).filter(movieEntity -> !movieEntity.isAdult());
        }
        return movieRepository.findById(id);
    }

    @Override
    public List<MovieEntity> getMovies(Integer pageNo, Integer pageSize, boolean filterAdult) {
        Pageable paging = PageRequest.of(pageNo, pageSize );

        Page<MovieEntity> moviesPage = movieRepository.findAll(paging);
        List<MovieEntity> moviesList = moviesPage.getContent();

        PaginatedResponse<MovieEntity> response = new PaginatedResponse<>();
        response.setContent(moviesList);
        response.setTotalPages(moviesPage.getTotalPages() - 1);

        Integer movieListSize = moviesList.size();
        Integer totalPageNumber = moviesPage.getTotalPages() - 1;

        // if the requested page has content equal to the page then complement from local db
//        if ((pageNo <= totalPageNumber) && movieListSize.equals(pageSize)) {
//            log.info("All within local db");
//            return moviesList;
//        }
        log.info("PageNo: " + pageNo + " TotalPageNumber: " + totalPageNumber + " MovieListSize: " + movieListSize + " PageSize: " + pageSize);
       // if the requested page has content but less than the page size compliment from feign
        if ((pageNo <= totalPageNumber) && movieListSize < pageSize) {
            log.info("Partial from db and rest from feign");
            // get the rest from feign
            if (filterAdult) {
                log.info("Adult filter---------------");
                return Stream.concat(
                        moviesList.stream().filter(movie -> !movie.isAdult()),
                        wrapperMapper.mapToMovies((WrapperDto) client.getTrending(((totalPageNumber - pageNo) + 1 ), pageSize).getBody())
                                .stream()
                                .filter(movie -> !movie.isAdult())
                                .limit(pageSize - movieListSize)
                ).collect(Collectors.toList());
            }
            List<MovieEntity> fr = wrapperMapper.mapToMovies((WrapperDto) client.getTrending(((totalPageNumber - pageNo) + 1 ), pageSize).getBody()).stream().limit(pageSize - movieListSize).toList();
            log.info("No filter--------");
            return Stream.concat(moviesList.stream(), fr.stream()).collect(Collectors.toList());
        }
       // if the requested page doesn't have any content then get all from feign
        if (pageNo >= totalPageNumber && movieListSize.equals(0)) {
            log.info("All from feign");
            ResponseEntity<?> c = client.getTrending((pageNo - totalPageNumber) + 1, pageSize);
            if (filterAdult) {
                return wrapperMapper.mapToMovies((WrapperDto) c.getBody()).stream().filter(movie -> !movie.isAdult()).collect(Collectors.toList());
            }
            return wrapperMapper.mapToMovies((WrapperDto) c.getBody());
        }
        if (filterAdult) {
            return moviesList.stream().filter(movie -> !movie.isAdult()).collect(Collectors.toList());
        }
        return moviesList;
    }

    @Override
    public Optional<MovieEntity> updateMovie(Long id, MovieEntity movieEntity) {
        if (movieRepository.existsById(id)) {
            movieEntity.setId(id);
            return Optional.of(movieRepository.save(movieEntity));
        }
        return Optional.empty();
    }

    @Override
    public void deleteMovie(Long id) {
        movieRepository.deleteById(id);
        viewsRepo.deleteByMovieId(id);
    }

}
