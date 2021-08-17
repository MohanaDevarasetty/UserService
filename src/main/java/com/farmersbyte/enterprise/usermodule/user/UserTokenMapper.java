package com.farmersbyte.enterprise.usermodule.user;

import com.farmersbyte.enterprise.usermodule.dto.UserTokenDto;
import com.farmersbyte.enterprise.usermodule.mapper.BaseMapper;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserTokenMapper extends BaseMapper {
    User tokenToUser(UserTokenDto userTokenDto);
    UserTokenDto userToToken(User user);
}
