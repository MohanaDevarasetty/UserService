package com.farmersbyte.enterprise.usermodule.user.family;

import com.farmersbyte.enterprise.usermodule.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface FamilyMapper extends BaseMapper {
    Family dtoToEntity(FamilyDto dto);
    FamilyDto entityToDto(Family entity);
}
