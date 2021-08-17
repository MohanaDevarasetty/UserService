package com.farmersbyte.enterprise.usermodule.user;

import com.farmersbyte.enterprise.usermodule.dto.BaseDto;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@ApiModel("UserSignup")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupDto extends BaseDto {
    private String userName;
    private String email;
    private String password;
    private String phone;
    private String countryCode;
    private Integer mfaUserId;
    private String mfaToken;
}
