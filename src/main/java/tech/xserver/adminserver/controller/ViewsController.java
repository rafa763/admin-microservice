package tech.xserver.adminserver.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.xserver.adminserver.DTO.ViewsDto;
import tech.xserver.adminserver.config.ViewsConfig;
import tech.xserver.adminserver.filter.Claims;
import tech.xserver.adminserver.mappers.ViewsMapper;
import tech.xserver.adminserver.service.ViewsService;

@Slf4j
@RestController
@RequestMapping("/views")
public class ViewsController {
    private final ViewsService viewsService;
    private final ViewsMapper viewsMapper;
    private final ViewsConfig viewsConfig;
    private final Claims claims;

    public ViewsController(ViewsService viewsService, ViewsMapper viewsMapper, ViewsConfig viewsConfig, Claims claims) {
        this.viewsService = viewsService;
        this.viewsMapper = viewsMapper;
        this.viewsConfig = viewsConfig;
        this.claims = claims;
    }

    @PostMapping
    public void addView(@RequestAttribute("accessToken") String accessToken, @RequestBody ViewsDto viewsDto) {
        log.info("Adding view: " + viewsDto);
        Long userId = claims.getClaims(accessToken, "uid");
        viewsDto.setUserId(userId);
        viewsConfig.sendMessage(viewsMapper.mapToEntity(viewsDto));
//        viewsService.addView(viewsMapper.mapToEntity(viewsDto));
    }

    @GetMapping("/total/{movieId}")
    public ResponseEntity<Long> getMovieViews(@PathVariable Long movieId) {
        return ResponseEntity.ok(viewsService.getMovieViews(movieId));
    }

    @GetMapping()
    public ResponseEntity<?> getUserViews(@RequestAttribute("accessToken") String accessToken) {
        Long userId = claims.getClaims(accessToken, "uid");
        log.info("Getting views for user: " + userId);
        return ResponseEntity.ok(viewsService.getUserViews(userId));
    }

}
