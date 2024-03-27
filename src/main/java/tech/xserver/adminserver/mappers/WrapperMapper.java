package tech.xserver.adminserver.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import tech.xserver.adminserver.DTO.MovieDto;
import tech.xserver.adminserver.DTO.WrapperDto;
import tech.xserver.adminserver.entity.CertificationEntity;
import tech.xserver.adminserver.entity.MovieEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface WrapperMapper {
    default List<MovieEntity> mapToMovies(WrapperDto wrapperDto) {
        if (wrapperDto == null || wrapperDto.getResults() == null) {
            return Collections.emptyList();
        }

        List<MovieDto> movieDtos = wrapperDto.getResults();
        return movieDtos.stream()
                .map(this::mapToMovieEntity)
                .collect(Collectors.toList());
    }

//    @Mapping(target = "id", ignore = true)
    @Mapping(target = "id", expression = "java(90000000000L+ movieDto.getId())")
    @Mapping(target = "title", expression = "java(movieDto.getName() != null ? movieDto.getName() : movieDto.getTitle())")
    @Mapping(target="release_date", expression = "java(movieDto.getFirst_air_date() != null ? movieDto.getFirst_air_date() : movieDto.getRelease_date())")
    @Mapping(target = "original_title", expression = "java(movieDto.getOriginal_name() != null ? movieDto.getOriginal_name() : movieDto.getOriginal_title())")
    @Mapping(target = "type", constant = "External")
    MovieEntity mapToMovieEntity(MovieDto movieDto);

    CertificationEntity map(String value);
}