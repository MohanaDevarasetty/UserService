package com.farmersbyte.enterprise.usermodule.policy;

import com.farmersbyte.enterprise.usermodule.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PolicyMapper extends BaseMapper {
    Policy dtoToEntity(PolicyDto dto);
    PolicyDto entityToDto(Policy entity);
}
