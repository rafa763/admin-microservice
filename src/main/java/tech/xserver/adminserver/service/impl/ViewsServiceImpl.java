package tech.xserver.adminserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tech.xserver.adminserver.DTO.ViewsDto;
import tech.xserver.adminserver.entity.MovieEntity;
import tech.xserver.adminserver.entity.UserEntity;
import tech.xserver.adminserver.entity.ViewsEntity;
import tech.xserver.adminserver.repo.MovieRepo;
import tech.xserver.adminserver.repo.UserRepo;
import tech.xserver.adminserver.repo.ViewsRepo;
import tech.xserver.adminserver.service.MovieService;
import tech.xserver.adminserver.service.UserService;
import tech.xserver.adminserver.service.ViewsService;
import tech.xserver.adminserver.service.VotesService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ViewsServiceImpl implements ViewsService {
    private final ViewsRepo viewsRepo;
    private final UserService userService;
    private final UserRepo userRepo;
    private final MovieService movieService;
    private final MovieRepo movieRepo;

    public ViewsServiceImpl(ViewsRepo viewsRepo, UserRepo userRepo, MovieRepo movieRepo, UserService userService, MovieService movieService) {
        this.viewsRepo = viewsRepo;
        this.userRepo = userRepo;
        this.movieRepo = movieRepo;
        this.userService = userService;
        this.movieService = movieService;
    }
    @Override
    @KafkaListener(topics = "viewsTopic", groupId = "gid", containerFactory = "factory")
    public void addView(ViewsEntity viewsEntity) {
        //check if the user has already viewed the movie
//        Optional<UserEntity> user = userRepo.findById(ViewsDto.getUserId());
//        Optional<UserEntity> user = userService.getUserById(.getUserId());
        Optional<MovieEntity> movie = movieRepo.findById(viewsEntity.getMovieId());
//        Optional<MovieEntity> movie = movieService.getMovie(ViewsDto.getMovieId());
        if(viewsRepo.findByUserIdAndMovieId(viewsEntity.getUserId(), viewsEntity.getMovieId()).isPresent()) {
            log.info("User has already viewed the movie");
            return;
        }
        viewsEntity.setCreated_at(Instant.now().toString());
        if (movie.isPresent() && movie.get().getType().equals("Internal")) {
            viewsEntity.setType("Internal");
        } else {
            viewsEntity.setType("External");
        }
        viewsRepo.save(viewsEntity);
        log.info("View added successfully");
    }

    @Override
    public Long getMovieViews(Long movieId) {
        return viewsRepo.countByMovieId(movieId);
    }

    @Override
    public List<MovieEntity> getUserViews(Long userId, boolean filterAdult) {
//        log.info("views" + viewsRepo.findByUserId(userId));
//        return viewsRepo.findByUserId(userId);
        List<ViewsEntity> views = viewsRepo.findByUserId(userId);
        List<MovieEntity> movies = new ArrayList<>();
        for (ViewsEntity view : views) {
            if (view.getMovieId() != null && view.getType().equals("Internal")) {
                movieService.getMovie(view.getMovieId(), false).ifPresent(movies::add);
            } else {
                log.info("External movie");
                Optional<MovieEntity> movie = movieService.getMovie(view.getMovieId(), filterAdult);
                if (movie.isPresent()) {
                    movie.get().setId(90000000000L + movie.get().getId());
                    movies.add(movie.get());
                }
            }
        }
        return movies;
    }
}
