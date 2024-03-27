package tech.xserver.adminserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tech.xserver.adminserver.DTO.VotesDto;
import tech.xserver.adminserver.entity.MovieEntity;
import tech.xserver.adminserver.entity.UserEntity;
import tech.xserver.adminserver.entity.VotesEntity;
import tech.xserver.adminserver.repo.MovieRepo;
import tech.xserver.adminserver.repo.UserRepo;
import tech.xserver.adminserver.repo.ViewsRepo;
import tech.xserver.adminserver.repo.VotesRepo;
import tech.xserver.adminserver.service.MovieService;
import tech.xserver.adminserver.service.ViewsService;
import tech.xserver.adminserver.service.VotesService;

import java.time.Instant;
import java.util.Optional;

@Slf4j
@Service
public class VotesServiceImpl implements VotesService {
    private final VotesRepo votesRepo;
    private final MovieService movieService;
    private final UserRepo userRepo;
    private final MovieRepo movieRepo;
    private final ViewsRepo viewsRepo;

    public VotesServiceImpl(VotesRepo votesRepo, MovieService movieService, UserRepo userRepo, MovieRepo movieRepo, ViewsRepo viewsRepo) {
        this.votesRepo = votesRepo;
        this.viewsRepo = viewsRepo;
        this.movieService = movieService;
        this.movieRepo = movieRepo;
        this.userRepo = userRepo;
    }
    @Override
    public void addVote(VotesEntity votesEntity) {
        //check if the user has already voted the movie
//        Optional<UserEntity> user = userRepo.findById(votesEntity.getUserId());
        Optional<MovieEntity> movie = movieRepo.findById(votesEntity.getMovieId());

        if(votesRepo.findByUserIdAndMovieId(votesEntity.getUserId(), votesEntity.getMovieId()).isPresent()) {
            log.info("User has already voted the movie");
            return;
        }
        // set the created_at field to the current time (Instant.now().toString()
        votesEntity.setCreated_at(Instant.now().toString());
        votesRepo.save(votesEntity);
        getMovieVotes(votesEntity.getMovieId());
//        log.info("Movie type: " + movie.get().getType());
//        if (movie.get().getType().equals("Internal") && (getMovieVotes(votesEntity.getMovieId()) > 50f)) {
//            movie.get().setAdult(true);
//        } else {
//            movie.get().setAdult(false);
//        }
//        log.info("Vote added successfully");
    }

    @Override
    public boolean isVoted(Long userId, Long movieId){
        boolean x = votesRepo.findByUserIdAndMovieId(userId, movieId).isPresent();
        log.info("User has voted: " + x);
        return x;
    }
    @Override
    public float getMovieVotes(Long movieId) {
        Long votes = votesRepo.countByMovieId(movieId);
        Long views = viewsRepo.countByMovieId(movieId);
        // update the movie rating and increment the vote count
        if(views == 0){
            return 0;
        }
        float percentage = Math.round((votes/(float)views) * 100);
        movieRepo.findById(movieId).ifPresent(movieEntity -> {
                        boolean adult = percentage > 50;
                        movieEntity.setVote_average(percentage);
                        movieEntity.setVote_count(movieEntity.getVote_count() + 1);
                        movieEntity.setAdult(adult);
                        movieRepo.save(movieEntity);
                    });
        Optional<MovieEntity> movieEntity = movieService.getMovie(movieId, false);
        if(movieEntity.isEmpty()){
            return 0;
        }

//        log.info("Votes: " + votes + " Views: " + views);
        return percentage;
    }
}
