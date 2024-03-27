package tech.xserver.adminserver.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import tech.xserver.adminserver.DTO.MovieDto;
import tech.xserver.adminserver.DTO.ViewsDto;
import tech.xserver.adminserver.entity.MovieEntity;
import tech.xserver.adminserver.entity.ViewsEntity;

@Mapper(componentModel = "spring")
public interface ViewsMapper {
     ViewsEntity mapToEntity(ViewsDto viewsDto);
     ViewsDto mapToDto(ViewsEntity viewsEntity);
}
