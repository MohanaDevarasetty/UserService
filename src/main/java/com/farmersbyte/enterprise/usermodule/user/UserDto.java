package com.farmersbyte.enterprise.usermodule.user;

import com.farmersbyte.enterprise.usermodule.dto.BaseDto;
import com.farmersbyte.enterprise.usermodule.dto.UserTokenDto;
import com.farmersbyte.enterprise.usermodule.model.Address;
import com.farmersbyte.enterprise.usermodule.model.TwilioModel;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@ApiModel("User")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends BaseDto {
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
    @Field private String pwdHash;
    @Field private UserStatus userStatus;
    @Field private List<UUID> roles;
    @Field private List<String> profilePictures;
    @Field private Integer loginAttempts;
    @Field private Date passwordModifiedAt;
    @Field private UUID passwordModifiedBy;
    @Field private String gender;
    @Field private String notes;
    @Field private String countryCode;
    @Field private List<Address> addressList;
    @Field private TwilioModel twilio;
    @Field private UserTokenDto userTokenDetails;
}
