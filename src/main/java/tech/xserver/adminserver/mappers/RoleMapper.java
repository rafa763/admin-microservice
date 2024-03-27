package tech.xserver.adminserver.mappers;

import org.mapstruct.Mapper;
import tech.xserver.adminserver.DTO.RoleDto;
import tech.xserver.adminserver.entity.RoleEntity;

import java.util.Optional;

@Mapper(componentModel = "spring")
public interface RoleMapper {
//    Optional<RoleEntity> mapToRoleEntity(RoleDto roleDto);
//    Optional<RoleDto> mapToRoleDto(RoleEntity roleEntity);


    default Optional<RoleEntity> mapToRoleEntity(Optional<RoleDto> roleDtoOptional) {
        return roleDtoOptional.map(this::mapToRoleEntity);
    }

    default Optional<RoleDto> mapToRoleDto(Optional<RoleEntity> roleEntityOptional) {
        return roleEntityOptional.map(this::mapToRoleDto);
    }

    RoleEntity mapToRoleEntity(RoleDto roleDto);
    RoleDto mapToRoleDto(RoleEntity roleEntity);
}
