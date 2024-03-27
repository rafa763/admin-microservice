package tech.xserver.adminserver.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tech.xserver.adminserver.DTO.MovieDto;
import tech.xserver.adminserver.DTO.ViewsDto;
import tech.xserver.adminserver.DTO.VotesDto;
import tech.xserver.adminserver.entity.MovieEntity;
import tech.xserver.adminserver.entity.ViewsEntity;
import tech.xserver.adminserver.entity.VotesEntity;

@Mapper(componentModel = "spring")
public interface VotesMapper {
     VotesEntity mapToEntity(VotesDto votesDto);
     VotesDto mapToDto(VotesEntity votesEntity);

}
