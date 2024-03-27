package tech.xserver.adminserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tech.xserver.adminserver.DTO.VotesDto;
import tech.xserver.adminserver.filter.Claims;
import tech.xserver.adminserver.mappers.VotesMapper;
import tech.xserver.adminserver.service.VotesService;

@Slf4j
@RestController
@RequestMapping("/votes")
public class VotesController {
    private final VotesService votesService;
    private final VotesMapper votesMapper;
    private final Claims claims;

    public VotesController(VotesService votesService, VotesMapper votesMapper, Claims claims) {
        this.votesService = votesService;
        this.votesMapper = votesMapper;
        this.claims = claims;
    }

    @PostMapping()
    public void addVote(@RequestAttribute("accessToken") String accessToken,@RequestBody VotesDto votesDto) {
        Long userId = claims.getClaims(accessToken, "uid");
        votesDto.setUserId(userId);
        votesService.addVote(votesMapper.mapToEntity(votesDto));
    }

    @GetMapping("/check/{movieId}")
    public boolean checkIfUserVoted(@RequestAttribute("accessToken") String accessToken, @PathVariable Long movieId) {
        Long userId = claims.getClaims(accessToken, "uid");
        return votesService.isVoted(userId, movieId);
    }

    @GetMapping("/{movieId}")
    public float getMovieVotes(@PathVariable Long movieId) {
        return votesService.getMovieVotes(movieId);
    }

}
