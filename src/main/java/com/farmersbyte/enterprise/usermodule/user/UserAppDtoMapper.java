package com.farmersbyte.enterprise.usermodule.user;

import com.farmersbyte.enterprise.usermodule.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAppDtoMapper extends BaseMapper {
    AppUserDto userToAppUserDto(User entity);

}
