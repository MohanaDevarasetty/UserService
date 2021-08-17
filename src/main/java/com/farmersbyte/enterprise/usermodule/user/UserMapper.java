package com.farmersbyte.enterprise.usermodule.user;

import com.farmersbyte.enterprise.usermodule.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper {
    User dtoToEntity(UserDto dto);
    UserDto entityToDto(User entity);
}
