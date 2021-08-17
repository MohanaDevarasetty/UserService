package com.farmersbyte.enterprise.usermodule.user;

import com.farmersbyte.enterprise.usermodule.dto.BaseDto;
import com.farmersbyte.enterprise.usermodule.model.Address;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.UUID;

@ApiModel("User")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUserDto extends BaseDto {
    @Field private String name;
    @Field private String userName;
    @Field private String givenName;
    @Field private String familyName;
    @Field private String displayName;
    @Field private UserType userType;
    @Field private String preferredLanguage;
    @Field private String locale;
    @Field private String email;
    @Field private String phone;
    @Field private UserStatus userStatus;
    @Field private List<UUID> roles;
    @Field private List<String> profilePictures;
    @Field private String gender;
    @Field private String notes;
    @Field private String countryCode;
    @Field private List<Address> addressList;
}
