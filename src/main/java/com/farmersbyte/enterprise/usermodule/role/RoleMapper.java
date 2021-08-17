package com.farmersbyte.enterprise.usermodule.role;

import com.farmersbyte.enterprise.usermodule.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper extends BaseMapper {
    Role dtoToEntity(RoleDto dto);
    RoleDto entityToDto(Role entity);
}
