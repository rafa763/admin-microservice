package tech.xserver.adminserver.service;

import tech.xserver.adminserver.DTO.ViewsDto;
import tech.xserver.adminserver.entity.MovieEntity;
import tech.xserver.adminserver.entity.UserEntity;
import tech.xserver.adminserver.entity.ViewsEntity;

import java.util.List;

public interface ViewsService {
    public void addView(ViewsEntity viewsEntity);
    public Long getMovieViews(Long movieId);
    public List<MovieEntity> getUserViews(Long userId);

}
