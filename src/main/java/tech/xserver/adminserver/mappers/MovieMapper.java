package tech.xserver.adminserver.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
//import tech.xserver.adminserver.DTO.CertificationDto;
import tech.xserver.adminserver.DTO.MovieDto;
import tech.xserver.adminserver.entity.CertificationEntity;
import tech.xserver.adminserver.entity.MovieEntity;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface MovieMapper {
    @Mapping(target = "name", source = "title")
    @Mapping(target = "original_name", source = "original_title")
    @Mapping(target = "first_air_date", source = "release_date")
    MovieDto mapToMovieDto(MovieEntity movie);

    @Mapping(target = "title", expression = "java(movieDto.getName() != null ? movieDto.getName() : movieDto.getTitle())")
    @Mapping(target = "release_date", expression = "java(movieDto.getFirst_air_date() != null ? movieDto.getFirst_air_date() : movieDto.getRelease_date())")
    @Mapping(target = "original_title", expression = "java(movieDto.getOriginal_name() != null ? movieDto.getOriginal_name() : movieDto.getOriginal_title())")
    MovieEntity mapToMovie(MovieDto movieDto);
}
