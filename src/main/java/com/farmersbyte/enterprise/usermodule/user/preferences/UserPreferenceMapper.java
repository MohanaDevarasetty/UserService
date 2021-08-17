package com.farmersbyte.enterprise.usermodule.user.preferences;

import com.farmersbyte.enterprise.usermodule.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPreferenceMapper extends BaseMapper {
    UserPreference dtoToEntity(UserPreferenceDto dto);
    UserPreferenceDto entityToDto(UserPreference entity);
}
