package tech.xserver.adminserver.service;

import tech.xserver.adminserver.DTO.VotesDto;
import tech.xserver.adminserver.entity.MovieEntity;
import tech.xserver.adminserver.entity.UserEntity;
import tech.xserver.adminserver.entity.ViewsEntity;
import tech.xserver.adminserver.entity.VotesEntity;

import java.util.Optional;

public interface VotesService {
    void addVote(VotesEntity votesEntity);
    public boolean isVoted(Long userId, Long movieId);
    float getMovieVotes(Long movieId);
}
