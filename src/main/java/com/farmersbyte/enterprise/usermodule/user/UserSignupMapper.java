package com.farmersbyte.enterprise.usermodule.user;

import com.farmersbyte.enterprise.usermodule.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserSignupMapper extends BaseMapper {
    UserDto signUpUserDtoToUserDto(UserSignupDto userSignupDto);
    UserSignupDto userDtoToUserSignupDto(UserDto userDto);
}
