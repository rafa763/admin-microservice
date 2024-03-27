package tech.xserver.adminserver.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import tech.xserver.adminserver.DTO.RegisterDto;
import tech.xserver.adminserver.DTO.SafeUserDto;
import tech.xserver.adminserver.DTO.UserDto;
import tech.xserver.adminserver.entity.RoleEntity;
import tech.xserver.adminserver.entity.UserEntity;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface UserMapper {

//    UserEntity mapToUserEntity(UserDto userDto);
//    UserDto mapToUserDto(UserEntity userEntity);
//
//    UserEntity mapToUserEntityFromRegister(RegisterDto registerDto);
//
//    @Mapping(target = "role", source = "roles")
//    SafeUserDto mapToSafeUserDto(UserEntity userEntity);
//
    default String map(Collection<RoleEntity> roles) {
        return roles.stream()
                .map(RoleEntity::getName)
                .collect(Collectors.joining(","));
    }
    UserDto mapToUserDto(UserEntity userEntity);
    UserEntity mapToUserEntity(UserDto userDto);
    @Mapping(target = "role", source = "roles")
    SafeUserDto mapToSafeUserDto(UserEntity userEntity);

    UserEntity mapToUserEntityFromRegister(RegisterDto registerDto);

}
